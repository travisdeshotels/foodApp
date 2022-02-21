package tk.codedojo.food;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Order;
import tk.codedojo.food.beans.OrderItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.mongo.CustomerDaoMongo;
import tk.codedojo.food.dao.mongo.OrderDaoMongo;
import tk.codedojo.food.dao.mongo.RestaurantDaoMongo;
import tk.codedojo.food.exception.InvalidOrderException;
import tk.codedojo.food.exception.OrderNotFoundException;
import tk.codedojo.food.service.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    @Mock
    private CustomerDaoMongo customerDao;
    @Mock
    private RestaurantDaoMongo restaurantDao;
    @Mock
    private OrderDaoMongo orderDao;
    private OrderServiceImpl orderService;

    @Before
    public void setupMock(){
        orderService = new OrderServiceImpl(customerDao, restaurantDao, orderDao);
    }

    @Test
    public void testMockCreation(){
        assertNotNull(customerDao);
        assertNotNull(restaurantDao);
        assertNotNull(orderDao);
    }

    @Test (expected = NullPointerException.class)
    public void testNullFoodItem(){
        MenuItem item = new MenuItem(null, 1d);
    }

    @Test (expected = NullPointerException.class)
    public void testNullPrice(){
        MenuItem item = new MenuItem("fod", null);
    }

    @Test (expected = NullPointerException.class)
    public void testNullMenuItem(){
        OrderItem item = new OrderItem(null, 1);
    }

    @Test (expected = NullPointerException.class)
    public void testNullQuantity(){
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, null);
    }

    @Ignore
    @Test (expected = NullPointerException.class)
    public void testNullCustomerID(){
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", null, "1", orderItems);
    }

    @Ignore
    @Test (expected = NullPointerException.class)
    public void testNullRestaurantID(){
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", null, orderItems);
    }

    @Ignore
    @Test (expected = NullPointerException.class)
    public void testNullItemList(){
        Order order = new Order("1", "1", "1", null);
    }

    @Test
    public void testValidOrder(){
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", "1", orderItems);
    }

    @Test (expected = InvalidOrderException.class)
    public void testInvalidRestaurant() throws InvalidOrderException {
        when(restaurantDao.findById("1")).thenReturn(Optional.empty());
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderService.addOrder(new Order("", "1", "1", orderItems));
    }

    @Test (expected = InvalidOrderException.class)
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
        orderService.addOrder(new Order("", "1", "1", orderItems));
    }

    @Test (expected = InvalidOrderException.class)
    public void testItemNotOnMenu() throws InvalidOrderException {
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);
        when(restaurantDao.findById("1")).thenReturn(Optional.of(
                new Restaurant("1", "this", "12", menuItems)));
        when(customerDao.findById("1")).thenReturn(Optional.of(new Customer(
                "1", "a", "a", "bcad", "p4ssw0rd", null)));
        menuItem = new MenuItem("gumbo", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderService.addOrder(new Order("", "1", "1",orderItems));
    }

    @Test
    public void testItemIsOnMenu() throws InvalidOrderException {
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);
        when(restaurantDao.findById("1")).thenReturn(
                Optional.of(new Restaurant("1", "this", "12", menuItems)));
        when(customerDao.findById("1")).thenReturn(Optional.of(
                new Customer("1", "a", "a", "bcad", "p4ssw0rd", null)));
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderService.addOrder(new Order("", "1", "1", orderItems));
    }

    @Test (expected = OrderNotFoundException.class)
    public void testCompleteNullOrder() throws OrderNotFoundException {
        when(orderDao.findById("1")).thenReturn(Optional.empty());
        orderService.completeOrder("1");
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
