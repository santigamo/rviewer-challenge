## 👀 Overview
This backend system is a simple API that allows users to create safeboxes and store content in them. The API is secured with Basic Auth and is also [documented with OpenAPI](./open-api.spec.yaml), and the documentation can be found attached with the source code.
The project follows a clean architecture, with the application layer interacting with the external system through a data provider interface. This allows the application layer to be isolated from the implementation details of the external system and makes it easier to test and maintain the code.

## 🤖 Technologies
- [Java 18](https://openjdk.java.net/projects/jdk/18/): The backend system is implemented using Java 18, one of the latest available versions of this language, which provides us with different features that we take advantage of in the API implementation.
- [Spring boot](https://spring.io/projects/spring-boot): The system uses Spring Boot as the web framework to handle HTTP requests and responses.

## ☝️ How to run this project
### ️Make
1. Install `make` on your computer, if you do not already have it.
2. Start the application: `make up`
3. Run the application tests: `make test`

### Docker compose
1. Install docker and docker-compose on your computer, if you do not already have it.
2. Start the application: `docker-compose up`
3. Execute `docker compose run -d -p "8080:8080" java-skeleton-api gradle clean build bootRun -x test`

### Gradle
1. Move to the project directory: `cd C4EEtnTqMi8aXhxyocR2`
2. Build the project for the first time: `./gradlew build`
3. To just run the project execute: `./gradlew run`

## 🎯 API Calls
- `GET /api/v1/health` - Health check
- `POST /safebox` - Creates a new safebox based on a non-empty name and a password.
- `GET /safebox/{id}/items` - Retrieves the currently stored contents in the safebox identified by the given ID.
- `PUT /safebox/{id}/items` - Inserts new contents in the safebox identified by the given ID and with the given Basic Auth.

## ℹ️ Demo
A sample user with some items on his safebox is available with the following details:
- username: `rviewer`
- password: `test`
- safebox id: `1bb1a31d-b525-4ee3-b4c3-5e8fe49c1af5`

**Additionally, a [postman collection](./Unsafebox.postman_collection.json) has been added to facilitate API testing.**

## 👽 Technical Details
### 📚 Dependencies
- [Lombok](https://projectlombok.org/): Used to reduce boilerplate code in the project.
- [PostgresSQL](https://www.postgresql.org/): The driver to connect with the database used in the project.
- [H2](https://www.h2database.com/): in-memory database used for testing.
- [Flyway](https://flywaydb.org/): Used to initialize the tables and manage database migrations.

### 🏗️ Architecture
- Clean Architecture: In the project we have always maintained a clean architecture, giving great importance to not coupling the domain and application layers to any framework. Pushing these needs to the infrastructure layer.

### 🛡️ Security
- Basic Auth: To implement the Basic Auth in the project, a minimum order filter has been implemented, which verifies authentication on all incoming calls. This solution has been chosen because it allows us to enable this functionality, with zero configuration, without adding major dependencies to the project and giving us a simple but total control of the project security.
- Password are stored in the database using the algorithm SHA3-256, which is a secure algorithm that is not reversible.

### ✨ Implementation highlights
- In the infrastructure structure layer we have the [package config](src/main/java/com/rviewer/safebox/infrastructure/config) where we can find:
  - The [DatabaseConfig](src/main/java/com/rviewer/safebox/infrastructure/config/DatabaseConfig.java): which is responsible for configuring the database connection.
  - The [DependencyInjectionConfig](src/main/java/com/rviewer/safebox/infrastructure/config/DependencyInjectionConf.java): which is responsible for configuring the dependency injection of the project.
- [application.yml](src/main/resources/application.yml)
  - `server.shutdown = graceful`: This property is responsible for allowing the application to finish the current requests before shutting down. 
- [DB Migration folder](src/main/resources/db/migration): This folder contains the scripts that are executed when the application starts. Are responsible for creating the tables in the database if they do not exist and migrating the database if necessary.
- Exception are defined individually in the [exception package](src/main/java/com/rviewer/safebox/domain/exceptions) and are managed by the [ControllerAdvisor](src/main/java/com/rviewer/safebox/infrastructure/exceptions/ControllerAdvisor.java) to keep a simple but ordered and powerful error handling.
- Security concerns are packaged in the [security package](src/main/java/com/rviewer/safebox/infrastructure/security) composed by:
  - The [BasicSecurityFilter](src/main/java/com/rviewer/safebox/infrastructure/security/BasicSecurityFilter.java): which is responsible for implementing the Basic Auth.
  - The [SecurityUtils](src/main/java/com/rviewer/safebox/infrastructure/security/SecurityUtils.java): which is responsible for providing the necessary methods to implement the Basic Auth.