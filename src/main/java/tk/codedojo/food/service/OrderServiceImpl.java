package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Order;
import tk.codedojo.food.beans.OrderItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.dao.OrderDao;
import tk.codedojo.food.dao.RestaurantDao;
import tk.codedojo.food.exception.*;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private OrderDao orderDao;

    public void completeOrder(String orderID) throws OrderNotFoundException{
        Order order = orderDao.findOne(orderID);
        if (order == null){
            throw new OrderNotFoundException("Cannot complete order, order id invalid!");
        }
        order.setComplete(true);
        orderDao.save(order);
    }

    public void addOrder(Order order) throws InvalidOrderException {
        Restaurant restaurant = restaurantDao.findOne(order.getRestaurantID());
        if(restaurant == null){
            throw new InvalidOrderException("Order does not have a valid restaurant!");
        }
        if(customerDao.findOne(order.getCustomerID()) == null){
            throw new InvalidOrderException("Order does not have a valid customer!");
        }
        order.setComplete(false);
        //check that each item in the order is on the menu
        for (OrderItem item : order.getItems()){
            if (!itemIsOnMenu(item.getMenuItem().getFoodItem(),restaurant.getMenuItems())){
                throw new InvalidOrderException("An item on the order is not on the menu!");
            }
        }
        orderDao.save(order);
    }

    private boolean itemIsOnMenu(String item, List<MenuItem> menuItems){
        for(MenuItem menuItem : menuItems){
            if(!item.equals(menuItem.getFoodItem())){
                return false;
            }
        }
        return true;
    }
}
