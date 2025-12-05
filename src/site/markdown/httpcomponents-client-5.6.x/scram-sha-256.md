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

SCRAM-SHA-256 authentication
============================

HttpClient 5.6 adds support for **SCRAM-SHA-256** as defined by
[RFC 7804] and [RFC 7677]. The scheme is exposed as:

- `StandardAuthScheme.SCRAM_SHA_256` (scheme name `"SCRAM-SHA-256"`)
- `ScramSchemeFactory` (client-side implementation)
- `ScramException` (specialised `AuthenticationException` for SCRAM errors)

The default async and classic client builders register the scheme alongside
existing ones (Basic, Digest, Bearer), so that it can be selected through the
normal HTTP authentication negotiation process.

Why SCRAM?
----------

SCRAM is a challenge-response mechanism that:

- avoids sending the plaintext password over the wire,
- supports salted password storage on the server,
- offers channel binding variants when used over TLS (not covered here),
- is widely deployed in modern services (databases, HTTP gateways, etc.).

Compared to `Basic`, SCRAM provides much stronger protection for credentials
and server-side password databases. Compared to legacy `Digest`, it has a
cleaner design and better password hashing support.

Scheme name
-----------

The wire-level auth scheme name is:

```text
SCRAM-SHA-256
```

In code, use the constant from `StandardAuthScheme`:

```java
StandardAuthScheme.SCRAM_SHA_256
```

Enabling SCRAM-SHA-256
----------------------

The default builders already register the scheme:

```java
Lookup<AuthSchemeFactory> authSchemeRegistry = RegistryBuilder.<AuthSchemeFactory>create()
        .register(StandardAuthScheme.BASIC,  BasicSchemeFactory.INSTANCE)
        .register(StandardAuthScheme.DIGEST, DigestSchemeFactory.INSTANCE)
        .register(StandardAuthScheme.BEARER, BearerSchemeFactory.INSTANCE)
        .register(StandardAuthScheme.SCRAM_SHA_256, ScramSchemeFactory.INSTANCE)
        .build();
```

If you build your own registry, make sure to add the SCRAM factory as above.

Classic client example
----------------------

The following snippet shows a typical setup with `CloseableHttpClient` where
the target server advertises `WWW-Authenticate: SCRAM-SHA-256` and the client
responds with the appropriate `Authorization` header.

```java
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.StandardAuthScheme;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.ScramSchemeFactory;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.ClassicHttpResponse;

public class ClassicScramExample {

    public static void main(final String[] args) throws Exception {
        final BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("example.com", 443),
                new UsernamePasswordCredentials("user", "p4ssw0rd".toCharArray()));

        try (final CloseableHttpClient client = HttpClients.custom()
                // SCRAM-SHA-256 is included by default; explicit registration shown for clarity
                .setDefaultCredentialsProvider(credsProvider)
                .build()) {

            final ClassicHttpResponse response = client.executeOpen(null,
                    ClassicRequestBuilder.get("https://example.com/protected").build(),
                    null);

            System.out.println(response.getCode() + " " + response.getReasonPhrase());
        }
    }
}
```

As long as the server prefers `SCRAM-SHA-256` in `WWW-Authenticate`, the
`DefaultAuthenticationStrategy` will select it and the `ScramSchemeFactory`
will drive the handshake.

Async client example
--------------------

For the async API, configuration is similar: register credentials and let the
auth strategy select SCRAM when the server advertises it.

```java
import java.util.concurrent.Future;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;

public class AsyncScramExample {

    public static void main(final String[] args) throws Exception {
        final BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("example.com", 443),
                new UsernamePasswordCredentials("user", "p4ssw0rd".toCharArray()));

        try (final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build()) {

            client.start();

            final SimpleHttpRequest request = SimpleRequestBuilder
                    .get("https://example.com/protected")
                    .build();

            final Future<SimpleHttpResponse> future = client.execute(request, null);
            final SimpleHttpResponse response = future.get();

            System.out.println(response.getCode() + " " + response.getReasonPhrase());
        }
    }
}
```

If the server supports both SCRAM and weaker schemes, you can influence the
selection order via a custom `AuthenticationStrategy` if needed.

Error handling
--------------

SCRAM-specific problems (bad server messages, invalid channel binding, etc.)
are reported as `ScramException`, which extends `AuthenticationException`:

```java
try {
    // execute request…
} catch (final ScramException ex) {
    // SCRAM handshake failed (protocol-level issue)
} catch (final AuthenticationException ex) {
    // generic auth failure
}
```

Operational notes
-----------------

- SCRAM-SHA-256 should normally be used over **TLS** (`https:`) to protect the
  rest of the HTTP exchange.
- The server must implement RFC 7804 / 7677 correctly; misconfigured servers
  may fall back to other schemes or fail the handshake.
- Credentials are provided through the standard HttpClient
  `CredentialsProvider` mechanism; there is no SCRAM-specific credential type
  for the common username/password case.

Summary
-------

- Scheme name: `SCRAM-SHA-256` (`StandardAuthScheme.SCRAM_SHA_256`).
- Registered on both classic and async clients through `ScramSchemeFactory`.
- Negotiated via the usual HTTP auth challenge/response flow.
- Use `UsernamePasswordCredentials` + `CredentialsProvider` as with other
  built-in schemes; HttpClient handles the SCRAM details.
