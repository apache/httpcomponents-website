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

NTLM support in HttpClient
==========================

Background
----------

NTLM is a proprietary authentication scheme developed by Microsoft and optimized for Windows operating system.

Until year 2008 there was no official, publicly available, complete documentation of the
protocol. [Unofficial](https://davenport.sourceforge.net/ntlm.html) 3rd party protocol descriptions existed as a result
of reverse-engineering efforts. It was not really known whether the protocol based on the reverse-engineering were
complete or even correct.

Microsoft
published [MS-NLMP](https://download.microsoft.com/download/a/e/6/ae6e4142-aa58-45c6-8dcf-a657e5900cd3/%5BMS-NLMP%5D.pdf)
and [MS-NTHT](https://download.microsoft.com/download/a/e/6/ae6e4142-aa58-45c6-8dcf-a657e5900cd3/%5BMS-NTHT%5D.pdf)
specifications in February 2008 as a part of its
[Interoperability Principles initiative](https://www.microsoft.com/interop/principles/default.mspx).

HttpClient as of version 4.1 initially supported NTLMv1, NTLMv2, and NTLM2SessionResponse authentication protocols,
based on the reverse engineering approach. As of version 4.2.3, HttpClient now supports a more correct implementation,
based in large part on Microsoft's own specifications. This is expected to correct a number of problems, especially
since Microsoft (as of Windows Server 2008 R2) began using a new implementation of its protocols. This new Microsoft
implementation has led to authentication failures in some cases from some of the older reverse-engineered client
implementations of NTLM.

The new HttpClient NTLM implementation is known to have been tried successfully against at least the following systems:

* Windows Server 2000 and Server 2003 systems, configured to use LM and NTLMv1 authentication

* Windows Server 2003 systems, configured to use NTLMv2 authentication

* Windows Server 2008 R2 systems, configured to use NTLM2SessionResponse authentication

If the current HttpClient NTLM implementation should prove problematic in your environment, we'd definitely like to hear
about it. You are also welcome to try an alternative NTLM implementation, should it seem necessary. One can also
use [JCIFS](https://jcifs.samba.org/), which includes an NTLM engine developed by members of the Samba project.

Using Samba JCIFS as an alternative NTLM engine
-----------------------------------------------

Follow these instructions to build an NTLMEngine implementation using JCIFS library

***Disclaimer: Use code at your own discretion. Do NOT report any issues related to the use of JCIFS library to Apache
HttpComponents project***.

* Download version 1.3.14 or newer of the JCIFS library from the [Samba](https://jcifs.samba.org/) web site

* Implement NTLMEngine interface

  ```
  import java.io.IOException;
  
  import jcifs.ntlmssp.NtlmFlags;
  import jcifs.ntlmssp.Type1Message;
  import jcifs.ntlmssp.Type2Message;
  import jcifs.ntlmssp.Type3Message;
  import jcifs.util.Base64;
  
  import org.apache.http.impl.auth.NTLMEngine;
  import org.apache.http.impl.auth.NTLMEngineException;
  
  public final class JCIFSEngine implements NTLMEngine {
  
      private static final int TYPE_1_FLAGS = 
              NtlmFlags.NTLMSSP_NEGOTIATE_56 | 
              NtlmFlags.NTLMSSP_NEGOTIATE_128 | 
              NtlmFlags.NTLMSSP_NEGOTIATE_NTLM2 | 
              NtlmFlags.NTLMSSP_NEGOTIATE_ALWAYS_SIGN | 
              NtlmFlags.NTLMSSP_REQUEST_TARGET;
  
      public String generateType1Msg(final String domain, final String workstation)
              throws NTLMEngineException {
          final Type1Message type1Message = new Type1Message(TYPE_1_FLAGS, domain, workstation);
          return Base64.encode(type1Message.toByteArray());
      }
  
      public String generateType3Msg(final String username, final String password,
              final String domain, final String workstation, final String challenge)
              throws NTLMEngineException {
          Type2Message type2Message;
          try {
              type2Message = new Type2Message(Base64.decode(challenge));
          } catch (final IOException exception) {
              throw new NTLMEngineException("Invalid NTLM type 2 message", exception);
          }
          final int type2Flags = type2Message.getFlags();
          final int type3Flags = type2Flags
                  & (0xffffffff ^ (NtlmFlags.NTLMSSP_TARGET_TYPE_DOMAIN | NtlmFlags.NTLMSSP_TARGET_TYPE_SERVER));
          final Type3Message type3Message = new Type3Message(type2Message, password, domain,
                  username, workstation, type3Flags);
          return Base64.encode(type3Message.toByteArray());
      }
  
  }
  ```

* Implement AuthSchemeProvider interface

  ```
  public class JCIFSNTLMSchemeFactory implements AuthSchemeProvider {
  
      public AuthScheme create(final HttpContext context) {
          return new NTLMScheme(new JCIFSEngine());
      }
  }
  ```

* Register NTLMSchemeFactory with the HttpClient instance you want to NTLM enable.

  ```
  Registry<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder.<AuthSchemeProvider>create()
        .register(AuthSchemes.NTLM, new JCIFSNTLMSchemeFactory())
        .register(AuthSchemes.BASIC, new BasicSchemeFactory())
        .register(AuthSchemes.DIGEST, new DigestSchemeFactory())
        .register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory())
        .register(AuthSchemes.KERBEROS, new KerberosSchemeFactory())
        .build();
  CloseableHttpClient httpClient = HttpClients.custom()
        .setDefaultAuthSchemeRegistry(authSchemeRegistry)
        .build();
  ```

* Set NTCredentials for the web server you are going to access.


