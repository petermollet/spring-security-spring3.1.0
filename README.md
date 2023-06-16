# ReadMe



## Description

Project Spring with Spring Security for the release of **Spring 3.1.0** ( using Bearer token JWT) since the way to handle it change a bit.

Also the project uses:

- **Liquibase**: to generate the database and insert some values
- **spring-boot-docker-compose**: Newly released in spring 3.1.0. Is use to automatically inject configurations of the docker-compose into the properties of the application. Meaning that in the file `application.yml` there isn't properties to connect to the DB since it's in the `docker-compose.yaml`. When the project will be run, the docker-compose will be use to create the server for the database, then the project will connect itself to this one. 



## Environment

To be able to run the application, you need :

- **JAVA**: JDK 17 or above
- **Docker**
- Nothing running on ports:
  - 8080: port of the app
  - 8432: port of the db postgres (the credentials are *postgres/postgres* with the db name: *test-db*)



## RUN

To run the project, in CMD:

```CMD
mvnw
```

or

```CMD
./mvnw
```



You can also use `IntelliJ`.