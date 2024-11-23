package io.github.travisdeshotels.food.beans.security;

import lombok.Data;
import io.github.travisdeshotels.food.beans.RestaurantInfo;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userName;
    private RestaurantInfo restaurantInfo;
}
