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

HttpClient for Android
======================

Google Android 1.0 was released with a pre-BETA snapshot of Apache HttpClient. To coincide with the first Android
release Apache HttpClient 4.0 APIs had to be frozen prematurely, while many of interfaces and internal structures were
still not fully worked out. As Apache HttpClient 4.0 was maturing the project was expecting Google to incorporate the
latest code improvements into their code tree. Unfortunately it did not happen. Version of Apache HttpClient shipped
with Android has effectively become a fork.

Eventually Google decided to discontinue further development of their fork while refusing to upgrade to the stock
version of Apache HttpClient citing compatibility concerns as a reason for such decision. Google completely removed
their fork of Apache HttpClient from Android in version 8.0 (API 26) only.

Those users who want to continue using Apache HttpClient on Android are advised to consider

* [Apache HttpCLient 5.0](../httpcomponents-client-ga) stock version, which works well with Android API 19 and newer

    ```
    dependencies {
        compile group: 'org.apache.httpcomponents.client5' , name: 'httpclient5' , version: '5.0.3'
    }
    ```

* Apache HttpClient packages for
  Android [maintained by Marek Sebera](https://github.com/smarek/httpclient-android/wiki/Project-Introduction) when
  targeting Android API 23 and newer

    ```
    dependencies {
        compile group: 'cz.msebera.android' , name: 'httpclient', version: '4.5.3'
    }
    ```

* [Android extensions](https://ok2c.github.io/httpclient-android-ext/) for Apache HttpClient 4.5 when targeting Android
  API 26 or newer.

  Android extensions for Apache HttpClient provide a replacement for the default `HostnameVerifier`
  implementation incompatible with Android and provide a builder for `PoolingHttpClientConnectionManager`
  instances optimized for Android called `AndroidHttpClientConnectionManagerBuilder`.

    ```
    dependencies {
        api 'com.github.ok2c.hc4.android:httpclient-android:0.1.0'
    }
    ```
