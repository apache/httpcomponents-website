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

HttpCore Examples (JSON Bindings)
=================

- [Client message content as Java Object](https://github.com/apache/httpcomponents-core/blob/master/httpcore5-jackson2/src/test/java/org/apache/hc/core5/jackson2/http/examples/JsonObjectRequestExample.java)

  This example demonstrates how a plain Java Object can be serialized as a request
  content body in JSON format and an HTTP response JSON content can be consumed as 
  a plain Java Object compatible with Jackson JSON bindings.
  
- [Client message content as JsonNode](https://github.com/apache/httpcomponents-core/blob/master/httpcore5-jackson2/src/test/java/org/apache/hc/core5/jackson2/http/examples/JsonNodeResponseExample.java)

  This example demonstrates how an HTTP response JSON content can be consumed as 
  a JsonNode object.

- [Client message content as JSON events](https://github.com/apache/httpcomponents-core/blob/master/httpcore5-jackson2/src/test/java/org/apache/hc/core5/jackson2/http/examples/JsonTokenEventResponseExample.java)

  This example demonstrates how an HTTP response JSON content can be consumed as a stream 
  of events.

- [Server message content as Java Object](https://github.com/apache/httpcomponents-core/blob/master/httpcore5-jackson2/src/test/java/org/apache/hc/core5/jackson2/http/examples/JsonServerExample.java)

  Example of asynchronous embedded HTTP server that exchanges messages in JSON format.

- [Client request content as a sequence of Java Objects](https://github.com/apache/httpcomponents-core/blob/master/httpcore5-jackson2/src/test/java/org/apache/hc/core5/jackson2/http/examples/JsonSequenceRequestExample.java)

  This example demonstrates how to stream out a sequence plain Java Objects compatible
  with Jackson JSON bindings as a request content body in JSON format.

- [Client response content as a sequence of Java Objects](https://github.com/apache/httpcomponents-core/blob/master/httpcore5-jackson2/src/test/java/org/apache/hc/core5/jackson2/http/examples/JsonSequenceResponseExample.java)

  This example demonstrates how an HTTP response content can be consumed as a sequence 
  of plain Java Objects compatible with Jackson JSON bindings.

Please note content of incoming and outgoing HTTP messages gets streamed without making 
a copy of the message body content in an intermediate buffer.  

