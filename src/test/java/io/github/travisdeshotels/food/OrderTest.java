package io.github.travisdeshotels.food;

import io.github.travisdeshotels.food.beans.security.Role;
import io.github.travisdeshotels.food.dao.CustomerDaoMongo;
import io.github.travisdeshotels.food.dao.OrderDaoMongo;
import io.github.travisdeshotels.food.dao.RestaurantDaoMongo;
import io.github.travisdeshotels.food.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.beans.MenuItem;
import io.github.travisdeshotels.food.beans.Order;
import io.github.travisdeshotels.food.beans.OrderItem;
import io.github.travisdeshotels.food.beans.Restaurant;
import io.github.travisdeshotels.food.exception.InvalidOrderException;
import io.github.travisdeshotels.food.exception.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @Mock
    private CustomerDaoMongo customerDao;
    @Mock
    private RestaurantDaoMongo restaurantDao;
    @Mock
    private OrderDaoMongo orderDao;
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setupMock(){
        orderService = new OrderServiceImpl(customerDao, restaurantDao, orderDao);
    }

    @Test
    public void testNullFoodItem(){
        assertThrows(NullPointerException.class, () -> new MenuItem(null, 1d));
    }

    @Test
    public void testNullPrice(){
        assertThrows(NullPointerException.class, () ->  new MenuItem("fod", null));
    }

    @Test
    public void testNullMenuItem(){
        assertThrows(NullPointerException.class, () -> new OrderItem(null, 1));
    }

    @Test
    public void testNullQuantity(){
        MenuItem menuItem = new MenuItem("food", 1d);
        assertThrows(NullPointerException.class, () -> new OrderItem(menuItem, null));
    }

    @Disabled
    @Test
    public void testNullCustomerID(){
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        assertThrows(NullPointerException.class, () -> new Order("1", null, "1", orderItems));
    }

    @Disabled
    @Test
    public void testNullRestaurantID(){
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        assertThrows(NullPointerException.class, () -> new Order("1", "1", null, orderItems));
    }

    @Disabled
    @Test
    public void testNullItemList(){
        assertThrows(NullPointerException.class, () -> new Order("1", "1", "1", null));
    }

    @Test
    public void testValidOrder(){
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        new Order("1", "1", "1", orderItems);
    }

    @Test
    public void testInvalidRestaurant() throws InvalidOrderException {
        when(restaurantDao.findById("1")).thenReturn(Optional.empty());
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        assertThrows(InvalidOrderException.class, () ->
                orderService.addOrder(new Order("", "1", "1", orderItems)));
    }

    @Test
    public void testInvalidCustomer() throws InvalidOrderException {
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);
        when(restaurantDao.findById("1")).thenReturn(
                Optional.of(new Restaurant("1", "this", "12", menuItems)));
        when(customerDao.findById("1")).thenReturn(Optional.empty());
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        assertThrows(InvalidOrderException.class, () ->
                orderService.addOrder(new Order("", "1", "1", orderItems)));
    }

    @Test
    public void testItemNotOnMenu(){
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);
        when(restaurantDao.findById("1")).thenReturn(Optional.of(
                new Restaurant("1", "this", "12", menuItems)));
        when(customerDao.findById("1")).thenReturn(Optional.of(new Customer(
                "1", "a", "a", "bcad", "p4ssw0rd", "me@gov.gov", Role.USER, null)));
        menuItem = new MenuItem("gumbo", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        assertThrows(InvalidOrderException.class, () ->
                orderService.addOrder(new Order("", "1", "1",orderItems)));
    }

    @Test
    public void testItemIsOnMenu() throws InvalidOrderException {
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);
        when(restaurantDao.findById("1")).thenReturn(
                Optional.of(new Restaurant("1", "this", "12", menuItems)));
        when(customerDao.findById("1")).thenReturn(Optional.of(
                new Customer("1", "a", "a", "bcad", "p4ssw0rd", "me@gov.gov", Role.USER, null)));
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderService.addOrder(new Order("", "1", "1", orderItems));
    }

    @Test
    public void testCompleteNullOrder(){
        when(orderDao.findById("1")).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () ->
                orderService.completeOrder("1"));

    }

    @Test
    public void testCompleteValidOrder() throws OrderNotFoundException {
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", "1", orderItems);
        when(orderDao.findById("1")).thenReturn(Optional.of(order));
        orderService.completeOrder("1");
    }
}
