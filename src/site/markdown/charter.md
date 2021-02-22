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

<!--
  The Charter was approved by a PMC vote ending on 2008-02-09.
  Changes to the Charter require PMC approval.
  DO NOT EDIT the "Charter" section, not even to fix typos.
-->

Charter
-------

The Apache HttpComponents project is responsible for creating and maintaining a toolset of low level Java components
focused on HTTP and associated protocols.

We develop and maintain a component called `HttpCore`, which addresses the basic needs for communicating via HTTP on the
client and server side. HttpCore defines a framework for extending the provided functionality beyond the basic needs.

We develop and maintain a component called `HttpClient`, which builds on HttpCore and adds functionality typically
required for client-side HTTP communication. In particular, HttpClient adds support for cookies, authentication, and
client-side connection management.

`HttpCore` and `HttpClient` are at the center of the Apache HttpComponents project. All other activities orbit around
this duo.

We maintain the codebase of the Jakarta Commons HttpClient, also known as Commons HttpClient 3.1, until such time that
the new HttpClient based on HttpCore is considered ready for use in production systems.

We are looking for new components that build upon and extend the functionality of the existing ones. We are also looking
for new components that complement the functionality of the existing ones and make them more useful or easier to use.

We are open to give a home to applications that derive a significant part of their functionality from our components, if
approached by such projects.

<!--
end of the "Charter" section that must not be edited without PMC approval
-->

History
-------

The history of the HttpComponents starts with the now retired
[Jakarta Slide](http://jakarta.apache.org/slide/) project. Slide was a WebDAV server including a client component, and
the WebDAV protocol builds on HTTP. There was interest in using the client-side HTTP implementation independently of
Slide. The code was spun off from Slide in 2001 to become the HttpClient subproject of the Jakarta Commons.

Since it generated a disproportional amount of traffic on the Commons mailing lists, HttpClient activity was moved to
separate mailing lists. This started the dissociation of HttpClient from Commons, which continued when HttpClient was
promoted to the Jakarta subproject level in 2004. The latter event is recorded as the oldest [News](news.md) item of the
HttpComponents project.

The [Commons](http://commons.apache.org/), cradle of HttpClient, left Jakarta in 2007 to become an independent Top Level
Project. Later in the same year, the HttpComponents project also left Jakarta to become an independent Top Level
Project, taking the responsibility for maintaining HttpClient 3.x with it. Our TLP resolution is presented below.

Resolution
----------

The Apache HttpComponents project was established as an Apache top level project in November 2007, when the Apache Board
approved the following resolution:

<!--
The following is a verbatim copy of the TLP resolution.
DO NOT EDIT, not even to fix typos
-->

---------
```text
Establish the Apache HttpComponents project

WHEREAS, the Board of Directors deems it to be in the best interests of the Foundation and
consistent with the Foundation\'s purpose to establish a Project Management Committee charged with
the creation and maintenance of open-source software related to a toolset of low level Java
components focused on HTTP and associated protocols, and of applications based on these components,
for distribution at no charge to the public.

NOW, THEREFORE, BE IT RESOLVED, that a Project Management Committee (PMC), to be known as "Apache
HttpComponents Project", be and hereby is established pursuant to Bylaws of the Foundation; and be
it further

RESOLVED, that the Apache HttpComponents Project be and hereby is responsible for the creation and
maintenance of a toolset of low level Java components focused on HTTP and associated protocols, and
of applications based on these components; and be it further

RESOLVED, that the office of "Vice President, Apache HttpComponents" be and hereby is created, the
person holding such office to serve at the direction of the Board of Directors as the chair of the
Apache HttpComponents Project, and to have primary responsibility for management of the projects
within the scope of responsibility of the Apache HttpComponents Project; and be it further

RESOLVED, that the persons listed immediately below be and hereby are appointed to serve as the
initial members of the Apache HttpComponents Project:

    * Oleg Kalnichevski <olegk AT apache DOT org>
    * Sebastian Bazley <sebb AT apache DOT org>
    * Erik Abele <erikabele AT apache DOT org>
    * Ortwin Glück <oglueck AT apache DOT org>
    * Roland Weber <rolandw AT apache DOT org>
    * Ant Elder <antelder AT apache DOT org>
    * Paul Fremantle <pzf AT apache DOT org>
    * Asankha Perera <asankha AT apache DOT org>

NOW, THEREFORE, BE IT FURTHER RESOLVED, that Erik Abele be appointed to the office of Vice
President, Apache HttpComponents, to serve in accordance with and subject to the direction of the
Board of Directors and the Bylaws of the Foundation until death, resignation, retirement, removal or
disqualification, or until a successor is appointed; and be it further

RESOLVED, that the Apache HttpComponents Project be and hereby is tasked with the migration and
rationalization of the Apache Jakarta HttpComponents subproject; and be it further

RESOLVED, that the Apache HttpComponents Project be and hereby is tasked with the migration and
maintenance of the codebase formerly known as Apache Jakarta Commons HttpClient until it is
obsoleted by the components of the Apache HttpComponents Project; and be it further

RESOLVED, that all responsibilities pertaining to the Apache Jakarta HttpComponents subproject and
the former Apache Jakarta Commons HttpClient codebase encumbered upon the Apache Jakarta Project are
hereafter discharged.
```
--------
