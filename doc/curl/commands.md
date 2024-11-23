# Sign in
```shell
curl -v -d "@signin.json" -H "Content-Type: application/json" http://localhost:8080/api/auth/signin | jq
```

# Admin dump
```shell
curl -v -H "Authorization: Bearer $TOKEN" localhost:8080/api/admin | jq
```

# Sign up
```shell
curl -v -d "@signup.json" -H "Content-Type: application/json" http://localhost:8080/api/auth/signup
```
