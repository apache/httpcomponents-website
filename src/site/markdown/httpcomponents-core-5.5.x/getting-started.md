Getting started with HttpCore
=================

Please note HttpCore is a set of customizable components, not an embeddable server or a
client. It is not intended for deployment to productive environments, especially
potentially hostile, without prior customization and configuration.

Supported I/O models and HTTP protocol versions
------------------

HttpCore supports two i/o models: the classic i/o model based on blocking InputStream /
OutputStream APIs and the event-driven async i/o model. Both models have their advantages
and their special use cases.

The async i/o model is believed to be more scalable and better suited for working with
thousands of concurrent connections while utilizing just a few reactor threads. This can
especially be relevant if many of connections can remain idle for a considerable period
of time. However, this may no longer be the case for newer Java Runtimes that support
virtual threads.

The classic i/o model tends to be simpler and more convenient due to its seamless
integration with any InputStream / OutputStream based content producer and consumer
and much more straight-forward exception handling.

The async i/o model on the other hand tends to be more complex due to its event-driven
nature. It also does not work well with InputStream / OutputStream based content
producers and consumers and usually may still need to maintain a worker thread if the
message exchange handler can get blocked while producing or consuming data.

The classic i/o model requires a dedicated thread for each active connection in order
to be able to read or write data. But it cannot do both reading and writing while using
a single thread. This makes the classic i/o model ill-suited for multiplexing protocols
such as HTTP/2. However, it works very well with request / response based protocols such
as HTTP/1.1.

Ultimately the choice of an i/o model boils down to whether or not an application can
make an effective use of message exchange multiplexing with multiple long message streams
running concurrently over the same physical connection. If so, one would be better off
choosing the async (event-driven) model. Otherwise, one may choose the classic i/o model
as in many common use cases HTTP/2 provides no tangible advantage over HTTP/1.1,
especially if the application is based on the REST request / response style of
communicaton.

HTTP requesters
------------------
HttpCore ships with several HTTP requester implementations. Please note that HttpCore
requesters are not full-featured clients. They can efficiently execute HTTP message
exchanges over multiple pooled connections but do not support advanced client features
such as automatic redirect handling, content compression / decompression, response
caching, state management and different authentication schemes and credentials management.
For advanced client features one should use Apache HttpClient instead.

HttpCore requesters can however be efficiently used to execute JSON formatted message
exchanges with remote REST endpoints using a Bearer tokenor similar for authetication.

### Async requester

The async requester can efficiently execute multiple message exchanges over thousands of
concurrent connections using HTTP/1.1 and HTTP/2 protocol.

Please note async message exchange handlers are stateful and cannot be executed multiple
times (unless their state can be reset). One must create a new instance for each new
message exchange execution.

One can use`AsyncClientPipeline` and `AsyncJsonClientPipeline` utility classes to 
simplify assembly of async message exchange handlers.

```java
final HttpAsyncRequester requester = H2RequesterBootstrap.bootstrap()
        .setIOReactorConfig(IOReactorConfig.custom()
                .setSoTimeout(Timeout.ofMinutes(1))
                .build())
        .setVersionPolicy(HttpVersionPolicy.NEGOTIATE)
        .create();
requester.

start();

final HttpHost target = new HttpHost("httpbin.org");
final HttpCoreContext context = HttpCoreContext.create();

final ObjectMapper objectMapper = new ObjectMapper();

final CountDownLatch latch = new CountDownLatch(1);
requester.

execute(
        AsyncJsonClientPipeline.assemble(objectMapper)
                .

request()
                .

post(target, "/post")
                .

asObject(new BasicNameValuePair("name", "value"))
        .

response()
                .

asObject(RequestData .class)
                .

result(new FutureCallback<>() {

    @Override
    public void completed ( final Message<HttpResponse, RequestData> m){
        final HttpResponse response = m.head();
        System.out.println(target + "->" + response.getCode());
        latch.countDown();
    }

    @Override
    public void failed ( final Exception ex){
        ex.printStackTrace(System.out);
        latch.countDown();
    }

    @Override
    public void cancelled () {
        latch.countDown();
    }

})
        .

create(),
        Timeout.

ofMinutes(1),

context);

        latch.

await();
requester.

close(CloseMode.GRACEFUL);
```

### Classic requester

The classic transport generally tends to be more convenient to use and integrate with
content libraries based on standard `InputStream`/`OutputStream` APIs. One should consider
choosing the classic requester for use scenarios with the HTTP/2 protocol support being
optional.

Classic message exchange handlers can be stateless and they generally tend to have
a simpler, more predictable execution flow and exception handling.

```java
final HttpRequester requester = RequesterBootstrap.bootstrap()
        .setSocketConfig(SocketConfig.custom()
                .setSoTimeout(Timeout.ofMinutes(1))
                .build())
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
System.out.

println(requestData);

requester.

close(CloseMode.GRACEFUL);
```

HTTP servers
------------------
HttpCore ships with several HTTP embeddable server implementations. The servers are fully
capable of efficiently managing multiple concurrent message exchanges over multiple
connections. Incoming requrests can be routed to individual request handlers for
processing based on predefined request route definitions.

Please note however HttpCore servers do not support advanced server features such as
different authentication schemes, access control and user identity management out of the
box. These features however can be plugged in using custom message exchange interceptors.

HttpCore servers can be efficiently used to handle JSON formatted message exchanges with
remote REST client using Bearer token scheme or similar for authentication.

### Async server

The async server can efficiently execute multiple message exchanges over thousands of
concurrent connections using HTTP/1.1 and HTTP/2 protocol.

Please note async message exchange handlers are state-ful and cannot be executed multiple
times. One must create a new instance for each new message exchange execution.

One can use`AsyncServerPipeline` and `AsyncJsonServerPipeline` utility classes to 
simplify assembly of async message exchange handlers.

```java
final IOReactorConfig config = IOReactorConfig.custom()
        .setSoTimeout(15, TimeUnit.SECONDS)
        .build();

final ObjectMapper objectMapper = new ObjectMapper();

final Supplier<AsyncServerExchangeHandler> exchangeHandlerSupplier = AsyncJsonServerPipeline.assemble(objectMapper)
        // Read GET / HEAD requests by consuming content stream as JSON nodes
        .request(Method.GET, Method.HEAD, Method.POST, Method.PUT, Method.PATCH)
        .asJsonNode()
        // Write out responses by streaming out content of JSON object
        .response()
        .asObject(RequestData.class)
        // Map exceptions to a response message
        .errorMessage(Throwable::getMessage)
        // Generate a response to a request
        .handle((m, context) -> {
            final HttpRequest request = m.head();
            final RequestData rd = new RequestData();
            try {
                rd.setUrl(request.getUri());
            } catch (final URISyntaxException ex) {
                throw new ProtocolException("Invalid request URI");
            }
            rd.generateHeaders(request.getHeaders());
            rd.setJson(m.body());
            rd.setData(Objects.toString(m.error()));

            final HttpCoreContext coreContext = HttpCoreContext.cast(context);
            final EndpointDetails endpointDetails = coreContext.getEndpointDetails();

            final InetSocketAddress remoteAddress = (InetSocketAddress) endpointDetails.getRemoteAddress();
            rd.setOrigin(Objects.toString(remoteAddress.getAddress()));

            return Message.of(new BasicHttpResponse(HttpStatus.SC_OK), rd);
        })
        .supplier();

final AbstractAsyncServerAuthFilter<String> authFilter = new AbstractAsyncServerAuthFilter<>(true) {

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
        return "let me pass".equals(challengeResponse);
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
final HttpAsyncServer server = H2ServerBootstrap.bootstrap()
        .setExceptionCallback(e -> e.printStackTrace())
        .setIOReactorConfig(config)
        .setRequestRouter(RequestRouter.<Supplier<AsyncServerExchangeHandler>>builder()
                .addRoute(RequestRouter.LOCAL_AUTHORITY, "*", exchangeHandlerSupplier)
                .resolveAuthority(RequestRouter.LOCAL_AUTHORITY_RESOLVER)
                .build())
        .replaceFilter(StandardFilter.EXPECT_CONTINUE.name(), authFilter)
        .create();


server.

start();

final Future<ListenerEndpoint> future = server.listen(new InetSocketAddress(port), URIScheme.HTTP);
final ListenerEndpoint listenerEndpoint = future.get();
System.out.

println("Listening on "+listenerEndpoint.getAddress());
        server.

awaitShutdown(TimeValue.MAX_VALUE);
```

### Classic server

The classic server can be a good choice when one controls both the client and the server
sides, does not need to handle requests with large or unexpected content body and support
thousands of concurrent connections.

Classic message exchange handlers can be stateless and they generally tend to have a
simpler, more predictable execution flow and exception handling.

```java
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
        .create();

server.

start();
System.out.

println("Listening on port "+port);
server.

awaitTermination(TimeValue.MAX_VALUE);
```

Request multiplexing / pipelining
------------------

HttpCore supports message exchange multiplexing over HTTP/2 connections and message
exchange pipelining over HTTP/1.1 connections by default. However initiation of concurrent
message exchanges on the client side require some manual management of the connection
lease.
One needs to lease a connection from the connection pool to execute multiple messages
over the same connection, and release it back to the pool when done. One also must
aqcuire a new connection from the connection in case a message exchange failure due to
an I/O error.

```java
final HttpAsyncRequester requester = H2RequesterBootstrap.bootstrap()
        .setIOReactorConfig(IOReactorConfig.custom()
                .setSoTimeout(Timeout.ofMinutes(1))
                .build())
        .setVersionPolicy(HttpVersionPolicy.NEGOTIATE)
        .create();
requester.

start();

final HttpHost target = new HttpHost("nghttp2.org");

final ObjectMapper objectMapper = new ObjectMapper();

final Future<AsyncClientEndpoint> future = requester.connect(target, Timeout.ofSeconds(30));
final AsyncClientEndpoint clientEndpoint = future.get();

int n = 3;
final CountDownLatch latch = new CountDownLatch(n);
final AtomicBoolean failure = new AtomicBoolean();
for(
int i = 1;
i <=3;i++){
        requester.

execute(
        AsyncJsonClientPipeline.assemble(objectMapper)
                    .

request()
                    .

post(target, "/post")
                    .

asObject(new BasicNameValuePair("name", "value"))
        .

response()
                    .

asObject(RequestData .class)
                    .

result(new FutureCallback<>() {

    @Override
    public void completed ( final Message<HttpResponse, RequestData> m){
        final HttpResponse response = m.head();
        final RequestData requestData = m.body();
        System.out.println(target + "->" + response.getCode());
        System.out.println(requestData);
        latch.countDown();
    }

    @Override
    public void failed ( final Exception ex){
        ex.printStackTrace(System.out);
        failure.set(true);
        latch.countDown();
    }

    @Override
    public void cancelled () {
        latch.countDown();
    }

})
        .

create(),
            Timeout.

ofMinutes(1),
            HttpCoreContext.

create());
        }

        latch.

await();
if(failure.

get()){
        clientEndpoint.

releaseAndDiscard();
}else{
        clientEndpoint.

releaseAndReuse();
}

        requester.

close(CloseMode.GRACEFUL);
```

Logging
------------------

HttpCore does not depend on a specific logging toolkit or logging facade. This is a
conscious decision as HttpCore has no direct dependencies on any other libraties by
design.

HttpCore requesters and servers however expose several listener interfaces that can
be used to plug-in custom logging.

HttpCore ships with `httpcore5-testing` module that provides a number of listener
implementations that log DEBUG level priority events through `slf4j` facade.

* Async server logging

```java
final HttpAsyncServer server = H2ServerBootstrap.bootstrap()
        // Pluggable logging
        .setStreamListener(LoggingHttp1StreamListener.INSTANCE_SERVER)
        .setStreamListener(LoggingH2StreamListener.INSTANCE)
        .setIOSessionDecorator(LoggingIOSessionDecorator.INSTANCE)
        .setExceptionCallback(LoggingExceptionCallback.INSTANCE)
        .setIOSessionListener(LoggingIOSessionListener.INSTANCE)
        .setIOReactorMetricsListener(LoggingReactorMetricsListener.INSTANCE)

        .create();
```

* Classic server logging

```java
final HttpServer server = ServerBootstrap.bootstrap()
        // Pluggable logging
        .setConnectionFactory(LoggingBHttpServerConnectionFactory.INSTANCE)
        .setStreamListener(LoggingHttp1StreamListener.INSTANCE)
        .setExceptionListener(LoggingExceptionListener.INSTANCE)

        .create();
```

* Async requester logging

```java
final HttpAsyncRequester requester = H2RequesterBootstrap.bootstrap()
        // Pluggable logging
        .setStreamListener(LoggingHttp1StreamListener.INSTANCE_CLIENT)
        .setStreamListener(LoggingH2StreamListener.INSTANCE)
        .setConnPoolListener(LoggingConnPoolListener.INSTANCE)
        .setIOSessionDecorator(LoggingIOSessionDecorator.INSTANCE)
        .setExceptionCallback(LoggingExceptionCallback.INSTANCE)
        .setIOSessionListener(LoggingIOSessionListener.INSTANCE)
        .setIOReactorMetricsListener(LoggingReactorMetricsListener.INSTANCE)

        .create();
```

* Classic requester logging

```java
final HttpRequester requester = RequesterBootstrap.bootstrap()
        // Pluggable logging
        .setConnectionFactory(LoggingBHttpClientConnectionFactory.INSTANCE)
        .setStreamListener(LoggingHttp1StreamListener.INSTANCE)

        .create();
```
