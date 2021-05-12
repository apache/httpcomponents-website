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

HttpClient Examples (Classic)
=============================

- [Response handling](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientWithResponseHandler.java)

  This example demonstrates how to process HTTP responses using a response handler. This is the recommended way of
  executing HTTP requests and processing HTTP responses. This approach enables the caller to concentrate on the process
  of digesting HTTP responses and to delegate the task of system resource deallocation to HttpClient. The use of an HTTP
  response handler guarantees that the underlying HTTP connection will be released back to the connection manager
  automatically in all cases.

- [Manual connection release](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientConnectionRelease.java)

  This example demonstrates how to ensure the release of the underlying HTTP connection back to the connection manager
  in case of a manual processing of HTTP responses.

- [HttpClient configuration](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientConfiguration.java)

  This example demonstrates how to customize and configure the most common aspects of HTTP request execution and
  connection management.

- [Request execution interceptors](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientInterceptors.java)

  This example demonstrates how to insert custom request interceptor and an execution interceptor to the request
  execution chain.

- [Abort method](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientAbortMethod.java)

  This example demonstrates how to abort an HTTP request before its normal completion.

- [Client authentication](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientAuthentication.java)

  This example uses HttpClient to execute an HTTP request against a target site that requires user authentication.

- [Request via a proxy](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientExecuteProxy.java)

  This example demonstrates how to send an HTTP request via a proxy.

- [Proxy authentication](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientProxyAuthentication.java)

  A simple example showing execution of an HTTP request over a secure connection tunneled through an authenticating
  proxy.

- [Chunk encoded POST](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientChunkEncodedPost.java)

  This example shows how to stream out a request entity using chunk encoding.

- [Custom execution context](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientCustomContext.java)

  This example demonstrates the use of a local HTTP context populated custom attributes.

- [Form based logon](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientFormLogin.java)

  This example demonstrates how HttpClient can be used to perform form-based logon.

- [Threaded request execution](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientMultiThreadedExecution.java)

  An example that executes HTTP requests from multiple worker threads.

- [Custom SSL context](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientCustomSSL.java)

  This example demonstrates how to create secure connections with a custom SSL context.

- [Preemptive BASIC authentication](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientPreemptiveBasicAuthentication.java)

  This example shows how HttpClient can be customized to authenticate preemptively using BASIC scheme. Generally,
  preemptive authentication can be considered less secure than a response to an authentication challenge and therefore
  discouraged.

- [Preemptive DIGEST authentication](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientPreemptiveDigestAuthentication.java)

  This example shows how HttpClient can be customized to authenticate preemptively using DIGEST scheme. Generally,
  preemptive authentication can be considered less secure than a response to an authentication challenge and therefore
  discouraged.

- [Proxy tunnel](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ProxyTunnelDemo.java)

  This example shows how to use ProxyClient in order to establish a tunnel through an HTTP proxy for an arbitrary
  protocol.

- [Multipart encoded request entity](https://github.com/apache/httpcomponents-client/tree/5.1.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientMultipartFormPost.java)

  This example shows how to execute requests enclosing a multipart encoded entity. 

