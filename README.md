# Spring WEB пет-проект
Демонстрационная реализация REST сервиса авиаперелетов для персонала аэропортов

База данных используемая в проекте: https://postgrespro.ru/education/demodb

## Реализованные сервисы:
### 1. Airports API (/flights/api/v1/airports) - работа с данными аэропортов (для авторизованных пользователей)
- /cities - получение всех городов из базы
- /city - получение аэропортов в конкретном городе
### 2. Flights API (/flights/api/v1/flights) - работа с данными авиаперелетов (для авторизованных пользователей)
- /schedule - получение расписания аэропортов
- /ticket_number - получение перелётов по номеру билета
- /phone - получение перелётов по номеру телефона пассажира
- /email - получение перелётов по email пассажира
### 3. Tickets API (/flights/api/v1/tickets) - работа с данными билетов пассажиров (для авторизованных пользователей)
- /phone - получение списка билетов по номеру телефона пассажира
- /email - получение списка билетов по email пассажира
### 4. Auth API (/flights/api/v1/tickets/auth) - авторизация пользователей (открыто для всех)
- /login - авторизация в API
- /refresh_token - обновление JWT токена
- /check_access - проверка доступности API
### 5. Admin API (/flights/api/v1/tickets/admin) - сервис администрирования (для пользователей с ролью ADMIN)
- /users/new - создание нового пользователя
- /users/delete - удаление пользователя по имени
- /users/active - установка активности пользователя по имени
- /users/close_access - закрытие доступа к API для проведения технических работ (кроме открытого Auth API и пользователей с ролью ADMIN)
- /users/open_access - открытие доступа к API для пользователей
## Технологии проекта:
- Spring Framework: Boot, MVC, Data JPA, Security
- Hibernate
- Lombok
- Mockito
- Swagger
## Реализованные фичи:
- Логирование вызовов методов сервисов и контроллеров с помощью аспектов (настраивается в yaml конфигурации)
- Авторизация пользователей с помощью JWT токена
- Swagger UI с JWT авторизацией и подробной документацией для тестирования API