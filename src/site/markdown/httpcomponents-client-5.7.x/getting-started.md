Getting started with HttpClient
=================

Supported I/O models and HTTP protocol versions
------------------

HttpCore, the transport library HttpClient is based upon, supports two i/o models:
the classic i/o model based on blocking InputStream / OutputStream APIs and
the event-driven async i/o model. Both models have their advantages and their special
use cases.

Ultimately, the choice of an i/o model boils down to whether or not an application can
make an effective use of message exchange multiplexing with multiple long message streams
running concurrently over the same physical connection. If so, one would be better off
choosing the async (event-driven) model. Otherwise, one may choose the classic i/o model
as in many common use cases HTTP/2 provides no tangible advantage over HTTP/1.1,
especially if the application is based on a request / response style of communicaton.

For more details regarding the i/o modes supported by HttpCore please see
[HttpCore Getting Started Guide](../httpcomponents-core-5.5.x/getting-started.html)

HttpClient implementations
------------------

HttpClient comes with several HTTP client implementations based on different i/o models
and with different set of supported features.

* **Classic HttpClient**. This is a full-featured, general-purpose HttpClient
  implementation based on the classic i/o model. This implementation should be the default
  choice for the majority of users.
* **Async HttpClient**. This is a full-featured, general-purpose HttpClient implementation
  based on the event-driven i/o model. This implementation supports message exchange 
  multiplexing over HTTP/2 connections.
* **Async HTTP/2 HttpClient**. This is a full-featured HttpClient implementation based on 
  the event-driven i/o model. This implementation is optimized for message exchange 
  multiplexing but it does not support the HTTP/1.1 protocol.
* **Minimal HttpClient**. There are several minimal HttpClient implementations based
  the classic and the event-driven models optimized for efficiently of message exchange
  execution with some advanced features such as automatic authentication, redirect
  handling, state management and automatic content decompression removed from the 
  protocol pipeline.
* **Reactive Bindings**. This is a facade that acts a compatibility layer with
  [Reactive Streams Bindings](https://www.reactive-streams.org/) on top of the Async
  HttpClient.
* **Jakarta REST Bindings**. This is a facade that generates a dynamic proxy around an 
  interface with Jakarta REST annotations backed by Async HttpClient.

The choice of an HttpClient implementation should be driven by the specific application
requirements but the classic HttpClient is likely the most reasonable option to get 
started with. If the application can benefit from the message exchange multiplexing, one 
can migrate from the classic implementation to Async HttpClient or Async HTTP/2 
HttpClient as described in the 
[Migration Guide](../httpcomponents-client-5.6.x/migration-guide/migration-to-async-simple.html)  

For applications designed around REST communication the 
[Jakarta REST Bindings](rest-client.md) may be a good choice.   

HttpClient life-cycle
------------------

There are several builder classes provided by the framework that facilitate the process
of HttpClient configuration and instantiation. HttpClient instances are very expensive 
to create. Usually one should always re-use HttpClient for subsequent message exchanges.
Creating a new instance of HttpClient for each message exchange is like opening and 
closing the browser for each and every link. It is very resource inefficient and wasteful.

It is strongly recommended to create a single instance of HttpClient per distinct service
or application layer. The client singleton should be created and closed at the same time 
with its service or application layer.

* Classic HttpClient

```java
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
```

* Async HttpClient

```java
final static PoolingAsyncClientConnectionManager CONN_MANAGER;

static {
    CONN_MANAGER = PoolingAsyncClientConnectionManagerBuilder.create()
            .build();
}

final static CloseableHttpAsyncClient CLIENT;

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
```
Please note that Connection manager and HttpClient instances are made static for 
simplicity. One should be using an application container to manage service singletons.

HttpClient and Connection manager instances are fully thread-safe.

Request execution
------------------

HttpClient ships with several classes that represent standard HTTP methods such as `GET`,
`POST`, `HEAD`, `PUT`, `DELETE`, `QUERY` and so on. There are also builder classes that
can be used to assemble requests with custom headers and request bodies.

* Classic HttpClient

Please note that classic request and entity objects are not thread-safe. They must not
be executed multiple times unless their state can be reset. When sharing request objects
and content data between multiple threads access to attributes of those objects must be
synchronized. 

```java
// Get HttpClient singleton
final CloseableHttpClient client = getClient();
final ClassicHttpRequest httpGet = ClassicRequestBuilder.get()
        .setHttpHost(new HttpHost("http", "httpbin.org"))
        .setPath("/get")
        .build();
final Message<HttpResponse, String> response = client.execute(httpGet, r ->
    new Message<>(r, EntityUtils.toString(r.getEntity())));
System.out.println(response.head().getCode());
System.out.println(response.body());
```

* Async HttpClient

Please note that basic request and entity producer objects are not thread-safe. They must 
not be executed multiple times unless their state can be reset. When sharing request 
objects and content data between multiple threads access to attributers of those objects 
must be synchronized.

```java
// Get HttpClient singleton
final CloseableHttpAsyncClient client = getClient();
final HttpRequest httpGet = BasicRequestBuilder.get()
        .setHttpHost(new HttpHost("http", "httpbin.org"))
        .setPath("/get")
        .build();
final Future<Message<HttpResponse, String>> future = client.execute(
        new BasicRequestProducer(httpGet, null),
        new BasicResponseConsumer<>(StringAsyncEntityConsumer::new),
        null);
final Message<HttpResponse, String> response = future.get();
System.out.println(response.head().getCode());
System.out.println(response.body());
```

Please note that the response content is represented as a String for simplicity. In 
productive scenarios one should transform the response directly into a high-level value 
object, for instance by way of JSON bindings.

Resource management
------------------

There are several ways to ensure HttpClient does not keep any system resources such
persistent connections in the connection pool indefinitely. One can use a dedicated 
thread to evict idle or expired connections automatically or manually by explicitly 
evicting connections prior or post a long period of inactivity.

One should also generally limit connection total time to live (TTL) to a finite value.

```java
final static PoolingHttpClientConnectionManager CONN_MANAGER;

static {
  CONN_MANAGER = PoolingHttpClientConnectionManagerBuilder.create()
          .setDefaultConnectionConfig(ConnectionConfig.custom()
                  .setTimeToLive(TimeValue.ofMinutes(5))
                  .build())
          .build();
}

static ConnPoolControl<HttpRoute> getConnPool() {
  return CONN_MANAGER;
}
```
```java
final ConnPoolControl<HttpRoute> connPool = getConnPool();
// Close out all connections
connPool.closeIdle(TimeValue.ZERO_MILLISECONDS);
```

Connection release
------------------

All HttpClient implementations automatically lease connections from the connection 
manager and release them back once the message exchange has been fully executed (also
in case of an error or an exception).

One exception to this principle is when the response object must to be kept open, in 
which case the caller is responsible for closing the response object to ensure release
of resources associated with the response stream. Failure to close the response object, 
for instance in case of an exception, will likely cause a resource leak and connection 
pool resource starvation.

```java
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
```

Client execution context
------------------

HTTP request messages are self-contained and the ability to add custom headers to 
the request messages should generally be a sufficient enough customization mechanism for 
many use scenarios. However, there are situations when one requires access to a wider 
context of request execution or more fine-tuned configuration of the HTTP protocol 
execution. The execution context managed by HttpClient is represented by the 
`HttpClientContext` class, which basically acts a holder of various attributes that can 
be set prior to request execution, updated in the course of the request execution and 
the response processing, and interrogated upon the message exchange completion. 

* Classic HttpClient

```java
final CloseableHttpClient client = getClient();

final CookieStore cookieStore = new BasicCookieStore();

final HttpClientContext localContext = ContextBuilder.create()
        // Bind custom cookie store to the local context
        .useCookieStore(cookieStore)
        .build();
// Provide request custom settings
localContext.setRequestConfig(RequestConfig.custom()
        .setCookieSpec(CookieSpecs.STANDARD_STRICT)
        .build());

final ClassicHttpRequest httpGet = ClassicRequestBuilder.get()
        .setHttpHost(new HttpHost("http", "httpbin.org"))
        .setPath("/cookies")
        .build();
final Message<HttpResponse, String> response = client.execute(
        httpGet, localContext, r ->
                new Message<>(r, EntityUtils.toString(r.getEntity())));
System.out.println(response.head().getCode());
System.out.println(response.body());

for (Cookie cookie : cookieStore.getCookies()) {
    System.out.println("Local cookie: " + cookie);
}
```

* Async HttpClient

```java
final CloseableHttpAsyncClient client = getClient();

final CookieStore cookieStore = new BasicCookieStore();

final HttpClientContext localContext = ContextBuilder.create()
        // Bind custom cookie store to the local context
        .useCookieStore(cookieStore)
        .build();
// Provide request custom settings
localContext.setRequestConfig(RequestConfig.custom()
        .setCookieSpec(CookieSpecs.STANDARD_STRICT)
        .build());

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
```
Please note that while `HttpClientContext` itself is thread-safe, some of its attributes
may not be. It is strongly recommended to have `HttpClientContext` instances associated
with a single message exchange at any given time. It, however, can be benefitial to have 
subsequent requests within the same logical HTTP session share the same execution 
context.

```java
final CloseableHttpAsyncClient client = getClient();

final CookieStore cookieStore = new BasicCookieStore();

// Create session HTTP context
final HttpClientContext localContext = ContextBuilder.create()
        // Bind custom cookie store to the local context
        .useCookieStore(cookieStore)
        .build();

for (int i = 1; i <= 3; i++) {
    final HttpRequest httpGet = BasicRequestBuilder.get()
            .setHttpHost(new HttpHost("http", "httpbin.org"))
            .setPath("/cookies")
            .build();
    // Share the same HttpClientContext
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
```