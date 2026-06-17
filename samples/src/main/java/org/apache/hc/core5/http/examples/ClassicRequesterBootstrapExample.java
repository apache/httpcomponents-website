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

import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.impl.bootstrap.HttpRequester;
import org.apache.hc.core5.http.impl.bootstrap.RequesterBootstrap;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityTemplate;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.protocol.HttpCoreContext;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.testing.classic.LoggingBHttpClientConnectionFactory;
import org.apache.hc.core5.testing.classic.LoggingHttp1StreamListener;
import org.apache.hc.core5.util.Timeout;

public class ClassicRequesterBootstrapExample {

    public static void main(final String[] args) throws Exception {
        final HttpRequester requester = RequesterBootstrap.bootstrap()
                .setSocketConfig(SocketConfig.custom()
                        .setSoTimeout(Timeout.ofMinutes(1))
                        .build())

                // Pluggable logging
                .setConnectionFactory(LoggingBHttpClientConnectionFactory.INSTANCE)
                .setStreamListener(LoggingHttp1StreamListener.INSTANCE)

                .create();

        final HttpHost target = new HttpHost("httpbin.org");
        final HttpCoreContext context = HttpCoreContext.create();

        final ObjectMapper objectMapper = new ObjectMapper();

        final ClassicHttpRequest request = ClassicRequestBuilder.get()
                .setHttpHost(target)
                .setEntity(new EntityTemplate(ContentType.APPLICATION_JSON, out -> {
                    objectMapper.writeValue(out, new BasicNameValuePair("name", "value"));
                }))
                .setPath("/post")
                .build();
        final RequestData requestData = requester.execute(target, request, Timeout.ofSeconds(5), context, response -> {
            System.out.println(target + "->" + response.getCode());
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
                    return objectMapper.readValue(inputStream, RequestData.class);
                }
            } else {
                return null;
            }
        });
        System.out.println(requestData);

        requester.close(CloseMode.GRACEFUL);
    }

}
