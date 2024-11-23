package io.github.travisdeshotels.food.dao;

import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.beans.security.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDaoMongo extends MongoRepository<Customer, String>{
    Customer getCustomerByUserName(String userName);
    Customer getCustomerByEmail(String email);
    Customer getCustomerByRole(Role role);
}
