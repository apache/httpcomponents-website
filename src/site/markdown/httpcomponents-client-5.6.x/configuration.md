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

# Configuration Guide

The concept of HttpClient configuration APIs is closely aligned with
its [Architecture](architecture.md) layering principles. Configuration parameters are
grouped by the architecture layer they apply to. Those groups of configuration parameters
are modelled as immutable bean classes referred to as config beans. Config beans are safe
to pass around to different classes as those beans cannot be modified at runtime.

A config beans class always represents logically related properties of a single
architecture layer. There can be however properties shared by multiple layers applicable
to the underlying connection or ongoing message exchange at a different points of their
life-cycle. Socket timeout is one of such parameters. For example, connections can start
off with a default value of the socket timeout which can later be overridden at a higher
level or at a specific phase of connection life-cycle such as TLS handshake.

## Configuration Levels

### Network I/O Level

* [SocketConfig](/httpcomponents-core-5.3.x/current/httpcore5/apidocs/org/apache/hc/core5/http/io/SocketConfig.html):
  these parameters apply to the Classic Network I/O layer and define connection socket
  properties.

  Socket configuration applies on a per-socket basis.

* [IOReactorConfig](/httpcomponents-core-5.3.x/current/httpcore5/apidocs/org/apache/hc/core5/reactor/IOReactorConfig.html):
  these parameters apply to the Async Network I/O layer and define I/O reactor properties.

### Protocol Transport Level

* [Http1Config](/httpcomponents-core-5.3.x/current/httpcore5/apidocs/org/apache/hc/core5/http/config/Http1Config.html):
  these parameters apply to the HTTP/1.1 protocol handlers and define common aspects of
  HTTP/1.1 message transmission.

  HTTP/1.1 configuration applies on a per-connection basis.

* [H2Config](/httpcomponents-core-5.3.x/current/httpcore5-h2/apidocs/org/apache/hc/core5/http2/config/H2Config.html):
  these parameters apply to the HTTP/2 protocol handlers and define common aspects of
  HTTP/2 message transmission and message multiplexing.

  HTTP/2 configuration applies on a per-connection basis.

* [CharCodingConfig](/httpcomponents-core-5.3.x/current/httpcore5/apidocs/org/apache/hc/core5/http/config/CharCodingConfig.html):
  these parameters apply to text based protocols such as HTTP/1.1 and define properties of
  binary to text coding.

  Character coding configuration applies on a per-connection basis.

### Connection Management Level

* [ConnectionConfig](/httpcomponents-client-5.5.x/current/httpclient5/apidocs/org/apache/hc/client5/http/config/ConnectionConfig.html):
  these parameters apply to connections managed by connection managers. Connection
  configuration is expected to work consistently across all supported network I/O
  implementations.

  Connection configuration applies on a per-route basis.

  An example of per-route connection configuration for the classic transport can be found
  here: [classic](https://github.com/apache/httpcomponents-client/blob/5.5.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientConnectionConfig.java)
  and [async](https://github.com/apache/httpcomponents-client/blob/5.5.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientConnectionConfig.java).

### TLS Level

* [TlsConfig](/httpcomponents-client-5.5.x/current/httpclient5/apidocs/org/apache/hc/client5/http/config/TlsConfig.html):
  these parameters apply to connections at the time of TLS handshake
  execution and session negotiation. TLS configuration is expected to work consistently
  across all supported network I/O implementations.

  TLS configuration applies on a per-host basis.

  An example of per-host TLS configuration for the classic transport can be found
  here: [classic](https://github.com/apache/httpcomponents-client/blob/5.5.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientConnectionConfig.java)
  and [async](https://github.com/apache/httpcomponents-client/blob/5.5.x/httpclient5/src/test/java/org/apache/hc/client5/http/examples/AsyncClientConnectionConfig.java).

### Message Exchange Protocol Level

* [RequestConfig](/httpcomponents-client-5.5.x/current/httpclient5/apidocs/org/apache/hc/client5/http/config/RequestConfig.html):
  these parameters apply to individual HTTP message exchanges. Request execution
  parameters are generally expected to work consistently to all HTTP protocol versions
  with a few exceptions when certain functionality is not applicable due to differences in
  message transport between different HTTP protocol versions.

  Request configuration applies on a per-request basis.

### Caching Protocol Level

* [CacheConfig](/httpcomponents-client-5.5.x/current/httpclient5-cache/apidocs/org/apache/hc/client5/http/impl/cache/CacheConfig.html):
  these parameters apply to the caching protocol layer. They also define the most common
  properties shared by different cache storage implementations. Individual caching
  backends may still expose configuration options specific to those backends.

  Cache configuration applies on a per-instance basis.
