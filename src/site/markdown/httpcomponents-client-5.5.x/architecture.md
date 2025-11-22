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

# Architecture

## Layering Principles

### Network I/O layer

This layer is responsible for transmitting octets of data between endpoints (local and
remote)

* Classic: based on `java.net.Socket` / `java.io.OutputStream` / `java.io.InputStream`
  APIs.
* Event-driven (async): based on `java.nio.channels.Selector` and
  `java.nio.channels.Channel` APIs

Both implementations are provided by HttpCore.

Potentially there can be other network layer implementations, for example, QUIC based
or Netty based.

### Transport Security Layer

This layer implements Transport Layer Security on top of the network I/O layer as defined
by the TLS protocol. Both the classic and async implementations rely on the TLS protocol
functionality provided by the standard JSSE security provider. Alternative JSSE providers
such as Conscrypt can be used as well.

### Protocol Transport Layer

This layer is responsible for transport of messages between endpoints based on a
communication protocol.

* HTTP/1.1 with HTTP/1.0 compatibility
* HTTP/2

The classic implementation supports HTTP/1.1. The async implementation supports HTTP/1.1
and HTTP/2.

Both implementations are provided by HttpCore.

Please note that the classic I/O model work well with request / response oriented
protocols such as HTTP/1.1 but does not lend itself well to multiplexing protocols such as
HTTP/2. This is the reason the classic I/O based clients presently support HTTP/1.1 only.

### Connection Management Layer

This layer facilitates efficient creation and re-use of connections. It also aims at
harmonising the connection management APIs across different netweok I/O models and
protocols and making those APIs as compatible and consistent as possible. This layer is
responsible for connection creation, initialization, upgrade to TLS and re-use by
maintaining pools of reusable persistent connection that can be leased to execute message
exchanges and released back to the pool.

Both Classic and Async I/O models are supported.

Connection pool implementations are provided by HttpCore.

### Message Exchange Protocol Layer

This layer is responsible for execution of complex message exchanges that may involve
complex routing, authentication, state management, automatic re-execution or recovery and
transparent compression / decompression of content. It also aims at harmonising the
request execution APIs across different transport models and protocols and making those
APIs as compatible and consistent as possible.

Both Classic and Async I/O models are supported.

### Caching Protocol Layer

This layer implements HTTP caching protocol on top of a message exchange protocol layer.

Both Classic and Async I/O models are supported.

This layer is optional.

### Reactive Streams Layer

This layer provides [Reactive Streams Bindings](https://www.reactive-streams.org/) on top
of the async message exchange protocol layer.

The bindings implementation is provided by HttpCore.

This layer is optional.

### Classic Over Async Facade

This layer provides compatibility bindings with the classic `java.io.OutputStream` /
`java.io.InputStream` based APIs on top of the asnyc message exchange protocol layer.

This layer is optional and is still considered experimental.




