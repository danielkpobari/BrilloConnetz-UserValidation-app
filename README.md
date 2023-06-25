
A Java Application that validates user input and returns a response based on the input.

Technologies used:
Java 17
Spring Boot
Maven
JWT
Postman

API Documentation:
[https://documenter.getpostman.com/view/23166862/2s93z6ejh1](https://github.com/danielkpobari/BrilloConnetz-UserValidation-app.git)

**Installation:**
1. Clone the repository
git clone https://github.com/danielkpobari/BrilloConnetz-UserValidation-app.git
2. Build the project
./mvnw clean install
3. Run the project
./mvnw spring-boot:run

**Usage:**
1. validate user input
POST http://localhost:8080/validate
Request Body:
```json
{
    "name": "Daniel Kpobari",
    "email": "danielkpobari",
    "password": "F1s2g3h467",
    "dateOfBirth": "1990-01-01"
}
Response:
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYW5pZWxrcG9iYXJpIiwiaWF0IjoxNjM0MjU0NjQ3LCJleHAiOjE2Mz```


2. **Verify user input**
POST http://localhost:8080/verify
Request Body:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYW5pZWxrcG9iYXJpIiwiaWF0IjoxNjM0MjU0NjQ3LCJleHAiOjE2Mz"
    }
   