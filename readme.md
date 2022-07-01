# ojol-api (ojek online)

## Tech Stack

1. Kotlin Language
2. Spring Framework
3. MongoDB
4. Security with JWT
5. Role Access

## Deploy Heroku

```
Deploy : 
Using GitHub Action

Log    :
heroku logs --tail -a ojol-api

Note   : 
ojol-api is app name in heroku
```

## Base url

```
https://ojol-api.herokuapp.com/api/
```

## Table Endpoints

### Customer

| Name              | Endpoint               | Method | Bearer token | Body and response                    |
|-------------------|------------------------|--------|--------------|--------------------------------------|
| Register          | `v1/customer/register` | `POST` | no           | [example](#customer-register---post) |
| Login             | `v1/customer/login`    | `POST` | no           | [example](#customer-login---post)    |
| Get All Customer  | `v1/customer/all`      | `GET`  | yes          | [example](#customer-all---get)       |
| Get Info Customer | `v1/customer`          | `GET`  | yes          | [example](#customer-info---get)      |

### Customer Register - POST

```
POST v1/customer/register
```

Body

```json
{
  "name": "Badang",
  "username": "cust_badang",
  "password": "1234"
}
```

Response

```json
{
  "status": true,
  "message": "Success",
  "data": true
}
```

### Customer Login - POST

```
POST v1/customer/login
```

Body

```json
{
  "username": "cust_joni",
  "password": "1234"
}
```

Response

```json
{
  "status": true,
  "message": "Success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjNzM3NzRmYS0yNmI3LTQ1ODctYjEwMi0zZDE5ZWM3NWY5M2UiLCJhdXRoIjpbImN1c3Rfam9uaSJdLCJleHAiOjE2NTY3NDc3OTF9.KBDjAatzh9DjQsyUQJpV_XNNNtu4_ytbDw7ILh09418"
  }
}
```

### Customer All - GET

```
GET v1/customer/all
HEADER Authorization Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjNzM3NzRmYS0yNmI3LTQ1ODct...
```

Body
`none`

Response

```json
{
  "status": true,
  "message": "Success",
  "data": [
    {
      "id": "c8b78fec-1ec9-4223-8121-775323a2adc5",
      "name": "Badang",
      "username": "cust_badang",
      "password": "1234",
      "role": "customer"
    },
    {
      "id": "96ec13bb-0806-4b7b-8384-6213a42c1943",
      "name": "Ojak",
      "username": "ojak",
      "password": "1234",
      "role": "customer"
    }
  ]
}
```

### Customer Info - GET

```
GET v1/customer
HEADER Authorization Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjNzM3NzRmYS0yNmI3LTQ1ODct...
```

Body
`none`

Response

```json
{
  "status": true,
  "message": "Success",
  "data": {
    "id": "96ec13bb-0806-4b7b-8384-6213a42c1943",
    "name": "Ojak",
    "username": "ojak",
    "password": null,
    "role": "customer"
  }
}
```

### Driver

| Name            | Endpoint             | Method | Bearer token | Body and response                  |
|-----------------|----------------------|--------|--------------|------------------------------------|
| Register        | `v1/driver/register` | `POST` | no           | [example](#driver-register---post) |
| Login           | `v1/driver/login`    | `POST` | no           | [example](#driver-login---post)    |
| Get All Driver  | `v1/driver/all`      | `GET`  | yes          | [example](#driver-all---get)       |
| Get Info Driver | `v1/driver`          | `GET`  | yes          | [example](#driver-info---get)      |

### Driver Register - POST

```
POST v1/driver/register
```

Body

```json
{
  "name":"Jupri",
  "username":"driver_jupri",
  "password":"1234"
}
```

Response

```json
{
  "status": true,
  "message": "Success",
  "data": true
}
```

### Driver Login - POST

```
POST v1/driver/login
```

Body

```json
{
  "name":"Jupri",
  "username":"driver_jupri",
  "password":"1234"
}
```

Response

```json
{
  "status": true,
  "message": "Success",
  "data": true
}
```



### Driver All - GET

```
GET v1/driver/all
HEADER Authorization Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjNzM3NzRmYS0yNmI3LTQ1ODct...
```

Body
`none`

Response

```json
{
  "status": true,
  "message": "Success",
  "data": [
    {
      "id": "f1b641d2-4dc8-4aad-958b-ecb421203a0f",
      "name": "Jupri",
      "username": "driver_jupri",
      "password": "1234",
      "role": "driver"
    },
    {
      "id": "6826126d-8129-4978-9728-c81af6587deb",
      "name": "Sap",
      "username": "sapii",
      "password": "1234",
      "role": "driver"
    }
  ]
}
```

### Driver Info - GET

```
GET v1/driver
HEADER Authorization Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjNzM3NzRmYS0yNmI3LTQ1ODct...
```

Body
`none`

Response

```json
{
  "status": true,
  "message": "Success",
  "data": {
    "id": "f1b641d2-4dc8-4aad-958b-ecb421203a0f",
    "name": "Jupri",
    "username": "driver_jupri",
    "password": null,
    "role": "driver"
  }
}
```

