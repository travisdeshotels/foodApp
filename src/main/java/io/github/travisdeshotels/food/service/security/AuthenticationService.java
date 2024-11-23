package io.github.travisdeshotels.food.service.security;

import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.beans.security.JwtAuthenticationResponse;
import io.github.travisdeshotels.food.beans.security.RefreshTokenRequest;
import io.github.travisdeshotels.food.beans.security.SignUpRequest;
import io.github.travisdeshotels.food.beans.security.SignInRequest;
import io.github.travisdeshotels.food.exception.RestaurantException;

public interface AuthenticationService {
    Customer signup(SignUpRequest request) throws RestaurantException;
    JwtAuthenticationResponse signin(SignInRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);
}
