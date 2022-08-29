# DatabaseTestProject
Simple project with REST API for shipping management

Techs:
* Spring Boot
* Spring Data Jpa
* Liquibase
* Docker


# How to launch: 
* pull project

From project directory:
* $ mvn clean compile jib:dockerBuild
* $ docker compose up -d
* PGAdmin on localhost:5050, register any server name, host: postgres, user: user, password: password
