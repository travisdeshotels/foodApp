package tk.codedojo.food.service;

import tk.codedojo.food.beans.Order;

public interface OrderService {
    boolean addOrder(Order order) throws Exception;
}
