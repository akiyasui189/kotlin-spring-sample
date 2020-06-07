kotlin-spring-sample
===

Sample Application of Kotlin Using Spring Boot


Hello World
---

```
curl -kv 'http://localhost:8080/hello/world'
```

User Controller
---

### create user

```
$ curl -kv -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' -d '{"account": "username", "email": "akiyasui47@gmail.com", "password": "Password1", "confirmPassword": "Password1"}' 'http://localhost:8080/users/create' | jq
```

### get users

```
$ curl -kv -X GET -H 'Accept: application/json' -H 'Content-Type: application/json' 'http://localhost:8080/users/list'
```

