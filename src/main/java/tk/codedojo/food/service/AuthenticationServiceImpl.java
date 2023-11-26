package tk.codedojo.food.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.beans.Role;
import tk.codedojo.food.beans.SignUpRequest;
import tk.codedojo.food.dao.mongo.CustomerDaoMongo;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final CustomerDaoMongo dao;
    private final PasswordEncoder passwordEncoder;

    public Customer signup(SignUpRequest request){
        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setUserName(request.getUserName());
        customer.setRole(Role.USER);
        customer.setPassword(passwordEncoder.encode(request.getPassword()));

        return dao.save(customer);
    }
}
