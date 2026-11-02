/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.hc.client5.http.examples;

import java.util.concurrent.Future;

import org.apache.hc.client5.http.ContextBuilder;
import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.Message;
import org.apache.hc.core5.http.nio.entity.StringAsyncEntityConsumer;
import org.apache.hc.core5.http.nio.support.BasicRequestProducer;
import org.apache.hc.core5.http.nio.support.BasicResponseConsumer;
import org.apache.hc.core5.http.support.BasicRequestBuilder;
import org.apache.hc.core5.pool.ConnPoolControl;


public class AsyncHttpClientContextExample {

    final static PoolingAsyncClientConnectionManager CONN_MANAGER;
    final static CloseableHttpAsyncClient CLIENT;

    static {
        CONN_MANAGER = PoolingAsyncClientConnectionManagerBuilder.create()
                .build();
    }

    static {
        CLIENT = HttpAsyncClientBuilder.create()
                .setConnectionManager(CONN_MANAGER)
                .build();
        CLIENT.start();
    }

    static CloseableHttpAsyncClient getClient() {
        return CLIENT;
    }

    static ConnPoolControl<HttpRoute> getConnPool() {
        return CONN_MANAGER;
    }

    public static void main(final String[] args) throws Exception {
        final CloseableHttpAsyncClient client = getClient();

        // Create a local instance of cookie store
        final CookieStore cookieStore = new BasicCookieStore();

        // Create local HTTP context
        final HttpClientContext localContext = ContextBuilder.create()
                // Bind custom cookie store to the local context
                .useCookieStore(cookieStore)
                .build();
        // Provide request custom settings
        localContext.setRequestConfig(RequestConfig.custom()
                .setCookieSpec(StandardCookieSpec.STRICT)
                .build());

        for (int i = 1; i <= 3; i++) {
            final HttpRequest httpGet = BasicRequestBuilder.get()
                    .setHttpHost(new HttpHost("http", "httpbin.org"))
                    .setPath("/cookies")
                    .build();
            final Future<Message<HttpResponse, String>> future = client.execute(
                    new BasicRequestProducer(httpGet, null),
                    new BasicResponseConsumer<>(StringAsyncEntityConsumer::new),
                    null,
                    localContext,
                    null);
            final Message<HttpResponse, String> response = future.get();
            System.out.println(response.head().getCode());
            System.out.println(response.body());

            for (Cookie cookie : cookieStore.getCookies()) {
                System.out.println("Local cookie: " + cookie);
            }
        }
    }

}
