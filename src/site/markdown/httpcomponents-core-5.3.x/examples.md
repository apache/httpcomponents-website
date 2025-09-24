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

HttpCore Examples
=================

- [ Classic (blocking) HTTP/1.1 GET requests](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/ClassicGetExecutionExample.java)

  This example demonstrates synchronous execution of multiple HTTP/1.1 GET requests.

- [ Classic (blocking) HTTP/1.1 POST requests](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/ClassicPostExecutionExample.java)

  This example demonstrates synchronous execution of multiple HTTP/1.1 POST requests with enclosed content of various
  types.

- [ Asynchronous HTTP/1.1 GET requests](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/AsyncRequestExecutionExample.java)

  This example demonstrates asynchronous execution of multiple HTTP/1.1 requests.

- [ Asynchronous HTTP/2 GET requests](https://github.com/apache/httpcomponents-core/tree/master/httpcore5-h2/src/test/java/org/apache/hc/core5/http2/examples/H2RequestExecutionExample.java)

  This example demonstrates asynchronous execution of multiple HTTP/2 requests.

- [ HTTP/2 requests over TLS connections with ALPN support on Java 1.7 and Java 1.8](https://github.com/apache/httpcomponents-core/tree/master/httpcore5-h2/src/test/java/org/apache/hc/core5/http2/examples/H2ConscriptRequestExecutionExample.java)

  This example demonstrates how to execute HTTP/2 requests over TLS connections with ALPN support on Java 1.7 and Java
  1.8.

- [ Asynchronous HTTP/1.1 GET requests with message pipelining](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/AsyncPipelinedRequestExecutionExample.java)

  This example demonstrates asynchronous, pipelined execution multiple HTTP/1.1 requests.

- [ Asynchronous HTTP/2 GET requests with multiple concurrent streams](https://github.com/apache/httpcomponents-core/tree/master/httpcore5-h2/src/test/java/org/apache/hc/core5/http2/examples/H2MultiStreamExecutionExample.java)

  This example demonstrates asynchronous, multistream execution of multiple HTTP/2 requests.

- [ Classic (blocking) HTTP/1.1 file server](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/ClassicFileServerExample.java)

  This is an example of an embedded HTTP/1.1 file server with a classic (blocking) message transport.

- [ Request filters with classic (blocking) HTTP/1.1 server](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/ClassicServerFilterExample.java)

  This is an example of using synchronous request filters with an embedded HTTP/1.1 server.

- [ Asynchronous HTTP/1.1 file server](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/AsyncFileServerExample.java)

  This is an example of an embedded HTTP/1.1 file server with an event driven, non-blocking message transport.

- [ Request filters with asynchronous HTTP/1.1 server](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/AsyncServerFilterExample.java)

  This is an example of using asynchronous request filters with an embedded HTTP/1.1 server.

- [ Asynchronous HTTP/2 file server](https://github.com/apache/httpcomponents-core/tree/master/httpcore5-h2/src/test/java/org/apache/hc/core5/http2/examples/H2FileServerExample.java)

  This is an example of an embedded HTTP/2 file server with an event driven, non-blocking message transport.

- [ Classic (blocking) HTTP reverse proxy](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/ClassicReverseProxyExample.java)

  This is an example of an embedded HTTP/1.1 reverse proxy with a classic (blocking) message transport.

- [ Asynchronous HTTP reverse proxy](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/AsyncReverseProxyExample.java)

  This is an example of an embedded HTTP/1.1 reverse proxy with an event driven, non-blocking message transport.

- [ Client Reactive Streams](https://github.com/apache/httpcomponents-core/tree/master/httpcore5-reactive/src/test/java/org/apache/hc/core5/reactive/examples/ReactiveFullDuplexClientExample.java)

  This is an example of full-duplex HTTP/1.1 client side message exchanges using reactive streaming.

- [ Server Reactive Streams](https://github.com/apache/httpcomponents-core/tree/master/httpcore5-reactive/src/test/java/org/apache/hc/core5/reactive/examples/ReactiveFullDuplexServerExample.java)

  This is an example of full-duplex HTTP/1.1 server side message exchanges using reactive streaming.

- [ SNI (Server Name Identification) with classic (blocking) I/O](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/ClassicGetExecutionExample.java)

  This is an example of SNI (Server Name Identification) usage with classic I/O.

- [ SNI (Server Name Identification) with async I/O](https://github.com/apache/httpcomponents-core/tree/master/httpcore5/src/test/java/org/apache/hc/core5/http/examples/AsyncClientSNIExample.java)

  This is an example of SNI (Server Name Identification) usage with async I/O.
