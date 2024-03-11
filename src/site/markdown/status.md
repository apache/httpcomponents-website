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

HttpComponents HttpClient 5.4.x
-------------------------------

HttpClient 5.4.x branch is the current development branch. The 5.4.x release series upgrades HTTP protocol
conformance level to RFC 9110, replaces `synchronized` blocks with lock primitives, redesigns the HTTP caching 
protocol layer for better efficiency and improved conformance to RFC 9111, introduces support for RFC 2817 
(Upgrading to TLS Within HTTP/1.1), and provides a number of other API enhancements. This branch also seeks 
to optimize common HTTP context operations that should translate in a better performance of HTTP message 
exchanges.

HttpComponents HttpClient 5.3.x
-------------------------------

HttpClient 5.2.x branch is considered stable and production ready. It is being actively maintained and supported. 
The 5.3.x release series introduces support for the Bearer authentication scheme (RFC 6750) and will deprecate 
NTLM and GSS based experimental authentication schemes in favor of Basic / Bearer authentication with TLS.

HttpComponents HttpCore 5.3.x
-------------------------------

HttpClient 5.3.x branch is the current development branch. The 5.3.x release series will upgrade HTTP protocol 
conformance level to RFC 9110 and will replace `synchronized` blocks with lock primitives.

HttpComponents HttpCore 5.2.x
-----------------------------

HttpCore 5.2.x branch is considered stable and production ready. It is being actively maintained and supported. The
5.2.x release series provides comprehensive support for HTTP/1.1 and HTTP/2 protocol, full conformance with mandatory
requirements of RFC 7230, RFC 7231, RFC 7540 and RFC 7541 and conditional conformance to RFC 3986.

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




