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
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.pool.ConnPoolControl;
import org.apache.hc.core5.util.TimeValue;

public class ClassicHttpClientOpenResponseExample {

    final static PoolingHttpClientConnectionManager CONN_MANAGER;
    final static CloseableHttpClient CLIENT;

    static {
        CONN_MANAGER = PoolingHttpClientConnectionManagerBuilder.create()
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
        final HttpHost target = new HttpHost("http", "httpbin.org");

        final ClassicHttpRequest httpGet = ClassicRequestBuilder.get()
                .setHttpHost(target)
                .setPath("/get")
                .build();

        final HttpClientContext clientContext = HttpClientContext.create();
        try (final ClassicHttpResponse response = client.executeOpen(target, httpGet, clientContext)) {
            System.out.println(response.getCode());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }

        final ConnPoolControl<HttpRoute> connPool = getConnPool();
        connPool.closeIdle(TimeValue.ZERO_MILLISECONDS);
    }

}
