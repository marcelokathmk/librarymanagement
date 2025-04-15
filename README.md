# Microservice: Library Management

## Technologies used

- Java 17
- Apache Maven
- OpenApi generator (maven plugin)
- Spring Boot, Spring Data, Spring Security
- PostgreSQL
- JPA/Hibernate
- Docker/Docker Compose
- Flyway

## Links and References
- https://hub.docker.com/_/openjdk
- https://hub.docker.com/_/postgres
- https://openapi-generator.tech/docs/generators/spring/#metadata

## Prerequisites
It is necessary to install Git, Java 17, Maven, and Docker on the local machine:
- https://git-scm.com/downloads
- https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html
- https://maven.apache.org/download.cgi
- https://www.docker.com/products/docker-desktop/

## Architecture
The microservice has two modules: api and server.

The API module will be responsible for generating the objects and REST interfaces, so that the server module can implement them.
To generate these interfaces, the OpenAPI Maven plugin was used, which is based on reading the api.yaml file located at the root of the api module.

The server module, in addition to implementing the interfaces generated in the API module, is responsible for generating the final artifact (Jar) as it contains the configurations for the database and the authentication and security rules of the service.

## Steps to run the service
You can download the .zip or clone the repo available on: https://github.com/marcelokathmk/librarymanagement

The project contains a Docker Compose file that starts the microservice and a PostgreSQL database.

Once the project has been cloned or downloaded, there is a .sh file (execute.sh) in the root directory that builds the service and starts the Docker containers, making the application functional.

Inside this file, the following commands are included:

```mvn clean package``` to download the maven dependencies and build the JAR file

```docker compose -f docker-compose.yml down -v``` to stop the containers configured in the file

```docker compose -f docker-compose.yml build``` to build the docker images in the file

```docker compose -f docker-compose.yml up``` to start the containers configured in the file

At the end of the execute.sh script execution, you can check if the service started successfully by accessing the Swagger UI:

http://localhost:9000/library/api/swagger-ui/index.html#/

It is also possible to verify the availability of the database using a client such as DBeaver, by adding the following authentication information:

- URL: jdbc:postgresql://localhost:5432/library
- Database: library
- username: postgres
- password: postgres

## Business Rules

All endpoints, except for /auth/login, are authenticated and require a Bearer token to be provided in the Authorization header of the request.

It is recommended to download the Postman application(https://www.postman.com/downloads/), as there is a JSON file inside the 'Postman' folder that can be imported to find all the existing REST API requests of the microservice.

The application uses the Flyway library to manage the versioning of the database structure, and two users are created when the service is started. The system has 2 authorization roles: ROLE_OWNER and ROLE_CLIENT.

The password is encrypted in the database, so to execute the login endpoint, use one of the following users, depending on which role you need to use:

User with role: ROLE_OWNER
- login: user_owner
- password: myownerpasswordencrypted

User with role: ROLE_CLIENT
- login: user_client
- password: myclientpasswordencrypted

## REST Apis, Roles and Authorization

With the ROLE_OWNER role, it is possible to execute the following REST resources:

```GET /fees``` to get the fees

```PUT /fees``` to update the fees value


```GET /loans/{book_id}/history``` to view the loan history of a specific book

```GET /books``` to search/view books filtering by specific fields

```POST /books``` to create a new book

```GET /books/{book_id}``` to view the book details

```DELETE /books/{book_id}``` to logically delete a book

```PATCH /books/{book_id}``` to edit/change the book details


With the ROLE_CLIENT role, it is possible to execute the following REST resources:

```GET /books``` to search/view books filtering by specific fields


```POST /loans``` to borrow a given book

```PATCH /loans/{book_id}/return``` to return the given book previously borrowed

