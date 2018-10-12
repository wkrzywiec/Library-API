# Library-API
This a simple REST service and is part of the other project - [Library Portal](https://github.com/wkrzywiec/Library-Spring) (web application). It allows to perform CRUD actions on basic resources, like users or books entities, and retrieve them as JSON respond.

Similarly to the main project, it contains Flyway migration scripts so MySQL database could be set up regardless of the main app.

There is also a different version of this app that can be deployed and ran on the Google Cloud Platform service - [Google App Engine](https://cloud.google.com/appengine/) and [Cloud SQL](https://cloud.google.com/sql/). To see the source code go to another branch of this repository called [app-engine](https://github.com/wkrzywiec/Library-API/tree/app-engine).

## How to run?

First create a new user and schema in you local database.

```sql
  CREATE USER 'library-spring'@'localhost' IDENTIFIED BY 'library-spring';
  GRANT ALL PRIVILEGES ON  *.* TO 'library-spring'@'localhost';
  SET GLOBAL EVENT_SCHEDULER = ON;
  
  CREATE SCHEMA `library_db`
```

Next, clone this repository.

```
$ git clone https://github.com/wkrzywiec/Library-Spring
```

Finally run this application thru LibraryRestApplication as Java application.

```java
@SpringBootApplication
public class LibraryRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryRestApplication.class, args);
	}
}
```

## Documentation
### Swagger 2
For rendering the documentation of the API Swagger 2 with Springfox implementation was used. 

When the application is running, type in a browser:

```
http://localhost:8888/library-spring/api/v2/api-docs
```
To receive such respond (only part)

```json
{
	"swagger": "2.0",
	"info": {
		"description": "This a simple REST service and is part of the other project - Library Portal. It allows to perform CRUD actions on basic resources, like users or books entities, and retrieve them as a JSON respond.",
		"version": "1.0",
		"title": "Library Portal API",
		"termsOfService": "urn:tos",
		"contact": {
			"name": "Wojciech Krzywiec",
			"url": "https://github.com/wkrzywiec"
		},
		"license": {
			"name": "Apache 2.0",
			"url": "http://www.apache.org/licenses/LICENSE-2.0:"
		}
	},
	"host": "localhost:8888",
	"basePath": "/library-spring/api",
	"tags": [],
	"paths": {},
	"definitions": {
		"Author": {
			"type": "object",
			"properties": {
			"id": {
				"type": "integer",
				"format": "int64"
			},
			"name": {
				"type": "string"
			}
			},
			"title": "Author"
		},
```
Also [Swagger UI](https://swagger.io/tools/swagger-ui/) is supported, to enter it type in a browser:

```
http://localhost:8888/library-spring/api/swagger-ui.html
```
### Resources

Base URL for each request is `https://localhost:8888//library-spring/api`, so combine it with one of the following parts.
#### User

| Link          | HTTP Method   | Description                                        | 
| ------------- | ------------- | -------------------------------------------------  |
| `/users`      | GET           | Retrieve the list of all users.                    |
| `/users/{id}` | GET           | Retrieve specific user with provided `{id}`.    |
| `/users`      | POST          | Add new user to the database.                      |
| `/users/{id}` | PUT           | Update specific user's (`{id}`) fields.           |
| `/users/{id}` | DELETE        | Delete from the database user with provided `{id}`. |

Sample JSON respond of the user resource.

```json
{
	"id": 17,
	"username": "daenerys",
	"email": "denerys.targaryen@kings-landing.com",
	"enable": true,
	"firstName": "Daenerys",
	"lastName": "Targaryen",
	"phone": null,
	"birthday": null,
	"address": null,
	"postalCode": null,
	"city": null,
	"recordCreated": "2018-09-27T06:37:54.000+0000",
	"roles": [
		{
		"id": 1,
		"name": "USER"
		}
	],
	"_links": {
	"self": {
		"href": "http://localhost:8888/library-spring/api/users/17"
		},
		"users": {
		"href": "http://localhost:8888/library-spring/api/users"
		}
	}
}

```

#### Book

| Link          | HTTP Method   | Description                                        | 
| ------------- | ------------- | -------------------------------------------------  |
| `/books`      | GET           | Retrieve the list of all books.                    |
| `/books/{id}` | GET           | Retrieve specific book with provided `{id}`.    	|
| `/books/{id}` | DELETE         | Delete from the database book with provided `{id}`.    	|

Sample JSON respond of the book resource.

```json
{
	"id": 6,
	"googleId": "5NomkK4EV68C",
	"title": "A Game of Thrones",
	"authors": [
		{
			"id": 6,
			"name": "George R. R. Martin"
		}
	],
	"publisher": "Random House Publishing Group",
	"publishedDate": "2003-01-01",
	"isbn": {
		"id": 6,
		"isbn10": "0553897845",
		"isbn13": "9780553897845"
	},
	"pageCount": 720,
	"categories": [
		{
			"id": 1,
			"name": "Fiction / Action & Adventure"
		},
		{
			"id": 11,
			"name": "Fiction / Science Fiction / Action & Adventure"
		},
		{
		"id": 12,
		"name": "Fiction / Fantasy / Epic"
		}
	],
	"rating": 4,
	"imageLink": "http://books.google.com/books/content?id=5NomkK4EV68C&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE72NMsHhHG15TMsLXMP_WEUjVBNIr6BQ7m8dvfjDiT6wLCw2QcIXGE4nQdaKFJnWgu2INjLeAxmWg5hnq5GyKW7b-u0mtGnGIdc2GWdBa-LSp118g0Ug4H-8OKFS1qXSOtQxxcZc&source=gbs_api",
	"description": "A SONG OF ICE AND FIRE: BOOK ONE...",
	"_links": {
		"self": {
			"href": "http://localhost:8888/library-spring/api/books/6"
		},
		"books": {
			"href": "http://localhost:8888/library-spring/api/books"
		}
	}
}
```

#### Reserved

| Link          | HTTP Method   | Description                                        | 
| ------------- | ------------- | -------------------------------------------------  |
| `/reserved`     | GET         | Retrieve the list of all reservations.             |
| `/reserved/{id}`| GET         | Retrieve specific reservation with provided `{id}`.    |
| `/reserved/users/{userId}`   | GET         | Retrieve list of reservations that are assigned to the user `{userId}`. |
| `/reserved/books/{bookId}`   | GET         | Retrieve reservation resource that is assigned to the book `{bookId}`.|


#### Borrowed

| Link          | HTTP Method   | Description                                        | 
| ------------- | ------------- | -------------------------------------------------  |
| `/borrowed`     | GET         | Retrieve the list of all borrowed books.             |
| `/borrowed/{id}`| GET         | Retrieve specific borrowed book with provided `{id}`.    |
| `/borrowed/users/{userId}`   | GET         | Retrieve list of borrowed books that are assigned to the user `{userId}`. |
| `/borrowed/books/{bookId}`   | GET         | Retrieve borrowed resource that is assigned to the book `{bookId}`.|


## Monitoring
Library API has also several monitoring endpoints enabled. After typing following address in a web browser

```
http://localhost:8888/library-spring/api/actuator/
```
You should get the list of links. Each of them provides useful information about running application.

```json
{
	"_links": {
		"self": {
			"href": "http://localhost:8888/library-spring/api/actuator",
			"templated": false
		},
		"health": {
			"href": "http://localhost:8888/library-spring/api/actuator/health",
			"templated": false
		},
		"flyway": {
			"href": "http://localhost:8888/library-spring/api/actuator/flyway",
			"templated": false
		},
		"info": {
			"href": "http://localhost:8888/library-spring/api/actuator/info",
			"templated": false
		},
		"loggers": {
			"href": "http://localhost:8888/library-spring/api/actuator/loggers",
			"templated": false
		},
		"loggers-name": {
			"href": "http://localhost:8888/library-spring/api/actuator/loggers/{name}",
			"templated": true
		},
		"httptrace": {
			"href": "http://localhost:8888/library-spring/api/actuator/httptrace",
			"templated": false
		},
		"mappings": {
			"href": "http://localhost:8888/library-spring/api/actuator/mappings",
			"templated": false
		}
	}
}
```

## Technology stack

- Java 8
- Spring Boot 
- Hibernate, JPA
- Swagger
- JSON
- HATEOAS
- MySQL
- Flyway
- Tomcat server (only for local deployment)
- Jetty server (only for Google Cloud Platform deployment)
- Lombok
- JUnit
- Hamcrest

## Useful links

- [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
- [Postman](https://www.getpostman.com/)
- [Setting Up Swagger 2 with a Spring REST API](https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api)
- [Spring Boot Actuator: Health check, Auditing, Metrics gathering and Monitoring](https://www.callicoder.com/spring-boot-actuator/)
- [Tired of Null Pointer Exceptions? Consider Using Java SE 8's Optional!](https://www.oracle.com/technetwork/articles/java/java8-optional-2175753.html)