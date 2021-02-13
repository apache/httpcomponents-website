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

HttpAsyncClient Examples
========================

- [Asynchronous HTTP exchange](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientHttpExchange.java?view=markup)

  This example demonstrates a basic asynchronous HTTP request / response exchange. Response content is buffered in
  memory for simplicity.

- [Asynchronous HTTP exchange with content streaming](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientHttpExchangeStreaming.java?view=markup)

  This example demonstrates an asynchronous HTTP request / response exchange with a full content streaming.

- [Concurrent asynchronous HTTP exchanges](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientHttpExchangeFutureCallback.java?view=markup)

  This example demonstrates a fully asynchronous execution of multiple HTTP exchanges where the result of an individual
  operation is reported using a callback interface.

- [Pipelined HTTP exchanges](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientPipelined.java?view=markup)

  This example demonstrates a pipelined execution of multiple HTTP request / response exchanges. Response content is
  buffered in memory for simplicity.

- [Pipelined HTTP exchanges with content streaming](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientPipelinedStreaming.java?view=markup)

  This example demonstrates a pipelined execution of multiple HTTP request / response exchanges with a full content
  streaming.

- [Asynchronous request via a proxy](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientExecuteProxy.java?view=markup)

  This example demonstrates how to send an HTTP request via a proxy.

- [HttpAsyncClient configuration](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientConfiguration.java?view=markup)

  This example demonstrates how to customize and configure the most common aspects of HTTP request execution and
  connection management.

- [Custom execution context](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientCustomContext.java?view=markup)

  This example demonstrates the use of a local execution context with custom context settings.

- [Client authentication](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientAuthentication.java?view=markup)

  This example demonstrates execution of an HTTP request against a target site that requires user authentication.

- [Proxy authentication](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientProxyAuthentication.java?view=markup)

  This example shows execution of an HTTP request over a secure connection tunneled through an authenticating proxy.

- [Custom SSL context](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientCustomSSL.java?view=markup)

  This example demonstrates how to create secure connections with a custom SSL context.

- [Zero copy file upload / download](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/ZeroCopyHttpExchange.java?view=markup)

  This example demonstrates how HttpAsyncClient can be used to upload or download files without creating an intermediate
  content buffer in memory (zero copy file transfer).

- [Connection eviction](http://svn.apache.org/viewvc/httpcomponents/httpasyncclient/branches/4.1.x/httpasyncclient/src/examples/org/apache/http/examples/nio/client/AsyncClientEvictExpiredConnections.java?view=markup)

  This example demonstrates how to evict expired and idle connections from the connection pool.