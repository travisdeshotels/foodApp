# Sign in
```shell
TOKEN=$(curl -v -d "@signinadmin.json" -H "Content-Type: application/json" http://localhost:8080/api/auth/signin | jq -r '.token')
```

# Admin dump
```shell
curl -v -H "Authorization: Bearer $TOKEN" localhost:8080/api/admin | jq
```

# Sign up
```shell
curl -v -d "@signup.json" -H "Content-Type: application/json" http://localhost:8080/api/auth/signup
```

# Update menu
```shell
curl -v -d "@updatemenu.json" -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" \
-X PUT http://localhost:8080/api/food/restaurant/id/$R_ID
```
