/*
 * Copyright 2018 OK2 Consulting Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hc.client5.migration.examples;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.hc.client5.http.async.methods.BasicHttpRequests;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.Message;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.apache.http.NameValuePair;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ok2c.hc5.json.http.JsonRequestProducers;
import com.ok2c.hc5.json.http.JsonResponseConsumers;

public class HttpClient5AsyncStreamExample {

    public static void main(String... args) throws Exception {
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
    }

}
