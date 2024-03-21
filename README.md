# API для сокращения Url адреса
[![Actions Status](https://github.com/JavaQuaker/test/actions/workflows/main.yml/badge.svg)](https://github.com/JavaQuaker/test/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/9cd50f82ca8c9a8c1cac/maintainability)](https://codeclimate.com/github/JavaQuaker/test/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/9cd50f82ca8c9a8c1cac/test_coverage)](https://codeclimate.com/github/JavaQuaker/test/test_coverage)
# Docker:
```
docker run -it -p 3000:8080 short
```
## Как работает
### Авторизация
Для работе в приложении необходима авторизация.
Для первого входа необходимо пройти аутентификацию по токену:

post запрос: 
http://localhost:3000/api/login
```
username: qwe@mail.ru
password: qwerty
```
###
Пример для проверки генерации коротких ссылок в Postman:

post запрос:
http://localhost:3000/api/urls
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
http://localhost:3000/api/urls
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
http://localhost:3000/api/urls/zxcvb
Ответ:
```
{
    "id": 2,
    "url": "http://ya.ru",
    "assigneeId": 1,
    "createdAt": "2024-03-13"
}
```
