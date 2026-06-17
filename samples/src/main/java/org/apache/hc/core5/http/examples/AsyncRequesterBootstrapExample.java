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
package org.apache.hc.core5.http.examples;

import java.util.concurrent.CountDownLatch;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.Message;
import org.apache.hc.core5.http.impl.bootstrap.HttpAsyncRequester;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.protocol.HttpCoreContext;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.http2.impl.nio.bootstrap.H2RequesterBootstrap;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.jackson2.http.AsyncJsonClientPipeline;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.testing.classic.LoggingConnPoolListener;
import org.apache.hc.core5.testing.nio.LoggingExceptionCallback;
import org.apache.hc.core5.testing.nio.LoggingH2StreamListener;
import org.apache.hc.core5.testing.nio.LoggingHttp1StreamListener;
import org.apache.hc.core5.testing.nio.LoggingIOSessionDecorator;
import org.apache.hc.core5.testing.nio.LoggingIOSessionListener;
import org.apache.hc.core5.testing.nio.LoggingReactorMetricsListener;
import org.apache.hc.core5.util.Timeout;

public class AsyncRequesterBootstrapExample {

    public static void main(final String[] args) throws Exception {

        final HttpAsyncRequester requester = H2RequesterBootstrap.bootstrap()
                .setIOReactorConfig(IOReactorConfig.custom()
                        .setSoTimeout(Timeout.ofMinutes(1))
                        .build())
                .setVersionPolicy(HttpVersionPolicy.NEGOTIATE)

                // Pluggable logging
                .setStreamListener(LoggingHttp1StreamListener.INSTANCE_CLIENT)
                .setStreamListener(LoggingH2StreamListener.INSTANCE)
                .setConnPoolListener(LoggingConnPoolListener.INSTANCE)
                .setIOSessionDecorator(LoggingIOSessionDecorator.INSTANCE)
                .setExceptionCallback(LoggingExceptionCallback.INSTANCE)
                .setIOSessionListener(LoggingIOSessionListener.INSTANCE)
                .setIOReactorMetricsListener(LoggingReactorMetricsListener.INSTANCE)

                .create();
        requester.start();

        final HttpHost target = new HttpHost("httpbin.org");
        final HttpCoreContext context = HttpCoreContext.create();

        final ObjectMapper objectMapper = new ObjectMapper();

        final CountDownLatch latch = new CountDownLatch(1);
        requester.execute(
                AsyncJsonClientPipeline.assemble(objectMapper)
                        .request()
                        .post(target, "/post")
                        .asObject(new BasicNameValuePair("name", "value"))
                        .response()
                        .asObject(RequestData.class)
                        .result(new FutureCallback<>() {

                            @Override
                            public void completed(final Message<HttpResponse, RequestData> m) {
                                final HttpResponse response = m.head();
                                final RequestData requestData = m.body();
                                System.out.println(target + "->" + response.getCode());
                                System.out.println(requestData);
                                latch.countDown();
                            }

                            @Override
                            public void failed(final Exception ex) {
                                ex.printStackTrace(System.out);
                                latch.countDown();
                            }

                            @Override
                            public void cancelled() {
                                latch.countDown();
                            }

                        })
                        .create(),
                Timeout.ofMinutes(1),
                context);

        latch.await();

        requester.close(CloseMode.GRACEFUL);
    }

}
