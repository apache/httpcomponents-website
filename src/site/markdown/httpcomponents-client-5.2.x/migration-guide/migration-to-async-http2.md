# Migration to Apache HttpClient 5.0 async APIs for HTTP/2 only

For those scenarios where HTTP/1.1 compatibility is no longer required HttpClient 5.0
provides `CloseableHttpAsyncClient` optimized for HTTP/2 protocol with full support for multiplexed request execution
over a single HTTP/2 connection.

## Migration steps

-  Replace the default client builder with HTTP/2 specific one. Request and response handlers do not require any
   modification.

   Please note HTTP/2 clients do not have a connection manager. They maintain an internal map of active connections,
   one per distinct origin host. Therefore, TLS settings can be applied directly to the client instance, not a
   connection manager.

   Please note that presently HTTP/2 clients do not support request execution via an HTTP proxy.

   ```java
   CloseableHttpAsyncClient client = HttpAsyncClients.customHttp2()
         .setTlsStrategy(ClientTlsStrategyBuilder.create()
                 .setSslContext(SSLContexts.createSystemDefault())
                 .setTlsVersions(TLS.V_1_3, TLS.V_1_2)
                 .build())
         .setIOReactorConfig(IOReactorConfig.custom()
                 .setSoTimeout(Timeout.ofSeconds(5))
                 .build())
         .setDefaultRequestConfig(RequestConfig.custom()
                 .setConnectTimeout(Timeout.ofSeconds(5))
                 .setResponseTimeout(Timeout.ofSeconds(5))
                 .setCookieSpec(StandardCookieSpec.STRICT)
                 .build())
         .build();
   client.start();
   
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
   
   HttpRequest httpPost = BasicHttpRequests.post("https://nghttp2.org/httpbin/post");
   
   List<NameValuePair> requestData = Arrays.asList(
         new org.apache.http.message.BasicNameValuePair("name1", "value1"),
         new org.apache.http.message.BasicNameValuePair("name2", "value2"));
   
   Future<?> future = client.execute(
         JsonRequestProducers.create(httpPost, requestData, objectMapper),
         JsonResponseConsumers.create(jsonFactory),
         new FutureCallback<Message<HttpResponse, JsonNode>>() {
   
             @Override
             public void completed(Message<HttpResponse, JsonNode> message) {
                 System.out.println(message.getBody());
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
   
   future.get();
   client.close(CloseMode.GRACEFUL);
   ```
