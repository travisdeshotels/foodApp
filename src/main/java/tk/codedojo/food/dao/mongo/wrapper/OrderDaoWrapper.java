package tk.codedojo.food.dao.mongo.wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.codedojo.food.beans.Order;
import tk.codedojo.food.dao.OrderDaoType;
import tk.codedojo.food.dao.mongo.OrderDaoMongo;

import java.util.List;

@Component
public class OrderDaoWrapper implements OrderDaoType {
    private OrderDaoMongo dao;

    @Autowired
    public OrderDaoWrapper(OrderDaoMongo orderDaoMongo){
        this.dao = orderDaoMongo;
    }

    @Override
    public Order findOne(String id) {
        return this.dao.findOne(id);
    }

    @Override
    public List<Order> findAll() {
        return this.dao.findAll();
    }

    @Override
    public void save(Order order) {
        this.dao.save(order);
    }

    @Override
    public List<Order> getOpenOrdersByCustomerID(String id) {
        return this.dao.getOpenOrdersByCustomerID(id);
    }

    @Override
    public List<Order> getByCustomerID(String id) {
        return this.dao.getByCustomerID(id);
    }

    @Override
    public List<Order> getOpenOrdersByRestaurantID(String id) {
        return this.dao.getOpenOrdersByRestaurantID(id);
    }

    @Override
    public List<Order> getByRestaurantID(String id) {
        return this.dao.getByRestaurantID(id);
    }
}
