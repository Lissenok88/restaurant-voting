Restaurants voting system for deciding where to have lunch.
===========================================================

This is the REST API implementation of voting system for deciding where to have lunch.

### Technology stack used:
* JDK 17
* Spring Boot 2.5
* Maven
* Spring MVC
* Spring Data JPA (Hibernate)
* Spring Security
* Lombok
* H2
* Caffeine Cache
* JUnit 5
* Swagger/OpenAPI 3.0

### Project key logic:
- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
  - If it is before 11:00 we assume that he changed his mind.
  - If it is after 11:00 then it is too late, vote can't be changed
- Each restaurant provides a new menu each day.

### API documentation:
### [REST API documentation](http://localhost:8080/swagger-ui.html)  

### Credentials:
```
Admin: admin@gmail.com / admin
User:  user@yandex.ru / password
Bob:   bob@mail.ru / bob
```
