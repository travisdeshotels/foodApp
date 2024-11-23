package io.github.travisdeshotels.food.service;

import io.github.travisdeshotels.food.beans.*;
import io.github.travisdeshotels.food.exception.InvalidOrderException;
import io.github.travisdeshotels.food.exception.OrderException;
import io.github.travisdeshotels.food.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.travisdeshotels.food.dao.CustomerDaoMongo;
import io.github.travisdeshotels.food.dao.OrderDaoMongo;
import io.github.travisdeshotels.food.dao.RestaurantDaoMongo;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private CustomerDaoMongo customerDao;
    private RestaurantDaoMongo restaurantDao;
    private OrderDaoMongo orderDao;

    @Autowired
    public OrderServiceImpl(CustomerDaoMongo customerDao, RestaurantDaoMongo restaurantDao, OrderDaoMongo orderDao){
        this.customerDao = customerDao;
        this.restaurantDao = restaurantDao;
        this.orderDao = orderDao;
    }

    @Override
    public void completeOrder(String orderID) throws OrderNotFoundException {
        Order order = orderDao.findById(orderID).isPresent() ? orderDao.findById(orderID).get() : null;
        if (order == null){
            throw new OrderNotFoundException("Cannot complete order, order id invalid!");
        }
        order.setStatus(OrderStatus.COMPLETE);
        orderDao.save(order);
    }

    @Override
    public List<Order> findAll() {
        return this.orderDao.findAll();
    }

    @Override
    public List<Order> getOpenOrdersByCustomerID(String customerID) {
        return this.orderDao.getOpenOrdersByCustomerID(customerID);
    }

    @Override
    public List<Order> getByCustomerID(String customerID) {
        return this.orderDao.getByCustomerID(customerID);
    }

    @Override
    public List<Order> getOpenOrdersByRestaurantID(String restaurantID) {
        return this.orderDao.getOpenOrdersByRestaurantID(restaurantID);
    }

    @Override
    public List<Order> getByRestaurantID(String restaurantID) {
        return this.orderDao.getByRestaurantID(restaurantID);
    }

    @Override
    public void cancelOrder(String orderID) throws OrderException {
        Order order = orderDao.findById(orderID).isPresent() ? orderDao.findById(orderID).get() : null;
        if (order == null){
            throw new OrderException("Cannot cancel order, order id invalid!");
        } else if (OrderStatus.COMPLETE.equals(order.getStatus())){
            throw new OrderException("Cannot cancel an order that has been completed!");
        }
        if (!OrderStatus.CANCELLED.equals(order.getStatus())) {
            order.setStatus(OrderStatus.CANCELLED);
            orderDao.save(order);
        }
    }

    public Order addOrder(Order order) throws InvalidOrderException {
        validateOrder(order);
        orderDao.save(order);
        return order;
    }

    private void validateOrder(Order order) throws InvalidOrderException {
        Restaurant restaurant = validateRestaurant(order);
        validateCustomer(order);
        validateOrderItems(order, restaurant);
    }

    private void validateCustomer(Order order) throws InvalidOrderException {
        if(!customerDao.findById(order.getCustomerID()).isPresent()){
            throw new InvalidOrderException("Order does not have a valid customer!");
        }
    }

    private Restaurant validateRestaurant(Order order) throws InvalidOrderException {
        Restaurant restaurant = restaurantDao.findById(order.getRestaurantID()).isPresent() ?
                                                       restaurantDao.findById(order.getRestaurantID()).get() : null;
        if(restaurant == null){
            throw new InvalidOrderException("Order does not have a valid restaurant!");
        }
        return restaurant;
    }

    private void validateOrderItems(Order order, Restaurant restaurant) throws InvalidOrderException {
        for (OrderItem item : order.getItems()){
            if (!itemIsOnMenu(item.getMenuItem().getFoodItem(),restaurant.getMenuItems())){
                throw new InvalidOrderException("An item on the order is not on the menu!");
            }
        }
    }

    private boolean itemIsOnMenu(String item, List<MenuItem> menuItems){
        boolean onMenu = false;
        for(MenuItem menuItem : menuItems){
            if (item.equals(menuItem.getFoodItem())) {
                onMenu = true;
                break;
            }
        }
        return onMenu;
    }
}
