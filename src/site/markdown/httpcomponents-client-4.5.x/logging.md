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
the logging interface provided by the
[Commons Logging](http://commons.apache.org/logging/) package. `Commons Logging` provides a simple and generalized
[log interface](http://commons.apache.org/logging/commons-logging-1.0.4/docs/apidocs/) to various logging packages. By
using `Commons Logging`, HttpClient can be configured for a variety of different logging behaviours. That means the user
will have to make a choice which logging framework to use. By default `Commons Logging` supports the following logging
frameworks:

* [Log4J 2](https://logging.apache.org/log4j/2.x/index.html)

* [java.util.logging](http://docs.oracle.com/javase/7/docs/api/java/util/logging/package-summary.html)

* [SimpleLog](http://commons.apache.org/logging/commons-logging-1.0.4/docs/apidocs/org/apache/commons/logging/impl/SimpleLog.html) (
  internal to `Commons Logging`)

By implementing some simple interfaces `Commons Logging` can be extended to support basically any other custom logging
framework. `Commons Logging` tries to automatically discover the logging framework to use. If it fails to select the
expected one, you must configure `Commons Logging` by hand. Please refer to the `Commons Logging`
documentation for more information.

HttpClient performs three different kinds of logging: the standard context logging used within each class, HTTP header
logging and full wire logging.

### Context Logging

Context logging contains information about the internal operation of HttpClient as it performs HTTP requests. Each class
has its own log named according to the class's fully qualified name. For example the class `DefaultHttpClient` has a log
named
`org.apache.http.impl.client.DefaultHttpClient`. Since all classes follow this convention it is possible to configure
context logging for all classes using the single log named
`org.apache.http.impl.client`.

### Wire Logging

The wire log is used to log all data transmitted to and from servers when executing HTTP requests. The wire log uses
the `org.apache.http.wire` logging category. This log should only be enabled to debug problems, as it will produce an
extremely large amount of log data.

### HTTP header Logging

Because the content of HTTP requests is usually less important for debugging than the HTTP headers,
the `org.apache.http.headers` logging category for capturing HTTP headers only.

### Configuration Examples

`Commons Logging` can delegate to a variety of loggers for processing the actual output. Below are configuration
examples for `Commons Logging`, `Log4j 2` and `java.util.logging`.

## Commons Logging Examples

`Commons Logging` comes with a basic logger called `SimpleLog`. This logger writes all logged messages to `System.err`.
The following examples show how to configure
`Commons Logging` via system properties to use `SimpleLog`. It is strongly recommended configuring `Commons Logging`
system properties through JVM process arguments at the start up.

* Enable header wire + context logging - <<Best for Debugging>>

  ```
  -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog
  -Dorg.apache.commons.logging.simplelog.showdatetime=true
  -Dorg.apache.commons.logging.simplelog.log.org.apache.http=DEBUG
  -Dorg.apache.commons.logging.simplelog.log.org.apache.http.wire=ERROR
  ```

* Enable full wire + context logging

   ```
  -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog
  -Dorg.apache.commons.logging.simplelog.showdatetime=true
  -Dorg.apache.commons.logging.simplelog.log.org.apache.http=DEBUG
  ```

* Enable context logging for connection management

  ```
  -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog
  -Dorg.apache.commons.logging.simplelog.showdatetime=true
  -Dorg.apache.commons.logging.simplelog.log.org.apache.http.impl.conn=DEBUG
  ```

* Enable context logging for connection management / request execution

  ```
  -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog
  -Dorg.apache.commons.logging.simplelog.showdatetime=true
  -Dorg.apache.commons.logging.simplelog.log.org.apache.http.impl.conn=DEBUG
  -Dorg.apache.commons.logging.simplelog.log.org.apache.http.impl.client=DEBUG
  -Dorg.apache.commons.logging.simplelog.log.org.apache.http.client=DEBUG
  ```

## Log4j 2 Examples

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
        <Logger name="org.apache.http" level="DEBUG">
          <AppenderRef ref="Console"/>
        </Logger>
        <Logger name="org.apache.http.wire" level="DEBUG">
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
        <Logger name="org.apache.http" level="DEBUG">
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
        <Logger name="org.apache.http.impl.conn" level="DEBUG">
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
        <Logger name="org.apache.http.impl.conn" level="DEBUG">
          <AppenderRef ref="Console"/>
        </Logger>
        <Logger name="org.apache.http.impl.client" level="DEBUG">
          <AppenderRef ref="Console"/>
        </Logger>
        <Logger name="org.apache.http.client" level="DEBUG">
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


### java.util.logging Examples

Since JDK 1.4 there has been a package
[java.util.logging](http://docs.oracle.com/javase/7/docs/api/java/util/logging/package-summary.html) that provides a
logging framework similar to `Log4J`. By default it reads a config file from `$JAVA_HOME/jre/lib/logging.properties`
which looks like this (comments stripped):

```
handlers=java.util.logging.ConsoleHandler
.level=INFO
java.util.logging.FileHandler.pattern = %h/java%u.log
java.util.logging.FileHandler.limit = 50000
java.util.logging.FileHandler.count = 1
java.util.logging.FileHandler.formatter = java.util.logging.XMLFormatter
java.util.logging.ConsoleHandler.level = INFO
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
com.xyz.foo.level = SEVERE
```

To customize logging a custom `logging.properties` file should be created in the project directory. The location of this
file must be passed to the JVM as a system property. This can be done on the command line like so:

```
$JAVA_HOME/java -Djava.util.logging.config.file=$HOME/myapp/logging.properties
-classpath $HOME/myapp/target/classes com.myapp.Main
```

Alternatively [LogManager#readConfiguration(InputStream)](http://docs.oracle.com/javase/7/docs/api/java/util/logging/LogManager.html#readConfiguration(java.io.InputStream))
can be used to pass it the desired configuration.

* Enable header wire + context logging - **Best for Debugging**

  ```
  .level = INFO
  
  handlers=java.util.logging.ConsoleHandler
  java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
  java.util.logging.ConsoleHandler.level = ALL
  
  org.apache.http.level = FINEST
  org.apache.http.wire.level = SEVERE
  ```

* Enable full wire + context logging

  ```
  .level = INFO
  
  handlers=java.util.logging.ConsoleHandler
  java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
  java.util.logging.ConsoleHandler.level = ALL
  
  org.apache.http.level = FINEST
  ```

* Enable context logging for connection management

  ```
  .level = INFO
  
  handlers=java.util.logging.ConsoleHandler
  java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
  java.util.logging.ConsoleHandler.level = ALL
  
  org.apache.http.impl.conn.level = FINEST
  ```

* Enable context logging for connection management / request execution

  ```
  .level = INFO
  
  handlers=java.util.logging.ConsoleHandler
  java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
  java.util.logging.ConsoleHandler.level = ALL
  
  org.apache.http.impl.conn.level = FINEST
  org.apache.http.impl.client.level = FINEST
  org.apache.http.client.level = FINEST
  ```

More detailed information is available from the
[Java Logging documentation](http://docs.oracle.com/javase/7/docs/technotes/guides/logging/overview.html).