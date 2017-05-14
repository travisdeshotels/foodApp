package tk.codedojo.food.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tk.codedojo.food.beans.Order;

import java.util.List;

@Repository
public interface OrderDao extends MongoRepository<Order, String>{
    public List<Order> getByRestaurantID(String id);
    public List<Order> getByCustomerID(String id);
}
