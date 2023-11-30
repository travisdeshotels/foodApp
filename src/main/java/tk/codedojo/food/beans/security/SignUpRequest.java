package tk.codedojo.food.beans.security;

import lombok.Data;
import tk.codedojo.food.beans.RestaurantInfo;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userName;
    private RestaurantInfo restaurantInfo;
}
