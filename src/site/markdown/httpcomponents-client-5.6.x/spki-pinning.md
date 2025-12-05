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

SPKI pinning TLS strategy
=========================

HttpClient 5.6 adds an **opt-in SPKI pinning TLS strategy** for clients that
need an additional defence layer on top of the normal PKI and hostname
verification.

The implementation lives in:

- `org.apache.hc.client5.http.ssl.SpkiPinningClientTlsStrategy`

and is used as a drop-in replacement for the regular client-side TLS strategy
in both classic and async connection managers.

> :warning: **Warning:** Certificate pinning increases operational risk.
> Misconfigured pins can brick your client fleet until you ship an update.
> Always keep normal PKI and hostname verification enabled, and ship at least
> one backup pin for each host.

What is pinned?
---------------

Pins are applied to the **SubjectPublicKeyInfo (SPKI)** of certificates in the
peer chain, not to raw certificate bytes.

Each pin has the form:

```text
sha256/<base64(SPKI)>
```

For example:

```text
sha256/qrvdF0L7Kp5l3H8k0m3x7VZq3p5O6s4L4kC2Z7tZt+Q=
```

Only `sha256/…` pins are accepted. Any other prefix, invalid Base64, or wrong
decoded length (not 32 bytes) will cause an `IllegalArgumentException` during
builder configuration.

Host patterns and wildcards
---------------------------

Pins are configured per **host pattern**, which can be:

- an exact host (for example `api.example.com`), or
- a **single-label wildcard** (for example `*.example.com`).

Behaviour:

- Host names are normalised to IDNA ASCII (Punycode) and lowercased.
- `*.example.com` matches `a.example.com`, `b.example.com`, etc.
- `*.example.com` does **not** match `a.b.example.com`.
- Wildcards must be single-label; patterns like `"*."` are rejected.

How enforcement works
---------------------

`SpkiPinningClientTlsStrategy` decorates the default client TLS strategy:

1. The normal trust manager and hostname verification run first.
2. If neither of those fails, pinning logic is applied:
   - It finds all rules whose host pattern matches the target host.
   - It computes `SHA-256(SPKI)` for all `X509Certificate` entries in the peer
     chain.
   - If at least one configured pin matches at least one SPKI hash, pinning
     passes.
   - Otherwise an `SSLException` is thrown and the connection fails.

If no rules match the host, pinning is **skipped** and only normal PKI rules
apply.

Classic client configuration
----------------------------

To enable SPKI pinning for the classic (`CloseableHttpClient`) API, plug the
strategy into a `PoolingHttpClientConnectionManager`:

```java
import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SpkiPinningClientTlsStrategy;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.ssl.SSLContexts;

public final class ClientSpkiPinningExample {

    public static void main(final String[] args) throws Exception {
        final SSLContext sslContext = SSLContexts.createSystemDefault();

        final SpkiPinningClientTlsStrategy pinning = SpkiPinningClientTlsStrategy
                .newBuilder(sslContext)
                // Replace these with real host(s) and real pins (sha256/<base64(SPKI)>)
                .add("example.com",
                        "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=",
                        "sha256/BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB=") // backup
                .add("*.example.net",
                        "sha256/CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC=")
                .build();

        final PoolingHttpClientConnectionManager cm =
                PoolingHttpClientConnectionManagerBuilder.create()
                        .setTlsSocketStrategy(pinning)
                        .build();

        try (final CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .build()) {

            final HttpGet httpget = new HttpGet("https://example.com/");
            System.out.println("Executing: " + httpget.getMethod() + " " + httpget.getUri());

            httpclient.execute(httpget, response -> {
                System.out.println("----------------------------------------");
                System.out.println(httpget + " -> " + new StatusLine(response));
                EntityUtils.consume(response.getEntity());
                return null;
            });
        }
    }
}
```

If the peer certificate chain does not contain any of the configured pins for
`example.com`, the TLS handshake will fail with an `SSLException` explaining
which pins were configured and which SPKI hashes were observed.

Async client configuration
--------------------------

For the async client (`CloseableHttpAsyncClient`) the approach is analogous,
but you plug the strategy into `PoolingAsyncClientConnectionManagerBuilder`:

```java
import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SpkiPinningClientTlsStrategy;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.support.BasicRequestProducer;
import org.apache.hc.core5.http.nio.support.BasicResponseConsumer;
import org.apache.hc.core5.http.nio.entity.StringAsyncEntityConsumer;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.core5.ssl.SSLContexts;

public final class AsyncSpkiPinningExample {

    public static void main(final String[] args) throws Exception {
        final SSLContext sslContext = SSLContexts.createSystemDefault();

        final SpkiPinningClientTlsStrategy pinning = SpkiPinningClientTlsStrategy
                .newBuilder(sslContext)
                .add("api.example.com",
                        "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=",
                        "sha256/BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB=")
                .build();

        final PoolingAsyncClientConnectionManager cm =
                PoolingAsyncClientConnectionManagerBuilder.create()
                        .setTlsStrategy(pinning)
                        .setIoReactorConfig(IOReactorConfig.custom()
                                .setSoTimeout(Timeout.ofSeconds(5))
                                .build())
                        .build();

        try (final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
                .setConnectionManager(cm)
                .build()) {

            client.start();

            final SimpleHttpRequest request = SimpleRequestBuilder
                    .get("https://api.example.com/data")
                    .build();

            final SimpleHttpResponse response = client.execute(
                    HttpHost.create("https://api.example.com"),
                    new BasicRequestProducer(request, null),
                    new BasicResponseConsumer<>(new StringAsyncEntityConsumer()),
                    null).get();

            System.out.println(response.getCode() + " " + response.getReasonPhrase());
        }
    }
}
```

(Adjust host, pins and request details to your environment.)

Failure modes
-------------

- **Pinning failure**: if no configured pin matches any SPKI in the peer
  chain, `SpkiPinningClientTlsStrategy` throws an `SSLException` with a
  message containing:
  - the computed peer pins, and
  - the configured pins for the matching rules.
- **Configuration errors** (invalid Base64, wrong length, invalid wildcard,
  empty pin list, etc.) are signalled as `IllegalArgumentException` at build
  time.
- **No matching rules**: pinning is skipped and only the normal trust /
  hostname checks apply.

Testing and reference implementation
------------------------------------

The behaviour of `SpkiPinningClientTlsStrategy` is covered by:

- [ClientSpkiPinningExample](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/examples/ClientSpkiPinningExample.java)
- [SpkiPinningClientTlsStrategyTest](https://github.com/apache/httpcomponents-client/tree/master/httpclient5/src/test/java/org/apache/hc/client5/http/ssl/SpkiPinningClientTlsStrategyTest.java)

These tests validate:

- exact host and wildcard matches,
- IDN normalisation (`bücher.example`),
- backup pins,
- invalid or missing pins,
- cases where no rules apply and pinning is bypassed.

Summary
-------

- Opt-in, immutable TLS strategy for SPKI pinning: `SpkiPinningClientTlsStrategy`.
- Pins are `sha256/<base64(SPKI)>` over `SubjectPublicKeyInfo`.
- Supports exact hosts and single-label wildcards with IDN handling.
- Applied **after** normal PKI and hostname verification.
- Integrates with both classic and async connection managers.
