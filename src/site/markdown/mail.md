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

HttpComponents Mailing Lists
============================

About Mailing Lists
-------------------

A mailing list is an electronic discussion forum that you can subscribe to. Once you are subscribed, you will receive
every email that is sent to the list, and you can send mails to the list yourself. Every mail you send there will be
received by hundreds, maybe thousands of subscribers. It will also become available in public archives indefinitely.

Please note that usage of these mailing lists is subject to the
[Public Forum Archive Policy](https://www.apache.org/foundation/public-archives.html)

**Please** take a few minutes to read this page, in order to avoid annoyance for other subscribers, and embarrassment
for yourself. There is a common set of etiquette guidelines for internet forums, sometimes called _netiquette_. You
should be aware of these and try to observe them, here as well as in other forums.

Eric S. Raymond and Rick Moen have written an article
called ["How To Ask Questions The Smart Way"](http://www.catb.org/~esr/faqs/smart-questions.html) about mailing list
netiquette. It's a long read, but worth the effort.

**Note:** Please do NOT send your HttpComponents questions to the two authors. They welcome feedback on the article
itself, but are simply not a help resource for HttpComponents.

Research First
--------------

Before you post a question to a mailing list, make sure it isn't already answered. Read the available documentation.
Search the web, and in particular the mailing list archives. Being answered with a link to a mail or FAQ entry that
addresses exactly your question is one of the embarrassments you should avoid.

If you are answered with a link to a mail, don't take it too hard either. Maybe you didn't know the correct terms to
search for, or the mail was sent on another list. People that follow a mailing list for a long time are bound to
remember some of the old mails, and it may be easier for them to locate the old mail than to write down the answer
again.

Choose The Appropriate Forum
----------------------------

Different kinds of questions are discussed in different forums. It is important to choose the right forum for your
question. Posting a question to the wrong forum reduces your chances of getting a useful response. You will reach fewer
people that can answer your question, and those that could, will be less inclined to do so.

**Do not cross-post.** Do not send your question to more than one list. If you are in doubt where to post your question,
it is better to pick the wrong list than to send it to several lists. Even if you get answers on more than one list,
people subscribed to only one list will not be able to follow the whole discussion. Mailing list archives will also
contain only fragments of the discussion, so that it becomes harder for others with the same question to find the
answer.

The article mentioned above has a section
called ["Choose your forum carefully"](http://www.catb.org/~esr/faqs/smart-questions.html#forum). It basically tells you
the same stuff you'll find below.

### User List

If you develop an application that _uses_ HttpClient, and you need advice on how to achieve something with it, post your
question to the `httpclient-users` list. If you don't know whether HttpClient can do what you want it to do, post your
question to the users list. Even though you are a developer, you are not a developer of the HttpComponents. So please
don't post your question to the developer list.

And if somebody else asks a question you can answer, please do so!

### Developer List

If you want to discuss _development of HttpComponents_, post your question or suggestion to the `dev` list. The
developer list is used to discuss architecture, API design, new features, and bugs. Bug reports and comments filed
in [JIRA](./issue-tracking.html) will automatically be sent to this list, too.

### Issue Tracking

We are using [JIRA](./issue-tracking.html) as our issue tracking system. Although this is not a mailing list, and should
not be abused as a general discussion forum, it is another way to contact the HttpComponent developers. All issues and
comments will be sent to the developer mailing list.

If you are absolutely sure that you have found a bug, you can open a new issue for it. Choose "Bug" as the issue type.
If you are absolutely sure that our components don't provide a feature that would be useful for you and others, you can
open a new issue for it. Choose "New Feature" or "Wish" for it.

If you are not absolutely sure, please ask on the appropriate mailing list first. Most developers are monitoring both
lists, and we will tell you if you should open a new issue.

### Personal Mail

**Never** send a question directly to one of the people you have seen active on the mailing lists, or whose email
address you've found in the source code. You will be scorned and rebuffed, or at best ignored.

HttpComponents, as any other Apache project, is a _community_. Questions asked on a mailing list can be answered by any
member of the community who knows the answer and has the time to write it down. Answers sent to a mailing list are
available to everyone, through the public mailing list archives. This benefits the whole community.

By sending a question directly to somebody, you are implying that this person alone is responsible for helping you out,
and only you. No, we're not. We participate in a community. Post your question to the community at large, and chances
are that one of the members will answer it. If that requires information that is of little interest to the community,
for example large log files, you will be _asked_ to send such information directly to the person that picked up your
question.

It's OK to send a "Thank You" mail to a person that helped you. Just make sure that your next question goes to the
mailing list again.

### Shape Your Mail

Some people reading your mail will be processing dozens or hundreds of mails daily. To get their attention and a quick
reply, it is important that you make your mail easy to read and that you provide the background information that is
needed to answer your question. The biggest part of the article on
[asking smart questions](http://www.catb.org/~esr/faqs/smart-questions.html) mentioned above addresses this problem, so
we'll only give you the highlights here.

- Choose a descriptive Subject for your mail. Not:
  ```
  "Help! URGENT: Problem with HttpClient!!!"
  ```
  This subject does not give the least indication of what your mail is about. So you have a problem with HttpClient? And
  you need help? Duh, why else would you post to the mailing list. And it's urgent? For you maybe, but not for anyone
  else here.

- **Don't send HTML mail**, or other stylized mails. Use plain text. Either format it with about 72 characters per line,
  or just type without linebreaks so automatic formatters can take care of it. Don't format your mails with 90
  characters per line. Automatic formatters will split each line, making the result very hard to read.

- **Get to the point.**
  Ideally, keep your mail short. Just describe the problem and give the necessary background information. If it's read
  in less than a minute, many people will read it and the answer can probably be given quickly as well. If it takes
  several minutes to read your mail, people will take care of other mails first, and may never bother to read yours. If
  you have to provide extensive background information, make sure to get to the point in the first paragraph. The one
  that can be read in less than a minute. Describe your problem there, so people can decide quickly whether it makes
  sense for them to read the rest.

* **Don't Reply to send a new question.**
  If you have an answer or otherwise want to join an ongoing discussion, then use Reply-To on another mail. If you have
  a new question or want to start a new discussion, do not reply to a mail you've received from the list. Even if you
  change the subject, your mail client would still flag it as belonging to the existing thread. Many archives and email
  clients provide a threaded view, where only the initial mail of a thread is shown by default. Your mail will just get
  ignored by the people not interested in the original thread, even though they might be able to help you.

### The Lists

The HttpComponents project currently uses the following lists. Clicking on a list name will take you to a page with
subscribe, unsubscribe, and archive information. See below for information on digests.

* [httpclient-users@hc.apache.org](https://apache.org/foundation/mailinglists.html) - The list for users of HttpClient,
  either version 3 or 4. Users of HttpCore can also post their questions here, although most subscribers will probably
  not be able to answer them.

* [dev@hc.apache.org](https://apache.org/foundation/mailinglists.html) - The list for developers of HttpComponents and
  HttpClient 3. We don't mind getting HttpCore user questions here. Our issues tracker JIRA also posts here.

* [commits@hc.apache.org](https://apache.org/foundation/mailinglists.html) - The list for messages from our source code
  repository. Whenever the source code is modified, a mail with the changes is sent. This list is read-only.
  HttpComponents committers are expected to subscribe to the commits list, so they can review the changes.

* _private_ - The list for private communication of the HttpComponents PMC. Only PMC members and ASF members can
  subscribe. There is no public archive.

### Subscribe And Unsubscribe

To subscribe to the list `<xxx>`, send a mail to `<xxx>-subscribe@hc.apache.org`. While you are subscribed, you will
receive all mails sent to the list. You can send mails to the list yourself using the address `<xxx>@hc.apache.org`.
This does not apply for the commits list, where only the source code repository is allowed to send mails.

To unsubscribe from the list `<xxx>`, send a mail to`<xxx>-unsubscribe@hc.apache.org`. Unsubscribe information is also
appended to every mail sent via the list.

The **digest** of a list collects all mails of that list, sending you occasional updates. Each update contains the
subjects of the recent mails, and the mails themselves as attachments. This significantly reduces the number of
individual mails you receive from the list, while still giving you access to all the information.

To subscribe to the digest of list `<xxx>`, send a mail to`<xxx>-digest-subscribe@hc.apache.org`. While you are
subscribed to the digest, you can send mails to the list itself using the address`<xxx>@hc.apache.org`. This does not
apply for the commits list, where only the source code repository is allowed to send mails.

To unsubscribe from the digest of list `<xxx>`, send a mail to `<xxx>-digest-unsubscribe@hc.apache.org`. Unsubscribe
information is also appended to every mail sent by the digest.
