package io.github.travisdeshotels.food.beans.security;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
