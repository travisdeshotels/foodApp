package tk.codedojo.food.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tk.codedojo.food.beans.Order;

import java.util.List;

@Repository
public interface OrderDaoMongo extends MongoRepository<Order, String>{
    List<Order> getByRestaurantID(String id);
    List<Order> getByCustomerID(String id);

    @Query("{$and: [{customerID: ?0 }, {status: 'OPEN'}]}")
    List<Order> getOpenOrdersByCustomerID(String customerID);

    @Query("{$and: [{restaurantID: ?0 }, {status: 'OPEN'}]}")
    List<Order> getOpenOrdersByRestaurantID(String customerID);
}
