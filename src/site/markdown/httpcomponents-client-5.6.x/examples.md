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

- [Response handling](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientResponseProcessing.java)

  This example demonstrates how to process HTTP responses using a response handler. This is the recommended way of
  executing HTTP requests and processing HTTP responses. This approach enables the caller to concentrate on the process
  of digesting HTTP responses and to delegate the task of system resource deallocation to HttpClient. The use of an HTTP
  response handler guarantees that the underlying HTTP connection will be released back to the connection manager
  automatically in all cases.

- [Manual connection release](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientConnectionRelease.java)

  This example demonstrates how to ensure the release of the underlying HTTP connection back to the connection manager
  in case of a manual processing of HTTP responses.

- [HttpClient configuration](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientConfiguration.java)

  This example demonstrates how to customize and configure the most common aspects of HTTP request execution and
  connection management.

- [Request execution interceptors](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientInterceptors.java)

  This example demonstrates how to insert custom request interceptor and an execution interceptor to the request
  execution chain.

- [Classic APIs over async HttpClient](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientClassicOverAsync.java)

  This example demonstrates create a classic HttpClient adaptor over an async HttpClient providing compatibility
  with the classic APIs based on the InputStream / OutputStream model.

- [Abort method](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientAbortMethod.java)

  This example demonstrates how to abort an HTTP request before its normal completion.

- [Client authentication](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientAuthentication.java)

  This example uses HttpClient to execute an HTTP request against a target site that requires user authentication.

- [Request via a proxy](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientExecuteProxy.java)

  This example demonstrates how to send an HTTP request via a proxy.

- [Proxy authentication](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientProxyAuthentication.java)

  A simple example showing execution of an HTTP request over a secure connection tunneled through an authenticating
  proxy.

- [Chunk encoded POST](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientChunkEncodedPost.java)

  This example shows how to stream out a request entity using chunk encoding.

- [Custom execution context](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientCustomContext.java)

  This example demonstrates the use of a local HTTP context populated custom attributes.

- [Form based logon](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientFormLogin.java)

  This example demonstrates how HttpClient can be used to perform form-based logon.

- [Threaded request execution](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientMultiThreadedExecution.java)

  An example that executes HTTP requests from multiple worker threads.

- [Custom SSL context](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientCustomSSL.java)

  This example demonstrates how to create secure connections with a custom SSL context.

- [Connection / TLS configuation per route / host](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientConnectionConfig.java)

  This example demonstrates how to use connection configuration on a per-route or a per-host basis.

- [Preemptive BASIC authentication](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientPreemptiveBasicAuthentication.java)

  This example shows how HttpClient can be customized to authenticate preemptively using BASIC scheme. Generally,
  preemptive authentication can be considered less secure than a response to an authentication challenge and therefore
  discouraged.

- [Preemptive DIGEST authentication](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientPreemptiveDigestAuthentication.java)

  This example shows how HttpClient can be customized to authenticate preemptively using DIGEST scheme. Generally,
  preemptive authentication can be considered less secure than a response to an authentication challenge and therefore
  discouraged.

- [Proxy tunnel](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ProxyTunnelDemo.java)

  This example shows how to use ProxyClient in order to establish a tunnel through an HTTP proxy for an arbitrary
  protocol.

- [Multipart encoded request entity](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientMultipartFormPost.java)

  This example shows how to execute requests enclosing a multipart encoded entity. 

- [Connection endpoint details](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientRemoteEndpointDetails.java)

  This example demonstrates how to get details of the underlying connection endpoint.

- [Virtual HTTPS / SNI](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientSNI.java)

  This example demonstrates how to use SNI to send requests to a virtual HTTPS endpoint using the classic I/O.

- [Unix domain sockets](https://github.com/apache/httpcomponents-client/blob/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/UnixDomainSocket.java)

  This example demonstrates how to connect to a local daemon (in this case, the Docker daemon) over a Unix domain socket. Note that this requires either Java 17+ or a dependency on [JUnixSocket](https://mvnrepository.com/artifact/com.kohlschutter.junixsocket/junixsocket-core).
