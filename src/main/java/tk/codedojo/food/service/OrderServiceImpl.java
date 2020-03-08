package tk.codedojo.food.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.*;
import tk.codedojo.food.dao.mongo.CustomerDaoMongo;
import tk.codedojo.food.dao.mongo.OrderDaoMongo;
import tk.codedojo.food.dao.mongo.RestaurantDaoMongo;
import tk.codedojo.food.exception.*;

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
    public void completeOrder(String orderID) throws OrderNotFoundException{
        Order order = orderDao.findOne(orderID);
        if (order == null){
            throw new OrderNotFoundException("Cannot complete order, order id invalid!");
        }
        order.setComplete(true);
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
    public void cancelOrder(String orderID) throws OrderNotFoundException {
        Order order = orderDao.findOne(orderID);
        if (order == null){
            throw new OrderNotFoundException("Cannot complete order, order id invalid!");
        }
        order.setCancelled(true);
        orderDao.save(order);
    }

    public void addOrder(Order order) throws InvalidOrderException {
        validateOrder(order);
        order.setComplete(false);
        orderDao.save(order);
    }

    private void validateOrder(Order order) throws InvalidOrderException {
        Restaurant restaurant = validateRestaurant(order);
        validateCustomer(order);
        validateOrderItems(order, restaurant);
    }

    private void validateCustomer(Order order) throws InvalidOrderException {
        if(customerDao.findOne(order.getCustomerID()) == null){
            throw new InvalidOrderException("Order does not have a valid customer!");
        }
    }

    private Restaurant validateRestaurant(Order order) throws InvalidOrderException {
        Restaurant restaurant = restaurantDao.findOne(order.getRestaurantID());
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
