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

HttpClient Examples (Observation)
===========================

- [Observability quick start (classic)](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-observation/src/test/java/org/apache/hc/client5/http/observation/example/ClassicWithMetricConfigDemo.java)

  This example demonstrates enabling Micrometer observations and metrics on a classic client using `HttpClientObservationSupport.enable(...)`, with a custom `MetricConfig` (prefix, SLO, percentiles) and a Prometheus scrape.

- [Observability quick start (async)](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-observation/src/test/java/org/apache/hc/client5/http/observation/example/AsyncMetricsDemo.java)

  This example demonstrates enabling observations and metrics on the async client, firing concurrent requests to show the `http.client.inflight{kind=async}` gauge and printing a Prometheus scrape.

- [Connection pool gauges (classic)](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-observation/src/test/java/org/apache/hc/client5/http/observation/example/PoolGaugesDemo.java)

  This example demonstrates exposing connection pool gauges (`<prefix>.pool.leased`, `<prefix>.pool.available`, `<prefix>.pool.pending`) by binding a pooling manager via `ConnPoolMeters.bindTo(...)`.

- [DNS metrics (classic)](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-observation/src/test/java/org/apache/hc/client5/http/observation/example/DnsMetricsDemo.java)

  This example demonstrates wrapping the system DNS resolver with `MeteredDnsResolver` to record resolution timers and counters for `resolve` and `resolveCanonicalHostname` with `result` and optional `host` tags.

- [TLS handshake metrics (async)](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-observation/src/test/java/org/apache/hc/client5/http/observation/example/TlsMetricsDemo.java)

  This example demonstrates recording TLS handshake latency and outcome counters by wrapping an async `TlsStrategy` with `MeteredTlsStrategy` on the connection manager (`<prefix>.tls.handshake`, `<prefix>.tls.handshakes`).

- [Span sampling & I/O counters](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-observation/src/test/java/org/apache/hc/client5/http/observation/example/SpanSamplingDemo.java)

  This example demonstrates using `ObservingOptions.spanSampling` to skip observations/metrics for selected URIs and recording request/response byte counters (`<prefix>.request.bytes`, `<prefix>.response.bytes`).

- [Tag levels (LOW vs EXTENDED)](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-observation/src/test/java/org/apache/hc/client5/http/observation/example/TagLevelDemo.java)

  This example demonstrates minimal vs extended metric tagging (adding `protocol` and `target` when `EXTENDED`) using different metric prefixes to avoid label-set clashes.

- [Tracing + metrics (classic, OpenTelemetry bridge)](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-observation/src/test/java/org/apache/hc/client5/http/observation/example/TracingAndMetricsDemo.java)

  This example demonstrates wiring Micrometer `ObservationRegistry` to OpenTelemetry (in-memory exporter) while recording HttpClient timers/counters, printing an exported span and a Prometheus scrape.

