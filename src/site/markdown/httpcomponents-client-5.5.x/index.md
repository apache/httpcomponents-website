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

HttpClient Overview
===================

The Hyper-Text Transfer Protocol (HTTP) is perhaps the most significant protocol used on the Internet today. Web
services, network-enabled appliances and the growth of network computing continue to expand the role of the HTTP
protocol beyond user-driven web browsers, while increasing the number of applications that require HTTP support.

Although the java.net package provides basic functionality for accessing resources via HTTP, it doesn't provide the full
flexibility or functionality needed by many applications. HttpClient seeks to fill this void by providing an efficient,
up-to-date, and feature-rich package implementing the client side of the most recent HTTP standards and recommendations.

Designed for extension while providing robust support for the base HTTP protocol, HttpClient may be of interest to
anyone building HTTP-aware client applications such as web browsers, web service clients, or systems that leverage or
extend the HTTP protocol for distributed communication.

Documentation
-------------

1. [Quick Start](quickstart.md) - contains simple, complete examples of request execution with the classic, fluent and
   async APIs.
1. Examples demonstrating some common as well as more complex use cases

    * [HttpClient (classic APIs)](examples.md)
    * [HttpClient (async APIs)](examples-async.md)
    * [HttpClient (reactive APIs)](examples-reactive.md)

1. Javadocs

    * [HttpClient](./current/httpclient5/apidocs/)
    * [HC Fluent](./current/httpclient5-fluent/apidocs/)
    * [HttpClient Cache](./current/httpclient5-cache/apidocs/)
    * [HttpClient Windows extensions](./current/httpclient5-win/apidocs/)

1. API compatibility reports

    * [HttpClient](./current/httpclient5/japicmp.html)
    * [HC Fluent](./current/httpclient5-fluent/japicmp.html)
    * [HttpClient Cache](./current/httpclient5-cache/japicmp.html)
    * [HttpClient Windows extensions](./current/httpclient5-win/japicmp.html)

Features
--------

- Standards based, pure Java, implementation of HTTP versions 1.0, 1.1, 2.0
- Supports encryption with HTTPS (HTTP over SSL) protocol.
- Pluggable TLS strategies.
- Transparent message exchanges through HTTP/1.1, HTTP/1.0 and SOCKS proxies.
- Tunneled HTTPS connections through HTTP/1.1 and HTTP/1.0 proxies, via the CONNECT method.
- Basic, Digest, Bearer authentication schemes.
- HTTP state management and cookie support.
- Flexible connection management and pooling.
- Support for HTTP response caching.
- Source code is freely available under the Apache License.

Standards Compliance
--------------------

HttpClient strives to conform to the following specifications endorsed by the Internet Engineering Task Force (IETF) and
the internet at large:

- [RFC 9110](https://datatracker.ietf.org/doc/html/rfc9110) - HTTP Semantics
- [RFC 9111](https://datatracker.ietf.org/doc/html/rfc9111) - HTTP Caching
- [RFC 9112](https://datatracker.ietf.org/doc/html/rfc9112) - Hypertext Transfer Protocol Version 1.1 (HTTP/1.1)
- [RFC 7540](https://datatracker.ietf.org/doc/html/rfc7540) - Hypertext Transfer Protocol Version 2 (HTTP/2)
- [RFC 7541](https://datatracker.ietf.org/doc/html/rfc7541) - HPACK: Header Compression for HTTP/2
- [RFC 1945](https://datatracker.ietf.org/doc/html/rfc1945) - Hypertext Transfer Protocol -- HTTP/1.0
- [RFC 2396](https://datatracker.ietf.org/doc/html/rfc2396) - Uniform Resource Identifiers (URI): Generic Syntax
- [RFC 6265](https://datatracker.ietf.org/doc/html/rfc6265) - HTTP State Management Mechanism (Cookies)
- [RFC 7616](https://datatracker.ietf.org/doc/html/rfc7616) - HTTP Digest Access Authentication
- [RFC 7617](https://datatracker.ietf.org/doc/html/rfc7617) - HTTP 'Basic' Authentication Scheme
- [RFC 5861](https://datatracker.ietf.org/doc/html/rfc5861) - HTTP Cache-Control Extensions for Stale Content
