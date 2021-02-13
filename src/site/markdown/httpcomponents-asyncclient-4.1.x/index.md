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

HttpAsyncClient Overview
========================

The Hyper-Text Transfer Protocol (HTTP) is perhaps the most significant protocol used on the Internet today. Web
services, network-enabled appliances and the growth of network computing continue to expand the role of the HTTP
protocol beyond user-driven web browsers, while increasing the number of applications that require HTTP support.

Although the java.net package provides basic functionality for accessing resources via HTTP, it doesn't provide the full
flexibility or functionality needed by many applications. HttpAsyncClient seeks to fill this void by providing an
efficient, up-to-date, and feature-rich package implementing the client side of the most recent HTTP standards and
recommendations.

Designed for extension while providing robust support for the base HTTP protocol, HttpAsyncClient may be of interest to
anyone building HTTP-aware client applications based on asynchronous, event driven I/O model.

Documentation
-------------

1. [Quick Start](quickstart.md) - contains a simple, complete example of asynchronous request execution.
1. [HttpAsyncClient Examples](examples.md) - a set of examples demonstrating some of the more complex use scenarios.
1. Javadocs
    - [HttpAsyncClient](./current/httpasyncclient/apidocs/)
    - [HttpAsyncClient Cache](./current/httpasyncclient-cache/apidocs/)

Features
--------

- Standards based, pure Java, implementation of HTTP versions 1.0 and 1.1
- Full implementation of all HTTP methods (GET, POST, PUT, DELETE, HEAD, OPTIONS, and TRACE) in an extensible OO
  framework.
- Supports encryption with HTTPS (HTTP over SSL) protocol.
- Transparent connections through HTTP proxies.
- Tunneled HTTPS connections through HTTP proxies, via the CONNECT method.
- Connection management support concurrent request execution. Supports setting the maximum total connections as well as
  the maximum connections per host. Detects and closes expired connections.
- Persistent connections using KeepAlive in HTTP/1.0 and persistence in HTTP/1.1
- The ability to set connection timeouts.
- Source code is freely available under the Apache License.
- Basic, Digest, NTLMv1, NTLMv2, NTLM2 Session, SNPNEGO and Kerberos authentication schemes.
- Plug-in mechanism for custom authentication schemes.
- Automatic Cookie handling for reading Set-Cookie: headers from the server and sending them back out in a `Cookie`
  header when appropriate.
- Plug-in mechanism for custom cookie policies.
- Support for HTTP/1.1 response caching.
- Support for pipelined request execution and processing.

Standards Compliance
--------------------

HttpAsyncClient strives to conform to the following specifications endorsed by the Internet Engineering Task Force
(IETF) and the internet at large:

- [RFC 1945](http://www.ietf.org/rfc/rfc1945.txt) Hypertext Transfer Protocol -- HTTP/1.0
- [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt) Hypertext Transfer Protocol -- HTTP/1.1
- [RFC 2617](http://www.ietf.org/rfc/rfc2617.txt) HTTP Authentication: Basic and Digest Access Authentication
- [RFC 2109](http://www.ietf.org/rfc/rfc2109.txt) HTTP State Management Mechanism (Cookies)
- [RFC 2965](http://www.ietf.org/rfc/rfc2965.txt) HTTP State Management Mechanism (Cookies v2)
