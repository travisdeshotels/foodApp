package io.github.travisdeshotels.food.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.exception.CustomerException;

import java.util.List;

public interface CustomerService {
//    void addCustomer(Customer c) throws UserNameException;
    boolean usernameInUse(String username);
    void updateCustomer(Customer c) throws CustomerException;
    UserDetailsService userDetailsService();
    List<Customer> findAll();
    Customer findOne(String id);
    Customer GetByUserName(String userName);
}
