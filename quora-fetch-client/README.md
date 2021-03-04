# Quora Fetch Client

An unofficial client library allowing the fetching of [Quora](https://www.quora.com/) questions and answers. 

## Getting Started

This library uses Java and the Selenium framework to read questions and answers from the [Quora](https://www.quora.com/)  platform.

### Functions

- Authentication
- Question Searching
- Question retrieval including answers

### Prerequisite

- Java 11
- Maven 3
- Docker (For Running Tests)

### Testing

To test the loader use the following command (update the `changeme` to the correct details)

```BASH
./mvnw clean install -pl .,quora-fetch-client -DargLine="-Dquora.login.username=changeme -Dquora.login.password=changeme -Dquora.contact=changeme"
```

- The username used to log into Quora `quora.login.username` 
- The Password used to log into Quora `quora.login.password`
- The contact information to be supplied in the user agent as required by [Quora TOS](https://www.quora.com/about/tos) `quora.contact`

#### Selenium

The selenium driver is configured to use the chrome driver (provided by the docker image). 

The following setting can be configured:

- Configure headless: `-Dwebdriver.headless={true|false}`
- Configure video recording: `-Dcontainer.record={true|false}`

### Usage

Below are samples of how to use the Quora loader in your project.

#### Setup

The following snippets will set up the loader into the correct state.

```JAVA

  final String BASE_URL="https://www.quora.com";

  var driver = new EnhancedDriver(BASE_URL, new ChromeDriver());
  
```

#### Authentication

Authenticate with the Quora platform.

```JAVA

    EnhancedDriver driver = ...
        
    var credentials = LoginCredential.builder()
    .username("changeme")
    .password("changeme")
    .build();
    
    var loginNav = new LoginPageNavigator(credentials, driver);
    
    AuthenticatedNavigator authenticatedNav = navigator.authenticate();

```

#### Search For Question

Search for a question on the Quora platform using an authenticated session.

```JAVA
    
    AuthenticatedNavigator authenticatedNav = ...
        
        
    String searchTerm = "example term";

    SearchPageNavigator searchNav = authenticatedNav.searchForTerm(searchTerm);

    /* Fetch the first page of questions **/
    List<QuestionSearchResult> searchResults = searchNav.results().next();

    /* Fetch the first question on the first page **/
    QuestionSearchResult searchResult = searchNav.firstResult();
    
```

#### Fetch Question

Fetch a question and its answers from the Quora platform using an authenticated session.

```JAVA

  AuthenticatedNavigator authenticatedNav = ...
  QuestionSearchResult searchResult = ...

  QuestionPageNavigator questionNav = authenticatedNav.getQuestionAt(searchResult.getLocation());

  /* Read first page of answers for a question **/
  List<Answer> answers = questionNav.answers().next();
  
```
