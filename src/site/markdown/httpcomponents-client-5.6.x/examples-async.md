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

HttpClient Examples (Async)
===========================

- [Asynchronous HTTP exchange](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientHttpExchange.java)

  This example demonstrates a basic asynchronous HTTP request / response exchange. Response content is buffered in
  memory for simplicity.

- [Asynchronous HTTP exchange with content streaming](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientHttpExchangeStreaming.java)

  This example demonstrates an asynchronous HTTP request / response exchange with a full content streaming.

- [Pipelined HTTP/1.1 exchanges](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientHttp1Pipelining.java)

  This example demonstrates a pipelined execution of multiple HTTP/1.1 request / response exchanges. Response content is
  buffered in memory for simplicity.

- [Multiplexed HTTP/2 exchanges](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientH2Multiplexing.java)

  This example demonstrates a multiplexed execution of multiple HTTP/2 request / response exchanges. Response content is
  buffered in memory for simplicity.

- [Request execution interceptors](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientInterceptors.java)

  This example demonstrates how to insert custom request interceptor and an execution interceptor to the request
  execution chain.

- [Message trailers](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientMessageTrailers.java)

  This example demonstrates how to use a custom execution interceptor to add trailers to all outgoing request enclosing
  an entity..

- [Multiplexed HTTP/2 exchanges](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientHttp2ServerPush.java)

This example demonstrates handling of HTTP/2 message exchanges pushed by the server.

- [Client authentication](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientAuthentication.java)

  This example demonstrates execution of an HTTP request against a target site that requires user authentication.

- [Custom SSL context](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientCustomSSL.java)

  This example demonstrates how to create secure connections with a custom SSL context.

- [Connection / TLS configuation per route / host](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientConnectionConfig.java)

  This example demonstrates how to use connection configuration on a per-route or a per-host basis.

- [Connection eviction](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientConnectionEviction.java)

  This example demonstrates how to evict expired and idle connections from the connection pool.

- [Preemptive BASIC authentication](https://github.com/apache/httpcomponents-client/blob/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncPreemptiveBasicClientAuthentication.java)

  This example shows how HttpClient can be customized to authenticate preemptively using BASIC scheme. Generally,
  preemptive authentication can be considered less secure than a response to an authentication challenge and therefore
  discouraged.

- [HTTP version negotiation](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientHttpVersionPolicy.java)

  This example demonstrates how to make HttpClient negotiate or force a particular version of HTTP protocol during 
  the TLS handshake. Please note that protocol version policy setting also applies to non-HTTPS connections.

- [Virtual HTTPS / SNI](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientSNI.java)

  This example demonstrates how to use SNI to send requests to a virtual HTTPS endpoint using the async I/O.

