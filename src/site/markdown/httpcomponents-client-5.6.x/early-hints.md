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

RFC 8297 – 103 Early Hints (async)
==================================

HttpClient 5.6 adds async support for **RFC 8297 – 103 Early Hints** via a
callback interface:

- `EarlyHintsListener` – functional interface invoked for each `103` response.
- `HttpAsyncClientBuilder#setEarlyHintsListener(...)` – registers a global
  listener on the async client.
- `EarlyHintsAsyncExec` – internal exec-chain handler that taps informational
  responses without changing final response processing.

This allows applications to observe Early Hints (for example, `Link` headers
with `rel=preload` / `rel=preconnect`) while leaving the normal response path
untouched.

When is the listener called?
----------------------------

- Called **once per `103`** informational response.
- Never called for the final non-1xx response.
- May be called **multiple times** for a single request if the server sends
  multiple `103` responses.
- Runs on the I/O thread, so implementations must be **fast and non-blocking**;
  offload heavier work to your own executor.

API surface
-----------

The public entry point is the listener:

```java
@FunctionalInterface
public interface EarlyHintsListener {

    void onEarlyHints(HttpResponse hints, HttpContext context)
            throws HttpException, IOException;
}
```

You register it on the async client builder:

```java
final EarlyHintsListener listener = (hints, ctx) -> {
    // Only 103s will be forwarded here
    System.out.println("Early Hints: " + hints.getCode());
    for (final Header h : hints.getHeaders("Link")) {
        System.out.println("  Link: " + h.getValue());
    }
};

final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
        .setEarlyHintsListener(listener)
        .build();
```

If the listener is `null` no extra handler is added to the exec chain.

Minimal usage example
---------------------

This is a cut-down version of the `AsyncClientEarlyHintsEndToEnd` example. It
shows how to register the listener and consume the final response as usual.

```java
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.EarlyHintsListener;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.http.Header;

public class AsyncClientEarlyHintsExample {

    public static void main(final String[] args) throws Exception {
        final EarlyHintsListener hintsListener = (hints, ctx) -> {
            System.out.println("[client] 103 Early Hints:");
            for (final Header h : hints.getHeaders("Link")) {
                System.out.println("  " + h.getValue());
            }
        };

        try (final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
                .setEarlyHintsListener(hintsListener)
                .build()) {

            client.start();

            final SimpleHttpRequest req = SimpleRequestBuilder
                    .get("https://example.com/with-early-hints")
                    .build();

            final Future<SimpleHttpResponse> f = client.execute(req, null);
            final SimpleHttpResponse resp = f.get(10, TimeUnit.SECONDS);

            System.out.println("[client] final: " + resp.getCode() + " " + resp.getReasonPhrase());
            System.out.println("[client] body: " + resp.getBodyText());
        }
    }
}
```

Notes:

- The **final response** (e.g. `200 OK`) is handled by the normal async flow.
- Early Hints are purely **side-band** notifications.

End-to-end example
------------------

The full end-to-end demo lives in the examples package:

- [AsyncClientEarlyHintsEndToEnd](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientEarlyHintsEndToEnd.java)

This example:

1. Starts a local async HTTP server that:
   - sends one `103 Early Hints` with two `Link` headers, then
   - completes the exchange with `200 OK` and a short body.
2. Builds an async client with `setEarlyHintsListener(...)`.
3. Prints:
   - all `Link` headers from `103` responses, and
   - the final status and body for the main response.

This is the canonical reference for wiring Early Hints support end-to-end.

Interaction with the exec chain
-------------------------------

When `setEarlyHintsListener(...)` is used, the builder inserts an internal
`EarlyHintsAsyncExec` handler **before** the protocol handler:

```java
if (earlyHintsListener != null) {
    addExecInterceptorBefore(
            ChainElement.PROTOCOL.name(),
            "early-hints",
            new EarlyHintsAsyncExec(earlyHintsListener));
}
```

This handler:

- intercepts `handleInformationResponse(...)` callbacks,
- forwards each `103` to the listener,
- delegates all other informational responses and the final response unchanged.

If you never register a listener, no extra handler is added and the async exec
chain behaves as before.

Summary
-------

- Use `EarlyHintsListener` + `setEarlyHintsListener(...)` to observe `103` responses.
- Normal response processing is unaffected; Early Hints are side-band.
- Keep listener logic lightweight; bounce heavy work to your own executor.
- Use `AsyncClientEarlyHintsEndToEnd` as a reference for testing and wiring.
