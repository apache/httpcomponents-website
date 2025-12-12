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

Connection pooling
==================

HttpClient uses connection pools to reuse persistent connections between requests and reduce connection setup overhead.

The main pool implementations are:

* `PoolingHttpClientConnectionManager` for classic (blocking) I/O
* `PoolingAsyncClientConnectionManager` for async I/O

Both managers:

* Maintain per-route and total connection limits.
* Reuse idle persistent connections when possible.
* Enforce time-to-live (TTL) and idle expiry.

Pool concurrency policies
-------------------------

The concurrency behaviour of the underlying pool is controlled by
`PoolConcurrencyPolicy`:

* **STRICT** (default)  
  Per-route queues and strong fairness. Each route has its own waiter queue;
  connections are handed out in FIFO order per route. This is the safest choice
  for most applications.

* **LAX**  
  More relaxed fairness in favour of throughput. Waiters share a global queue
  and busy routes can get more connections under load. This can improve
  utilisation at the cost of strict per-route fairness.

* **OFFLOCK** *(experimental)*  
  Uses the route-segmented pool implementation (`RouteSegmentedConnPool`) which
  keeps per-route state in independent segments and reduces contention on shared
  structures. The implementation still uses locking where needed; the goal is
  to minimise time spent under hot pool locks. As an experimental policy its
  behaviour and configuration may change in future releases.

You can select the policy with the builder:

```java
final PoolingHttpClientConnectionManager connManager =
        PoolingHttpClientConnectionManagerBuilder.create()
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.OFFLOCK)
                .build();
```

Off-lock disposal (blocking pools)
----------------------------------

Closing a connection gracefully can be slow (TLS shutdown, FIN/ACK, etc.). If
this work is done while holding internal pool locks, it can increase latency
for other threads leasing or releasing connections.

Blocking pools provide an option to move slow graceful closes *off* the hot
pool locks:

* Immediate closes (`CloseMode.IMMEDIATE`) are always executed on the caller
  thread.
* Graceful closes can be deferred and performed outside the core pool
  synchronisation.

This behaviour is enabled in the classic manager via:

```java
final PoolingHttpClientConnectionManager connManager =
        PoolingHttpClientConnectionManagerBuilder.create()
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setOffLockDisposalEnabled(true)
                .build();
```

With off-lock disposal enabled:

* The connection is removed from the pool immediately.
* The actual graceful close is performed later, outside the critical sections
  used by `lease` / `release`.
* From the caller’s point of view, pool semantics (keep-alive, TTL, idle timeout)
  remain the same.

Configuration hints
-------------------

* Start with `STRICT` and default disposal behaviour.
* Consider `LAX` for high-throughput workloads where strict per-route fairness
  is not required.
* Consider `OFFLOCK` for highly concurrent applications with many routes and
  contention on the pool.
* Enable off-lock disposal for classic I/O if profiling shows slow graceful
  closes affecting lease/release latency under load.
