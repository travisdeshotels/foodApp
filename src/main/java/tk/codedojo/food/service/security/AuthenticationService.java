package tk.codedojo.food.service.security;

import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.beans.security.JwtAuthenticationResponse;
import tk.codedojo.food.beans.security.RefreshTokenRequest;
import tk.codedojo.food.beans.security.SignUpRequest;
import tk.codedojo.food.beans.security.SignInRequest;
import tk.codedojo.food.exception.RestaurantException;

public interface AuthenticationService {
    Customer signup(SignUpRequest request) throws RestaurantException;
    JwtAuthenticationResponse signin(SignInRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);
}
