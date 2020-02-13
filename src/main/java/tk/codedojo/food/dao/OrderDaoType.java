package tk.codedojo.food.dao;

import tk.codedojo.food.beans.Order;

import java.util.List;

public interface OrderDaoType {
    Order findOne(String id);
    List<Order> findAll();
    void save(Order order);
    List<Order> getOpenOrdersByCustomerID(String id);
    List<Order> getByCustomerID(String id);
    List<Order> getOpenOrdersByRestaurantID(String id);
    List<Order> getByRestaurantID(String id);
}
