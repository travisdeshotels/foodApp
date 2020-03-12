package tk.codedojo.food.service;

import tk.codedojo.food.beans.Order;
import tk.codedojo.food.exception.InvalidOrderException;
import tk.codedojo.food.exception.OrderException;
import tk.codedojo.food.exception.OrderNotFoundException;

import java.util.List;

public interface OrderService {
    Order addOrder(Order order) throws InvalidOrderException;
    void completeOrder(String orderID) throws OrderNotFoundException;
    List<Order> findAll();
    List<Order> getOpenOrdersByCustomerID(String customerID);
    List<Order> getByCustomerID(String customerID);
    List<Order> getOpenOrdersByRestaurantID(String restaurantID);
    List<Order> getByRestaurantID(String restaurantID);
    void cancelOrder(String orderID) throws OrderNotFoundException, OrderException;
}
