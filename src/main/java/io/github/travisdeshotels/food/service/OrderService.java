package io.github.travisdeshotels.food.service;

import io.github.travisdeshotels.food.beans.Order;
import io.github.travisdeshotels.food.exception.InvalidOrderException;
import io.github.travisdeshotels.food.exception.OrderException;
import io.github.travisdeshotels.food.exception.OrderNotFoundException;

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
