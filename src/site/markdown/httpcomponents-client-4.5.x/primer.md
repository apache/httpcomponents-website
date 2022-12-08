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

Client HTTP Programming Primer
==============================

About
-----

This document is intended for people who suddenly have to or want to implement an application that automates something
usually done with a browser, but are missing the background to understand what they actually need to do. It provides
guidance on the steps required to implement a program that interacts with a web site which is designed to be used with a
browser. It does not save you from eventually learning the background of what you are doing, but it should help you to
get started quickly and learn the details later.

This document has evolved from discussions on the HttpClient mailing lists. Although it refers to HttpClient, the
concepts described here apply equally to HttpComponents or
Java's [HttpURLConnection](https://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html)
or any other HTTP communication library for any programming language. So you might find it useful even if you're not
using Java and HttpClient.

The existence of this document does not imply that the HttpClient community feels responsible for teaching you how to
program a client HTTP application. It is merely a way for us to reduce the noise on the mailing list without just
leaving the newbies out in the cold.

Scenario
--------

Let's assume that you have some kind of repetitive, web-based task that you want to automate. Something like:

* goto page http://xxx.yyy.zzz/login.html

* enter username and password in a web form and hit the "login" button

* navigate to a specific page

* check the number/headline/whatever shown on that page

At this time, we don't have a specific example which could be developed into a sample application. So this document is
all bla-bla, and you will have to work out the details - all the details - yourself. Such is life.

Caveat
------

This scenario describes a hobbyist usage of HTTP, in other words:
***a bad practice***. Web sites are designed for user interaction, not as an application programming interface (API).
The interface of a web site is the user interface displayed by a browser. The HTTP communication between the browser and
the server is an internal API, subject to change without notice.

A web site can be redesigned at any point in time. The server then sends different documents and a browser will display
the new content. The user easily adjusts to click the appropriate links, and the browser communicates via HTTP as
specified by the new documents from the server. Your application that only mimicks a browser will simply break.

Nevertheless, implementing this scenario will help you to get familiar with HTTP communication. It is also "good enough"
for hobbyists applications, for example if you want to download the latest installment of your favorite daily webcomic
to install it as the screen background. There is no big damage if such an application breaks.

If you want to implement a solid application, you should use only published APIs. For example, to check for new mail on
your webmail account, you should ask the webmail provider for POP or IMAP access. These are standardized protocols
supported my most EMail client applications. If you want to have a newsticker, look for RSS feeds from the provider and
applications that display them.

As another example, if you want to perform a web search, there are search companies that provide an API for using their
search engines. Unlike the examples before, such APIs are proprietary. You will still have to implement an application,
but then you are using a published API that the provider will not change without notice.

Not a Browser
-------------

HttpClient is not a browser. It is an HTTP communication library and as such it provides only a subset of functions
expected from a common browser application. The most fundamental difference is absence of user interface in HttpClient.
The browser needs a rendering engine to display pages, and to interpret user input such as mouse clicks somewhere on the
displayed page. There is a layout engine which computes how an HTML page should be displayed, including cascading style
sheets and images. A JavaScript interpreter runs JavaScript code embedded in or referenced from HTML pages. Events from
the user interface are passed to the JavaScript interpreter for processing. On top of that, there are interfaces for
plugins that can handle Applets, embedded media objects like PDF files, Quicktime movies and Flash animations, or
ActiveX controls that can do anything. HttpClient can only be used programmatically through its APIs to transmit and
receive HTTP messages. HttpClient is also completely content agnostic. It can transfer message content but it is unable
to render or process it in any fashion.

Another major difference is tolerance for bad input or HTTP standard violations. There needs to be tolerance for invalid
user input to make the browser user friendly. There also needs to be tolerance for malformed documents retrieved from
servers, and for flaws in server behavior when executing protocols, to make as many websites as possible accessible to
the user. HttpClient is however strives to adhere to the HTTP standard specification and related standards as close and
as possible by default. It also provides means to relaxing some of the restrictions imposed by the specification where
permissible or required for compatibility with non-compliant HTTP origin or proxy servers.

Terminology
-----------

This section introduces some important terms you have to know to understand the rest of this document.

### HTTP Message

consists of a header section and an optional entity. There are two kinds of messages, requests and responses. They
differ in the format of the first line, but both can have header fields and an optional entity.

### HTTP Request

is sent from a client to a server. The first line includes the URI for which the request is sent, and a method that the
server should execute for the client.

### HTTP Response

is sent from a server to a client in response to a request. The first line includes a status code that tells about
success or failure of the request. HTTP defines a set of status codes, like 200 for success and 404 for not found. Other
protocols based on HTTP can define additional status codes.

### Method

is an operation requested from the server. HTTP defines a set of operations, the most frequent being GET and POST. Other
protocols based on HTTP can define additional methods.

### Header Fields

are name-value pairs, where both name and value are text. The name of a header field is not case sensitive. Multiple
values can be assigned to the same name. RFC 2616 defines a wide range of header fields for handling various aspects of
the HTTP protocol. Other specifications, like RFC 2617 and RFC 2965, define additional headers. Some of the defined
headers are for general use, others are meant for exclusive use with either requests or responses, still others are
meant for use only with an entity.

### Entity

is data sent with an HTTP message. For example, a response can contain the page or image you are downloading as an
entity, or a request can include the parameters that you entered into a web form. The entity of an HTTP message can have
an arbitrary data format, which is usually specified as a MIME type in a header field.

### Session

is a series of requests from a single source to a server. The server can keep session data, and needs to recognize the
session to which each incoming request belongs. For example, if you execute a web search, the server will only return
one page of search results. But it keeps track of the other results and makes them available when you click on the link
to the "next" page. The server needs to know from the request that it is you and your session for which more results are
requested, and not me and my session. That's because I searched for something else.

### Cookies

are the preferred way for servers to track sessions. The server supplies a piece of data, called a cookie, in response
to a request. The server expects the client to send that piece of data in a header field with each following request of
the same session. The cookie is different for each session, so the server can identify to which session a request
belongs by looking at the cookie. If the cookie is missing from a request, the server will not respond as expected.

Step by Step
------------

### GET the Login Page

Create and execute a GET request for the login page. Just use the link you would type into the browser as the URL. This
is what a browser does when you enter a URL in the address bar or when you click on a link that points to another web
page.

Inspect the response from the server:

* do you get the page you expected?

It should be sent as the entity of the response to your request. The entity is also referred to as the response body.

* do you get a session cookie?

Cookies are sent in a header field named Set-Cookie or Set-Cookie2. It is possible that you don't get a session cookie
until you log in. If there is no session cookie in the response, you'll have to do perform step 2 later, after you reach
the point where the cookie is set.

If you do not get the page you expect, check the URL you are requesting. If it is correct, the server may use a browser
detection. You will have to set the header field User-Agent to a value used by a popular browser to pretend that the
request is coming from that browser.

If you can't get the login page, get the home page instead now. Get the login page in the next step, when you establish
the session.

### Establish the Session

Create and execute another GET request for a page. You can simply request the login page again, or some other page of
which you know the URL. Do NOT try to get a page which would be returned in response to submitting a web form. Use
something you can reach simply by clicking on a link in the browser. Something where you can see the URL in the browser
status line while the mouse pointer is hovering over the link.

This step is important when developing the application. Once you know that your application does establish the session
correctly, you may be able to remove it. Only if you couldn't get the login page directly and had to get the home page
first, you know you have to leave it in.

Inspect the request being sent to the server.

* is the session cookie sent with the request?

You can see what is sent to the server by enabling the wire log for HttpClient. You only need to see the request
headers, not the body. The session cookie should be sent in a header field called Cookie. There may be several of those,
and other cookies might be sent as well.

Inspect the response from the server:

* do you get another session cookie?

You should not get another session cookie. If you get the same session cookie as before, the server behaves a little
strange but that should not be a problem. If you get a new session cookie, then the server did not recognize the session
for the request. Usually, this happens if the request did not contain the session cookie. But servers might use other
means to track sessions, or to detect session hijacking.

If the session cookie is not sent in the request, one of two things has gone wrong. Either the cookie was not detected
in the previous response, or the cookie was not selected for being sent with the new request.

HttpClient automatically parses cookies sent in responses and puts them into a cookie store. HttpClient uses a
configurable cookie policy to decide whether a cookie being sent from a server is correct. The default policy complies
strictly with RFC 2109, but many servers do not. Play around with the cookie policies until the cookie is accepted and
put into the cookie store.

If the cookie is accepted from the previous response but still not sent with the new request, make sure that HttpClient
uses the same cookie store object. Unless you explicitly manage cookie store objects (not recommended for newbies!),
this will be the case if you use the same HttpClient object to execute both requests.

If the cookie is still not sent with the request, make sure that the URL you are requesting is in the scope for the
cookie. Cookies are only sent to the domain and path specified in the cookie scope. A cookie for host "
jakarta.apache.org" will not be sent to host
"tomcat.apache.org". A cookie for domain ".apache.org" will be sent to both. A cookie for host "apache.org", without the
leading dot, will not be sent to "jakarta.apache.org". The latter case can be resolved by using a different cookie spec
that adds the leading dot. In the other cases, use a URL that in the cookie scope to establish the session.

If the session cookie is sent with the request, but a new session cookie is set in the response anyway, check whether
there are cookies other than the session cookie in the request. Some servers are incapable of detecting multiple cookies
sent in individual header fields. HttpClient can be advised to put all cookies into a single header field.

If that doesn't help, you are in trouble. The server may use additional means to track the session, for example the
header field named Referer. Set that field to the URL of the previous request.
([see this mail](https://mail-archives.apache.org/mod_mbox/jakarta-httpclient-user/200602.mbox/%3c19b.44e04b45.31166eaa@aol.com%3e))

If that doesn't help either, you will have to compare the request from your application to a corresponding one generated
by a browser. The instructions in step 5 for POST requests apply for GET requests as well. It's even simpler with GET,
since you don't have an entity.

### Analyze the Form

Now it is time to analyze the form defined in the HTML markup of the page. A form in HTML is a set of name-value-pairs
called parameters, where some of the values can be entered in the browser. By analyzing the HTML markup, you can learn
which parameters you have to define and how to send them to the server.

Look for the `<form>` tag in the page source. There may be several forms in the page, but they can not be nested. Locate
the form you want to submit. Locate the matching `</form>` tag. Everything in between the two may be relevant. Let's
start with the {attributes of the `<form>` tag}:

##### method

specifies the method used for submitting the form. If it is GET or not specified at all, then you need to create a GET
request. The parameters will be added as a query string to the URL. If the method is POST, you need to create a POST
request. The parameters will be put in the entity of the request, also referred to as the request body. How to do that
is discussed in step 5.

##### action

specifies the URL to which the request has to be sent. Do not try to get this URL from the address bar of your browser!
A browser will automatically follow redirects and only displays the final URL, which can be different from the URL in
this attribute. It is possible that the URL includes a query string that specifies some parameters. If so, keep that in
mind.

##### enctype

specifies the MIME type for the entity of the request generated by the form. The two common cases are url-encoded (
default) and multipart-mime. Note that these terms are just informally used here, the exact values that need to be
written in an HTML document are specified elsewhere. This attribute is only used for the POST method. If the method is
GET, the parameters will always be url-encoded, but not in an entity.

##### accept-charset

specifies the character set that the browser should allow for user input. It will not be discussed here, but you will
have to consider this value if you experience charset related problems.

Except for optional query parameters in the action attribute, the parameters of a form are specified by HTML tags
between <form> and </form>. The following is a list of tags that can be used to define parameters. Except where stated
otherwise, they have a name attribute which specifies the name of the parameter. The value of the parameter usually
depends on user input.

```
<input type="text" name="...">
<input type="password" name="...">
```

specify single-line input fields. Using the return key in one of these fields will submit the form, so the value really
is a single line of input from the user.

```
<input type="text" readonly name="..." value="...">
<input type="hidden" name="..." value="...">
```

specify a parameter that can not be changed by the user. The value of the parameter is given by the value attribute.

```
<input type="radio" name="..." value="...">
<input type="checkbox" name="..." value="...">
```

specify a parameter that can be included or omitted. There usually is more than one tag with the same name. For radio
buttons, only one can be selected and the value of the parameter is the value of the selected radio button. For
checkboxes, more than one can be selected. There will be one name-value-pair for each selected checkbox, with the same
name for all of them.

```
<input type="submit" name="..." value="...">
<button type="submit" name="..." value="...">
```

specify a button to submit the form. The parameter will only be added to the form if that button is used to submit. If
another button is used, or the form is submitted by pressing the return key in a text input field, the parameter is not
part of the submitted form data. If the name attribute is missing, no parameter is added to the form data for that
button.

```
<textarea name="...">
<textarea value="..." readonly>
```

specify a multi-line input field. In the readonly case, the value of the parameter is the text between the `<textarea>`
and `</textarea>` tags.

```
<select name="..." multiple>}}}
  <option value="...">...</option>}}}
  <option value="...">...</option>}}}
  ...
</select>
```

specify a selection list or drop-down menu. If the multiple attribute is not present, only one option can be selected.
There will be one name-value-pair for each selected option, with the same name for all of them. If there is no value
attribute, the value for that option is the text between <option> and </option>.

```
<input type="image" name="...">
```

specifies an image that can be clicked to submit the form. If that image is clicked to submit the form, two parameters
are added to the form data. The name attribute is suffixed with ".x" and ".y", the values for the parameters are the
relative coordinates of the mouse pointer within the image at the time of the click, in pixel. If the name attribute is
missing, no parameters will be added to the form data.

```
<input type="file" name="...">
```

specifies a file selection box. The user can select a file that should be sent as part of the form data. This is only
possible if the encoding is multipart-mime. Unlike other parameters, the file is not mapped to a simple name-value-pair.
File upload is not a topic for beginners.

These tags are used to define parameters in static HTML. With dynamic HTML, in particular JavaScript, the parameter
values can be changed before the form is submitted. If that is the case, you are in trouble. Learn JavaScript, analyze
the code that is executed, and modify your application to match that behavior.

### Analyze the Form, Again

After you have determined the action URL and name-value-pairs of a form, you should exit the program you used to get the
HTML source, start it again and repeat the analysis with the new page.

Most parameters will be the same for both pages. But some parameters, in particular those from hidden input fields, may
change from session to session, or even with every request. The same can be the case with the action URL.

Parameters that remain the same can be hard-coded in your program. If parameters change (except for user input), then
your application has to request the page with the form and extract the dynamic parameters at runtime. If you're lucky
you can locate them by simple string searches. If you're unlucky, you need an HTML parser to make sense of the page.
HTML parsing is out of scope for HttpClient, but you'll find some HTML parsers mentioned in the mailing list archives.

Note that a redesign of the form on the server can break your application at any time. Whenever that happens, you have
to repeat the analysis with the new form returned by the server after the redesign, and adjust your application
accordingly.

### POST the Form

After analyzing the form, it is time to create a request that matches what a browser would generate. If the method is
GET, just add the name-value-pairs for all parameters to the query string. If the method is POST, things are a little
more complicated.

It depends on the server how closely you have to match browser behavior. For example, a servlet will not distinguish
between parameters in the query string and url-encoded parameters of the entity. But other server side code might make
that distinction. The safe way is always to match browser behavior exactly.

HttpClient supports both encoding types, url-encoded and multipart-mime. To send parameters url-encoded, use the POST
request and add the parameters directly there. To send parameters in multipart-mime, collect the parameters in a
multipart-encoded request entity and add set the entity for the POST request. You will also find support for file upload
in the multipart package. Note that these techniques are mutually exclusive, they can not be combined. Parameters
defined in the query string of the URL can remain there.

Send the request. Inspect the response from the server:

* do you get a status code 303 or 307?

That is called a redirect. Follow redirects to the ultimate page and inspect that response. See step 6 on following
redirects.

* do you get the page you expected?

If the server response to your POST request indicates a problem, try to enable or disable the expect-continue handshake,
or switch the protocol version to HTTP/1.0. If that doesn't help...

Inspect the request you are sending:

* are there significant differences to the request of a browser?

There is a variety of sniffer programs you can use to grep the browser request. Some of them are mentioned in the
responses
to [on the mailing list](https://mail-archives.apache.org/mod_mbox/jakarta-httpclient-user/200603.mbox/%3c981224FF5B88B349B7C1FED584D2620E02A2CBB2@CORPUSMX50B.corp.emc.com%3e
this question).

Candidates for problems are missing or wrong parameters, and differences in the header fields. The parameters are all up
to you. As a general rule for the header fields, you should send the same as the browser does. The order of the fields
does not matter.

But there's a caveat: some header fields are controlled by HttpClient and can not be set explicitly. Other header fields
are used to indicate capabilities which a browser has, but your application probably has not. For these, the request
from your application has to and should differ. Here is a possibly incomplete list of headers that need special
consideration:

* `Host`

  controlled by HttpClient. The value is usually obtained from the URL you are posting to. It is possible to set a
  different value, called a "virtual host".

* `Content-Type`

* `Content-Length`

* `Transfer-Encoding`

  controlled by HttpClient. The values are obtained from the request entity.

* `Connection`

  usually controlled by HttpClient to handle connection keep-alive. Leave it alone or set the value to "close".

* `Content-Encoding`

  used to indicate the capability to process compressed responses. Do not set this, unless you are prepared to implement
  decompression.

### Follow Redirects

It is quite common for servers to respond with a 303 or 307 status code to a POST request. These redirects indicate that
your application has to send another request to retrieve the actual result of the operation you have triggered with the
POST request.

HttpClient can be configured to follow some redirects automatically. Others it is not allowed to follow automatically,
since RFC 2616 specifies that a user interaction should take place. We will make sure that HttpClient is compliant with
this requirement, but we can't stop you from implementing a different behavior in your application. The Location header
field in the redirect response indicates the URL from which to fetch the actual page. It is common practice that servers
return a relative URL as the location, although the specification requires an absolute URL.

Note that there may be more than one redirect in succession. Your application then has to follow the redirect for a
redirect, but make sure that you do not enter an infinite loop. If you find that there are more than two redirects in
succession, something probably is fishy.

### Logout

Your application can send as many GET and POST requests and follow as many redirects as is required. But you should
remember that there is a session tracked by the server. Once your application is done, and if the web site does provide
a logout link, you should send a final request to log out. This will tell the server that the session data can be
discarded. If the server prevents multiple logins with the same user ID and your application has to run repeatedly,
logout may even be required.

Further Reading
---------------

ReferenceMaterials: a list of technical specifications for HTTP and related stuff.

* [ HTML 4.01 Specification, Section on Forms](https://www.w3.org/TR/html4/interact/forms.html): Includes how browsers
  have to generate the data to submit to the server.

* [Giving Form to Forms](https://digital.com/tools/html-cheatsheet/):
  Explains how to define HTML forms and what is submitted to the server. Probably easier to digest than the HTML 4.01
  Specification.

* [Commons File Upload](https://jakarta.apache.org/commons/fileupload/): Server-side library for parsing multipart
  requests.

* [Tutorial on File Upload in HTML](http://www.cs.tut.fi/~jkorpela/forms/file.html)
