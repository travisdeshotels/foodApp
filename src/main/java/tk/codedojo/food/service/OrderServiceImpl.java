package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Order;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.dao.OrderDao;
import tk.codedojo.food.dao.RestaurantDao;
import tk.codedojo.food.exception.CustomerNotFoundException;
import tk.codedojo.food.exception.RestaurantNotFoundException;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    OrderDao orderDao;

    public void addOrder(Order order) throws Exception{
        System.out.println(order.getRestaurantID());
        System.out.println(order.getCustomerID());
        Restaurant restaurant = restaurantDao.findOne(order.getRestaurantID());
        if(restaurant == null){
            throw new RestaurantNotFoundException("Order does not have a valid restaurant!");
        }
        if(customerDao.findOne(order.getCustomerID()) == null){
            throw new CustomerNotFoundException("Order does not have a valid customer!");
        }
        if(order.getItems() == null){
            throw new NullPointerException();
        }
        //TODO verify that the items being ordered are on the menu
        orderDao.save(order);
    }

    private boolean itemIsOnMenu(String item, List<MenuItem> menuItems){
        //TODO implement this
        return false;
    }
}
