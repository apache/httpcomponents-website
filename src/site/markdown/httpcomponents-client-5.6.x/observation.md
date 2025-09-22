<!--
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->

# Observability (Micrometer / OpenTelemetry)

Since **5.6**, HttpClient ships an **optional** module, `httpclient5-observation`, that plugs straight into
**Micrometer** (metrics + observations) and can bridge to **OpenTelemetry** (traces). The goal is simple:
give you **drop-in visibility** of latency, throughput, I/O, connection-pool health, DNS, and TLS—without
rewriting application code or coupling the core client to any monitoring stack.

What you get out of the box:

- Request latency **timers** and response **counters** for classic and async clients
- Request/response **I/O byte** counters (opt-in URI tagging to control cardinality)
- Connection pool **gauges** (leased / available / pending) for classic and async managers
- **DNS** resolution timers/counters (resolve + canonical lookups)
- **TLS** handshake timers/counters (ok / error / cancel outcomes)
- Micrometer **Observation** interceptors that you can bridge to OpenTelemetry for end-to-end traces

How it fits:

- **Opt-in by design.** Nothing changes unless you add Micrometer to the classpath and call the provided
  `enable(...)` helpers. The observation module is separate; core HttpClient has no hard dependency.
- **Classic, Async, and Caching** are all supported. For caching builders, interceptors are inserted **after**
  the caching stage so metrics reflect the effective exchange.
- **Low friction.** A single call wires timers/counters and, if you want them, pool gauges, DNS/TLS meters,
  and observation spans. You keep full control via two small configs:
    - `ObservingOptions` – choose metric groups (BASIC, IO, CONN_POOL, TLS, DNS), tag level (`LOW` for minimal,
      `EXTENDED` for richer tags), per-URI sampling, and a tag customizer hook.
    - `MetricConfig` – set the metric name prefix (`http.client` by default), SLO bucket(s), percentiles, optional
      per-URI I/O tagging, and common tags to stamp on every meter.
- **Safe by default.** Per-URI I/O tagging is **off** to avoid high cardinality. Turn it on only where you know
  it’s useful (e.g., controlled path templates).
- **Thread-safe & lightweight.** The helpers are stateless. Interceptors register meters lazily, and pool gauges
  bind once to the connection manager (no reflection).
- **Your registry, your rules.** Use any Micrometer registry (Prometheus, OTLP, Cloud vendor). If you also enable
  `ObservationRegistry` with Micrometer Tracing, you can export **spans** via `micrometer-tracing-bridge-otel`.

Typical flow:

1. Add `httpclient5-observation` and whichever Micrometer bits you actually use (all Micrometer deps can stay `optional`).
2. Build your client as usual; call `HttpClientObservationSupport.enable(...)` on the builder
   (classic/async/caching variants provided).
3. (Optional) Bind **pool gauges** after you’ve attached a pooling connection manager.
4. (Optional) Wrap your DNS resolver with `MeteredDnsResolver` and your TLS strategy with `MeteredTlsStrategy`
   if you want those timing/outcome meters as well.
5. (Optional) Bridge the `ObservationRegistry` to OpenTelemetry to get client spans alongside metrics.

Practical notes:

- **Naming:** all meters are prefixed by `MetricConfig.prefix` (default `http.client`). Examples:
  `http.client.request` (timer), `http.client.response` (counter), `http.client.inflight{kind=classic|async}` (gauge),
  `http.client.pool.*` (gauges), `http.client.dns.*`, `http.client.tls.*`.
- **Tagging:** minimal set is `method` and `status`. `EXTENDED` adds `protocol` and `target`; DNS/TLS add `host`/`sni`.
  You can append `commonTags` globally and mutate per-request tags with a `TagCustomizer`.
- **Overhead:** the fast path short-circuits when `spanSampling` says “skip” (no timer/counter work). Publishing
  percentiles and fine-grained SLOs costs more—configure only what you need.
- **Compatibility:** works with existing client code; you instrument the **builder**, not call sites. Suitable for
  libraries: you can expose an instrumented `CloseableHttpClient`/`CloseableHttpAsyncClient` to callers without
  leaking Micrometer types into their APIs.



Nothing is enabled unless you add Micrometer and call the opt-in helpers.

> **Status:** Introduced in 5.6 (`@since 5.6`). Artifacts are optional; core modules do not depend on Micrometer/OTel.

---

## Dependency

**Maven**
```xml
<dependency>
  <groupId>org.apache.httpcomponents.client5</groupId>
  <artifactId>httpclient5-observation</artifactId>
  <version>5.6-alpha1</version>
</dependency>

## Dependency

**Maven**

```xml
<!-- Required: the optional observability module itself -->
<dependency>
  <groupId>org.apache.httpcomponents.client5</groupId>
  <artifactId>httpclient5-observation</artifactId>
  <version>5.6-alpha1</version>
</dependency>

<!-- Optional: add only if you actually use Micrometer metrics / observations / Prometheus / tracing -->

<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-core</artifactId>
  <version>${micrometer.version}</version>
  <optional>true</optional>
</dependency>

<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-observation</artifactId>
  <version>${micrometer.version}</version>
  <optional>true</optional>
</dependency>

<!-- Choose one or more registries you use (e.g., Prometheus) -->
<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-registry-prometheus</artifactId>
  <version>${micrometer.version}</version>
  <optional>true</optional>
</dependency>

<!-- Optional Micrometer→OpenTelemetry bridge for tracing spans -->
<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-tracing-bridge-otel</artifactId>
  <version>${micrometer.tracing.version}</version>
  <optional>true</optional>
</dependency>

