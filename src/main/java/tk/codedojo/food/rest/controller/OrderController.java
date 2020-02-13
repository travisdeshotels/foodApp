package tk.codedojo.food.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.codedojo.food.beans.Order;
import tk.codedojo.food.dao.fake.CustomerDaoFake;
import tk.codedojo.food.dao.fake.OrderDaoFake;
import tk.codedojo.food.dao.fake.RestaurantDaoFake;
import tk.codedojo.food.exception.InvalidOrderException;
import tk.codedojo.food.exception.OrderNotFoundException;
import tk.codedojo.food.service.OrderService;
import tk.codedojo.food.service.OrderServiceImpl;

import java.util.List;

import static tk.codedojo.food.beans.FoodConstants.API_URL;

@RestController
@RequestMapping(API_URL + "/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(){
        this.orderService = new OrderServiceImpl(new CustomerDaoFake(), new RestaurantDaoFake(), new OrderDaoFake());
    }

    @RequestMapping(method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrders(){
        return orderService.findAll();
    }

    @RequestMapping(method=RequestMethod.GET, value="/customer/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOpenOrdersForCustomer(@PathVariable("id") String customerID){
        return orderService.getOpenOrdersByCustomerID(customerID);
    }

    @RequestMapping(method=RequestMethod.GET, value="/customer/all/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getAllOrdersForCustomer(@PathVariable("id") String customerID){
        return orderService.getByCustomerID(customerID);
    }

    @RequestMapping(method=RequestMethod.GET, value="/restaurant/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOpenOrdersForRestaurant(@PathVariable("id") String restaurantID){
        return orderService.getOpenOrdersByRestaurantID(restaurantID);
    }

    @RequestMapping(method=RequestMethod.GET, value="/restaurant/all/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getAllOrdersForRestaurant(@PathVariable("id") String restaurantID){
        return orderService.getByRestaurantID(restaurantID);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<String> addOrder(@RequestBody Order order){
        Logger log = LoggerFactory.getLogger(OrderController.class.getName());
        try {
            orderService.addOrder(order);
        } catch(InvalidOrderException e){
                log.error("InvalidOrderException", e);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            log.error("", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.trace("Order added: " + order.toString());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/id/{id}")
    public ResponseEntity<String> completeOrder(@PathVariable("id") String orderID){
        Logger log = LoggerFactory.getLogger(OrderController.class.getName());
        try {
            orderService.completeOrder(orderID);
        } catch(OrderNotFoundException e){
            log.error("OrderNotFoundException", e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            log.error("", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.trace("Completed order " + orderID);
        return new ResponseEntity(HttpStatus.OK);
    }
}
