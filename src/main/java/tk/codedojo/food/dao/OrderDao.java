package tk.codedojo.food.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tk.codedojo.food.beans.Order;

import java.util.List;

@Repository
public interface OrderDao extends MongoRepository<Order, String>{
    List<Order> getByRestaurantID(String id);
    List<Order> getByCustomerID(String id);

    @Query("{$and: [{customerID: ?0 }, {complete: false}]}")
    List<Order> getOpenOrdersByCustomerID(String customerID);

    @Query("{$and: [{restaurantID: ?0 }, {complete: false}]}")
    List<Order> getOpenOrdersByRestaurantID(String customerID);
}
