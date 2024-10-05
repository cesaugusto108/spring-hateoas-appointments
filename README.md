# appointments
Study project for Spring Boot, HATEOAS and Spring Data Pagination

## Description
This is a Spring Boot application that works as a RESTful API. Through the endpoints of the application it is possible to send HTTP requests to fetch and save data. MySQL database is used.

Results are presented with pagination and sorting and with links to other pages. Links serve as access to other results and to the first, current, next and last pages.

## Features
- CRUD operations for appointment management
- Full API documentation available via Swagger
- Spring Security authentication and authorization

## Prerequisites
Before you begin, ensure you have the following installed on your machine:
- [Git](https://git-scm.com)
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java JDK 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) (if not using Docker)

## Getting Started

### Clone the Repository
To get a local copy of the project, run the following command in your terminal:

```bash
git clone git@github.com:cesaugusto108/spring-hateoas-appointments.git
cd spring-hateoas-appointments
```

### Setting Up the Environment with Docker

1. **Create a Docker Network** (optional):
   ```bash
   docker network create appointmentsapp-network
   ```
2. **Create a .env file**
   The .env file should contain the PASSWORD, ACTIVE_PROFILES and EMP_PWORD keys.
   Example:
   PASSWORD=1234
   ACTIVE_PROFILES=dev
   EMP_PWORD=1234
   
   ```bash
   vim .env
   ```
3. **Use Maven Wrapper to build the application**
   ```bash
   ./mvnw clean package -DEMPPASSWORD=1234
   ```

   Or this, to skip tests:
   ```
   ./mvnw clean package -DskipTests
   ```
4. **Build and Run the Application**:
   Use Docker Compose to build and run the application:

   ```bash
   docker-compose up --build
   ```

   If it fails on Windows, disable BuildKit temporarily using this instead:

   ```bash
   DOCKER_BUILDKIT=0 docker-compose up --build
   ```

### Running the Application Locally (without Docker)
If you prefer to run the application locally without Docker, follow these steps:

1. **Navigate to Project Directory**:
   ```bash
   cd spring-hateoas-appointments
   ```

2. **Run with Maven**:
   Use the following command to start the application (dev profile) with necessary arguments (replace details where necessary):

   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.arguments="--ACTIVE_PROFILES=dev --SERVER-PORT=8089 --DB-HOST=localhost --DB-PORT=3306 --DB-USERNAME=admin --DB-PASSWORD=1234"
   ```

   There should be a running MySQL service on your computer with a schema named `appointments_tracker_dev`.

## Accessing the API Documentation
Once the application is running, you can access the full API documentation via Swagger at:

[http://localhost:8089/appointment-tracker/dev/swagger-ui/index.html](http://localhost:8089/appointment-tracker/dev/swagger-ui/index.html)

## Author
César Augusto Silva  
cesaraugustosilva@proton.me

## Entities model

This application models a simple system to store information about patients, physicians and appointments.

![232262918-d8ccea2f-ca09-40aa-a312-3d805892c08d](https://user-images.githubusercontent.com/93228693/235155200-6a0d677f-2702-421c-947d-402327f1a016.svg)

## Examples (using API's endpoints):

- Get all appointments from database (5 per page as default):
- HTTP request: GET
- Request URL: localhost:8089/appointments

>     curl -v localhost:8089/appointments | json_pp
>     Trying 127.0.0.1:8089...
>     Connected to localhost (127.0.0.1) port 8089 (#0)
>     GET /physicians HTTP/1.1
>     Host: localhost:8089
>     User-Agent: curl/7.88.1
>     Accept: */*
>     
>     < HTTP/1.1 200
>     < Content-Type: application/hal+json
>     < Transfer-Encoding: chunked
>     < Date: Sun, 16 Apr 2023 02:00:28 GMT
>     
>     {
>         "_embedded": {
>             "appointmentList": [
>                 {
>                     "id": 1,
>                     "patient": {
>                         "id": 1,
>                         "firstName": "Pedro",
>                         "lastName": "Cardoso",
>                         "email": "pedro@email.com"
>                     },
>                     "physician": {
>                         "id": 2,
>                         "firstName": "João",
>                         "lastName": "Brito",
>                         "specialty": "DERMATOLOGIST"
>                     },
>                     "status": "PAYMENT_PENDING",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/appointments/1"
>                         },
>                         "confirm": {
>                             "href": "http://localhost:8089/appointments/1/confirm"
>                         },
>                         "cancel": {
>                             "href": "http://localhost:8089/appointments/1/cancel"
>                         },
>                         "appointments": {
>                             "href": "http://localhost:8089/appointments/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/appointments/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 2,
>                     "patient": {
>                         "id": 3,
>                         "firstName": "Hugo",
>                         "lastName": "Silva",
>                         "email": "hugo@email.com"
>                     },
>                     "physician": {
>                         "id": 2,
>                         "firstName": "João",
>                         "lastName": "Brito",
>                         "specialty": "DERMATOLOGIST"
>                     },
>                     "status": "CONFIRMED",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/appointments/2"
>                         },
>                         "cancel": {
>                             "href": "http://localhost:8089/appointments/2/cancel"
>                         },
>                         "finish": {
>                             "href": "http://localhost:8089/appointments/2/finish"
>                         },
>                         "appointments": {
>                             "href": "http://localhost:8089/appointments/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/appointments/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 3,
>                     "patient": {
>                         "id": 4,
>                         "firstName": "Ana",
>                         "lastName": "Martins",
>                         "email": "ana@email.com"
>                     },
>                     "physician": {
>                         "id": 1,
>                         "firstName": "Marcela",
>                         "lastName": "Cavalcante",
>                         "specialty": "GENERAL_PRACTITIONER"
>                     },
>                     "status": "FINISHED",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/appointments/3"
>                         },
>                         "appointments": {
>                             "href": "http://localhost:8089/appointments/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/appointments/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 4,
>                     "patient": {
>                         "id": 5,
>                         "firstName": "Plínio",
>                         "lastName": "Ferreira",
>                         "email": "plinio@email.com"
>                     },
>                     "physician": {
>                         "id": 5,
>                         "firstName": "Marcela",
>                         "lastName": "Barros",
>                         "specialty": "ORTHOPEDIST"
>                     },
>                     "status": "CANCELLED",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/appointments/4"
>                         },
>                         "appointments": {
>                             "href": "http://localhost:8089/appointments/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/appointments/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 5,
>                     "patient": {
>                         "id": 9,
>                         "firstName": "Verônica",
>                         "lastName": "Lima",
>                         "email": "veronica@email.com"
>                     },
>                     "physician": {
>                         "id": 4,
>                         "firstName": "Osvaldo",
>                         "lastName": "Pereira",
>                         "specialty": "CARDIOLOGIST"
>                     },
>                     "status": "CANCELLED",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/appointments/5"
>                         },
>                         "appointments": {
>                             "href": "http://localhost:8089/appointments/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/appointments/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 }
>             ]
>         },
>         "_links": {
>             "first": {
>                 "href": "http://localhost:8089/appointments?page=0&size=5&sort=id,asc"
>             },
>             "self": {
>                 "href": "http://localhost:8089/appointments?page=0&size=5&sort=id,asc"
>             },
>             "next": {
>                 "href": "http://localhost:8089/appointments?page=1&size=5&sort=id,asc"
>             },
>             "last": {
>                 "href": "http://localhost:8089/appointments?page=3&size=5&sort=id,asc"
>             }
>         },
>         "page": {
>             "size": 5,
>             "totalElements": 20,
>             "totalPages": 4,
>             "number": 0
>         }
>     }

- Get all patients from database (5 per page as default):
- HTTP request: GET
- Request URL: localhost:8089/patients

>     curl -v localhost:8089/patients | json_pp
>     Trying 127.0.0.1:8089...
>     Connected to localhost (127.0.0.1) port 8089 (#0)
>      GET /patients HTTP/1.1
>     Host: localhost:8089
>     User-Agent: curl/7.88.1
>     Accept: */*
>     
>     < HTTP/1.1 200
>     < Content-Type: application/hal+json
>     < Transfer-Encoding: chunked
>     < Date: Sun, 16 Apr 2023 01:52:28 GMT
>     
>     {
>         "_embedded": {
>             "patientList": [
>                 {
>                     "id": 1,
>                     "firstName": "Pedro",
>                     "lastName": "Cardoso",
>                     "email": "pedro@email.com",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/patients/1"
>                         },
>                         "patients": {
>                             "href": "http://localhost:8089/patients/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/patients/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 2,
>                     "firstName": "Paula",
>                     "lastName": "Martins",
>                     "email": "paula@email.com",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/patients/2"
>                         },
>                         "patients": {
>                             "href": "http://localhost:8089/patients/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/patients/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 3,
>                     "firstName": "Hugo",
>                     "lastName": "Silva",
>                     "email": "hugo@email.com",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/patients/3"
>                         },
>                         "patients": {
>                             "href": "http://localhost:8089/patients/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/patients/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 4,
>                     "firstName": "Ana",
>                     "lastName": "Martins",
>                     "email": "ana@email.com",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/patients/4"
>                         },
>                         "patients": {
>                             "href": "http://localhost:8089/patients/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/patients/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 5,
>                     "firstName": "Plínio",
>                     "lastName": "Ferreira",
>                     "email": "plinio@email.com",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/patients/5"
>                         },
>                         "patients": {
>                             "href": "http://localhost:8089/patients/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/patients/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 }
>             ]
>         },
>         "_links": {
>             "first": {
>                 "href": "http://localhost:8089/patients?page=0&size=5&sort=id,asc"
>             },
>             "self": {
>                 "href": "http://localhost:8089/patients?page=0&size=5&sort=id,asc"
>             },
>             "next": {
>                 "href": "http://localhost:8089/patients?page=1&size=5&sort=id,asc"
>             },
>             "last": {
>                 "href": "http://localhost:8089/patients?page=2&size=5&sort=id,asc"
>             }
>         },
>         "page": {
>             "size": 5,
>             "totalElements": 12,
>             "totalPages": 3,
>             "number": 0
>         }
>     }

- Get all physicians from database (5 per page as default):
- HTTP request: GET
- Request URL: localhost:8089/physicians

>     curl -v localhost:8089/physicians | json_pp
>     Trying 127.0.0.1:8089...
>     Connected to localhost (127.0.0.1) port 8089 (#0)
>     GET /physicians HTTP/1.1
>     Host: localhost:8089
>     User-Agent: curl/7.88.1
>     Accept: */*
>     
>     < HTTP/1.1 200
>     < Content-Type: application/hal+json
>     < Transfer-Encoding: chunked
>     < Date: Sun, 16 Apr 2023 02:00:28 GMT
>     
>     {
>         "_embedded": {
>             "physicianList": [
>                 {
>                     "id": 1,
>                     "firstName": "Marcela",
>                     "lastName": "Cavalcante",
>                     "specialty": "GENERAL_PRACTITIONER",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/physicians/1"
>                         },
>                         "physicians": {
>                             "href": "http://localhost:8089/physicians/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/physicians/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 2,
>                     "firstName": "João",
>                     "lastName": "Brito",
>                     "specialty": "DERMATOLOGIST",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/physicians/2"
>                         },
>                         "physicians": {
>                             "href": "http://localhost:8089/physicians/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/physicians/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 3,
>                     "firstName": "Marília",
>                     "lastName": "Dantas",
>                     "specialty": "CARDIOLOGIST",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/physicians/3"
>                         },
>                         "physicians": {
>                             "href": "http://localhost:8089/physicians/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/physicians/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 4,
>                     "firstName": "Osvaldo",
>                     "lastName": "Pereira",
>                     "specialty": "CARDIOLOGIST",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/physicians/4"
>                         },
>                         "physicians": {
>                             "href": "http://localhost:8089/physicians/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/physicians/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 },
>                 {
>                     "id": 5,
>                     "firstName": "Marcela",
>                     "lastName": "Barros",
>                     "specialty": "ORTHOPEDIST",
>                     "_links": {
>                         "self": {
>                             "href": "http://localhost:8089/physicians/5"
>                         },
>                         "physicians": {
>                             "href": "http://localhost:8089/physicians/?page=0&size=5&direction=ASC&field=id"
>                         },
>                         "search": {
>                             "href": "http://localhost:8089/physicians/search?search=&page=0&size=5&direction=ASC&field=id"
>                         }
>                     }
>                 }
>             ]
>         },
>         "_links": {
>             "first": {
>                 "href": "http://localhost:8089/physicians?page=0&size=5&sort=id,asc"
>             },
>             "self": {
>                 "href": "http://localhost:8089/physicians?page=0&size=5&sort=id,asc"
>             },
>             "next": {
>                 "href": "http://localhost:8089/physicians?page=1&size=5&sort=id,asc"
>             },
>             "last": {
>                 "href": "http://localhost:8089/physicians?page=1&size=5&sort=id,asc"
>             }
>         },
>         "page": {
>             "size": 5,
>             "totalElements": 10,
>             "totalPages": 2,
>             "number": 0
>         }
>     }
