package tk.codedojo.food.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tk.codedojo.food.beans.Customer;

@Repository
public interface CustomerDaoMongo extends MongoRepository<Customer, String>{
    Customer getCustomerByUserName(String userName);
}
