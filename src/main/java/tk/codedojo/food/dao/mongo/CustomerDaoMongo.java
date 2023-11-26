package tk.codedojo.food.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.beans.Role;

@Repository
public interface CustomerDaoMongo extends MongoRepository<Customer, String>{
    Customer getCustomerByUserName(String userName);
    Customer getCustomerByEmail(String email);
    Customer getCustomerByRole(Role role);
}
