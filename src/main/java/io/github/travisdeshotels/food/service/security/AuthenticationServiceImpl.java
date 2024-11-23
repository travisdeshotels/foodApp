package io.github.travisdeshotels.food.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.beans.Restaurant;
import io.github.travisdeshotels.food.beans.security.JwtAuthenticationResponse;
import io.github.travisdeshotels.food.beans.security.RefreshTokenRequest;
import io.github.travisdeshotels.food.beans.security.Role;
import io.github.travisdeshotels.food.beans.security.SignUpRequest;
import io.github.travisdeshotels.food.beans.security.SignInRequest;
import io.github.travisdeshotels.food.dao.CustomerDaoMongo;
import io.github.travisdeshotels.food.exception.RestaurantException;
import io.github.travisdeshotels.food.service.RestaurantService;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final CustomerDaoMongo dao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final RestaurantService restaurantService;

    @Override
    public Customer signup(SignUpRequest request) throws RestaurantException {
        Customer customer = new Customer();
        customer.setRole(Role.USER);
        String restaurantId = null;
        if (request.getRestaurantInfo() != null){
            restaurantId = restaurantService.addRestaurant(new Restaurant(request.getRestaurantInfo()));
            customer.setRole(Role.OWNER);
        }
        customer.setEmail(request.getEmail());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setUserName(request.getUserName());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setRestaurantId(restaurantId);

        return dao.save(customer);
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));

        Customer customer = dao.getCustomerByEmail(request.getEmail());
        String jwt = jwtService.generateToken(customer);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), customer);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);
        response.setRefreshToken(refreshToken);

        return response;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request){
        String userEmail = jwtService.extractUserName(request.getToken());
        Customer customer = dao.getCustomerByEmail(userEmail);
        if(jwtService.isTokenValid(request.getToken(), customer)){
            String jwt = jwtService.generateToken(customer);

            JwtAuthenticationResponse response = new JwtAuthenticationResponse();
            response.setToken(jwt);
            response.setRefreshToken(request.getToken());
            return response;
        }
        return null;
    }
}
