package io.github.travisdeshotels.food.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    String token;
    String refreshToken;
}
