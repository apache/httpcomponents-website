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

- [Synchronous HTTP GET requests](https://github.com/apache/httpcomponents-core/tree/4.4.x/httpcore/src/examples/org/apache/http/examples/ElementalHttpGet.java)

  This example demonstrates how to execute a series of synchronous (blocking) HTTP GET requests.

- [Synchronous HTTP POST requests](https://github.com/apache/httpcomponents-core/tree/4.4.x/httpcore/src/examples/org/apache/http/examples/ElementalHttpPost.java)

  This example demonstrates how to execute a series of synchronous (blocking) HTTP POST requests that enclose entity
  content of various types: a string, a byte array, an arbitrary input stream.

- [Synchronous HTTP GET requests with connection pooling](https://github.com/apache/httpcomponents-core/tree/4.4.x/httpcore/src/examples/org/apache/http/examples/ElementalPoolingHttpGet.java)

  This example demonstrates how to execute a series of synchronous (blocking) HTTP GET requests using a pool of
  persistent connections.

- [Synchronous HTTP file server](https://github.com/apache/httpcomponents-core/tree/4.4.x/httpcore/src/examples/org/apache/http/examples/HttpFileServer.java)

  This is an example of an HTTP/1.1 file server based on a synchronous (blocking) I/O model.

- [Synchronous HTTP reverse proxy](https://github.com/apache/httpcomponents-core/tree/4.4.x/httpcore/src/examples/org/apache/http/examples/ElementalReverseProxy.java)

  This is an example of an HTTP/1.1 reverse proxy based on a synchronous (blocking) I/O model.

- [Asynchronous HTTP GET requests](https://github.com/apache/httpcomponents-core/tree/4.4.x/httpcore-nio/src/examples/org/apache/http/examples/nio/NHttpClient.java)

  This example demonstrates how HttpCore NIO can be used to execute multiple HTTP requests asynchronously.

- [Pipelined HTTP GET requests](https://github.com/apache/httpcomponents-core/tree/4.4.x/httpcore-nio/src/examples/org/apache/http/examples/nio/PipeliningHttpClient.java)

  This example demonstrates how HttpCore NIO can be used to execute series of pipelined HTTP requests.

- [Asynchronous HTTP server](https://github.com/apache/httpcomponents-core/tree/4.4.x/httpcore-nio/src/examples/org/apache/http/examples/nio/NHttpFileServer.java)

  This example demonstrates the use of HttpCore NIO to build an asynchronous (non-blocking) HTTP server capable of
  direct channel (zero copy) data transfer.

- [Asynchronous HTTP reverse proxy](https://github.com/apache/httpcomponents-core/tree/4.4.x/httpcore-nio/src/examples/org/apache/http/examples/nio/NHttpReverseProxy.java)

  This example demonstrates how HttpCore NIO can be used to build an asynchronous, fully streaming reverse HTTP proxy.

