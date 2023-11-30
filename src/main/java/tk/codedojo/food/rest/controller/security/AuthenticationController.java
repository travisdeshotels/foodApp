package tk.codedojo.food.rest.controller.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.beans.security.JwtAuthenticationResponse;
import tk.codedojo.food.beans.security.RefreshTokenRequest;
import tk.codedojo.food.beans.security.SignUpRequest;
import tk.codedojo.food.beans.security.SignInRequest;
import tk.codedojo.food.exception.RestaurantException;
import tk.codedojo.food.service.security.AuthenticationService;

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
