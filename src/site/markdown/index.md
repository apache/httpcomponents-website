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

Apache HttpComponents
=====================

The Apache HttpComponents project is responsible for creating and maintaining a toolset of low level Java components
focused on HTTP and associated protocols.

This project functions under the Apache Software Foundation (https://www.apache.org), and is part of a larger community
of developers and users.

HttpComponents Overview
-----------------------

The Hyper-Text Transfer Protocol (HTTP) is perhaps the most significant protocol used on the Internet today. Web
services, network-enabled appliances and the growth of network computing continue to expand the role of the HTTP
protocol beyond user-driven web browsers, while increasing the number of applications that require HTTP support.

Designed for extension while providing robust support for the base HTTP protocol, the HttpComponents may be of interest
to anyone building HTTP-aware client and server applications such as web browsers, web spiders, HTTP proxies, web
service transport libraries, or systems that leverage or extend the HTTP protocol for distributed communication.

HttpComponents Structure
-----------------------

### HttpComponents Core

[HttpCore](./httpcomponents-core-ga/) is a set of low level HTTP transport components that can be used to build custom
client and server side HTTP services with a minimal footprint. HttpCore supports two I/O models: blocking I/O model
based on the classic Java I/O and non-blocking, event driven I/O model based on Java NIO.

### HttpComponents Client

[HttpClient](./httpcomponents-client-ga/) is a HTTP/1.1 compliant HTTP agent implementation based on HttpCore. It also
provides reusable components for client-side authentication, HTTP state management, and HTTP connection management.
HttpComponents Client is a successor of and replacement for
[Commons HttpClient 3.x](https://hc.apache.org/httpclient-legacy/index.html). Users of Commons HttpClient are strongly
encouraged to upgrade.

### Commons HttpClient (legacy)

Commons HttpClient 3.x codeline is at the end of life. All users of Commons HttpClient 3.x are strongly encouraged to
upgrade to HttpClient 4.1. 
