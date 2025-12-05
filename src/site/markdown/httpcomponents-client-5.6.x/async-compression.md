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

Async content compression & decompression
=========================================

Starting with HttpClient 5.6 the async transport can transparently **compress**
request bodies and **decompress** response bodies. The logic lives in the
message-exchange layer, so application code keeps working with plain (already
decoded) entities.

Capabilities
------------

- Automatic `Accept-Encoding` negotiation when content compression is enabled
  and the request does not already carry this header.
- Transparent response decompression for `Content-Encoding` values
  `deflate`, `gzip`, and `x-gzip`.
- Optional support for `zstd` and `br` (Brotli) encodings when the
  corresponding libraries are present on the classpath.
- Streaming request compression via `DeflatingAsyncEntityProducer` and the
  codec-specific wrappers (for example `DeflatingGzipEntityProducer`).

Supported codecs and dependencies
---------------------------------

Deflate and GZIP are available out of the box.

Zstandard and Brotli are only wired in when their optional dependencies are
present:

- **Zstandard** – `com.github.luben:zstd-jni`
- **Brotli** – `com.aayushatharva.brotli4j:brotli4j` (plus a matching
  `brotli4j-native` artefact for the target platform)

At runtime the async client checks the classpath and registers the extra codecs
without creating a hard dependency on either library.

Basic usage – transparent response decompression
------------------------------------------------

By default async clients send an `Accept-Encoding` header and automatically
decode compressed responses before handing them to the application.
No extra code is required:

```java
try (final CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
    client.start();

    final SimpleHttpRequest request = SimpleRequestBuilder.get("https://httpbin.org/gzip")
            .build();

    final Future<Message<HttpResponse, String>> future = client.execute(
            SimpleRequestProducer.create(request),
            new BasicResponseConsumer<>(new StringAsyncEntityConsumer()),
            null);

    final Message<HttpResponse, String> message = future.get();
    System.out.println("status=" + message.getHead().getCode());
    System.out.println("body:\n" + message.getBody());
}
```

Mapping codecs to examples
--------------------------

The async examples in the `org.apache.hc.client5.http.examples` package exercise
the same codecs that are registered in `ContentCompressionAsyncExec`:

```java
.register(ContentCoding.GZIP.token(),    InflatingGzipDataConsumer::new)
.register(ContentCoding.X_GZIP.token(),  InflatingGzipDataConsumer::new)
.register(ContentCoding.DEFLATE.token(), d -> new InflatingAsyncDataConsumer(d, null))

// Optional – only when the libraries are present
.register(ContentCoding.ZSTD.token(),    InflatingZstdDataConsumer::new)
.register(ContentCoding.BROTLI.token(),  InflatingBrotliDataConsumer::new);
```

The sections below link each `ContentCoding` token to a concrete runnable
example, in the same spirit as the Observation examples page.

GZIP (`gzip`, `x-gzip`)
-----------------------

- [AsyncClientGzipCompressionExample](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientGzipCompressionExample.java)

  Demonstrates streaming a request body compressed with **GZIP** using
  `DeflatingGzipEntityProducer`. The client sends a compressed POST while the
  async pipeline adds `Content-Encoding: gzip` automatically.

- [AsyncClientGzipDecompressionExample](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientGzipDecompressionExample.java)

  Demonstrates **transparent GZIP response decompression**. The server returns
  `Content-Encoding: gzip`, the async chain wraps the downstream consumer with
  `InflatingGzipDataConsumer`, and application code only sees the plain body.

These examples correspond directly to:

```java
.register(ContentCoding.GZIP.token(),   InflatingGzipDataConsumer::new)
.register(ContentCoding.X_GZIP.token(), InflatingGzipDataConsumer::new)
```

DEFLATE (`deflate`)
-------------------

- [AsyncClientDeflateCompressionExample](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientDeflateCompressionExample.java)

  Demonstrates streaming a request body compressed with **deflate** using
  `DeflatingAsyncEntityProducer` wrapped around an arbitrary `AsyncEntityProducer`.

- [AsyncClientInflateDecompressionExample](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientInflateDecompressionExample.java)

  Demonstrates **transparent deflate response decompression**. When the server
  responds with `Content-Encoding: deflate`, the async execution chain wraps
  the downstream consumer in `InflatingAsyncDataConsumer`.

These examples correspond to:

```java
.register(ContentCoding.DEFLATE.token(), d -> new InflatingAsyncDataConsumer(d, null))
```

Zstandard (`zstd`)
------------------

- [AsyncClientZstdCompressionExample](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientZstdCompressionExample.java)

  Shows streaming a request body compressed with **Zstandard** using
  `DeflatingZstdEntityProducer`, backed by the `zstd-jni` library.

- [AsyncClientServerZstdExample](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientServerZstdExample.java)

  End-to-end client/server demo where the classic server always responds with
  `Content-Encoding: zstd` and the async client transparently decodes it via
  `InflatingZstdDataConsumer`.

These examples correspond to the optional Zstandard decoder:

```java
.register(ContentCoding.ZSTD.token(), InflatingZstdDataConsumer::new)
```

Brotli (`br`)
-------------

- [AsyncClientServerBrotliRoundTrip](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientServerBrotliRoundTrip.java)

  Async client/server demo using **Brotli** in both directions:
  the client sends a Brotli-compressed request and the server responds with a
  Brotli-compressed body. The example uses `brotli4j` on the client side and
  Commons Compress for server-side decoding/encoding.

This example matches the optional Brotli decoder registration (and the extra
`Accept-Encoding: br` token):

```java
.register(ContentCoding.BROTLI.token(), InflatingBrotliDataConsumer::new);
// ...
tokens.add(ContentCoding.BROTLI.token());
```

Together, these examples show how each `ContentEncoding` registered in
`ContentCompressionAsyncExec` maps to a concrete usage pattern in the async
client.
