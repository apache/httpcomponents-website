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

HttpClient Quick Start
======================

- Download 'Binary' package of the latest HttpClient 4.5 release or configure dependency on `HttpClient` and `Fluent HC`
  modules using a dependency manager of your choice as described [here](download.md).

- HttpClient 4.5 requires Java 1.6 or newer.

- The below code fragment illustrates the execution of HTTP GET and POST requests using the HttpClient native API.

    ```
    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet("http://targethost/homepage");
    CloseableHttpResponse response1 = httpclient.execute(httpGet);
    // The underlying HTTP connection is still held by the response object
    // to allow the response content to be streamed directly from the network socket.
    // In order to ensure correct deallocation of system resources
    // the user MUST call CloseableHttpResponse#close() from a finally clause.
    // Please note that if response content is not fully consumed the underlying
    // connection cannot be safely re-used and will be shut down and discarded
    // by the connection manager. 
    try {
        System.out.println(response1.getStatusLine());
        HttpEntity entity1 = response1.getEntity();
        // do something useful with the response body
        // and ensure it is fully consumed
        EntityUtils.consume(entity1);
    } finally {
        response1.close();
    }
    
    HttpPost httpPost = new HttpPost("http://targethost/login");
    List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    nvps.add(new BasicNameValuePair("username", "vip"));
    nvps.add(new BasicNameValuePair("password", "secret"));
    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
    CloseableHttpResponse response2 = httpclient.execute(httpPost);
    
    try {
        System.out.println(response2.getStatusLine());
        HttpEntity entity2 = response2.getEntity();
        // do something useful with the response body
        // and ensure it is fully consumed
        EntityUtils.consume(entity2);
    } finally {
        response2.close();
    }
    ```

  Source can be
  downloaded [here](https://github.com/apache/httpcomponents-client/blob/4.5.x/httpclient/src/examples/org/apache/http/examples/client/QuickStart.java)

- The same requests can be executed using a simpler, albeit less flexible, fluent API.

    ```
    // The fluent API relieves the user from having to deal with manual deallocation of system
    // resources at the cost of having to buffer response content in memory in some cases.
    
    Request.Get("http://targethost/homepage")
        .execute().returnContent();
    Request.Post("http://targethost/login")
        .bodyForm(Form.form().add("username",  "vip").add("password",  "secret").build())
        .execute().returnContent();
    ```

  Source can be
  downloaded [here](https://github.com/apache/httpcomponents-client/blob/4.5.x/fluent-hc/src/examples/org/apache/http/client/fluent/FluentQuickStart.java)

- [HttpClient Examples](examples.md) - a set of examples demonstrating some of the more complex scenarios.

- [HttpClient Tutorial](./current/tutorial/html/) - gives a detailed examination of the HttpClient API, which was
  written in close accordance with the (sometimes not very intuitive)
  HTTP specification/standard. A copy is also shipped with the release.  
  [A PDF version](./current/tutorial/pdf/httpclient-tutorial.pdf) is also available

- [HttpClient Primer](./primer.html) - explains the scope of HttpClient. Note that HttpClient is not a browser. It lacks
  the UI, HTML renderer and a JavaScript engine that a browser will possess.   
