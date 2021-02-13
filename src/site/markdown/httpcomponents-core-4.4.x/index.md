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
-------------

1. HttpCore [Tutorial](./current/tutorial/html/) ([PDF](./tutorial/pdf/httpcore-tutorial.pdf))
1. Examples of HttpCore components in action can be found [here](examples.md)
1. Javadocs
    - [HttpCore](./current/httpcore/apidocs/)
    - [HttpCore NIO](./current/httpcore-nio/apidocs/)

1. API compatibility reports
    - [HttpCore](./current/httpcore/clirr-report.html)
    - [HttpCore NIO](./current/httpcore-nio/clirr-report.html)

Standards Compliance
--------------------

HttpCore components strive to conform to the following specifications endorsed by the Internet Engineering Task Force
(IETF) and the internet at large:

- [RFC 1945](http://tools.ietf.org/html/rfc1945) - Hypertext Transfer Protocol -- HTTP/1.0
- [RFC 2616](http://tools.ietf.org/html/rfc2616) - Hypertext Transfer Protocol -- HTTP/1.1
