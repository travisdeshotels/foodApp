package tk.codedojo.food.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tk.codedojo.food.beans.Order;

@Repository
public interface OrderDao extends MongoRepository<Order, String>{

}
