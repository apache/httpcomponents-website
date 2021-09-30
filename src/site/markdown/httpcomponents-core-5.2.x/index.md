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

HttpCore Overview
=================

HttpCore is a set of low level HTTP transport components that can be used to build custom client and server side HTTP
services with a minimal footprint. HttpCore supports two I/O models: blocking I/O model based on the classic Java I/O
and non-blocking, event driven I/O model based on Java NIO.

Documentation
=============

1. Some examples of HttpCore components in action can be found [here](./examples.html)

1. Javadocs
    - [HttpCore HTTP/1.1](./current/httpcore5/apidocs/)
    - [HttpCore HTTP/2](./current/httpcore5-h2/apidocs/)
    - [HttpCore Reactive Streams](./current/httpcore5-reactive/apidocs/)

1. API compatibility reports
    - [HttpCore HTTP/1.1](./current/httpcore5/clirr-report.html)
    - [HttpCore HTTP/2](./current/httpcore5-h2/clirr-report.html)
    - [HttpCore Reactive Streams](./current/httpcore5-reactive/clirr-report.html)

Standards Compliance
--------------------

HttpCore components strive to conform to the following specifications endorsed by the Internet Engineering Task Force
(IETF) and the internet at large:

- [RFC 7540](https://datatracker.ietf.org/doc/html/rfc7540) - Hypertext Transfer Protocol Version 2 (HTTP/2)
- [RFC 7541](https://datatracker.ietf.org/doc/html/rfc7541) - HPACK: Header Compression for HTTP/2
- [RFC 7230](https://datatracker.ietf.org/doc/html/rfc7230) - Hypertext Transfer Protocol (HTTP/1.1): Message Syntax and Routing
- [RFC 7231](https://datatracker.ietf.org/doc/html/rfc7231) - Hypertext Transfer Protocol (HTTP/1.1): Semantics and Content
- [RFC 1945](https://datatracker.ietf.org/doc/html/rfc1945) - Hypertext Transfer Protocol -- HTTP/1.0
- [RFC 2396](https://datatracker.ietf.org/doc/html/rfc2396) - Uniform Resource Identifiers (URI): Generic Syntax

