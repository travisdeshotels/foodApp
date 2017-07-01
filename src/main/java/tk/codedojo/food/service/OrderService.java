package tk.codedojo.food.service;

import tk.codedojo.food.beans.Order;
import tk.codedojo.food.exception.InvalidOrderException;
import tk.codedojo.food.exception.OrderNotFoundException;

public interface OrderService {
    void addOrder(Order order) throws InvalidOrderException;
    void completeOrder(String orderID) throws OrderNotFoundException;
}
