package tk.codedojo.food.dao;

import tk.codedojo.food.beans.Customer;

import java.util.List;

public interface CustomerDaoType {
    Customer getCustomerByUserName(String userName);
    void save(Customer customer);
    List<Customer> findAll();
    Customer findOne(String id);
}
