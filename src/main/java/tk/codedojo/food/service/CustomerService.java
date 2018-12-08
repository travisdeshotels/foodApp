package tk.codedojo.food.service;

import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.exception.CustomerException;
import tk.codedojo.food.exception.UserNameException;

import java.util.List;

public interface CustomerService {
    void addCustomer(Customer c) throws UserNameException;
    boolean usernameInUse(String username);
    void updateCustomer(Customer c) throws CustomerException;
    List<Customer> findAll();
}
