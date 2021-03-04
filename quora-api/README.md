# Quora API

An unofficial API allowing the fetching of [Quora](https://www.quora.com/) questions and answers.

## Getting Started

This application is a Spring boot Kotlin application. 

The fetching of information is managed using `com.bluurr:quora-fetch-client` to read questions and answers from the [Quora](https://www.quora.com/) platform.

### Functions

- Authentication
- Question Searching
- Question retrieval including answers

### Prerequisite

- Java 11
- Maven 3
- Chrome

### Testing

To test the loader use the following command (update the `changeme` to the correct details)

```BASH
./mvnw clean verify -pl .,quora-api -DargLine="-Dquora.login.username=changeme -Dquora.login.password=changeme -Dweb.driver.contact=changeme"
```

- The username used to log into Quora `quora.login.username`
- The Password used to log into Quora `quora.login.password`
- The contact information to be supplied in the user agent as required by [Quora TOS](https://www.quora.com/about/tos) `web.driver.contact`


### Usage

#### Starting Application

To start the Spring Boot application the following command should be run.

```BASH
./mvnw spring-boot:run -pl quora-api -Dspring-boot.run.arguments="--quora.login.username=changeme --quora.login.password=changeme --web.driver.contact=changeme"
```

#### OpenAPI Documentation

The service provides the API specification found [here](http://localhost:8080/swagger-ui.html)
