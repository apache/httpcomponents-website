<!--
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.
-->

# Connection pool metrics

The optional `httpclient5-observation` module provides Micrometer metrics for connection pools.
For dependencies and request metrics, see the [observability guide](../httpcomponents-client-5.6.x/observation.md).
The connection manager wrappers in this guide require HttpClient 5.7 or later.
Use the same version for `httpclient5` and `httpclient5-observation`.

A lease is a request to obtain a connection.
A gauge reports a current value, such as the number of pending leases.
Lease timers measure the wait for a connection, not the duration of an HTTP request.

With `ObservingOptions.MetricSet.CONN_POOL` enabled, the API provides two separate forms of measurement:

| Metrics | How to enable them |
|---------|--------------------|
| `<prefix>.pool.leased`, `<prefix>.pool.available`, `<prefix>.pool.pending` gauges | Attach a pooling connection manager to the builder, then call `HttpClientObservationSupport.enable(...)`. |
| `<prefix>.pool.lease` timer and `<prefix>.pool.leases` counter | Wrap the manager with `meteredConnectionManager(...)` or `meteredAsyncConnectionManager(...)`, then attach the returned manager to the builder. |

`<prefix>` comes from `MetricConfig.prefix` and defaults to `http.client`.
The lease timer and counter have a `result` tag.
`ObservingOptions.TagLevel.EXTENDED` also adds a `target` tag with the target hostname.
Lease meters appear after lease outcomes occur, rather than when you configure the builder.

`enable(...)` does not create, wrap, or replace a connection manager.
It binds gauges to the manager that is attached when you call it.
If no manager is attached, it does not bind pool gauges.
The default manager that `build()` creates later does not receive pool instrumentation.

## Classic client

Create and configure the pool, wrap it once, and attach the returned manager before you call `enable(...)`.
Use the same registry, observation options, and metric configuration for both calls.
The following example enables request metrics, pool gauges, and lease metrics without tracing.

```java
import java.util.EnumSet;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.observation.HttpClientObservationSupport;
import org.apache.hc.client5.http.observation.MetricConfig;
import org.apache.hc.client5.http.observation.ObservingOptions;

final var meters = new SimpleMeterRegistry();
final var options = ObservingOptions.builder()
        .metrics(EnumSet.of(
                ObservingOptions.MetricSet.BASIC,
                ObservingOptions.MetricSet.CONN_POOL))
        .build();
final var metricConfig = MetricConfig.DEFAULT;
final var pool = PoolingHttpClientConnectionManagerBuilder.create().build();
final var meteredManager = HttpClientObservationSupport.meteredConnectionManager(
        pool, meters, options, metricConfig);
final var builder = HttpClients.custom().setConnectionManager(meteredManager);

HttpClientObservationSupport.enable(
        builder, null, meters, options, metricConfig);
final var client = builder.build();
```

Keep the client and registry for the lifetime of the application component that owns them.
Close the client when that component stops, then close the registry if no other component uses it.
By default, the client closes its connection manager, and the wrapper forwards that close to the pool.

For an async client, use `PoolingAsyncClientConnectionManagerBuilder`, `meteredAsyncConnectionManager(...)`, and `HttpAsyncClients.custom()` in the same order.
Start the async client before you execute requests.
The same explicit wrapping and configuration order apply to caching client builders.

## Library builder callbacks

A library can expose a callback for configuring its HttpClient builder without exposing its connection manager.
In that callback, you can supply your own metered manager through `setConnectionManager(...)`.
You do not need to change HttpClient or the library to record lease metrics.

The following generic callback accepts an async builder and returns the same builder after configuration.
It uses the `meters`, `options`, and `metricConfig` from the classic example.
Use this callback instead of creating the classic client.

```java
import java.util.function.UnaryOperator;

import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;

final UnaryOperator<HttpAsyncClientBuilder> configureHttpClient =
        clientBuilder -> {
            final var pool = PoolingAsyncClientConnectionManagerBuilder.create()
                    .build();
            final var meteredManager =
                    HttpClientObservationSupport.meteredAsyncConnectionManager(
                            pool, meters, options, metricConfig);
            clientBuilder.setConnectionManager(meteredManager);
            HttpClientObservationSupport.enable(
                    clientBuilder, null, meters, options, metricConfig);
            return clientBuilder;
        };
```

Register equivalent configuration through the library's callback API.
`UnaryOperator` describes the callback's input and output here, not a required callback type for the library.
If the library attaches a manager before it invokes the callback, this configuration replaces that manager.

Before you build the new pool, configure its required pool limits and connection timeouts.
Apply any required TLS strategy and DNS resolver to this pool.
Settings on the original manager do not transfer to the replacement.
Library callbacks that configure the original manager do not configure this replacement.
Keep other client configuration, such as authentication, in the same callback.

Do not build or start the HttpClient inside the callback.
Let the library build, start, and close its client according to its lifecycle contract.
If the library changes the connection manager after the callback, use a configuration hook that retains your metered manager.

## Configuration and ownership

Wrap a manager only once.
The helpers do not detect an existing metered wrapper, so wrapping again records each lease more than once.
If `CONN_POOL` is disabled, the helpers return the original manager without lease instrumentation.

Use a pooling manager for pool gauges.
The wrappers implement `ConnPoolControl` even when their underlying manager does not support pooling.
With a non-pooling manager, their pool statistics are zero and do not describe an actual pool.

Do not replace the manager after you call `enable(...)`.
The gauges remain bound to the manager that was attached at that time.
If you need only gauges, attach the unwrapped pool before `enable(...)` and omit the wrapper.

Wrapping does not change the builder's `setConnectionManagerShared(...)` setting.
If you share a manager across clients, set `setConnectionManagerShared(true)` and close the manager after all clients stop.
If several pools share a registry, give each pool a distinct metric prefix or common tags.
These identifiers keep each pool's gauges separate.
