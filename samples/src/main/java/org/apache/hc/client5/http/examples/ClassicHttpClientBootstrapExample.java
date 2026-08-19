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

import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.Message;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.pool.ConnPoolControl;
import org.apache.hc.core5.util.TimeValue;

public class ClassicHttpClientBootstrapExample {

    final static PoolingHttpClientConnectionManager CONN_MANAGER;
    final static CloseableHttpClient CLIENT;

    static {
        CONN_MANAGER = PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(ConnectionConfig.custom()
                        .setTimeToLive(TimeValue.ofMinutes(5))
                        .build())
                .build();
    }

    static {
        CLIENT = HttpClientBuilder.create()
                .setConnectionManager(CONN_MANAGER)
                .build();
    }

    static CloseableHttpClient getClient() {
        return CLIENT;
    }

    static ConnPoolControl<HttpRoute> getConnPool() {
        return CONN_MANAGER;
    }

    public static void main(final String[] args) throws Exception {
        final CloseableHttpClient client = getClient();
        final ClassicHttpRequest httpGet = ClassicRequestBuilder.get()
                .setHttpHost(new HttpHost("http", "httpbin.org"))
                .setPath("/get")
                .build();
        final Message<HttpResponse, String> response = client.execute(httpGet, r ->
                new Message<>(r, EntityUtils.toString(r.getEntity())));
        System.out.println(response.head().getCode());
        System.out.println(response.body());

        final ConnPoolControl<HttpRoute> connPool = getConnPool();
        connPool.closeIdle(TimeValue.ZERO_MILLISECONDS);
    }

}
