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

WebSocket client
================

Starting with HttpClient 5.7 there is an optional **WebSocket** module,
`httpclient5-websocket`, for full-duplex messaging over a single connection
using the async transport.

Unlike SSE, WebSocket is bidirectional: once the handshake completes, either
peer may send text or binary messages at any time. The client supports both
transports defined for WebSocket:

- **HTTP/1.1 Upgrade** (RFC 6455), and
- **HTTP/2 Extended CONNECT** (RFC 8441), multiplexing the session over a
  single HTTP/2 stream,

with optional **per-message compression** (`permessage-deflate`, RFC 7692),
enabled by default.

Module and dependency
---------------------

WebSocket support lives in a separate module, `httpclient5-websocket`. For the
current release coordinates, see the [download](download.html) page.

It builds on the async I/O reactor and reuses the existing HTTP/1.1 and HTTP/2
machinery and TLS strategies.

Core types
----------

The client-facing API is in `org.apache.hc.client5.http.websocket.api` and
`org.apache.hc.client5.http.websocket.client`.

- **`WebSocketClients` / `WebSocketClientBuilder` / `CloseableWebSocketClient`**

  Entry points that create and hold a client. The simplest form is
  `WebSocketClients.createDefault()`; for tuning use
  `WebSocketClients.custom()` (or `WebSocketClientBuilder.create()`), configure
  the builder, and call `build()`. All of them return a
  `CloseableWebSocketClient`:

  ```java
  public abstract void start();
  public final CompletableFuture<WebSocket> connect(URI uri, WebSocketListener listener);
  public final CompletableFuture<WebSocket> connect(URI uri, WebSocketListener listener, WebSocketClientConfig cfg);
  public final CompletableFuture<WebSocket> connect(URI uri, WebSocketListener listener, WebSocketClientConfig cfg, HttpContext context);
  public abstract void initiateShutdown();
  public abstract void awaitShutdown(TimeValue waitTime) throws InterruptedException;
  ```

  The client must be started with `start()` before connecting. The URI scheme
  must be `ws` or `wss`. A single client can open many connections and can use
  either transport. `CloseableWebSocketClient` is `Closeable`, so it also works
  with try-with-resources.

- **`WebSocket`**

  A live, thread-safe connection. Outbound methods return `boolean` –
  `true` when the message was accepted for sending, `false` when the connection
  is closing or closed:

  ```java
  boolean isOpen();
  boolean sendText(CharSequence data, boolean finalFragment);
  boolean sendBinary(ByteBuffer data, boolean finalFragment);
  boolean sendTextBatch(List<CharSequence> fragments, boolean finalFragment);
  boolean sendBinaryBatch(List<ByteBuffer> fragments, boolean finalFragment);
  boolean ping(ByteBuffer data);   // payload <= 125 bytes, may be null
  boolean pong(ByteBuffer data);   // payload <= 125 bytes, may be null
  long queueSize();                // queued outbound bytes, -1 if unavailable
  CompletableFuture<Void> close(int statusCode, String reason);
  ```

- **`WebSocketListener`**

  Callback interface for inbound events. Every method is a `default` no-op, so
  you override only what you need:

  ```java
  default void onOpen(WebSocket webSocket) {}
  default void onText(CharBuffer data, boolean last) {}
  default void onBinary(ByteBuffer data, boolean last) {}
  default void onPing(ByteBuffer data) {}
  default void onPong(ByteBuffer data) {}
  default void onClose(int statusCode, String reason) {}
  default void onError(Throwable cause) {}
  ```

  The buffers handed to `onText` / `onBinary` are valid **only for the duration
  of the callback** – copy the contents if you need to retain them. The
  callback must be fast and non-blocking. The `last` flag is reserved for future
  streaming and is currently always `true`.

- **`WebSocketClientConfig`**

  Immutable configuration built with `WebSocketClientConfig.custom()`. It
  controls the handshake and framing (subprotocols, connect timeout, maximum
  message size, `permessage-deflate` options, and HTTP/2).

- **`WebSocketCloseStatus`**

  Convenience enum of the RFC 6455 close codes (`NORMAL(1000)`,
  `GOING_AWAY(1001)`, `PROTOCOL_ERROR(1002)`, ... ), each exposing `getCode()`.
  Note that `WebSocket.close(int, String)` and `WebSocketListener.onClose(int,
  String)` use the raw `int` code; the enum is a reference for the standard
  values.

Basic usage – connecting and exchanging messages
-------------------------------------------------

1. Create and start a `CloseableWebSocketClient`.
2. Call `connect(...)` with a `WebSocketListener`.
3. Send from `onOpen` (or any thread) using the `WebSocket`.
4. Receive through the listener callbacks.
5. Close the connection, then shut the client down.

```java
final CloseableWebSocketClient client = WebSocketClients.createDefault();
client.start();

final CompletableFuture<WebSocket> future = client.connect(
        URI.create("wss://example.com/socket"),
        new WebSocketListener() {

            @Override
            public void onOpen(final WebSocket ws) {
                ws.sendText("hello", true);
            }

            @Override
            public void onText(final CharBuffer data, final boolean last) {
                System.out.println("received: " + data);
            }

            @Override
            public void onClose(final int statusCode, final String reason) {
                System.out.println("closed: " + statusCode + " " + reason);
            }

            @Override
            public void onError(final Throwable cause) {
                cause.printStackTrace(System.err);
            }
        });

final WebSocket ws = future.get();

// ... exchange messages ...

ws.close(WebSocketCloseStatus.NORMAL.getCode(), "done");
client.initiateShutdown();
client.awaitShutdown(TimeValue.ofSeconds(5));
```

Sending messages
----------------

A message is a text or binary payload, optionally split into fragments:

- `sendText(text, true)` / `sendBinary(buffer, true)` send a complete message.
- Passing `false` as the final-fragment flag starts a fragmented message; send
  the remaining pieces and finish with a `true` fragment.
- `sendTextBatch(...)` / `sendBinaryBatch(...)` enqueue several fragments at
  once.
- `ping(...)` / `pong(...)` send control frames (payload at most 125 bytes).
  The client answers inbound pings automatically; an application only needs to
  send its own pings for keep-alive.

Each send returns `false` if the connection is already closing or closed, so
callers can stop producing. `queueSize()` reports the number of queued outbound
bytes (or `-1` when unavailable) and can be used for simple back-pressure.

Receiving messages
------------------

Inbound data is delivered on the reactor thread through `onText` / `onBinary`.
Because the supplied `CharBuffer` / `ByteBuffer` is only valid during the call,
copy anything you need to keep:

```java
@Override
public void onBinary(final ByteBuffer data, final boolean last) {
    final byte[] copy = new byte[data.remaining()];
    data.get(copy);
    // hand `copy` to application code
}
```

Closing
-------

Initiate a close with `close(int statusCode, String reason)`; the returned
`CompletableFuture<Void>` completes once the close frame has been enqueued. The
peer's close is reported through `onClose(int, String)`. After the connection is
closed, shut the client down with `initiateShutdown()` followed by
`awaitShutdown(TimeValue)`, or use try-with-resources.

Per-message compression (permessage-deflate)
---------------------------------------------

`permessage-deflate` (RFC 7692) is negotiated during the handshake and is
**enabled by default**. It is configured on `WebSocketClientConfig.Builder`:

```java
final WebSocketClientConfig cfg = WebSocketClientConfig.custom()
        .enablePerMessageDeflate(true)          // default true
        .offerServerNoContextTakeover(true)     // default true
        .offerClientNoContextTakeover(true)     // default true
        .offerServerMaxWindowBits(15)           // 8..15 or null
        .build();
```

Because the JDK deflater only supports a 15-bit window, the client accepts a
`server_max_window_bits` value of exactly `15`; other values are declined.
Decompression is bounded by the configured maximum message size to guard against
decompression bombs.

WebSocket over HTTP/2
---------------------

Enabling HTTP/2 makes the client attempt the WebSocket handshake using HTTP/2
Extended CONNECT (RFC 8441), multiplexing the session onto a single HTTP/2
stream:

```java
final WebSocketClientConfig cfg = WebSocketClientConfig.custom()
        .enableHttp2(true)
        .build();

client.connect(URI.create("wss://example.com/socket"), listener, cfg);
```

If the server does not support Extended CONNECT (or the H2 attempt fails at the
transport level), the client falls back to the HTTP/1.1 Upgrade handshake. Over
`wss://` the negotiated protocol is selected via ALPN.

Examples
--------

Runnable examples live in the `httpclient5-websocket` module under
`org.apache.hc.client5.http.websocket.example`:

- [WebSocketEchoClient](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-websocket/src/test/java/org/apache/hc/client5/http/websocket/example/WebSocketEchoClient.java)

  HTTP/1.1 Upgrade echo client: connects, enables per-message-deflate, sends a
  large text payload, prints the echo and closes with code 1000.

- [WebSocketEchoServer](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-websocket/src/test/java/org/apache/hc/client5/http/websocket/example/WebSocketEchoServer.java)

  Minimal HTTP/1.1 echo server, useful together with the client examples.

- [WebSocketH2EchoClient](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-websocket/src/test/java/org/apache/hc/client5/http/websocket/example/WebSocketH2EchoClient.java)

  Echo client over HTTP/2 Extended CONNECT (`enableHttp2(true)`), with automatic
  fallback to HTTP/1.1.

- [WebSocketH2EchoServer](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-websocket/src/test/java/org/apache/hc/client5/http/websocket/example/WebSocketH2EchoServer.java)

  HTTP/2 echo server that advertises Extended CONNECT and multiplexes each
  session on its own stream.

- [WebSocketH2TlsEchoClient](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-websocket/src/test/java/org/apache/hc/client5/http/websocket/example/WebSocketH2TlsEchoClient.java)

  Secure `wss://` client over HTTP/2 and TLS, negotiating the protocol via ALPN.

- [WebSocketH2TlsEchoServer](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-websocket/src/test/java/org/apache/hc/client5/http/websocket/example/WebSocketH2TlsEchoServer.java)

  Secure `wss://` HTTP/2 echo server advertising `h2` via ALPN.

Further reading
---------------

- The WebSocket protocol is defined in RFC 6455, per-message compression in
  RFC 7692, and bootstrapping over HTTP/2 in RFC 8441.
- For the full API, see the Javadoc of:

  - `org.apache.hc.client5.http.websocket.client.WebSocketClients`
  - `org.apache.hc.client5.http.websocket.client.CloseableWebSocketClient`
  - `org.apache.hc.client5.http.websocket.api.WebSocket`
  - `org.apache.hc.client5.http.websocket.api.WebSocketListener`
  - `org.apache.hc.client5.http.websocket.api.WebSocketClientConfig`
