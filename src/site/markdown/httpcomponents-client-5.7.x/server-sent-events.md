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

Server-Sent Events (SSE) client
===============================

Starting with HttpClient 5.6 there is an optional **Server-Sent Events (SSE)**
module, `httpclient5-sse`, for consuming long-lived event streams over HTTP/1.1
and HTTP/2 using the async transport.

Unlike WebSockets, SSE is strictly one-way (server → client) and reuses plain
HTTP with the media type `text/event-stream`. The SSE client in HttpClient
focuses on:

- correct parsing of the SSE wire format (fields `data:`, `event:`, `id:`,
  `retry:` and comments),
- robust reconnection with **backoff strategies** and `Last-Event-ID`
  propagation, and
- a simple listener API for application code.

Module and dependency
---------------------

SSE support lives in a separate module:

```xml
<dependency>
  <groupId>org.apache.httpcomponents.client5</groupId>
  <artifactId>httpclient5-sse</artifactId>
  <version>${httpclient5.version}</version>
</dependency>
```

This module depends on the async client (`httpclient5`) and reuses the existing
I/O reactor, connection pooling and TLS strategies.

Core types
----------

All SSE APIs are in the package `org.apache.hc.client5.http.sse`.

- **`EventSource`**

  Represents a single SSE connection (the client-side equivalent of a browser
  `EventSource` object). It exposes:

  - `start()` / `cancel()` – idempotent lifecycle methods,
  - `lastEventId()` / `setLastEventId(String)` – tracking and sending
    `Last-Event-ID`, and
  - simple header management (`setHeader`, `removeHeader`, `getHeaders`).

  Implementations are provided by this module; you normally obtain an
  `EventSource` via a factory such as `SseExecutor`.

- **`EventSourceListener`**

  Callback interface that receives parsed SSE events and lifecycle signals:
  open, event, comment, retry hints, errors and closed. The listener is wired
  when the `EventSource` is created and must be fast / non-blocking.

- **`BackoffStrategy` and `BackoffStrategies`**

  Strategy interface controlling **reconnect delays** after disconnects:

  ```java
  long nextDelayMs(int attempt, long previousDelayMs, Long serverRetryHintMs);

  default boolean shouldReconnect(int attempt,
                                  long previousDelayMs,
                                  Long serverRetryHintMs) {
      return true;
  }
  ```

  Implementations in `BackoffStrategies` cover common policies such as constant,
  exponential and jittered backoff. The strategy can honour server hints from
  `retry:` fields or HTTP `Retry-After` headers, or ignore them.

- **`SseExecutor`**

  High-level entry point that ties an async `CloseableHttpAsyncClient` to one or
  more SSE streams. It is responsible for:

  - opening the initial SSE request,
  - driving reconnects according to a `BackoffStrategy`,
  - feeding parsed events to the `EventSourceListener`, and
  - updating `Last-Event-ID` as events are received.

  See the Javadoc for exact factory methods and configuration options.

Basic usage – subscribing to an SSE stream
------------------------------------------

A typical flow is:

1. Create or reuse a `CloseableHttpAsyncClient`.
2. Build an `SseExecutor` bound to that client.
3. Implement an `EventSourceListener` that handles events.
4. Open an `EventSource` for the SSE endpoint and call `start()`.

Simplified sketch:

```java
// 1) Async client
final CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
client.start();

// 2) Listener that processes incoming events
final EventSourceListener listener = new EventSourceListener() {
    @Override
    public void onEvent(final SseEvent event) {
        System.out.println("event: " + event.getEventName());
        System.out.println("id   : " + event.getId());
        System.out.println("data : " + event.getData());
    }

    @Override
    public void onError(final Throwable cause) {
        cause.printStackTrace(System.err);
    }

    @Override
    public void onClosed() {
        System.out.println("SSE stream closed");
    }

    // see Javadoc for the full callback set (open, comment, retry hint, etc.).
};

// 3) Backoff policy (e.g. bounded exponential with jitter)
 BackoffStrategy backoff = BackoffStrategies.exponentialJitter(...);

// 4) Create an EventSource via SseExecutor (see actual factory signature in code)
 final SseExecutor sse = ...
 final EventSource source = sse.open(
         URI.create("https://example.com/events"),
         listener,
         backoff);

// 5) Start streaming
 source.start();
```

> The exact factory methods and builder options for `SseExecutor` may evolve;
> always refer to the Javadoc of `org.apache.hc.client5.http.sse.SseExecutor`
> for up-to-date signatures. The examples below show complete, compilable
> programs.

Last-Event-ID and resume
------------------------

The SSE client tracks the last non-null event id it observes:

- Incoming `id:` fields update the **current Last-Event-ID**.
- On reconnect, the `EventSource` can send this value as the `Last-Event-ID`
  request header so the server can resume the stream.

You can also seed or clear this value manually:

```java
// Seed from a persisted offset
source.setLastEventId("42");

// Inspect the current offset
final String last = source.lastEventId();
```

Backoff and reconnect behaviour
-------------------------------

Reconnect logic is fully driven by the configured `BackoffStrategy`:

- `nextDelayMs(...)` computes the delay before the next attempt.
- `shouldReconnect(...)` can opt out completely (for example, a strategy that
  never reconnects, or stops after N attempts).

The strategy receives:

- the attempt count (1-based),
- the previously used delay, and
- an optional server hint (`Long serverRetryHintMs`) derived from:

  - SSE `retry:` fields, or
  - HTTP `Retry-After` when the server returns a non-2xx response.

This lets applications balance aggression vs. politeness and apply custom
policies (for example, backoff only on certain status codes, or cap the maximum
delay regardless of server hints).

Examples
--------

Concrete examples live in the `httpclient5-sse` module under
`org.apache.hc.client5.http.sse.example`:

- [ClientSseExample](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-sse/src/test/java/org/apache/hc/client5/http/sse/example/ClientSseExample.java)

  Interactive SSE client demo that connects to an SSE endpoint, logs events,
  and shows how to wire `SseExecutor`, `EventSource`, `EventSourceListener` and
  a `BackoffStrategy` together.

- [SsePerfClient](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-sse/src/test/java/org/apache/hc/client5/http/sse/example/perf/SsePerfClient.java)

  Synthetic load / throughput client for SSE streams. Useful as a reference
  when benchmarking different backoff policies or server implementations.

- [SsePerfServer](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-sse/src/test/java/org/apache/hc/client5/http/sse/example/perf/SsePerfServer.java)

  Minimal HTTP server that emits SSE events at a configurable rate, intended to
  be used together with `SsePerfClient` for end-to-end testing.

Further reading
---------------

- SSE wire format and semantics are defined in the HTML Living Standard
  (“Server-sent events” section).
- For the full API, see the Javadoc of:

  - `org.apache.hc.client5.http.sse.EventSource`
  - `org.apache.hc.client5.http.sse.EventSourceListener`
  - `org.apache.hc.client5.http.sse.BackoffStrategy`
  - `org.apache.hc.client5.http.sse.BackoffStrategies`
  - `org.apache.hc.client5.http.sse.SseExecutor`
