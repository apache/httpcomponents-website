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

Jakarta REST client
===================

Starting with HttpClient 5.7 there is an optional **Jakarta REST client**
module, `httpclient5-jakarta-rest-client`, that turns an annotated Java
interface into a type-safe HTTP client backed by the async transport.

It is a **proxy generator**, not an implementation of the Jakarta REST *Client
API*. You declare an interface annotated with Jakarta REST annotations
(`@Path`, `@GET`, `@QueryParam`, ...); the module returns a dynamic proxy that
maps each method call to an HTTP request executed over a
`CloseableHttpAsyncClient` (HTTP/1.1 and HTTP/2). It does **not** provide the
JAX-RS `Client` / `ClientBuilder` / `WebTarget` runtime, and it is **not**
registered through `jakarta.ws.rs.client.ClientBuilder` service discovery.

> This module targets **Java 17** and Jakarta REST 4.0
> (`jakarta.ws.rs:jakarta.ws.rs-api` 4.0.0). JSON (de)serialization is handled
> by Jackson.

Module and dependency
---------------------

REST client support lives in a separate module,
`httpclient5-jakarta-rest-client`. For the current release coordinates, see the
[download](download.html) page.

It depends on the async client (`httpclient5`) and the Jackson integration
(`httpcore5-jackson2`), and pulls in the Jakarta REST annotations.

Core types
----------

The public API is in the package `org.apache.hc.client5.http.rest` and is
intentionally small.

- **`RestClientBuilder`**

  The entry point. Obtain one with `RestClientBuilder.newBuilder()` and
  configure it:

  - `baseUri(String)` / `baseUri(URI)` – required base address,
  - `httpClient(CloseableHttpAsyncClient)` – required; the caller owns the
    client's lifecycle and must `start()` it before use and close it
    afterwards,
  - `objectMapper(ObjectMapper)` – optional Jackson mapper (defaults to a new
    `ObjectMapper`),
  - `<T> T build(Class<T> iface)` – scans the interface and returns a proxy.

- **`RestResourceException`**

  Thrown when an interface violates the Jakarta REST contract or declares no
  annotated methods.

Supported annotations
---------------------

On the interface and its methods you may use:

- `@Path` (type and method level) and the HTTP verbs `@GET`, `@POST`, `@PUT`,
  `@DELETE`,
- `@Produces` / `@Consumes` for media types,
- `@QueryParam`, `@PathParam`, `@HeaderParam` and `@DefaultValue` for
  parameters.

`@FormParam`, `@CookieParam` and `@MatrixParam` are not handled.

Return and request types
------------------------

A method may return `String`, `byte[]`, `void`, any Jackson-deserializable
type, or `jakarta.ws.rs.core.Response` (buffered, inspected with
`readEntity(...)`). Any of these may be wrapped in `CompletionStage` /
`CompletableFuture` for non-blocking dispatch.

A request body may be a `String`, `byte[]`, any Jackson-serializable type, or a
`List<NameValuePair>` (sent as `application/x-www-form-urlencoded`).

A non-2xx response raises `jakarta.ws.rs.client.ResponseProcessingException`
(or completes the stage exceptionally), **unless** the method returns
`Response`, in which case the response is handed back for inspection.

Basic usage
-----------

1. Declare an annotated interface.
2. Create and start a `CloseableHttpAsyncClient`.
3. Build a proxy with `RestClientBuilder`.
4. Call the interface methods.

```java
@Path("/")
public interface HttpBinApi {

    @GET
    @Path("/get")
    @Produces("application/json")
    GetResponse get(@QueryParam("foo") String foo);

    @POST
    @Path("/post")
    @Consumes("application/json")
    @Produces("application/json")
    CompletableFuture<PostResponse> post(Payload body);
}
```

```java
final CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
httpClient.start();

final HttpBinApi api = RestClientBuilder.newBuilder()
        .baseUri("https://httpbin.org")
        .httpClient(httpClient)
        .build(HttpBinApi.class);

final GetResponse response = api.get("bar");
```

> The caller owns the `CloseableHttpAsyncClient`: start it before building the
> proxy and close it when finished. A single client (and proxy) can be reused
> for many calls.

Examples
--------

A runnable example lives in the `httpclient5-jakarta-rest-client` module:

- [RestClientMain](https://github.com/apache/httpcomponents-client/tree/master/httpclient5-jakarta-rest-client/src/test/java/org/apache/hc/client5/http/rest/examples/RestClientMain.java)

  Defines an `HttpBinApi` interface and calls httpbin.org over a JSON `GET`
  (with `@QueryParam`) and a JSON `POST`, mapping requests and responses to
  POJOs.

Further reading
---------------

- The Jakarta REST specification for the meaning of the annotations.
- For the full API, see the Javadoc of
  `org.apache.hc.client5.http.rest.RestClientBuilder`.
