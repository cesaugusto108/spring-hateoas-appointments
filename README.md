# spring-hateoas-appointments

Study project for Spring Boot, HATEOAS and Spring Data Pagination

## Description

This is a Spring Boot application that works as a RESTful API. Through the endpoints of the application it is possible
to send HTTP requests to fetch and save data. H2 in memory database is used.

Results are presented with pagination and sorting and with links to other pages. Links serve as access to other results
and to the first, current, next and last pages.

## Entities model

This application models a simple system to store information about patients, physicians and appointments.

![diagram](https://employee-images.githubusercontent.com/93228693/232262918-d8ccea2f-ca09-40aa-a312-3d805892c08d.svg)

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
