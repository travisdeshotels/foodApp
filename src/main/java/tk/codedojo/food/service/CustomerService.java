package tk.codedojo.food.service;

import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.exception.UserNameException;


public interface CustomerService {
    boolean addCustomer(Customer c) throws UserNameException;
    boolean usernameInUse(String username);
}
