# Apache HttpClient 5.x migration guide

1. [Migration from HttpClient 4.x](preparation.md)

   Prior to migration from HttpClient 4.x to HttpClient 5.x it is highly recommended to ensure that HttpClient 4.x is up
   to date and is being used in accordance with the best practices and recommendations.

1. [Migration to HttpClient 5.x classic APIs](migration-to-classic.md)

   When migrating from HttpClient 4.x to HttpClient 5.x it is generally recommended to migrate to the classic APIs as
   the first step.

1. [Migration to HttpClient 5.x async APIs with simple handlers](migration-to-async-simple.md)

   When migrating to HttpClient 5.x async APIs it might be easier to start off by using simple (using in-memory buffers)
   asynchronous handlers.

1. [Migration to HttpClient 5.x async APIs](migration-to-async-streaming.md)

   The ultimate goal of the migration process should be to use HttpClient 5.x async APIs with full content streaming
   over full-duplex HTTP/1.1 or HTTP/2 connections.

1. [Migration to HttpClient 5.x async APIs for HTTP/2 only](migration-to-async-http2.md)

   For those scenarios where HTTP/1.1 compatibility is no longer required HttpClient 5.x provides HTTP/2 optimized
   clients.
