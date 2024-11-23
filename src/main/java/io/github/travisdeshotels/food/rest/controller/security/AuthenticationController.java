package io.github.travisdeshotels.food.rest.controller.security;

import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.beans.security.JwtAuthenticationResponse;
import io.github.travisdeshotels.food.beans.security.RefreshTokenRequest;
import io.github.travisdeshotels.food.beans.security.SignInRequest;
import io.github.travisdeshotels.food.beans.security.SignUpRequest;
import io.github.travisdeshotels.food.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.travisdeshotels.food.exception.RestaurantException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<Customer> signup(@RequestBody SignUpRequest request){
        try {
            return ResponseEntity.ok(service.signup(request));
        } catch (RestaurantException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request){
        return ResponseEntity.ok(service.signin(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(service.refreshToken(request));
    }
}
