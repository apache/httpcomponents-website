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
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.EndpointDetails;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.MethodNotSupportedException;
import org.apache.hc.core5.http.ProtocolException;
import org.apache.hc.core5.http.impl.bootstrap.HttpServer;
import org.apache.hc.core5.http.impl.bootstrap.ServerBootstrap;
import org.apache.hc.core5.http.impl.bootstrap.StandardFilter;
import org.apache.hc.core5.http.impl.routing.RequestRouter;
import org.apache.hc.core5.http.io.HttpRequestHandler;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityTemplate;
import org.apache.hc.core5.http.io.support.AbstractHttpServerAuthFilter;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.http.protocol.HttpCoreContext;
import org.apache.hc.core5.net.URIAuthority;
import org.apache.hc.core5.testing.classic.LoggingBHttpServerConnectionFactory;
import org.apache.hc.core5.testing.classic.LoggingExceptionListener;
import org.apache.hc.core5.testing.classic.LoggingHttp1StreamListener;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

/**
 * Example of embedded HTTP/1.1 file server using classic I/O.
 */
public class ClassicServerBootstrapExample {

    public static void main(final String[] args) throws Exception {
        int port = 8080;
        if (args.length >= 1) {
            port = Integer.parseInt(args[1]);
        }

        final ObjectMapper objectMapper = new ObjectMapper();

        final HttpRequestHandler requestHandler = (request, response, localContext) -> {

            final String method = request.getMethod();
            if (!Method.GET.isSame(method) &&
                    !Method.HEAD.isSame(method) &&
                    !Method.POST.isSame(method) &&
                    !Method.PUT.isSame(method) &&
                    !Method.PATCH.isSame(method)) {
                throw new MethodNotSupportedException(method + " method not supported");
            }

            final RequestData rd = new RequestData();
            try {
                rd.setUrl(request.getUri());
            } catch (final URISyntaxException ex) {
                throw new ProtocolException("Invalid request URI");
            }
            rd.generateHeaders(request.getHeaders());
            final HttpEntity requestEntity = request.getEntity();
            if (requestEntity != null) {
                try (final InputStream inputStream = requestEntity.getContent()) {
                    rd.setJson(objectMapper.readTree(inputStream));
                }
            }

            final HttpCoreContext coreContext = HttpCoreContext.cast(localContext);
            final EndpointDetails endpointDetails = coreContext.getEndpointDetails();

            final InetSocketAddress remoteAddress = (InetSocketAddress) endpointDetails.getRemoteAddress();
            rd.setOrigin(Objects.toString(remoteAddress.getAddress()));
            response.setEntity(new EntityTemplate(ContentType.APPLICATION_JSON, out -> {
                objectMapper.writeValue(out, rd);
            }));
        };

        final AbstractHttpServerAuthFilter<String> authFilter = new AbstractHttpServerAuthFilter<>(false) {

            @Override
            protected String parseChallengeResponse(
                    final String authorizationValue, final HttpContext context) throws HttpException {
                // Parse the authorizaton header value into a custom token representation
                return authorizationValue;
            }

            @Override
            protected boolean authenticate(
                    final String token,
                    final URIAuthority authority,
                    final String requestUri,
                    final HttpContext context) {
                // Validate token and return true if the user has been autheticated
                return "let me pass".equals(token);
            }

            @Override
            protected String generateChallenge(
                    final String challengeResponse,
                    final URIAuthority authority,
                    final String requestUri,
                    final HttpContext context) {
                // Generate a challenge in case the user has not been autheticated
                return "who goes there?";
            }

        };

        final HttpServer server = ServerBootstrap.bootstrap()
                .setListenerPort(port)
                .setSocketConfig(SocketConfig.custom()
                        .setSoTimeout(Timeout.ofMinutes(1))
                        .build())
                .setRequestRouter(RequestRouter.<HttpRequestHandler>builder()
                        .addRoute(RequestRouter.LOCAL_AUTHORITY, "*", requestHandler)
                        .resolveAuthority(RequestRouter.LOCAL_AUTHORITY_RESOLVER)
                        .build())
                .replaceFilter(StandardFilter.EXPECT_CONTINUE.name(), authFilter)

                // Pluggable logging
                .setConnectionFactory(LoggingBHttpServerConnectionFactory.INSTANCE)
                .setStreamListener(LoggingHttp1StreamListener.INSTANCE)
                .setExceptionListener(LoggingExceptionListener.INSTANCE)

                .create();

        server.start();
        System.out.println("Listening on port " + port);
        server.awaitTermination(TimeValue.MAX_VALUE);
    }

}
