package tk.codedojo.food;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tk.codedojo.food.beans.*;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.dao.*;
import tk.codedojo.food.exception.InvalidOrderException;
import tk.codedojo.food.exception.OrderNotFoundException;
import tk.codedojo.food.service.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    @Mock
    private CustomerDao customerDao;
    @Mock
    private RestaurantDao restaurantDao;
    @Mock
    private OrderDao orderDao;
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

    @Test (expected = NullPointerException.class)
    public void testNullCustomerID(){
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", null, "1", false, orderItems);
    }

    @Test (expected = NullPointerException.class)
    public void testNullRestaurantID(){
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", null, false, orderItems);
    }

    @Test (expected = NullPointerException.class)
    public void testNullItemList(){
        Order order = new Order("1", "1", "1", false, null);
    }

    @Test
    public void testValidOrder(){
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", "1", false, orderItems);
    }

    @Test (expected = InvalidOrderException.class)
    public void testInvalidRestaurant() throws InvalidOrderException {
        when(restaurantDao.findOne("1")).thenReturn(null);
        when(customerDao.findOne("1")).thenReturn(new Customer("1", "a", "a", "abcde", "p4ssw0rd", null));
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderService.addOrder(new Order("", "1", "1", false, orderItems));
    }

    @Test (expected = InvalidOrderException.class)
    public void testInvalidCustomer() throws InvalidOrderException {
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);
        when(restaurantDao.findOne("1")).thenReturn(
                new Restaurant("1", "this", "12", menuItems));
        when(customerDao.findOne("1")).thenReturn(null);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderService.addOrder(new Order("", "1", "1", false, orderItems));
    }

    @Test (expected = InvalidOrderException.class)
    public void testItemNotOnMenu() throws InvalidOrderException {
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);
        when(restaurantDao.findOne("1")).thenReturn(
                new Restaurant("1", "this", "12", menuItems));
        when(customerDao.findOne("1")).thenReturn(new Customer("1", "a", "a", "bcad", "p4ssw0rd", null));
        menuItem = new MenuItem("gumbo", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderService.addOrder(new Order("", "1", "1", false, orderItems));
    }

    @Test
    public void testItemIsOnMenu() throws InvalidOrderException {
        MenuItem menuItem = new MenuItem("chaudin", 2d);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);
        when(restaurantDao.findOne("1")).thenReturn(
                new Restaurant("1", "this", "12", menuItems));
        when(customerDao.findOne("1")).thenReturn(new Customer("1", "a", "a", "bcad", "p4ssw0rd", null));
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderService.addOrder(new Order("", "1", "1", false, orderItems));
    }

    @Test (expected = OrderNotFoundException.class)
    public void testCompleteNullOrder() throws OrderNotFoundException {
        when(orderDao.findOne("1")).thenReturn(null);
        orderService.completeOrder("1");
    }

    @Test
    public void testCompleteValidOrder() throws OrderNotFoundException {
        MenuItem menuItem = new MenuItem("food", 1d);
        OrderItem orderItem = new OrderItem(menuItem, 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", "1", false, orderItems);
        when(orderDao.findOne("1")).thenReturn(order);
        orderService.completeOrder("1");
    }
}
