package io.github.travisdeshotels.food.beans.security;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
