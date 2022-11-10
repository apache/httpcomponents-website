# Migration to Apache HttpClient 5.0 async APIs with simple message handlers

While HttpClient 5.0 classic APIs are largely compatible with HttpClient 4.0 APIs and can work with
any `InputStream / OutputStream` based content processing library, the HttpClient 5.0 async APIs are completely
different.

HttpClient 5.0 async APIs use an event-driven, reactive programming model based on the concept of channels and event
handlers. The channels act as conduits for asynchronous data output. The event handlers react to asynchronous signals or
events and communicate with the opposite endpoint through available channels.

Both the classic and the async models have their merits. The event-driven, reactive model tends to be efficient and
convenient for communication protocols with message multiplexing such as HTTP/2. However async APIs generally not
integrate well with `InputStream / OutputStream` based content processing libraries.

HttpClient 5.0 provides simplified request and response handlers that hide the complexity of the event-driven model by
buffering message content in memory. Simplified APIs are intended as a temporary, intermediate step in the migration
process or for productive use in scenarios where messages known to be limited in length and the opposite endpoints are
either known to be well-behaved or specifically designed for simple message handlers.

## Migration steps

-  Replace `PoolingHttpClientConnectionManager` with `PoolingAsyncClientConnectionManager`

   ```java
   PoolingAsyncClientConnectionManager connectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
         .setTlsStrategy(ClientTlsStrategyBuilder.create()
                 .setSslContext(SSLContexts.createSystemDefault())
                 .setTlsVersions(TLS.V_1_3, TLS.V_1_2)
                 .build())
         .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
         .setConnPoolPolicy(PoolReusePolicy.LIFO)
         .setConnectionTimeToLive(TimeValue.ofMinutes(1L))
         .build();
   ```

   Please note that `PoolingAsyncClientConnectionManager` uses different `TLS/SSL` configuration. It also does not
   support `SocketConfig` configuration due to differences in the I/O model.

-  Replace `CloseableHttpClient` with `CloseableHttpAsyncClient`.

-  Select appropriate HTTP version policy. Presently supported policies are: `NEGOTIATE`, `FORCE_HTTP_1`
   and `FORCE_HTTP_2`. When the `NEGOTIATE` policy is chosen, HttpClient attempts to negotiate the use of HTTP/2 through
   the TLS ALPN (application protocol negotiation) extension as long as it is supported by the default JSSE .

   ```java
   CloseableHttpAsyncClient client = HttpAsyncClients.custom()
         .setConnectionManager(connectionManager)
         .setDefaultRequestConfig(RequestConfig.custom()
                 .setConnectTimeout(Timeout.ofSeconds(5))
                 .setResponseTimeout(Timeout.ofSeconds(5))
                 .setCookieSpec(StandardCookieSpec.STRICT)
                 .build())
         .setVersionPolicy(HttpVersionPolicy.NEGOTIATE)
         .build();
   ```
   
   When running on Java 9 or newer HttpClient can use TLS ALPN supported provided by the standard JSSE provider via
   reflection. When running on Java 1.7 or Java 1.8 it can automatically detect Conscrypt TLS library if it is present
   on the classpath and use its JSSE provider instead of the standard one shipped with the platform.

-  Unlike the classic client the asynchronous one needs to be started in order to be able to execute requests.

   ```java
   client.start();
   ```    
-  Request execution with simple asynchronous handlers is not that radically different than with the classic APIs. The
   major difference is that the result of the operation is controlled with a `Future` interface and is signalled
   with `FutureCallback` events.

   Message content processing for simple asynchronous handlers can be implemented with any library that can work with
   I/O streams (`InputStream`/`OutputStream`) or strings.

   ```java
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
   
   SimpleHttpRequest httpPost = SimpleHttpRequests.post("https://httpbin.org/post");
   
   List<NameValuePair> requestData = Arrays.asList(
         new BasicNameValuePair("name1", "value1"),
         new BasicNameValuePair("name2", "value2"));
   
   httpPost.setBody(objectMapper.writeValueAsString(requestData), ContentType.APPLICATION_JSON);
   
   Future<?> future = client.execute(httpPost, new FutureCallback<SimpleHttpResponse>() {
   
     @Override
     public void completed(SimpleHttpResponse response) {
         try {
             JsonNode responseData = objectMapper.readTree(response.getBodyText());
             System.out.println(responseData);
         } catch (IOException ex) {
             System.out.println("Error processing jSON content: " + ex.getMessage());
         }
     }
   
     @Override
     public void failed(Exception ex) {
         System.out.println("Error executing HTTP request: " + ex.getMessage());
     }
   
     @Override
     public void cancelled() {
         System.out.println("HTTP request execution cancelled");
     }
   
   });
   ```    
-  `CloseableHttpAsyncClient` instances should be closed when no longer needed or about to go out of score.

   ```java
   client.close(CloseMode.GRACEFUL);
   ```       