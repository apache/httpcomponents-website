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

# Migration to Apache HttpClient 5.x async APIs for HTTP/2 only

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
                 .setTlsVersions(TLS.V_1_3)
                 .buildAsync())
         .setIOReactorConfig(IOReactorConfig.custom()
                 .setSoTimeout(Timeout.ofMinutes(1))
                 .build())
         .setDefaultConnectionConfig(ConnectionConfig.custom()
                 .setSocketTimeout(Timeout.ofMinutes(1))
                 .setConnectTimeout(Timeout.ofMinutes(1))
                 .setTimeToLive(TimeValue.ofMinutes(10))
                 .build())
         .setDefaultRequestConfig(RequestConfig.custom()
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
         .setCookieSpec(StandardCookieSpec.STRICT)
         .build());
   
   JsonFactory jsonFactory = new JsonFactory();
   ObjectMapper objectMapper = new ObjectMapper(jsonFactory);

   Future<?> future = client.execute(
        JsonRequestProducers.create(
                BasicRequestBuilder.post("https://httpbin.org/post").build(),
                Arrays.asList(
                     new BasicNameValuePair("name1", "value1"),
                     new BasicNameValuePair("name2", "value2")),
                objectMapper),
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
