# API для сокращения Url адреса
# Как работает
## Docker
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
