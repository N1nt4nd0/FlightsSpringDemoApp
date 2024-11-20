# Spring WEB pet-project

Demo implementation of an air travel REST service for airport staff

### Development manual to get started:

Work environment settings:

- Java 17
- PostgreSQL 16.3
- Maven 3.9.7

Frameworks used in the project:

- Spring Boot 3.3.2

## Project technologies:

- Spring:
    - Boot
    - MVC
    - Data JPA
    - Security
- Lombok
- Mockito testing
- Swagger UI

## Implemented features:

- Logging calls and errors in methods of services and controllers using aspects (configured in yaml configuration)
- User authorization using JWT token
- Using pagination in queries
- Swagger UI with JWT authorization and detailed documentation for API testing

## Content definitions:

### [Database used in project (Version 13.10.2016-big)](https://postgrespro.ru/education/demodb) [(Download zip)](https://edu.postgrespro.ru/demo-big-20161013.zip)

After importing main database in PostgreSQL, use specified requests for creating additional database for simple Spring
Security users authentications

```roomsql
-- Creating new user 'pilot' with password 'pilot' for database connections
CREATE USER pilot WITH PASSWORD 'pilot';

-- Creating extension for uuid auto generator
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Creating flights_users table in public scheme
CREATE TABLE public.flights_users (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY NOT NULL,
    username character varying(255) UNIQUE NOT NULL,
    password character varying(255) NOT NULL,
    roles character varying[] NOT NULL,
    active boolean DEFAULT true NOT NULL
);

-- Grant privileges to users table for 'pilot' database user 
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE public.flights_users TO pilot;
```

## Before run:
Set up next environment variables:

`FLIGHTS_SPRING_ROOT_USERNAME` - root access username
`FLIGHTS_SPRING_ROOT_PASSWORD` - root access user password
`FLIGHTS_SPRING_JWT_SECRET` - secret key for spring security JWT Token encoding
`FLIGHTS_SPRING_POSTGRES_URL` - postgres flights database url
`FLIGHTS_SPRING_POSTGRES_USERNAME` - postgres flights database username
`FLIGHTS_SPRING_POSTGRES_PASSWORD` - postgres flights database user password

## Documentation:

[API documentation (SwaggerHUB)](https://app.swaggerhub.com/apis-docs/FEODORKEKOVICH/flights-spring-demo/0.0.2)

### Realized endpoints:

### 1. Airports API (/flights/api/v1/airports) - working with airport data (for authorized users)

- /cities - getting all cities from the database
- /city - getting airports in a specific city

### 2. Flights API (/flights/api/v1/flights) - working with air travel data (for authorized users)

- /schedule - getting airport schedules
- /ticket_number - getting flights by ticket number
- /phone - getting flights using the passenger's phone number
- /email - getting flights via passenger email

### 3. Tickets API (/flights/api/v1/tickets) - working with passenger ticket data (for authorized users)

- /phone - getting a list of tickets by passenger phone number
- /email - getting a list of tickets by passenger email

### 4. Auth API (/flights/api/v1/tickets/auth) - user authorization (permit to all)

- /login - API authorization
- /refresh_token - JWT token update
- /check_access - API availability check

### 5. Admin API (/flights/api/v1/tickets/admin) - administration service (for users with the ADMIN role)

- /users/new - creating a new user
- /users/delete - deleting a user by name
- /users/active - setting user activity by name
- /users/close_access - closing access to the API for technical work (except for the open Auth API and users with the
  ADMIN role)
- /users/open_access - opening access to the API for users