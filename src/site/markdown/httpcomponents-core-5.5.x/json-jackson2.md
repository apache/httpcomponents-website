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

HttpCore JSON bindings 
=================

While it is relatively easy to process HTTP message content with any data processing
libraries that support the classic (blocking) I/O model based on `InputStream` and
`OutputStream` APIs, there is no such common API for asynchronous data processing in 
Java 8 or earlier.

It is a very common anti-pattern with asynchronous HTTP content processing when message
body content gets buffered in memory and then processed using standard blocking
`InputStream` and `OutputStream` APIs.

HttpCore JSON bindings module aims at making asynchronous processing of JSON messages with
Apache HttpCore and Apache HttpClient simple and convenient while eliminating intermediate
content buffering in memory. The library uses the asynchronous JSON message parser of the
fantastic [Jackson JSON processor](https://github.com/FasterXML/jackson) to tokenize JSON 
content and map onto a higher level Java object model.

Presently HttpCore JSON bindings use version 2 of Jackson JSON processor.  

Some examples of HttpCore JSON bindings in action cab be found [here](examples-jackson2.md)