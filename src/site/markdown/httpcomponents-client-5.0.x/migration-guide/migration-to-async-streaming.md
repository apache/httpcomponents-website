# Migration to Apache HttpClient 5.0 async APIs

HttpClient ships with a number of standard message handlers for the most common scenarios as well as a number of
abstract classes that can be used as a base for custom handlers.

* `BasicAsyncEntityConsumer`, `BasicAsyncEntityProducer` entity handlers that buffer entity content in memory and
  therefore are not much different than simple handlers.

* `AbstractBinAsyncEntityConsumer` and `AbstractBinAsyncEntityProducer` handlers can server as the base classes for
  custom binary entity consumers or producers.

* `AbstractCharAsyncEntityConsumer` and `AbstractCharAsyncEntityProducer` handlers can server as the base classes for
  custom character entity consumers or producers.

* `FileEntityProducer` entity handler that generates data stream from a file.

* `AbstractClassicEntityConsumer` and `AbstractClassicEntityProducer` entity handlers that acts as a compatibility layer
  for classic `InputStream` / `OutputStream` based interfaces. Blocking input / output processing is executed through
  an `Executor`.

* `BasicRequestProducer` and `BasicResponseConsumer` messages handlers that perform no message head transformation and
  can use any custom entity producer or consumer to handler the message body.

* `AbstractBinResponseConsumer` response handler can server as the base class for custom binary response consumers.

* `AbstractCharResponseConsumer` response handler can server as the base class for custom character response consumers.

There are also third-party libraries that can provide specialized message handlers, for instance,
for [JSON message processing](https://github.com/ok2c/httpcomponents-jackson) using
[Jackson JSON processor](https://github.com/FasterXML/jackson).

## Migration steps

-  Depending on the preferred migration path it one might consider migrating to HttpClient 5.0 classic APIs or
   HttpClient 5.0 async APIs with simple message handlers as the first step.

-  Replace the existing request generation and response processing code with optimized message handlers shipped with
   HttpClient 5.0 or custom-built handlers.

   In this particular instance [JSON message handlers](https://github.com/ok2c/httpcomponents-jackson)
   are used to process JSON messages of arbitrary length without intermediate buffering of the entire message.

   The general use pattern remains the same as with simple message handlers.
   
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
    
   CloseableHttpAsyncClient client = HttpAsyncClients.custom()
        .setConnectionManager(connectionManager)
        .setIOReactorConfig(IOReactorConfig.custom()
                .setSoTimeout(Timeout.ofSeconds(5))
                .build())
        .setDefaultRequestConfig(RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(5))
                .setResponseTimeout(Timeout.ofSeconds(5))
                .setCookieSpec(StandardCookieSpec.STRICT)
                .build())
        .setVersionPolicy(HttpVersionPolicy.NEGOTIATE)
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
    
   HttpRequest httpPost = BasicHttpRequests.post("https://httpbin.org/post");
    
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
