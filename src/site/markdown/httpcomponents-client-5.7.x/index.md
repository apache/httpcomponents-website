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

Documentation
-------------

4. Examples demonstrating some common as well as more complex use cases

    * [HttpClient (sse APIs)](server-sent-events.md)

1. Javadocs

    * [HttpClient sse](./current/httpclient5-sse/apidocs/)

1. API compatibility reports

    * [HC sse](./current/httpclient5-sse/japicmp.html)

Features
--------

- Support for HTTP response caching. Pluggable storage backends based on Ehcache, Memcached, Caffeine.
- Optional Server-Sent Events (SSE) module for consuming long-lived event
  streams over HTTP/1.1 and HTTP/2 using the async transport.