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

# Migration to Apache HttpClient 5.x classic APIs

HttpClient 5.x releases can be co-located with earlier major versions on the same classpath due to versioned package
namespace and Maven module coordinates.

HttpClient 5.x classic APIs are largely compatible with HttpClient 4.0 APIs. Major differences are related to connection
management configuration, SSL/TLS and timeout settings when building HttpClient instances. 
There are also some important differences with URL normalization and encoding.

## Migration steps

-  Add HttpClient 5.x as a new dependency to the project and optionally remove HttpClient 4.x

-  Remove old `org.apache.http` imports and re-import HttpClient classes from
   `org.apache.hc.httpclient5` package namespace. Most old interfaces and classes should resolve automatically. One
   notable exception is `HttpEntityEnclosingRequest` interface In HttpClient 5.x one can enclose a request entity with
   any HTTP method even if violates semantic of the method.

-  There will be compilation errors due to API incompatibilities between version series 4.x and 5.x mostly related to
   SSL/TLS and timeout settings and `CloseableHttpClient` instance creation. Several modifications are likely to be
   necessary.

-  Use `PoolingHttpClientConnectionManagerBuilder` class to create connection managers with custom parameters

-  Use `DefaultClientTlsStrategy` class to create SSL connection socket factories with custom parameters

-  While HttpClient 5 automatically disables all SSL versions and weak TLS versions it may still be advisable to
   explicitly specify TLSv1.3 as the only enabled version.

-  Use `Timeout` class to define timeouts.

-  Use `TimeValue` class to define time values (duration).

-  Optionally choose a connection pool concurrency policy: `STRICT` for strict connection max limit guarantees; `LAX`
   for higher concurrency but with lax connection maximum limit guarantees. With `LAX` policy HttpClient can exceed the
   per route maximum limit under high load and does not enforce the total maximum limit.

-  Optionally choose a connection pool re-use policy: `LIFO` to re-use as few connections as possible making it possible
   for connections to become idle and expire; `FIFO` to re-use all connections equally preventing them from becoming
   idle and expiring.

-  Optionally choose a finite total time to live for connections. 

   ```java
   PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
      .setTlsSocketStrategy(ClientTlsStrategyBuilder.create()
                 .setSslContext(SSLContexts.createSystemDefault())
                 .setTlsVersions(TLS.V_1_3)
                 .buildClassic())
         .setDefaultSocketConfig(SocketConfig.custom()
                 .setSoTimeout(Timeout.ofMinutes(1))
                 .build())
         .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
         .setConnPoolPolicy(PoolReusePolicy.LIFO)
         .setDefaultConnectionConfig(ConnectionConfig.custom()
                 .setSocketTimeout(Timeout.ofMinutes(1))
                 .setConnectTimeout(Timeout.ofMinutes(1))
                 .setTimeToLive(TimeValue.ofMinutes(10))
                 .build())
         .build();
   ```

-  Favor the `strict` cookie policy when using HttpClient 5.0.

-  Use response timeout to define the maximum period of inactivity until receipt of response data.

-  All base principles and good practices of HttpClient programing still apply. Always re-use client instances. Client
   instances are expensive to create and are thread safe in both HttpClient 4.x and 5.x series.

   ```java
   CloseableHttpClient client = HttpClients.custom()
         .setConnectionManager(connectionManager)
         .setDefaultRequestConfig(RequestConfig.custom()
                 .setCookieSpec(StandardCookieSpec.STRICT)
                 .build())
         .build();
   
   CookieStore cookieStore = new BasicCookieStore();
   
   CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
   
   HttpClientContext clientContext = HttpClientContext.create();
   clientContext.setCookieStore(cookieStore);
   clientContext.setCredentialsProvider(credentialsProvider);
   clientContext.setRequestConfig(RequestConfig.custom()
         .setCookieSpec(StandardCookieSpec.STRICT)
         .build());
   
   JsonFactory jsonFactory = new JsonFactory();
   ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
   
   ClassicHttpRequest httpPost = ClassicRequestBuilder.post("https://httpbin.org/post")
          .setEntity(HttpEntities.create(outstream -> {
              objectMapper.writeValue(outstream, Arrays.asList(
                      new BasicNameValuePair("name1", "value1"),
                      new BasicNameValuePair("name2", "value2")));
              outstream.flush();
          }, ContentType.APPLICATION_JSON))
          .build();
   ```

-  HTTP response messages in HttpClient 5.x no longer have a status line. Use response code directly.

   ```java
   JsonNode responseData = client.execute(httpPost, response -> {
     if (response.getCode() >= 300) {
         throw new ClientProtocolException(new StatusLine(response).toString());
     }
     final HttpEntity responseEntity = response.getEntity();
     if (responseEntity == null) {
         return null;
     }
     try (InputStream inputStream = responseEntity.getContent()) {
         return objectMapper.readTree(inputStream);
     }
   });
   System.out.println(responseData);
   ```

-  `CloseableHttpClient` instances should be closed when no longer needed or about to go out of scope.

   ```java
   client.close();
   ```

- The 4.x `RequestConfig` property `normalizeUri` has been removed, and `URIUtils.normalizeSyntax` is no longer public.
 In 4.x, these only supported limited normalization from [RFC 3986](https://datatracker.ietf.org/doc/html/rfc3986) 
including removal of dot segments (section 5.2.4), and syntax-based normalization (section 6.2.2). 
The 5.x `URIBuilder` in [httpcomponents-core](https://hc.apache.org/httpcomponents-core-5.3.x/index.html) has a new 
public `normalizeSyntax` method, but it strives for more thorough support of RFC 3986, 
specifically percent-encoding all components. 
Since 5.3, `normalizeSyntax` has been deprecated and renamed to `optimize` to emphasize the difference in behaviour.

## Migration recipes

The following table provides some migration recipes from HttpClient 4.x to HttpClient 5.x classic APIs.

**Note:** the list of recipes is by no means complete and users are encouraged to contribute other recipes based on their experience.

| HttpClient 4.x                                                                                              | HttpClient 5.x                                                                                                                                                                                                                              |
|-------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `BasicHttpContext`                                                                                          | `HttpCoreContext`/ `HttpClientContext`                                                                                                                                                                                                      |
| `HttpClientConnectionManager.closeExpiredConnections()`                                                     | `ConnPoolControl.closeExpired()`                                                                                                                                                                                                            |
| `HttpClientConnectionManager.closeIdleConnections()`                                                        | `ConnPoolControl.closeIdle()`                                                                                                                                                                                                               |
| `HttpClients.custom().addInterceptorLast(<some logging interceptor>)`                                       | `HttpClients.custom().addExecInterceptorFirst("...", <some logging interceptor>)`<br/>(otherwise, compressed payload may be logged)                                                                                                         |
| `HttpClients.custom().setDefaultSocketConfig(...)`                                                          | `PoolingHttpClientConnectionManagerBuilder.create().setDefaultSocketConfig(...)`/ `BasicHttpClientConnectionManager.setSocketConfig(...)`<br/>`HttpClients.custom().setConnectionManager(...)`                                              |
| `HttpClients.custom().setRetryHandler()`                                                                    | `HttpClients.custom().setRetryStrategy()`                                                                                                                                                                                                   |
| `HttpContext.getAttribute(HttpCoreContext.HTTP_TARGET_HOST)`                                                | `HttpClientContext.getHttpRoute().getTargetHost()`                                                                                                                                                                                          |
| `((ManagedHttpClientConnection) HttpContext.getAttribute(HttpCoreContext.HTTP_CONNECTION)).getSSLSession()` | `HttpCoreContext.getSSLSession()`                                                                                                                                                                                                           |
| `HttpEntityEnclosingRequest`                                                                                | `HttpEntityContainer`                                                                                                                                                                                                                       |
| `HttpMessage.getAllHeaders()`                                                                               | `MessageHeaders.getHeaders()`                                                                                                                                                                                                               |
| `HttpRequest.getRequestLine()`                                                                              | `new RequestLine(request)`                                                                                                                                                                                                                  |
| `HttpRequestBase`                                                                                           | `HttpUriRequestBase`                                                                                                                                                                                                                        |
| `HttpResponse.getEntity()`                                                                                  | `ClassicHttpResponse.getEntity()`                                                                                                                                                                                                           |
| `HttpResponse.getStatusLine()`                                                                              | `new StatusLine(response)`                                                                                                                                                                                                                  |
| `HttpResponse.getStatusLine().getStatusCode()`                                                              | `HttpResponse.getCode()`                                                                                                                                                                                                                    |
| `RequestConfig.custom().setConnectTimeout()`<br/>`HttpClients.custom().setDefaultRequestConfig(...)`        | `ConnectionConfig.custom().setConnectTimeout()`<br/>`PoolingHttpClientConnectionManager.setDefaultConnectionConfig(...)` / `BasicHttpClientConnectionManager.setConnectionConfig(...)`<br/>`HttpClients.custom().setConnectionManager(...)` |
| `RequestConfig.custom().setSocketTimeout()`<br/>`HttpClients.custom().setDefaultRequestConfig(...)`         | `ConnectionConfig.custom().setSocketTimeout()`<br/>`PoolingHttpClientConnectionManager.setDefaultConnectionConfig(...)` / `BasicHttpClientConnectionManager.setConnectionConfig(...)`<br/>`HttpClients.custom().setConnectionManager(...)`  |
