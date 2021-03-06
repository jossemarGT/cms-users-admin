# cms-users-admin

[![Build Status](https://travis-ci.org/cms-orbits/cms-users-admin.svg?branch=master)](https://travis-ci.org/cms-orbits/cms-users-admin)

This application is the result of doing reversing engineering to the Contest
Management System(CMS) and provides a backend layer for controlling the register
process of new users.

## Development

### Build locally

1) mvn install
2) mvn package
3) mvn spring-boot:run

### Configuration file

It's necessary to add some properties to the configuration file before of
running the application, these properties are:

- Cookie secret key
- CMS domain
- CMS port
- Database connection
- Message broker
- OAuth
- Proxy considerations

The file is locate in src/main/resources.

#### Cookie secret

The cookie secret key is a string characters which is used to encrypt a user
password. It's mandatory to put the same string that was added in CMS
configuration file.

```yml
  cookie-secret: 8e045a51e4b102ea803c06f92841a1fb
```

#### CMS Domain

The domain name is necessary to redirect a login request to CMS home page.

```yml
  cms-domain: 192.168.187.134
```

#### CMS Port

A port number is required only if the CMS Server is reached by IP route instead
of using a sub-domain

```yml
  cms-port: 8888
```

#### Database connection

To set up a connection to the CMS a database connection is mandatory. It's
described into the application properties file.

```yml
---
spring:
  profiles: development
  db-one:
    datasource:
      url: jdbc:postgresql://192.168.187.133:5432/cmsdb
      username: postgres
      password: postgres1
```

#### RabbitMQ connection

As same as the database the RabbitQM connection depends on multiple fields.

```yml
----
spring:
  rabbitmq:
    host: 192.168.187.133
    port: 5672
    username: rabbit
    password: rabbit1
```

#### Security

[CMS-users-admin](https://github.com/cms-orbits/cms-users-admin) uses OAuth
protocol to allow authenticated access to its resources by establishing a
connection to a specific social network provider.

```yml
---
security:
  google:
    client:
      clientId:
      clientSecret:
  ...
  ...
  github:
    client:
      clientId:
      clientSecret:
```

#### Proxy considerations

If you are using a http-proxy in between to redirect all the HTTP requests to
[CMS-users-admin](https://github.com/cms-orbits/cms-users-admin) make sure to
include the parameter authorization in each request.

```http

Authorization: Bearer eyJh123tokentest
```

### Configuration overrides

As it is known spring boot let externalize the application configuration, in
order to do that parameters can be injected using the following formats.

#### Running a jar file

```bash
java -jar cmsusers.jar --spring.db-one.datasource.username=custom_user

```

#### Running a docker image

```bash
docker run -p 8081:8081 -e SPRING_DB_ONE_DATASOURCE_USERNAME='custom_user' cmsorbits/cms-users:1.1
```
