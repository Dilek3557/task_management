# Task Management API (JWT)

Spring Boot ile geliştirilmiş JWT Authentication + Authorization (USER/ADMIN) destekli Task Management REST API.

## Features
- Register / Login (JWT Token)
- Role based access (USER / ADMIN)
- USER: sadece kendi tasklarını görür / günceller / siler
- ADMIN: herhangi bir task’ın status’unu güncelleyebilir
- Task Status: TODO / IN_PROGRESS / DONE
- Global Exception Handling + Validation

## Tech Stack
- Java + Spring Boot
- Spring Security + JWT
- PostgreSQL
- JPA/Hibernate
- Lombok

## Run
1) PostgreSQL çalıştır
2) `application.properties` ayarlarını yap
3) `mvn spring-boot:run`

## API (Summary)
### Auth
- POST `/auth/register`
- POST `/auth/login`

### Tasks (Authenticated)
- POST `/tasks`
- GET `/tasks`
- PUT `/tasks/{id}`
- DELETE `/tasks/{id}`
- PATCH `/tasks/{id}/status`
