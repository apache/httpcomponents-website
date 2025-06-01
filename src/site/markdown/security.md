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

Apache HttpComponents Security
==============================

HttpComponents Security Overview
--------------------------------

The Apache HttpComponents operates under the [Apache-wide security procedures](https://www.apache.org/security/).

HttpComponents Security Model
-----------------------------

The HttpComponents libraries are low-level libraries typically designed to work with input that is either trusted or
validated/sanitized by the application using the library. It is unsafe to provide possibly malicious input to
HttpComponents libraries unless otherwise specified.

The HttpComponents libraries are expected to comply with the security requirements stated in the HTTP specifications or
in related RFC documents they explicitly state as being conformant with. The libraries cannot be expected to comply with
requirements of newer revisions of the HTTP specification or new RFC documents until they have been revised and updated
for conformance with those specifications.

The HttpComponents libraries do make an effort to be resilient to common DoS attacks, but they cannot be expected to
provide extra measures beyond those required by HTTP specification. It is strongly recommended to use the libraries in
combination with a DoS firewall or filter if they are to be deployed in a potentially hostile environment.

We consider calls to the HttpComponents API subject to the same caveat as the JDK, those calls will usually do what the
caller asks. Whether it is "dangerous" depends on the (application) context. Therefore, don't report a behavior as a
HttpComponents component's vulnerability if the same behavior would be considered legitimate for the JDK. We welcome
suggestions for hardening the code base. For example, if your program adds an HTTP header to a request, you are
responsible for the data you add to that header. On the other hand, the headers that are added and removed by
HttpComponents are its responsibility.

