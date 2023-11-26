package tk.codedojo.food.service;

import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.beans.SignUpRequest;

public interface AuthenticationService {
    Customer signup(SignUpRequest request);
}
