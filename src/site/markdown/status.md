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

HttpComponents Project Status
=============================

HttpComponents HttpClient 5.5.x
-------------------------------

HttpClient 5.5.x branch is considered stable and production ready. It is being actively maintained and supported.
The 5.5.x release series features  an experimental Classic API facade acting as a compatibility bridge between 
the classic I/O client services (based on the standard InputStream / OutputStream model) and the asynchronous 
message transport used internally. This branch also makes it possible for the pooling connection manager to make 
use of HTTP/2 message multiplexing.

HttpComponents HttpCore 5.4.x
-------------------------------

HttpCore 5.4.x branch is the current development branch. The 5.4.x release series improves HTTP/2 protocol support 
by ensuring conformance to the latest HTTP specification (RFC 9113), adds support for Unix domain sockets, and
also redesigns the classic over async API compatibity bridge.
 
HttpComponents HttpCore 5.3.x
-------------------------------

HttpCore 5.3.x branch is considered stable and production ready. It is being actively maintained and supported. 
The 5.3.x release series upgrades HTTP protocol conformance level to RFC 9110 and replaces `synchronized` 
blocks with lock primitives.

HttpComponents HttpClient 4.5.x
-------------------------------

HttpClient 4.5.x branch is considered stable and production ready. Please note the 4.x release series will be receiving
fixes for major defects and security issues only.

Users of HttpClient 4.x are strongly encouraged to migrate to HttpClient 5.x

HttpComponents HttpCore 4.4.x (EOL)
-----------------------------

HttpCore NIO in the 4.4.x branch is at End of Life. It is no longer being developed or supported. HttpCore Classic
in the 4.4.x branch will be receiving fixes for major defects and security issues only.

Users of HttpCore 4.x are strongly encouraged to migrate to HttpCore 5.x

HttpComponents HttpAsyncClient 4.1.x (EOL)
------------------------------------

HttpAsyncClient 4.1.x branch is at the End of Life. It is no longer being developed or supported.

Users of HttpAsyncClient 4.x are strongly encouraged to migrate to HttpClient 5.x

Commons HttpClient 3.1.x (EOL)
------------------------

The 3.1 branch of Commons HttpClient is at the end of life. No more public releases are expected.




