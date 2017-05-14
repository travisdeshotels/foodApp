package tk.codedojo.food.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.codedojo.food.beans.Order;
import tk.codedojo.food.dao.OrderDao;
import tk.codedojo.food.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/food/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDao orderDao;

    @RequestMapping(method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrders(){
        return orderDao.findAll();
    }

    @RequestMapping(method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrdersForCustomer(String customerID){
        return orderDao.getByCustomerID(customerID);
    }

    @RequestMapping(method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrdersForRestaurant(String restaurantID){
        return orderDao.getByRestaurantID(restaurantID);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<String> addOrder(@RequestBody Order order){
        try{
            orderService.addOrder(order);
        } catch(Exception e){
            //TODO log exception
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
