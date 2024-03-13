# API для сокращения Url адреса
<a href="https://codeclimate.com/github/JavaQuaker/test/maintainability"><img src="https://api.codeclimate.com/v1/badges/9cd50f82ca8c9a8c1cac/maintainability" /></a>
<a href="https://codeclimate.com/github/JavaQuaker/test/test_coverage"><img src="https://api.codeclimate.com/v1/badges/9cd50f82ca8c9a8c1cac/test_coverage" /></a>
# Docker
## Как работает
### Авторизация
Для работе в приложении необходима авторизация.
Для первого входа необходимо пройти аутентификацию по токену:

post запрос: 
http://localhost:8080/api/login
```
username: qwe@mail.ru
password: qwerty
```
###
Пример для проверки генерации коротких ссылок в Postman:

post запрос:
http://localhost:8080/api/urls
```
{
    "urlData": {
        "url": "http://mail.ru"
    },
    "hashData": {
        "name": ""
    }
}
```
Ответ:
```
{
    "id": 1,
    "name": "0Uhfk6",
    "nameHashId": 1
}
```

###
Пример для проверки ссылки введенной вручную:
post запрос:
http://localhost:8080/api/urls
```
{
    "urlData": {
        "url": "http://ya.ru"
    },
    "hashData": {
        "name": "zxcvb"
    }
}
```
Ответ:
```
{
    "id": 2,
    "name": "zxcvb",
    "nameHashId": 2
}
```
###
Пимер ввода короткой ссылки для получения исходного url:
get запрос:
http://localhost:8080/api/urls/zxcvb
Ответ:
```
{
    "id": 2,
    "url": "http://ya.ru",
    "assigneeId": 1,
    "createdAt": "2024-03-13"
}
```
