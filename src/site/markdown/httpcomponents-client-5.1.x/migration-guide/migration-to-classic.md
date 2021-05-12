# Migration to Apache HttpClient 5.0 classic APIs

HttpClient 5.0 releases can be co-located with earlier major versions on the same classpath due to versioned package
namespace and Maven module coordinates.

HttpClient 5.0 classic APIs are largely compatible with HttpClient 4.0 APIs. Major differences are related to connection
management configuration, SSL/TLS and timeout settings when building HttpClient instances.

## Migration steps

-  Add HttpClient 5.0 as a new dependency to the project and optionally remove HttpClient 4.x

-  Remove old `org.apache.http` imports and re-import HttpClient classes from
   `org.apache.hc.httpclient5` package namespace. Most old interfaces and classes should resolve automatically. One
   notable exception is `HttpEntityEnclosingRequest` interface In HttpClient 5.0 one can enclose a request entity with
   any HTTP method even if violates semantic of the method.

-  There will be compilation errors due to API incompatibilities between version series 4.x and 5.x mostly related to
   SSL/TLS and timeout settings and `CloseableHttpClient` instance creation. Several modifications are likely to be
   necessary.

-  Use `PoolingHttpClientConnectionManagerBuilder` class to create connection managers with custom parameters

-  Use `SSLConnectionSocketFactoryBuilder` class to create SSL connection socket factories with custom parameters

-  Explicitly specify TLSv1.2 or TLSv1.3 in order to disable older less versions of the SSL/TLS protocol. Please note
   all SSL versions are excluded by default.

-  Use `Timeout` class to define timeouts.

-  Use `TimeValue` class to define time values (duration).

-  Optionally choose a connection pool concurrency policy: `STRICT` for strict connection max limit guarantees; `LAX`
   for higher concurrency but with lax connection maximum limit guarantees. With `LAX` policy HttpClient can exceed the
   per route maximum limit under high load and does not enforce the total maximum limit.

-  Optionally choose a connection pool re-use policy: `FILO` to re-use as few connections as possible making it possible
   for connections to become idle and expire; `LILO` to re-use all connections equally preventing them from becoming
   idle and expiring.

   ```java
   PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
         .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                 .setSslContext(SSLContexts.createSystemDefault())
                 .setTlsVersions(TLS.V_1_3, TLS.V_1_2)
                 .build())
         .setDefaultSocketConfig(SocketConfig.custom()
                 .setSoTimeout(Timeout.ofSeconds(5))
                 .build())
         .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
         .setConnPoolPolicy(PoolReusePolicy.LIFO)
         .setConnectionTimeToLive(TimeValue.ofMinutes(1L))
         .build();
   ```

-  Favor `standard-strict` cookie policy when using HttpClient 5.0.

-  Use response timeout to define the maximum period of inactivity until receipt of response data.

-  All base principles and good practices of HttpClient programing still apply. Always re-use client instances. Client
   instances are expensive to create and are thread safe in both HttpClient 4.x and 5.0 series.

   ```java
   CloseableHttpClient client = HttpClients.custom()
         .setConnectionManager(connectionManager)
         .setDefaultRequestConfig(RequestConfig.custom()
                 .setConnectTimeout(Timeout.ofSeconds(5))
                 .setResponseTimeout(Timeout.ofSeconds(5))
                 .setCookieSpec(StandardCookieSpec.STRICT)
                 .build())
         .build();
   
   CookieStore cookieStore = new BasicCookieStore();
   
   CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
   
   HttpClientContext clientContext = HttpClientContext.create();
   clientContext.setCookieStore(cookieStore);
   clientContext.setCredentialsProvider(credentialsProvider);
   clientContext.setRequestConfig(RequestConfig.custom()
         .setConnectTimeout(Timeout.ofSeconds(10))
         .setResponseTimeout(Timeout.ofSeconds(10))
         .build());
   
   JsonFactory jsonFactory = new JsonFactory();
   ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
   
   HttpPost httpPost = new HttpPost("https://httpbin.org/post");
   
   List<NameValuePair> requestData = Arrays.asList(
         new BasicNameValuePair("name1", "value1"),
         new BasicNameValuePair("name2", "value2"));
   httpPost.setEntity(HttpEntities.create(outstream -> {
     objectMapper.writeValue(outstream, requestData);
     outstream.flush();
   }, ContentType.APPLICATION_JSON));
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

-  `CloseableHttpClient` instances should be closed when no longer needed or about to go out of score.

   ```java
   client.close();
   ```