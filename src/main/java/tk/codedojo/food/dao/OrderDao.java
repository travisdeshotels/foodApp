package tk.codedojo.food.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tk.codedojo.food.beans.Order;

import java.util.List;

@Repository
public interface OrderDao extends MongoRepository<Order, String>{
    public List<Order> getByRestaurantID(String id);
    public List<Order> getByCustomerID(String id);

    @Query("{$and: [{customerID: ?0 }, {complete: false}]}")
    public List<Order> getOpenOrdersByCustomerID(String customerID);

    @Query("{$and: [{restaurantID: ?0 }, {complete: false}]}")
    public List<Order> getOpenOrdersByRestaurantID(String customerID);
}
