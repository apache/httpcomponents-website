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

Connection management
=====================

HttpClient reuses persistent connections to reduce latency and resource usage.
Connection management is handled by dedicated connection managers for classic
(blocking) and async I/O.

Connection managers
-------------------

The main connection managers are:

* `PoolingHttpClientConnectionManager` (classic I/O)
* `PoolingAsyncClientConnectionManager` (async I/O)

Both managers:

* Maintain per-route and total connection limits.
* Reuse persistent connections when possible.
* Enforce connection time-to-live (TTL) and idle expiry.
* Provide APIs to close idle or expired connections explicitly.

For a detailed description of pooling policies see
[Connection pooling](connection-pooling.md).

Per-route and total limits
--------------------------

Connection limits are expressed in terms of:

* **Per-route limit** – maximum number of connections for a single `HttpRoute`.
* **Total limit** – maximum number of connections across all routes.

Classic:

```java
final PoolingHttpClientConnectionManager cm =
        PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(50)
                .build();
```

Async:

```java
final PoolingAsyncClientConnectionManager cm =
        PoolingAsyncClientConnectionManagerBuilder.create()
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(50)
                .build();
```

These limits should reflect expected concurrency and the capacity of the remote
services and network.

Connection lifetime and idle timeout
------------------------------------

Connection lifetime (TTL) limits how long a persistent connection can be reused
regardless of its keep-alive semantics. Idle timeout limits how long a
connection may stay idle in the pool before being considered stale.

Both are configured via `ConnectionConfig`:

```java
final ConnectionConfig connectionConfig = ConnectionConfig.custom()
        .setTimeToLive(TimeValue.ofMinutes(5))
        .setIdleTimeout(TimeValue.ofMinutes(1))
        .build();
```

Classic:

```java
final PoolingHttpClientConnectionManager cm =
        PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connectionConfig)
                .build();
```

Async:

```java
final PoolingAsyncClientConnectionManager cm =
        PoolingAsyncClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connectionConfig)
                .build();
```

Connections that exceed their TTL or idle timeout are discarded when leased or
by explicit eviction.

Evicting idle and expired connections
-------------------------------------

Applications are encouraged to run a background task to evict idle and expired
connections from the pool.

Classic:

```java
cm.closeExpired();
cm.closeIdle(TimeValue.ofMinutes(1));
```

Async:

```java
asyncManager.closeExpired();
asyncManager.closeIdle(TimeValue.ofMinutes(1));
```

This keeps the pool clean in long-lived applications and reduces the probability
of using dead connections after long periods of inactivity.

Validation after inactivity
---------------------------

Persistent connections kept idle for a long time can become half-closed by
intermediaries (stale connections). HttpClient can validate such connections
before re-use based on a configurable inactivity threshold:

```java
final ConnectionConfig connectionConfig = ConnectionConfig.custom()
        .setValidateAfterInactivity(TimeValue.ofSeconds(2))
        .build();
```

When the inactivity period of a connection exceeds this threshold, the manager
will perform a lightweight `isStale()` check before leasing it.

* A small positive value (e.g. 1–2 seconds) is often a good compromise.
* A non-positive value disables validation and may increase the risk of
  encountering stale connections on the first request after a long idle period.

Classic and async managers both honour `validateAfterInactivity` when leasing
connections.

Concurrency policies and off-lock disposal
------------------------------------------

Connection managers support different pool concurrency policies via
`PoolConcurrencyPolicy`:

* `STRICT` – per-route queues and strong fairness (default).
* `LAX` – relaxed fairness in favour of higher throughput.
* `OFFLOCK` – experimental route-segmented policy reducing contention on shared
  structures.

See [Connection pooling](connection-pooling.md) for details and examples.

Blocking connection pools can optionally move slow graceful closes off the hot
pool locks:

```java
final PoolingHttpClientConnectionManager cm =
        PoolingHttpClientConnectionManagerBuilder.create()
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setOffLockDisposalEnabled(true)
                .build();
```

With off-lock disposal enabled, removal from the pool happens immediately, while
the actual graceful close is performed outside the core pool synchronisation.

Practical recommendations
-------------------------

* Start with the default `STRICT` policy and conservative limits.
* Size `maxConnTotal` and `maxConnPerRoute` based on expected concurrency and
  backend capacity; monitor and adjust.
* Always run periodic eviction of idle and expired connections in long-lived
  applications.
* Enable `validateAfterInactivity` with a small positive value if connections
  are kept idle for extended periods.
* Consider `LAX` or `OFFLOCK` (experimental) for highly concurrent workloads
  where reducing contention is more important than strict per-route fairness.
