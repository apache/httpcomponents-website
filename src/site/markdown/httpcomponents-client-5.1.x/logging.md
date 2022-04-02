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

Logging Practices
=================

Being a library HttpClient is not to dictate which logging framework the user has to use. Therefore HttpClient utilizes
the logging facade provided by the [Simple Logging Facade for Java (SLF4J)](http://slf4j.org/) package. `SLF4J` provides
a simple and generalized [log interface](http://slf4j.org/manual.html) to various logging packages. By using `SLF4J`,
HttpClient can be configured for a variety of different logging behaviours. That means the user will have to make a
choice which logging implementation to use. There are several popular logging backends that can be used through
the `SLF4J` facade APIs:

- [Logback](http://logback.qos.ch/)

- [Log4j 2](http://logging.apache.org/log4j/2.x/index.html)

- [SimpleLogger](http://slf4j.org/api/org/slf4j/impl/SimpleLogger.html) (internal to `SLF4J`)

- [java.util.logging](http://slf4j.org/api/org/slf4j/impl/JDK14LoggerAdapter.html) (internal to `SLF4J`)

HttpComponents project however mostly works with [Log4j 2](http://logging.apache.org/log4j/2.x/index.html) backend and
recommends it to our users.

HttpClient performs three different kinds of logging: the standard context logging used within each class, HTTP header
logging and full wire logging.

Understanding Logger Names
--------------------------

Most logging implementations use a hierarchical scheme for matching logger names with logging configuration. In this
scheme, the logger name hierarchy is represented by '`.`' characters in the logger name, in a fashion very similar to
the hierarchy used for Java package names. For example, `org.apache.logging.appender` and `org.apache.logging.filter`
both have `org.apache.logging` as their parent. In most cases, applications name their loggers by passing the current
class's name to `LogManager.getLogger(...)`.

### Context Logging

Context logging contains information about the internal operation of HttpClient as it performs HTTP requests. Each class
has its own logger named according to the class's fully qualified name. For example the class `DefaultHttpClient` has a
logger named `org.apache.http.impl.client.DefaultHttpClient`. Since all classes follow this convention it is possible to
configure context logging for all classes using the single logger named
`org.apache.hc.client5.http`.

### Wire Logging

The wire logger is used to log all data transmitted to and from servers when executing HTTP requests. The wire logger
uses the `org.apache.hc.client5.http.wire` logger name. This logger should only be enabled to debug problems, as it will
produce an extremely large amount of log data.

### HTTP header Logging

Because the content of HTTP requests is usually less important for debugging than the HTTP headers, use the `
org.apache.hc.client5.http.headers` logger for capturing HTTP headers only.

Configuration Examples
----------------------

`SLF4J` can delegate to a variety of logging implementations for processing the actual output. Below are configuration
examples for `Log4j 2`, `Commons Logging`, and `java.util.logging`.

### Log4j 2 Examples

The simplest way to [configure](https://logging.apache.org/log4j/2.x/manual/configuration.html) `Log4j 2` is via
a `log4j2.xml` file. `Log4j 2`
will [automatically](https://logging.apache.org/log4j/2.x/manual/configuration.html#AutomaticConfiguration) configure
itself using a file named `log4j2.xml` when it's present at the root of the application classpath. 

Below are some `Log4j` configuration examples.

**Note:** The `Log4j 2` implementation a.k.a "core" is not included in the `HttpClient` distribution. You can include it
in your project using [Maven, Ivy, Gradle, or SBT](https://logging.apache.org/log4j/2.x/maven-artifacts.html).

- Enable header wire + context logging - **Best for Debugging**

    ```
    <Configuration>
      <Appenders>
        <Console name="Console">
          <PatternLayout pattern="%d %-5level [%logger] %msg%n%xThrowable" />
        </Console>
      </Appenders>
      <Loggers>
        <Logger name="org.apache.hc.client5.http" level="DEBUG">
          <AppenderRef ref="Console"/>
        </Logger>
        <Logger name="org.apache.hc.client5.http.wire" level="DEBUG">
          <AppenderRef ref="Console"/>
        </Logger>
        <Root level="INFO">
          <AppenderRef ref="Console" />
        </Root>
      </Loggers>
    </Configuration>
    ```

- Enable full wire + context logging

    ```
    <Configuration>
      <Appenders>
        <Console name="Console">
          <PatternLayout pattern="%d %-5level [%logger] %msg%n%xThrowable" />
        </Console>
      </Appenders>
      <Loggers>
        <Logger name="org.apache.hc.client5.http" level="DEBUG">
          <AppenderRef ref="Console"/>
        </Logger>
        <Root level="INFO">
          <AppenderRef ref="Console" />
        </Root>
      </Loggers>
    </Configuration>
    ```

- Enable context logging for connection management

    ```
    <Configuration>
      <Appenders>
        <Console name="Console">
          <PatternLayout pattern="%d %-5level [%logger] %msg%n%xThrowable" />
        </Console>
      </Appenders>
      <Loggers>
        <Logger name="org.apache.hc.client5.http.impl.io" level="DEBUG">
          <AppenderRef ref="Console"/>
        </Logger>
        <Logger name="org.apache.hc.client5.http.impl.nio" level="DEBUG">
          <AppenderRef ref="Console"/>
        </Logger>
        <Root level="INFO">
          <AppenderRef ref="Console" />
        </Root>
      </Loggers>
    </Configuration>
    ```

- Enable context logging for connection management / request execution

    ```
    <Configuration>
      <Appenders>
        <Console name="Console">
          <PatternLayout pattern="%d %-5level [%logger] %msg%n%xThrowable" />
        </Console>
      </Appenders>
      <Loggers>
        <Logger name="org.apache.hc.client5.http.impl" level="DEBUG">
          <AppenderRef ref="Console"/>
        </Logger>
        <Root level="INFO">
          <AppenderRef ref="Console" />
        </Root>
      </Loggers>
    </Configuration>
    ```

The `Log4J 2` manual is the best reference for how to configure `Log4J 2`. It is available at 
[https://logging.apache.org/log4j/2.x/manual/](https://logging.apache.org/log4j/2.x/manual/).

