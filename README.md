# Quora loader

A read-only locator and extraction library for Quora questions and answers.

[![Build Status](https://dev.azure.com/open-source-bluurr-io/quora-loader-project/_apis/build/status/bluurr.quora-loader?branchName=master)](https://dev.azure.com/open-source-bluurr-io/quora-loader-project/_build/latest?definitionId=2&branchName=master)

## Getting Started
This library allows you to login, search and retrieve answers for a question. As no public API is provided by the Quora platform at this current time, the library uses the Selenium framework to retrieve the data from the Quora platform.

### Prerequisite
The library requires the following minimum versions
1. Java 11
2. Maven 3 or higher
3. Docker (For Testing Only)

#### Selenium

Selenium testing is performed within Docker using the Chrome Selenium image. 

To enable headless mode: `-Dwebdriver.headless={true|false}`
To enable recording output: `-Dcontainer.record={true|false}`

### Usage
Below is an example of how to login, search and load back a question.


```Java
/** Set-up driver **/

var driver = new ChromeDriver();

var credentials = LoginCredential.builder()
    .username(username)
    .password(password)
    .build();

final String QUORA_LOCATION = "https://www.quora.com";


// Start on login page

LoginPageNavigator navigator = new LoginPageNavigator(BASE_URL, credentials, driver);

// Trigger a login

AuthenticatedNavigator authenticatedNav = navigator.authenticate();

/** Search the term Java on the Quora platform **/

SearchPageNavigator searchNav = authenticatedNav.searchForTerm(SEARCH_TERM);

/** Read the first page of questions **/
List<QuestionSearchResult> questions = searchNav.results().next();

/** Open the question page **/

QuestionSearchResult searchResult = searchNav.firstResult();
QuestionPageNavigator questionNav = authenticatedNav.getQuestionAt(searchResult.getLocation());

/** Read first page of answers **/

List<Answer> answers = questionNav.answers().next();

```
### Tests
To run the integration tests the following properties need to be set:
1. `-Dquora.login.username={username}`
2. `-Dquora.login.password={password}`

## Compliance

As per the Quora terms of service [TOS](https://www.quora.com/about/tos) for machine driven tools - 

1. You must use a descriptive user agent header.
2. You must follow robots.txt at all times.
3. Your access must not adversely affect any aspect of the Quora Platform’s functioning.
4. You must make it clear how to contact you, either in your user agent string, or on your website if you have one.

### Note
The integration tests require the following property to be define `-Dquora.contact={contact_here}` to meet these requirements.

## Maven
To add a dependency on Quora loader using Maven, use the following:

```xml
<dependency>
    <groupId>com.bluurr</groupId>
    <artifactId>quora_loader</artifactId>
    <version>1.7.1</version>
</dependency>
```

## License
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
