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

HttpComponents Project News
===========================

###### 7 November 2022 - HttpComponents Core 5.2 (GA) released

This is the first GA release in the 5.2 release series. This release finalizes the 5.2 APIs  and also corrects 
a number of defects discovered since the previous release.

Notable changes and features included in the 5.2 series:

- Upgrade to Java 8.
- Improved support for TLS upgrade and HTTP protocol upgrade (async).
- Improved HTTP protocol negotiation.
- Improved customization of HTTP listeners (async).
- Improved parsing and formatting of URI components.
- Use of Java 1.8 date / time APIs

###### 12 July 2022 - HttpComponents Core 5.1.4 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 5.1.3, mostly in H2 protocol
handling.

###### 8 June 2022 - HttpComponents Client 5.2-beta1 released

This is the first BETA release in the 5.2 release series that upgrades minimal JRE level to version 8 (8u251 is
required) and includes several protocol level and API improvements. It also includes all bug fixes from the 5.1 branch.

Notable changes and features included in the 5.2 series:

- Upgrade to Java 8.
- Improved support for TLS upgrade and HTTP protocol upgrade (async).
- Support for H2 tunneling via HTTP/1.1 proxy.
- Conformance to RFC 7617 (The 'Basic' HTTP Authentication Scheme).
- Migration to Java 8 Time primitives in State Management and Cache APIs.
- Base64 codec based on Commons Codec replaced with JRE Base64 codec. Dependency on Commons Codec dropped.
- Optional support for BR (Brotli) decompression.

###### 2 June 2022 - HttpComponents Core 5.2-beta2 released

This BETA release corrects a major regression in the TLS handshake handling code introduced in the previous BETA
release.

###### 17 March 2022 - HttpComponents Core 5.2-beta1 released

This is the first BETA release in the 5.2 release series that marks the completion of major API changes and starts the
transition toward a GA phase.

###### 1 February 2022 - HttpComponents Client 5.1.3 (GA) released

This release upgrades HttpCore to the latest 5.1 version and fixes a number of issues found since release 5.1.2.

###### 23 December 2021 - HttpComponents Core 5.1.3 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 5.1.2 including a regression in
treating HTTP/1.0 connections as persistent by default.

###### 13 December 2021 - HttpComponents AsyncClient 4.1.5 (GA) released

This is a maintenance release that fixes a number of issues discovered since 4.1.4.

###### 8 December 2021 - HttpComponents Core 4.4.15 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 4.4.14.

###### 17 November 2021 - HttpComponents Client 5.1.2 (GA) released

This is an emergency release that fixes a regression introduced in the previous release that can lead to a connection
leak when executing requests with a non-repeatable streaming entity with the classic (blocking) HttpClient. Async and
minimal HttpClient implementations are not affected by the regression.

###### 3 November 2021 - HttpComponents Client 5.2-alpha1 released

This is the first ALPHA release in the 5.2 release series that upgrades minimal JRE level to version 1.8 (8u251 is
required) and includes several protocol level and API improvements. It also includes all bug fixes from the 5.1 branch.

###### 26 October 2021 - HttpComponents Client 5.1.1 (GA) released

This release upgrades HttpCore to the latest 5.1 version and fixes a number of issues found since release 5.1.

###### 20 September 2021 - HttpComponents Core 5.2-alpha2 released

This is the second ALPHA release in the 5.2 release series that fixes a regression in the TLS layer introduced by the
previous ALPHA and adds a number of incremental improvements.

Notable changes and features included in the 5.2 series:

- Upgrade to Java 1.8.
- Improved support for TLS upgrade and HTTP protocol upgrade (async).
- Improved customization of HTTP listeners (async).
- Improved parsing and formatting of URI components.

###### 30 September 2021 - HttpComponents Core 5.1.2 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 5.1.1 including defects in
handling of identity transfer encoded HTTP/1.1 response messages.

###### 13 September 2021 - HttpComponents Core 5.2-alpha1 released

This is the first ALPHA release in the 5.2 release series that upgrades minimal JRE level to version 1.8 (8u251 is
required) and includes several protocol level and API improvements. It also includes all bug fixes from the 5.1 branch.

###### 12 May 2021 - HttpComponents Client 5.1 (GA) released

This is the first GA release in the 5.1 release series.

Notable changes and features included in the 5.1 series:

- Conditional conformance with RFC 3986 (Uniform Resource Identifier (URI): Generic Syntax).
- Improved support for out of sequence response message handing by the classic (blocking) HTTP transport.
- Improved message builders.

Please note that 5.1 is going to be the last release series compatible with Java 1.7. HttpClient will require Java 1.8
as of 5.2.

###### 12 May 2021 - HttpComponents Client 5.0.4 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 5.0.3 and upgrades HttpCore to
version 5.0.4 and Common Codec to version 1.15.

###### 6 May 2021 - HttpComponents Core 5.1.1 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 5.1 including a major defect
that can cause a connection pool resource leak.

###### 6 May 2021 - HttpComponents Core 5.0.4 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 5.0.3 including a major defect
that can cause a connection pool resource leak.

###### 15 March 2021 - HttpComponents Core 5.1 (GA) released

This is the first GA release in the 5.1 release series.

Notable changes and features included in the 5.1 series:

- Conditional conformance with RFC 3986 (Uniform Resource Identifier (URI): Generic Syntax).
- Improved support for out of sequence response message handing by the the classic (blocking)
  HTTP transport.
- Improved message builders.

###### 12 February 2021 - HttpComponents Client 5.1-beta1 released

This is the first BETA release in the 5.1 release series that includes a number of new features as well performance
optimizations in the classic HTTP transport.

Notable changes and features included in the 5.1 series:

- Conditional conformance with RFC 3986 (Uniform Resource Identifier (URI): Generic Syntax).
- Improved support for out of sequence response message handing by the classic (blocking) HTTP transport.

###### 8 February 2021 - HttpComponents Core 5.1-beta3 released

This is likely the last BETA release in the 5.1 release series. The next release is expected to be 5.1 GA. This beta
includes a number of new features as well as bug fixes from the stable 5.0.x branch.

###### 3 December 2020 - HttpComponents Core 5.1-beta2 released

This is the second BETA release in the 5.1 release series that includes a number of new features as well as bug fixes
from the stable 5.0.x branch.

###### 3 December 2020 - HttpComponents Core 5.0.3 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 5.0.2 including a defect in the
async (non-blocking) transport potentially causing an infinite event loop and and excessive CPU utilization.

###### 1 December 2020 - HttpComponents Core 4.4.14 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 4.4.13 including two defects in
the async (non-blocking) transport potentially causing an infinite event loop and and excessive CPU utilization.

###### 8 October 2020 - HttpComponents Client 5.0.3 (GA) released

This is a maintenance release that fixes incorrect handling of malformed authority component in request URIs.

###### 8 October 2020 - HttpComponents Client 4.5.13 (GA) released

This is a maintenance release that fixes incorrect handling of malformed authority component in request URIs.

###### 28 September 2020 - HttpComponents Client 5.0.2 (GA) released

This is a maintenance release that upgrades HttpCore to the latest version and addresses a number of issues found since
5.0.1 release.

###### 21 September 2020 - HttpComponents Core 5.1-beta1 released

This is the first BETA release in the 5.1 release series that includes a number of new features as well performance
optimizations in the classic HTTP transport.

###### 13 September 2020 - HttpComponents Core 5.0.2 (GA) released

This release reverts changes to early response handling logic introduced in 5.0.1 and fixes a number of minor defects.
Improvement of the early response handling by the classic client protocol handler has been moved to 5.1.

###### 15 June 2020 - HttpComponents Client 5.0.1 (GA) released

This is a maintenance release that upgrades HttpCore to the latest version and addresses a number of issues found since
5.0 release.

###### 10 June 2020 - HttpComponents Core 5.0.1 (GA) released

This is a maintenance release that improves handling of early response messages by the classic client protocol handler
and fixes a number of minor defects.

###### 9 March 2020 - HttpComponents Client 4.5.12 (GA) released

This is a maintenance release that fixes a regression introduced by the previous release that caused rejection of
certificates with non-standard domains.

###### 24 February 2020 - HttpComponents Client 5.0 (GA) released

This is the first stable (GA) release of HttpClient 5.0.

Notable changes and features included in the 5.0 series are:

- Support for the HTTP/2 protocol and conformance to requirements and recommendations of the latest HTTP/2 protocol
  specification documents
  (RFC 7540, RFC 7541.)

  Supported features:
    - HPACK header compression
    - Stream multiplexing (client and server)
    - Flow control
    - Response push
    - Message trailers
    - Expect-continue handshake
    - Connection validation (ping)
    - Application-layer protocol negotiation (ALPN)
    - TLS 1.2 security features

- Improved conformance to requirements and recommendations of the latest HTTP/1.1 protocol specification documents (
  RFC 7230, RFC 7231.)
- New connection pool implementation with lax connection limit guarantees and better performance under higher
  concurrency due to absence of a global pool lock.
- Support for Reactive Streams API http://www.reactive-streams.org/
- Package name space changed to 'org.apache.hc.client5'.
- Maven group id changed to 'org.apache.httpcomponents.client5'.

HttpClient 5.0 releases can be co-located with earlier major versions on the same classpath due to the change in package
names and Maven module coordinates.

###### 18 February 2020 - HttpComponents Core 5.0 (GA) released

This is the first stable (GA) release of HttpCore 5.0.

Notable changes and features included in the 5.0 series:

- Support for HTTP/2 protocol and conformance to requirements and recommendations of the latest HTTP/2 protocol
  specification (RFC 7540, RFC 7541)

  Supported features:
    - HPACK header compression
    - stream multiplexing (client and server)
    - flow control
    - response push (client and server)
    - message trailers
    - expect-continue handshake
    - connection validation (ping)
    - application-layer protocol negotiation (ALPN) on Java 9+
    - TLS 1.2 security features

  Features out of scope for 5.0 release:

    - padding of outgoing frames
    - stream priority
    - plain connection HTTP/1.1 upgrade
    - CONNECT method

- Improved conformance to requirements and recommendations of the latest HTTP/1.1 protocol specification (RFC 7230, RFC
    7231)
- New asynchronous HTTP transport APIs consistent for both HTTP/1.1 and HTTP/2 transport.
- Redesigned I/O reactor APIs and improved NIO based reactor implementation for a greater performance and scalability.
- Support for server-side request filters for classic and asynchronous server implementations. Request filters could be
  used to implement cross-cutting protocol aspects such as the 'expect-continue' handshaking and user authentication /
  authorization.
- Support for Reactive Streams API http://www.reactive-streams.org/
- Redesigned connection pool implementation with strict connection limit guarantees. The connection pool is expected to
  have a better performance under higher concurrency due to reduced global pool lock contention.
- New connection pool implementation with lax connection limit guarantees and better performance under higher
  concurrency due to absence of a global pool lock.
- Package name space changed to 'org.apache.hc.core5'

- Maven group id changed to 'org.apache.httpcomponents.core5'

###### 27 January 2020 - HttpComponents Client 5.0-beta7 released

This BETA release upgrades HttpCore to the latest version and addresses a number of issues found since the previous BETA
release.

###### 20 January 2020 - HttpComponents Client 4.5.11 (GA) released

This is a maintenance release that fixes a number defects discovered since 4.5.10 and upgrades HttpCore dependency to
version 4.4.13.

###### 14 January 2020 - HttpComponents Core 4.4.13 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 4.4.12.

###### 8 January 2019 - HttpComponents HttpCore 5.0-beta11 released

This BETA fixes HTTP/2 SETTINGS_HEADER_TABLE_SIZE negotiation and HTTP/2 connection window management logic as well as
fixes a number of other defects found since the last release.

###### 31 October 2019 - HttpComponents HttpCore 5.0-beta10 released

This BETA fixes a bug in the HTTP/2 setting handshake implementation and a performance regression in HTTP/1.1 protocol
handler.

###### 10 October 2019 - HttpComponents HttpClient 5.0-beta6 released

This BETA release picks up the latest fixes and performance improvements from HttpCore and addresses a number of issues
found since the previous BETA release.

###### 7 October 2019 - HttpComponents HttpCore 5.0-beta9 released

This BETA fixes a number of defects found since the last release, improves behavior of the lax (concurrent) connection
pools, simplifies and improves input event handling of SSL/TLS sessions and the HTTP/1.1 protocol event handler.

###### 10 Sept 2019 - HttpComponents Client 4.5.10 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 4.5.9.

###### 5 Sept 2019 - HttpComponents Core 4.4.12 (GA) released

This is a maintenance release that corrects a number of defects discovered since release 4.4.11.

###### 22 July 2019 - HttpComponents HttpClient 5.0-beta5 released

This BETA release picks up the latest fixes and performance improvements from HttpCore and addresses a number of issues
found since the previous BETA release.

###### 15 July 2019 - HttpComponents HttpCore 5.0-beta8 released

This BETA fixes a number of defects found since the last release and adds several convenience factory and builder
classes, mainly for TLS configuration and HTTP message construction.

Notable new features in this release:

- As of this version all server and requester implementations exclude weak TLS protocol versions and ciphers.

###### 12 June 2019 - HttpComponents HttpClient 4.5.9 (GA) released

This is a maintenance release that fixes a number defects discovered since 4.5.8.

###### 8 April 2019 - HttpComponents HttpClient 5.0-beta4 released

This BETA release picks up the latest fixes and performance improvements from HttpCore and addresses a number of issues
found since the previous BETA release.

Notable features in this release:

- Security improvements.
- URI handling improvements.

###### 31 March 2019 - HttpComponents HttpClient 4.5.8 (GA) released

This is a maintenance release that makes request URI normalization configurable on per request basis and also ports
several improvements in URI handling from HttpCore master.

###### 4 March 2019 - HttpComponents HttpCore 5.0-beta7 released

This BETA release adds support for SOCKS version 5, improves support for TLS handshake timeout configuration, improves
URI builder, and fixes various defects.

Notable new features in this release:

- SOCKS version 5 support
- Improved TLS handshake timeout configuration

###### 24 January 2019 - HttpComponents HttpClient 4.5.7 (GA) released

This is a maintenance release that corrects Automatic-Module-Name definitions added in the previous release and fixes a
number of minor defects discovered since 4.5.6.

###### 21 January 2019 - HttpComponents HttpCore 4.4.11 (GA) released

This is a maintenance release that corrects a number of defects in non-blocking SSL session code that caused
compatibility issues with TLSv1.3 protocol implementation shipped with Java 11.

###### 17 December 2018 - HttpComponents HttpClient 5.0-beta3 released

This BETA release adds support for advanced TLS functions (such as ALPN protocol negotiation)
on Java 1.7 and Java 1.8 through Conscrypt TLS library and picks up the latest fixes and performance improvements from
HttpCore.

Notable new features in this release:

- TLS ALPN protocol negotiation support on older JREs through Conscrypt TLS library.

###### 6 December 2018 - HttpComponents HttpCore 5.0-beta6 released

This BETA release adds support for advanced TLS functions (such as ALPN protocol negotiation)
on Java 1.7 and Java 1.8 through Conscrypt TLS library, and fixes a number of defects found since the previous release.

Notable new features in this release:

- TLS ALPN protocol negotiation on Java 1.7 and Java 1.8 through Conscrypt TLS library

###### 29 October 2018 - HttpComponents HttpClient 5.0-beta2 released

This BETA release resolves compatibility issues with Java 11 new TLS engine as well as a number of defects found since
the previous release.

Notable new features in this release:

- JDK 11 compatibility
- Support for request specific push consumers
- Support for Reactive Streams API http://www.reactive-streams.org/

###### 22 October 2018 - HttpComponents HttpCore 5.0-beta5 released

This BETA release adds support for Reactive Streams API [http://www.reactive-streams.org/]
and fixes compatibility issues with Java 11 new TLS engine as well as a number of defects found since the previous
release.

This release also includes a redesigned HTTP stress test tool loosely based on Apache Benchmark (AB) command interface
with support for HTTP/2.

###### 29 August 2018 - HttpComponents HttpCore 5.0-beta3 released

This BETA release fixes a number of defects found since the previous release, adds several incremental improvements and
improves javadoc documentation.

###### 23 July 2018 - HttpComponents HttpAsyncClient 4.1.4 (GA) released

This is a maintenance release that adds Automatic-Module-Name to the manifest for compatibility with Java 9 Platform
Module System and fixes a number of issues discovered since 4.1.3.

###### 9 July 2018 - HttpComponents HttpClient 4.5.6 (GA) released

This is a maintenance release that adds Automatic-Module-Name to the manifest for compatibility with Java 9 Platform
Module System and fixes a number of issues discovered since 4.5.5.

###### 3 July 2018 - HttpComponents HttpCore 4.4.10 (GA) released

This is a maintenance release that adds Automatic-Module-Name to the manifest for compatibility with Java 9 Platform
Module System and fixes a number of issues discovered since 4.4.9.

###### 22 January 2018 - HttpComponents HttpClient 4.5.5 GA released

This is a maintenance release that fixes a regression introduced by the previous release causing a NPE in
SystemDefaultCredentialsProvider.

###### 18 January 2018 - HttpComponents HttpClient 5.0-beta1 released

This is the first BETA release of HttpClient 5.0. The 5.0 release serices introduces support for the HTTP/2 protocol and
event driven messaging APIs consistent for all supported HTTP protocol versions.

HttpClient ships with two client implementations:

- HttpClient Classic is based on the classic (blocking) I/O model; largely compatible with the 4.x APIs; supports
  HTTP/1.1 only.
- HttpClient Async is based on NIO model; new event driven APIs consistent for all supported HTTP protocol versions;
  supports both HTTP/1.1 and HTTP/2.

Notable new features in this release:

- New asynchronous HTTP cache backend APIs
- Fully asynchronous HTTP cache backend based on Memcached
- Support for bulk cache retrieval

###### 15 January 2018 - HttpComponents HttpCore 5.0-beta2 released

This BETA release fixes a number of defects found since the previous release and adds several incremental improvements.

###### 11 January 2018 - HttpComponents HttpCore 4.4.9 (GA) released

This is a maintenance release that fixes a number of issues discovered since 4.4.8 and adds a few low-level methods.

###### 4 December 2017 - HttpComponents HttpClient 4.5.4 (GA) released

This is a maintenance release that fixes a number of issues discovered since release 4.5.3.

###### 27 November 2017 - HttpComponents HttpClient 5.0-alpha3 released

This is a major release that introduces support for the HTTP/2 protocol and event driven messaging APIs consistent for
all supported HTTP protocol versions.

Notable new features in this release:

- Asynchronous HttpClient implementations optimized for HTTP/2 multiplexed request execution.
- Full support for HTTP caching by asynchronous HttpClient implementations including streaming message exchanages.

###### 6 November 2017 - HttpComponents HttpCore 5.0-beta1 released

This is a major release that renders HttpCore API incompatible with the stable 4.x branch and upgrades HTTP/1.1 and
HTTP/2 protocol conformance to the requirements and recommendations of the latest protocol specification.

Notable new features in this release:

- New HTTP/2 requester optimized for multiplexed execution of requests.

###### 7 October 2017 - HttpComponents HttpCore 4.4.8 (GA) released

This is a maintenance release that fixes a number of issues discovered since release 4.4.7.

###### 14 September 2017 - HttpComponents HttpCore 4.4.7 (GA) released

This is a maintenance release that fixes a number of issues discovered since release 4.4.6.

###### 4 September 2017 - HttpComponents HttpCore 5.0-alpha4 released

This is a major release that renders HttpCore API incompatible with the stable 4.x branch and upgrades HTTP/1.1 and
HTTP/2 protocol conformance to the requirements and recommendations of the latest protocol specification.

###### 11 May 2017 - HttpComponents HttpClient 5.0-alpha2 released

This is a major release that introduces support for HTTP/2 protocol and event driven messaging APIs consistent for all
supported HTTP protocol versions.

###### 2 May 2017 - HttpComponents HttpCore 5.0-alpha3 released

This is a major release that renders HttpCore API incompatible with the stable 4.x branch and upgrades HTTP/1.1 and
HTTP/2 protocol conformance to the requirements and recommendations of the latest protocol specification.

###### 10 February 2017 - HttpComponents HttpAsyncClient 4.1.3 (GA) released

This is a maintenance release that fixes a number of issues discovered since release 4.1.2.

###### 27 January 2017 - HttpComponents HttpClient 4.5.3 (GA) released

This is a maintenance release that fixes a number of issues discovered since release 4.5.2.

###### 12 January 2017 - HttpComponents HttpCore 4.4.6 (GA) released

This is a maintenance release that fixes a number of issues discovered since release 4.4.5.

###### 27 December 2016 - HttpComponents HttpCore 5.0-alpha2 released

This is a major release that renders HttpCore API incompatible with the stable 4.x branch and upgrades HTTP/1.1 and
HTTP/2 protocol conformance to the requirements and recommendations of the latest protocol specification.

###### 27 June 2016 - HttpComponents HttpAsyncClient 4.1.2 (GA) released

This is a maintenance release that fixes a number of issues discovered since release 4.1.1.

###### 14 June 2016 - HttpComponents HttpCore 4.4.5 (GA) released

This is a maintenance release that fixes a number of issues discovered since release 4.4.4.

###### 1 March 2016 - HttpComponents HttpClient 4.5.2 (GA) released

This is a maintenance release that fixes a number of minor bugs reported since 4.5.1.

###### 28 January 2016 - HttpComponents HttpClient 5.0-alpha1 released

This is a major release that renders HttpClient API incompatible with the stable 4.x branch and upgrades HTTP/1.1
protocol conformance to the requirements and recommendations of the latest protocol specification. This release lays the
foundation for transition to HTTP/2 as the primary transport protocol in the future releases.

###### 3 January 2016 - HttpComponents HttpCore 5.0-alpha1 released

This is a major release that renders HttpCore API incompatible with the stable 4.x branch and upgrades HTTP/1.1 protocol
conformance to the requirements and recommendations of the latest protocol specification. This release lays the
foundation for transition to HTTP/2 as the primary transport protocol in the future releases.

###### 9 November 2015 - HttpComponents HttpAsyncClient 4.1.1 (GA) released

This is a maintenance release that fixes a number of issues discovered since release 4.1 and upgrades HttpCore and
HttpClient dependencies.

###### 4 November 2015 - HttpComponents HttpCore 4.4.4 (GA) released

This is a maintenance release that fixes a number of issues discovered since release 4.4.3.

###### 16 September 2015 - HttpComponents HttpClient 4.5.1 (GA) released

This is a maintenance release that fixes a number of minor bugs reported since 4.5.

###### 11 September 2015 - HttpComponents HttpCore 4.4.3 (GA) released

This maintenance release fixes a bug in non-blocking HTTP request pipelining code discovered since 4.3.1.

###### 5 June 2015 - HttpClient 4.5 (GA) released

HttpClient 4.5 (GA) is a minor feature release that includes several incremental enhancements to the exisitng
functionality such as support for private domains in the Mozilla Public Suffix List.

###### 23 April 2015 - HttpAsyncClient 4.1 (GA) released

This is the first stable (GA) release of HttpAsyncClient 4.1. Notable features and enhancements included in 4.1 series
are:

- Support for pipelined request execution
- Support for the latest HTTP state management specification (RFC 6265). Please note that the old cookie policy is still
  used by default for compatibility reasons. RFC 6265 compliant cookie policies need to be explicitly configured by the
  user. Please also note that as of next feature release support for Netscape draft, RFC 2109 and RFC 2965 cookie
  policies will be deprecated and disabled by default. It is recommended to use RFC 6265 compliant policies for new
  applications unless compatibility with RFC 2109 and RFC 2965 is required and to migrate existing applications to the
  default cookie policy.
- Enhanced, redesigned and rewritten default SSL hostname verifier with improved RFC 2818 compliance
- Default SSL hostname verifier and default cookie policy now validate certificate identity and cookie domain of origin
  against the public suffix list maintained by Mozilla.org
  <https://publicsuffix.org/list>
- Authentication cache thread-safety: authentication cache used by HttpClient is now thread-safe and can be shared by
  multiple threads in order to re-use authentication state for subsequent requests

###### 31 March 2015 - HttpComponents HttpClient 4.4.1 (GA) released

This is a maintenance release that fixes a number of bugs reported since 4.4.

###### 20 March 2015 - HttpComponents HttpCore 4.4.1 (GA) released

This is a maintenance release that fixes a number of minor bugs found since 4.4.

###### 5 February 2015 - HttpComponents HttpClient 4.4 released

This is the first stable (GA) release of HttpClient 4.4. Notable features and enhancements included in 4.4 series are:

- Support for the latest HTTP state management specification (RFC 6265). Please note that the old cookie policy is still
  used by default for compatibility reasons. RFC 6265 compliant cookie policies need to be explicitly configured by the
  user. Please also note that as of next feature release support for Netscape draft, RFC 2109 and RFC 2965 cookie
  policies will be deprecated and disabled by default. It is recommended to use RFC 6265 compliant policies for new
  applications unless compatibility with RFC 2109 and RFC 2965 is required and to migrate existing applications to the
  default cookie policy.
- Enhanced, redesigned and rewritten default SSL hostname verifier with improved RFC 2818 compliance
- Default SSL hostname verifier and default cookie policy now validate certificate identity and cookie domain of origin
  against the public suffix list maintained by Mozilla.org
  <https://publicsuffix.org/list>
- More efficient stale connection checking: indiscriminate connection checking which results in approximately 20 to 50
  ms overhead per request has been deprecated in favor of conditional connection state validation (persistent
  connections are to be re-validated only if a specified period inactivity has elapsed)
- Authentication cache thread-safety: authentication cache used by HttpClient is now thread-safe and can be shared by
  multiple threads in order to re-use authentication state for subsequent requests
- Native Windows Negotiate and NTLM via SSPI through JNA: when running on Windows OS HttpClient configured to use native
  NTLM or SPNEGO authentication schemes can make use of platform specific functionality via JNA and current user
  credentials. This functionality is still considered experimental, known to have compatibility issues and subject to
  change without prior notice.

###### 17 December 2014 - HttpComponents HttpCore 4.4 released

This is the first stable (GA) release of HttpCore 4.4. The most notable features included in 4.4 series are:

- Support for pipelined request processing on the server side
- Support for pipelined request execution on the client side
- Simplified bootstrapping of blocking and non-blocking (NIO) HTTP server implementations
- Inclusion of SSL context initialization utilities from HttpClient

###### 6 November 2014 - HttpComponents HttpClient 4.3.6 (GA) released

This is a maintenance release that fixes several problems with HttpClient OSGi bundle as well as some other issues
reported since release 4.3.5.

Please note that as of this release HttpClient disables all versions of SSL (including SSLv3)
in favor of the TLS protocol by default. Those users who wish to continue using SSLv3 need to explicitly enable support
for it.

###### 22 October 2014 - HttpComponents HttpCore 4.3.3 (GA) released

This maintenance release fixes a number of bugs found since 4.3.2, mostly in the NIO transport components. All users of
HttpCore 4.3 are advised to upgrade.

###### 17 October 2014 - HttpComponents HttpAsyncClient 4.4-beta1 released

This is the first BETA release of HttpAsyncClient 4.1. Notable features and enhancements included in 4.1 series are:

- Support for pipelined request execution
- Enhanced redesigned and rewritten default SSL hostname verifier with improved RFC 2818 compliance
- Default SSL hostname verifier and default cookie policy now validate certificate identity and cookie domain of origin
  against the public suffix list maintained by Mozilla.org
  <https://publicsuffix.org/list>
- Authentication cache thread-safety: authentication caches used by HttpAsyncClient is now thread-safe and can be shared
  by multiple contexts in order to re-use authentication state for subsequent requests

###### 28 September 2014 - HttpComponents HttpClient 4.4-beta1 released

This is the first BETA release of HttpClient 4.4. Notable features and enhancements included in 4.4 series are:
enhanced redesigned and rewritten default SSL hostname verifier with improved RFC 2818 compliance; default SSL hostname
verifier and default cookie policy now validate certificate identity and cookie domain of origin against the public
suffix list maintained by Mozilla.org; native windows Negotiate/NTLM via JNA; more efficient stale connection checking;
authentication cache thread-safety

###### 22 September 2014 - HttpComponents HttpCore 4.4-beta1 released

This is the first BETA release from the 4.4.x development branch. The most notable features included in 4.4 series are:
support for pipelined request processing on the server side; support for pipelined request execution on the client side;
simplified bootstrapping of blocking and non-blocking (NIO) HTTP server implementations.

###### 10 Aug 2014 - HttpComponents HttpAsyncClient 4.0.2 (GA) released

HttpAsyncClient 4.0.2 (GA) is a bug fix release that addresses several issues reported since release 4.0.1.

###### 10 Aug 2014 - HttpComponents HttpClient 4.3.5 (GA) released

HttpClient 4.3.5 (GA) is a bug fix release that addresses several issues reported since release 4.3.4.

###### 30 June 2014 - HttpComponents HttpClient 4.4-alpha1 released

This is the first ALPHA release from the 4.4.x development branch. Notable features and enhancements included this
release are: more efficient stale connection checking, native Windows Negotiate/NTLM via JNA, authentication cache
thread-safety

###### 18 June 2014 - HttpComponents HttpCore 4.4-alpha1 released

This is the first release from the 4.4.x development branch. The most notable features included in this release are:
support for pipelined request processing on the server side, support for pipelined request execution on the client
sides, simplified bootstrapping of blocking and non-blocking (NIO) HTTP server implementations

###### 6 June 2014 - HttpComponents HttpClient 4.3.4 (GA) released

HttpClient 4.3.4 (GA) is a maintenance release that improves performance in high concurrency scenarios. This version
replaces dynamic proxies with custom proxy classes and eliminates thread contention in java.reflect.Proxy.newInstance()
when leasing connections from the connection pool and processing response messages

###### 26 February 2014 - HttpComponents HttpClient 4.3.3 (GA) released

HttpClient 4.3.3 (GA) is a bug fix release that fixes a regression introduced by the previous release causing a
significant performance degradation in compressed content processing.

Users of HttpClient 4.3 are encouraged to upgrade.

###### 24 February 2014 - HttpComponents HttpAsyncClient 4.0.1 (GA) released

This maintenance release fixes a number of bugs including incorrect OSGi bundle metadata found since release 4.0. This
release also upgrades HttpCore and HttpClient dependencies to the latest stable versions.

Users of HttpAsyncClient 4.0 are advised to upgrade.

###### 17 February 2014 - HttpComponents HttpCore 4.3.2 (GA) released

This maintenance release fixes a number of bugs and regressions found since 4.3.1, mostly in the NIO transport
components. All users of HttpCore 4.3 are advised to upgrade.

###### 19 January 2014 - HttpComponents HttpClient 4.3.2 (GA) released

HttpClient 4.3.2 (GA) is a maintenance release that delivers a number of improvements as well as bug fixes for issues
reported since 4.3.1 release. SNI support for Oracle JRE 1.7+ is being among the most notable improvements.

Users of HttpClient 4.3 are encouraged to upgrade.

###### 27 December 2013 - HttpComponents HttpCore 4.3.1 (GA) released

This maintenance release fixes a number of bugs and regressions found since 4.3, mostly in the NIO transport components.
All users of HttpCore 4.3 are advised to upgrade.

###### 31 October 2013 - HttpComponents HttpAsyncClient 4.0 (GA) released

This is the first stable (GA) release of Apache HttpAsyncClient 4.0. HttpAsyncClient is a library for asynchronous
client-side HTTP communication built on top of HttpCore NIO transport. It is a complementary library to Apache
HttpClient intended and optimized for special cases whereby ability to scale to many thousands of concurrent connections
is more important than performance in terms of raw data throughput.

HttpAsyncClient 4.0 is designed to have similar APIs as Apache HttpClient 4.3 and a comparable feature set. In addition
HttpAsyncClient provides full support for zero-copy file upload and download operations. It presently does not support
transparent content decompression and automatic I/O error recovery. These features may be added in future releases.

###### 7 October 2013 - HttpComponents HttpClient 4.3.1 (GA) released

This is a maintenance release that addresses a number of issues reported since release 4.3, including one major security
issue. Users of HttpClient 4.3 are strongly advised to upgrade.

###### 12 September 2013 - HttpComponents HttpClient 4.3 (GA) released

This is the first stable (GA) release of HttpClient 4.3. The 4.3 branch enhances HttpClient in several key areas and
includes several notable features and improvements:

- Support for Java 7 try-with-resources for resource management (connection release.)
- Added fluent Builder classes for HttpEntity, HttpRequest, HttpClient and SSLContext instances.
- Deprecation of preference and configuration API based on HttpParams interface in favor of constructor injection and
  plain configuration objects.
- Reliance on object immutability instead of access synchronization for thread safety. Several old classes whose
  instances can be shared by multiple request exchanges have been replaced by immutable equivalents.
- DefaultHttpClient, DecompressingHttpClient, CachingHttpClient and similar classes are deprecated in favor of builder
  classes that produce immutable HttpClient instances.
- HttpClient builders now dynamically construct a request execution pipeline tailored specifically to the user
  configuration by physically excluding unnecessary protocol components.
- There is now an option to construct a minimal HttpClient implementation that can only execute basic HTTP message
  exchanges without redirects, authentication, state management or proxy support. This feature might be of particular
  use in web crawler development.
- There is now option to avoid strict URI syntax for request URIs by executing HTTP requests with an explicitly
  specified target host. HttpClient will no longer attempt to parse the request URI if it does not need to extract the
  target host from it.

This release also includes all fixes from the stable 4.2.x release branch.

###### 12 September 2013 - HttpComponents HttpClient 4.2.6 (GA) released

This is a maintenance release that addresses a number of non-critical issues reported since release 4.2.5.

###### 5 August 2013 - HttpComponents HttpCore 4.3 released

This is the first stable (GA) release of HttpCore 4.3. The most notable features in the 4.3 branch are:

- Deprecation of preference and configuration API based on HttpParams interface in favor of constructor injection and
  plain configuration objects.
- Reliance on object immutability instead of access synchronization for thread safety. Several old classes whose
  instances can be shared by multiple request exchanges have been replaced by immutable equivalents.

The 4.3 branch also contains performance optimizations such as reduced TCP packet fragmentation and more efficient lease
/ release operations for pools of persistent connections on the client side.

This release also includes all fixes from the 4.2.x release branch.

###### 5 August 2013 - HttpComponents HttpCore 4.2.5 released

This is a maintenance release that fixes a number of bugs found in NIO components since 4.2.4. Users of earlier versions
of HttpCore 4.2 are advised to upgrade.

This is likely to be the last release in the 4.2.x branch.

###### 12 June 2013 - HttpComponents HttpClient 4.3-beta2 released

This is the second BETA release of HttpClient 4.3. The 4.3 branch enhances HttpClient in several key areas and includes
several notable features and improvements: Support for Java 7 try-with-resources for resource management (
connection release); fluent Builder classes for HttpEntity, HttpRequest and HttpClient instances, deprecation of
preference and configuration API based on HttpParams interface in favor of constructor injection and plain configuration
objects, reliance on object immutability instead of access synchronization for thread safety.

This release also includes all fixes from the stable 4.2.x release branch.

###### 16 May - HttpComponents HttpAsyncClient 4.0-beta4 released

The 4.0 BETA4 release delivers significant performance improvements in request execution, especially for short HTTP
messages, and also re-aligns programming interfaces used by the library with HttpCore 4.3 and HttpClient 4.3 APIs.
Configuration and preference APIs of HttpAsyncClient are now consistent with those used by HttpClient 4.3.

###### 8 May - HttpComponents HttpCore 4.3-beta2 released

This is the second BETA release from the 4.3.x release branch. This release addresses performance issues in the
non-blocking connection pool implementation and also includes a number of performance improvements in the low level NIO
based transport components.

###### 24 April 2013 - HttpComponents HttpClient 4.2.5 (GA) released

This is a maintenance release that addresses a number of issues reported since release 4.2.4 including a major bug that
can lead to re-use of persistent connections in a inconsistent state.

###### 11 April 2013 - HttpComponents HttpClient 4.3-beta1 released

This is the first BETA release of HttpClient 4.3. The 4.3 branch enhances HttpClient in several key areas and includes
several notable features and improvements: Support for Java 7 try-with-resources for resource management (connection
release); fluent Builder classes for HttpEntity, HttpRequest and HttpClient instances, deprecation of preference and
configuration API based on HttpParams interface in favor of constructor injection and plain configuration objects,
reliance on object immutability instead of access synchronization for thread safety.

This release also includes all fixes from the stable 4.2.x release branch.

###### 11 April 2013 - HttpComponents HttpClient 4.2.4 (GA) released

This is a bug fix release that addresses a number of issues reported since release 4.2.3.

###### 25 March 2013 - HttpComponents HttpCore 4.3-beta1 released

This is the first BETA release from the 4.3 release branch. The main theme of the 4.3 release series is streamlining of
component configuration and deprecation of the old configuration API based on HttpParams in favor of constructor-based
dependency injection and plain objects for configuration parameters.

This release also includes performance optimizations intended to reduce TCP packet fragmentation when writing out HTTP
messages both in blocking and non-blocking I/O modes, which should result in up to 20% higher throughput for short
entity enclosing messages.

###### 25 March 2013 - HttpComponents HttpCore 4.2.4 released

This is a maintenance release that fixes a number of bugs found in NIO components since 4.2.3. We advise users of
HttpCore NIO of all versions to upgrade.

###### 21 January 2013 - HttpComponents HttpClient 4.3-alpha1 released

This is the first ALPHA release of HttpClient 4.3. The 4.3 branch enhances HttpClient in several key areas and includes
several notable features and improvements: Support for Java 7 try-with-resources for resource management (
connection release); fluent Builder classes for HttpEntity, HttpRequest and HttpClient instances, deprecation of
preference and configuration API based on HttpParams interface in favor of constructor injection and plain configuration
objects, reliance on object immutability instead of access synchronization for thread safety.

###### 15 January 2013 - HttpComponents HttpClient 4.2.3 (GA) released

This is a bug fix release that addresses a number of issues reported since release 4.2.2. This release also includes a
thoroughly reworked NTLM authentication engine which should result in a better compatibility with the newest Microsoft
products.

###### 08 Dec 2012 - Welcome new HttpComponents committer Karl Wright

Karl Wright has been unanimously voted in as a new HttpComponents committer due to his invaluable help in supporting the
internal NTLM engine and NTLM related authentication code.  
Karl is a committer on a number of ASF projects: Lucene, Lucene connectors, Incubator.

Welcome on board, Karl!

###### 30 November 2012 - HttpComponents HttpCore 4.3-alpha1 released

This is the first release from the 4.3.x release branch. The main theme of the 4.3 release series is streamlining of
component configuration and deprecation of the old configuration API based on HttpParams in favor of constructor-based
dependency injection and plain objects for configuration parameters.

###### 30 November 2012 - HttpComponents HttpCore 4.2.3 (GA) released

HttpCore 4.2.3 is a maintenance release that fixes a number of bugs found since 4.2.2 including a major bug in the NIO
module that can cause an infinite loop in SSL sessions under special circumstances when the remote peer terminates the
session in the middle of SSL handshake. We advise users of HttpCore NIO of all versions to upgrade.

###### 25 October 2012 - HttpComponents HttpClient 4.2.2 (GA) released

HttpClient 4.2.2 is a bug fix release that addresses a number of issues reported since release 4.2.1. Users of
HttpClient 4.2 are advised to upgrade.

###### 29 September 2012 - HttpComponents HttpAsyncClient 4.0-beta3 released

This is a maintenance release that picks up the latest bug fixes in the core components.

###### 23 September 2012 - HttpComponents HttpCore 4.2.2 (GA) released

This is a maintenance release that fixes a number of bugs and regressions found since 4.2.1 including a major bug in the
NIO module causing incorrect handling of outgoing Content-Length delimited messages larger than 2GB. Users of HttpCore
4.2 are advised to upgrade.

###### 29 August 2012 - Welcome new HttpComponents committer William Speirs

William Speirs, a long time contributor to the project, has been unanimously voted in as a new HttpComponents committer.
William is already a committer on Apache Commons project.

Welcome on board, William!

###### 8 August 2011 - HttpComponents HttpAsyncClient 4.0-beta2 released

This release fixes a number of non-critical issues found since release 4.0-beta1 and introduces basic support for
HTTP/1.1 response caching. Please note that caching for streaming HTTP exchanges is currently not supported.

###### 4 August 2012 - Welcome new HttpComponents committer Gary Gregory

By 5 binding votes in favor Gary Gregory has been unanimously voted in as a new HttpComponents committer. Gary is
already a committer on Apache Commons, Logging and Xalan projects.

Welcome on board, Gary!

###### 5 July 2012 - HttpComponents HttpClient 4.2.1 (GA) released

HttpClient 4.2.1 is a bug fix release that addresses a number of issues reported since release 4.2. Users of HttpClient
4.2 are advised to upgrade.

###### 14 June 2012 - HttpComponents HttpCore 4.2.1 (GA) released

HttpCore 4.2.1 is a patch release that fixes a number of non-critical bugs found since 4.2. Users of HttpCore 4.2 are
advised to upgrade.

###### 22 May 2012 - HttpComponents HttpClient 4.2 (GA) released

This is the first stable (GA) release of HttpClient 4.2. The most notable enhancements included in this release are:

- New facade API for HttpClient based on the concept of a fluent interface. The fluent API exposes only the most
  fundamental functions of HttpClient and is intended for relatively simple use cases that do not require the full
  flexibility of HttpClient. However, the fluent API almost fully relieves the users from having to deal with connection
  management and resource deallocation.
- Redesigned and rewritten connection management code.
- Enhanced HTTP authentication API that enables HttpClient to handle more complex authentication scenarios. HttpClient
  4.2 is now capable of making use of multiple authentication challenges and retry authentication with a fall-back
  scheme in case the primary one fails. This can be important for compatibility with Microsoft products that are often
  configured to use SPNEGO/Kerberos as the preferred authentication scheme.

###### 5 May 2012 - HttpComponents HttpCore 4.2 (GA) released

This is the first stable (GA) release of HttpCore 4.2. The most notable features included in this release are connection
pool components for blocking and non-blocking HTTP connections and new asynchronous client and server side protocol
handlers.

New protocol handling API used in conjunction with connection pooling components is expected to make development of
asynchronous HTTP client agents and HTTP proxies easier and less error prone.

Connection pool components are based on mature code migrated from HttpClient and HttpAsyncClient modules but have a
slightly different API that makes a better use of Java standard concurrent primitives.

###### 22 February 2012 - HttpComponents HttpAsyncClient 4.0-beta1 released

This the first BETA release of HttpAsyncClient. This release completes the application programming interface and the
feature set of HttpAsyncClient and upgrades to the latest versions of core and client components (HttpCore 4.2-beta1 and
HttpClient 4.2-beta1). As of this release HttpAsyncClient is expected to be API stable.

###### 10 February 2012 - HttpComponents HttpClient 4.2-beta1 released

This is the first BETA release of HttpClient 4.2. This release completes development of several notable enhancements in
HttpClient: new facade API, redesigned connection management code and new HTTP authentication API.

###### 7 February 2012 - HttpComponents HttpClient 4.1.3 (GA) released

HttpClient 4.1.3 is a bug fix release that addresses a number of non-critical issues found since 4.1.2 primarily in the
HTTP caching module.

###### 1 February 2012 - HttpComponents HttpCore 4.2-beta1 released

This is the first BETA release of HttpCore 4.2. This release ships with an improved asynchronous protocol handling API
and new non-blocking client and server HTTP protocol handler implementations. New API is expected to be more flexible
especially for writing HTTP proxy or gateway type of services. Upstream projects are encouraged to evaluate the new API
and give feedback.

###### 23 December 2011 - HttpComponents HttpCore 4.1.4 (GA) released

HttpCore 4.1.4 is a patch release that fixes a number of bugs found since 4.1.3. It is also likely to be the last
release in the 4.1.x branch.

###### 3 November 2011 - HttpComponents HttpClient 4.2-alpha1 released

This is the first ALPHA release of HttpClient 4.2. The 4.2 branch enhances HttpClient in several key areas and includes
several notable features and improvements: new facade API, redesigned connection management code and new HTTP
authentication API.

###### 29 September 2011 - HttpComponents HttpAsyncClient 4.0-alpha3 released

This is the third ALPHA release of HttpAsyncClient 4.0. This release largely completes the application programming
interface and feature set of HttpAsyncClient. While the API may still change in the course of the ALPHA development
phase, this is expected to be the last round of major API changes and the API is expected to be reasonably stable as of
this release.

###### 23 September 2011 - HttpComponents HttpCore 4.2-alpha2 released

This is the second ALPHA release of HttpCore 4.2. This release comes with completely redesigned and rewritten
asynchronous protocol handlers. New protocol handling API used in conjunction with connection pooling components
introduced in the previous ALPHA release is expected to make development of asynchronous HTTP client agents and HTTP
proxies easier and less error prone.

###### 19 August 2011 - HttpComponents HttpCore 4.2-alpha1 released

This is the first ALPHA release of the 4.2 development branch. The most notable feature included in this release is
support for connection pools of blocking and non-blocking HTTP connections. Connection pool components are based on
mature code migrated from HttpClient and HttpAsyncClient modules but have a slightly different API that makes a better
use of Java standard concurrent primitives. Support for connection pools in HttpCore is expected to make development of
client and proxy HTTP services easier and less error prone.

###### 7 August 2011 - HttpComponents HttpClient 4.1.2 (GA) released

HttpClient 4.1.2 is a bug fix release that addresses a number of non-critical issues reported since release 4.1.1.

###### 31 July 2011 - HttpComponents HttpCore 4.1.3 (GA) released

HttpCore 4.1.3 is a patch release that fixes a critical regression in the non-blocking SSL I/O session code introduced
in the 4.1.2 release.

###### 18 July 2011 - HttpComponents HttpCore 4.1.2 (GA) released

HttpCore 4.1.2 is a patch release that fixes a number of non-critical issues found since release 4.1.1.

###### 24 May 2011 - HttpComponents HttpAsyncClient 4.0-alpha2 released

The second ALPHA release of HttpAsyncClient 4.0 comes with a number of important improvements and enhancements. As of
this version HttpAsyncClient fully supports HTTP state management
(cookies) and HTTP authentication (basic, digest, NTLM, spnego/kerberos). Connection management classes have been
thoroughly reworked and improved. This version also improves support for zero copy file upload / download operations.

###### 20 May 2011 - HttpComponents HttpCore 4.1.1 (GA) released

HttpCore 4.1.1 is a patch release that fixes a number of non-critical issues found since release 4.1.

This release marks the end of support for Java 1.3. As of release 4.2 HttpCore will require Java 1.5 for all its
components.

###### 20 March 2011 - HttpComponents HttpClient 4.1.1 (GA) released

HttpClient 4.1.1 is a bug fix release that addresses a number of issues reported since release 4.1, including one
critical security issue.

###### 23 January 2011 - HttpComponents HttpClient 4.1 (GA) released

The HttpClient 4.1 release builds upon the stable foundation laid by HttpClient 4.0 and adds several functional
improvements and popular features.

- Response caching conditionally compliant with HTTP/1.1 specification (full compliance with MUST requirements, partial
  compliance with SHOULD requirements)
- Full support for NTLMv1, NTLMv2, and NTLM2 Session authentication. The NTLM protocol code was kindly contributed by
  the Lucene Connector Framework project.
- Support for SPNEGO/Kerberos authentication.
- Persistence of authentication data between request executions within the same execution context.
- Support for preemptive authentication for BASIC and DIGEST schemes.
- Support for transparent content encoding. Please note transparent content encoding is not enabled per default in order
  to avoid conflicts with already existing custom content encoding solutions.
- Mechanism to bypass the standard certificate trust verification (useful when dealing with self-signed certificates).
- Simplified configuration for connection managers.
- Transparent support for host multihoming.

###### 18 January 2011 - HttpComponents HttpAsyncClient 4.0-alpha1 released

This is the first public release of HttpAsyncClient. The HttpAsyncClient 4.0 API is considered very experimental and is
expected to change in the course of the ALPHA development phase. This release is primarily intended for early adopters
who may be interested in contributing to the project and in helping shape the new API.

###### 21 November 2010 - HttpComponents HttpClient 4.1-beta1 released

This release finalizes the 4.1 API and brings a number of major improvements to the HTTP caching module. This release
also adds full support for NTLMv1, NTLMv2, and NTLM2 Session authentication schemes. The NTLM protocol code was kindly
contributed by the Lucene Connector Framework project.

###### 19 November 2010 - HttpComponents HttpCore 4.1 (GA) released

This is the first stable release of HttpCore 4.1. This release provides a compatibility mode with JREs that have a
naive (broken) implementation of SelectionKey API and also improves compatibility with the Google Android platform.
There has also been a number of performance related improvements and bug fixes in both blocking and non-blocking
components.

###### 26 October 2010 - Welcome new HttpComponents committer Jonathan Moore

By 4 binding votes in favor and none against Jonathan Moore has been voted in as a new HttpComponents committer.
Jonathan has made major contributions to the new HttpClient caching module.

Welcome on board, Jonathan!

###### 19 September 2010 - HttpComponents HttpClient 4.0.3 (GA) released

This is an emergency release fixing a critical regression in the SSL connection management code.

###### 9 September 2010 - HttpComponents HttpClient 4.0.2 (GA) released

This is a maintenance release that fixes a number of bugs found since 4.0.1. This is likely to be the last release in
the 4.0.x branch.

###### 30 August 2010 - HttpComponents HttpCore 4.1-beta2 released

This release addresses fixes a number of non-critical bugs. It is likely to be the last BETA release in the 4.1 branch.

###### 19 May 2010 - HttpComponents HttpClient 4.1-alpha2 released

This release fixes a number of non-severe bugs discovered since the last release and introduces support for two
frequently requested features:

- HTTP/1.1 response caching
- transparent support for host multihoming
- a mechanism to bypass the standard certificate trust verification (useful when dealing with self-signed certificates)

###### 3 April 2010 - HttpComponents HttpCore 4.1-beta1 released

This release finalizes the API introduced in the 4.1 development branch. It also fixes a number of bugs discovered since
the previous release and delivers a number of performance optimizations in the blocking HTTP transport components. The
blocking HTTP transport is expected to be 5% to 10% faster compared to previous releases.

###### 11 December 2009 - HttpComponents HttpClient 4.1-alpha1 released

This release builds on the stable 4.0 release and adds several functionality improvements and new features.

- Simplified configuration of connection managers.
- Persistence of authentication data between request executions within the same execution context.
- Support for SPNEGO/Kerberos authentication scheme
- Support for transparent content encoding. Please note transparent content encoding is not enabled per default in order
  to avoid conflicts with already existing custom content encoding solutions.

###### 11 December 2009 - HttpComponents HttpClient 4.0.1 (GA) released

This is a bug fix release that addresses a number of issues discovered since the previous stable release. None of the
fixed bugs is considered critical. Most notably this release eliminates dependency on JCIP annotations.

This release is also expected to improve performance by 5 to 10% due to elimination of unnecessary Log object lookups by
short-lived components.

###### 12 September 2009 - HttpComponents HttpCore 4.1-alpha1 released

This is the first public release from the 4.1 branch of HttpCore. This release adds a number of new features, most
notable being introduction of compatibility mode with IBM JREs and other JREs with naive (broken) implementation of
SelectionKey API.

###### 14 August 2009 - HttpComponents HttpClient 4.0 (GA) released

This the first stable (GA) release in the 4.x code line. This release completes the rewrite of HttpClient and delivers a
complete API documentation and fixes a few minor bugs reported since the previous release.

###### 22 June 2009 - HttpComponents HttpCore 4.0.1 (GA) released

This is a patch release addressing a number of issues discovered since the 4.0 release.

###### 26 February 2009 - HttpComponents HttpCore 4.0 (GA) released

This the first stable (GA) release in the 4.x code line. This release delivers complete API documentation and fixes a
few minor bugs reported since the previous release.

###### 20 December 2008 - HttpComponents HttpClient 4.0-beta2 released

The second BETA of HttpComponents HttpClient addresses a number of issues discovered since the previous release.

The only significant new feature is an addition of an OSGi compliant bundle combining HttpClient and HttpMime jars.

All upstream projects are strongly encouraged to upgrade.

###### 19 October 2008 - HttpComponents HttpCore 4.0-beta3 released

The third BETA version of HttpComponents Core addresses a number of issues discovered since the previous release.

The only significant new feature is an addition of an OSGi compliant bundle combining HttpCore and HttpCore NIO jars.

###### 12 September 2008 - HttpClient is one of the best open source development tools

HttpClient is among the 60 winners of
InfoWorlds ["Best of Open Source Software Awards 2008"](http://www.infoworld.com/article/08/08/04/32TC-bossies-2008_1.html)
.

HttpClient was selected as one of
the [best open source development tools](http://www.infoworld.com/slideshow/2008/08/166-best_of_open_so-4.html).

###### 29 August 2008 - HttpComponents HttpClient 4.0-beta1 released

The first BETA brings yet another round of API enhancements and improvements in the area of connection management. Among
the most notable ones is the capability to handle stateful connections such as persistent NTLM connections and private
key authenticated SSL connections.

This is the first API stable release of HttpClient 4.0. All further releases in the 4.0 code line will maintain API
compatibility with this release.

###### 22 June 2008 - HttpComponents HttpCore 4.0-beta2 released

The second BETA version of HttpComponents Core added a number of improvements to the NIO components, most notable being
improved asynchronous client side and server side protocol handlers.

###### 09 May 2008 - HttpComponents HttpClient 4.0-alpha4 released

The fourth ALPHA marks the completion of the overhaul of the connection management code in HttpClient. All known
shortcomings of the old HttpClient 3.x connection management API have been addressed.

###### 03 May 2008 - Welcome new HttpComponents committer Sam Berlin

By 6 binding votes in favor and none against Sam Berlin has been voted in as a new HttpComponents committer. Sam made
several valuable contributions to both core and client components in the course of the past several months.

Welcome on board, Sam!

###### 26 February 2008 - HttpComponents HttpClient 4.0-alpha3 released

The third ALPHA release brings another round of API refinements and improvements in functionality. As of this release
HttpClient requires Java 5 compatible runtime environment and takes full advantage of generics and new concurrency
primitives.

This release also introduces new default cookie policy that selects a cookie specification depending on the format of
cookies sent by the target host. It is no longer necessary to know beforehand what kind of HTTP cookie support the
target host provides. HttpClient is now able to pick up either a lenient or a strict cookie policy depending on the
compliance level of the target host.

Another notable improvement is a completely reworked support for multipart entities based on Apache mime4j library.

###### 24 January 2008 - HttpComponents HttpCore 4.0-beta1 released

The first BETA version of HttpComponents Core has been released. This release can be considered a major milestone, as it
marks the end of API instability in HttpCore. As of this release the API compatibility between minor releases in 4.x
codeline will be maintained.

This release includes several major improvements such as enhanced HTTP message parsing API and optimized parser
implementations, Java 5.0 compatibility for HttpCore NIO extensions.

The focus of the development efforts will be gradually shifting towards providing better test coverage, documentation
and performance optimizations.

###### 15 November 2007 - HttpComponents becomes TLP

The ASF board had approved HttpComponents 'graduation' from Jakarta to a TLP of its own.

We are now Apache HttpComponents Project!

###### 7 November 2007 - HttpComponents HttpClient 4.0-alpha2 released

The second ALPHA release is another important milestone in the redesign of HttpClient. The release includes a number of
improvements since ALPHA1, among which are improved connection pooling, support for proxy chains, redesigned HTTP state
and authentication credentials management API, improved RFC 2965 cookie specification.

###### 9 October 2007 - HttpComponents HttpCore 4.0-alpha6 released

The sixth ALPHA version of HttpComponents Core has been released. This release sports an improved message parsing and
formatting API in the base module and lots of incremental improvements and bug fixes in the NIO and NIOSSL modules.
Based on the improved API, it is now possible to send and receive SIP messages with HttpComponents Core.

###### 20 July 2007 - HttpComponents HttpClient 4.0-alpha1 released

This release represents a complete, ground-up redesign and almost a complete rewrite of the old HttpClient 3.x codeline.
This release finally addresses several design flaws that existed since the 1.0 release and could not be fixed without a
major code overhaul and breaking API compatibility.

Notable changes and enhancements:

- Redesign of the HttpClient internals addressing all known major architectural shortcomings of the 3.x codeline
- Cleaner, more flexible and expressive API
- Better performance and smaller memory footprint due to a more efficient HTTP transport based on HttpCore. HttpClient
  4.0 is expected to be 10% to 25% faster than HttpClient 3.x codeline
- More modular structure
- Pluggable redirect and authentication handlers
- Support for protocol incerceptors
- Improved connection management
- Improved support for sending requests via a proxy or a chain of proxies
- Improved handling redirects of entity enclosing requests
- More flexible SSL context customization
- Reduced intermediate garbage in the process of generating HTTP requests and parsing HTTP responses

###### 4 July 2007 - HttpComponents HttpCore 4.0-alpha5 released

The fifth ALPHA version of HttpComponents Core has been released. This release delivers a number of incremental
improvements across the board in all modules and adds several performance oriented features such as ability to transfer
data directly between a file and a socket NIO channels.

###### 30 March 2007 - HttpComponents HttpCore 4.0-alpha4 released

The fourth ALPHA version fixes a number of bugs and adds a number of improvements to HttpCore base and the HttpCore NIO
extensions. This release also introduces NIOSSL extensions that can be used to extend HttpCore non-blocking transport
components with the ability to transparently encrypt data in transit using SSL/TLS.

###### 6 December 2006 - HttpComponents HttpCore 4.0-alpha3 released

The third ALPHA version of HttpCore has been released. The ALPHA3 release includes a number of API optimizations and
improvements and introduces a set of NIO extensions to the HttpCore API. NIO extensions can be used to build HTTP
services intended to handle thousands of simultaneous connections with a small number of I/O threads.

###### 9 June 2006 - HttpComponents HttpCore 4.0-alpha2 released

The second ALPHA version of HttpCore has been released, which addresses a number of non-critical problems found in the
previous release. The upstream projects are strongly encouraged use this release as a dependency while HttpCore
undergoes another round of reviews and optimization in the SVN trunk.

###### 12 May 2006 - HttpClient issue tracking migrated to Jira

HttpClient issue tracking has migrated from Bugzilla to Jira. Please use
[this project](http://issues.apache.org/jira/browse/HTTPCLIENT) in Jira to report new issues against HttpClient and
search for reported ones. All existing issue reports can be accessed in Jira by their original Bugzilla bug id.

###### 29 April 2006 - New Project Logo

HttpComponents project now has a brand new logo kindly contributed by Regula Wernli.

Many thanks, Regula!

###### 23 April 2006 - HttpComponents HttpCore 4.0-alpha1 released

This is the first ALPHA release of HttpCore intended for API review and use in experimental projects. The HttpCore API
is still deemed unstable and it can still undergo significant changes based on the feedback from early adopters.

###### 12 February 2006 - Welcome new HttpComponents committer Roland Weber

By 5 binding votes in favor and none against Roland Weber has been voted in as a new HttpComponents committer. Roland
has been an invaluable contributor to the Jakarta Commons HttpClient project for many years and he is the very first
committer to join the Jakarta HttpComponents project.

Welcome, Roland

###### 31 October 2005 - Jakarta HttpClient becomes Jakarta HttpComponents

By the count 15 votes in favor, Jakarta HttpClient as been renamed as Jakarta HttpComponents. The Jakarta PMC has
approved the new project charter and the new project scope.

###### 16 April 2004 - Welcome Jakarta HttpClient!

By the count 26 votes in favor, none against, Jakarta Commons HttpClient as been promoted to the Jakarta sub-project
level 
