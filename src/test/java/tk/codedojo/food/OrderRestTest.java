package tk.codedojo.food;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tk.codedojo.food.beans.*;
import tk.codedojo.food.exception.InvalidOrderException;
import tk.codedojo.food.exception.OrderNotFoundException;
import tk.codedojo.food.rest.controller.OrderController;
import tk.codedojo.food.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class OrderRestTest {
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testGetOrders() throws Exception {
        OrderItem orderItem = new OrderItem(new MenuItem("Boudin", 2d), 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", "1", orderItems);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderService.findAll()).thenReturn(orders);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/food/order")).andReturn();
        String expected = "[{\"id\":\"1\",\"customerID\":\"1\",\"restaurantID\":\"1\",\"status\":\"OPEN\",\"items\":[{\"menuItem\":{\"foodItem\":\"Boudin\",\"price\":2.0},\"quantity\":1}]}]";
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetOrdersByCustomer() throws Exception {
        OrderItem orderItem = new OrderItem(new MenuItem("Boudin", 2d), 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", "1", orderItems);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderService.getByCustomerID("1")).thenReturn(orders);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/food/order/customer/all/{id}", "1")).andReturn();
        String expected = "[{\"id\":\"1\",\"customerID\":\"1\",\"restaurantID\":\"1\",\"status\":\"OPEN\",\"items\":[{\"menuItem\":{\"foodItem\":\"Boudin\",\"price\":2.0},\"quantity\":1}]}]";
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetOpenOrdersByCustomer() throws Exception {
        OrderItem orderItem = new OrderItem(new MenuItem("Boudin", 2d), 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", "1", orderItems);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderService.getOpenOrdersByCustomerID("1")).thenReturn(orders);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/food/order/customer/{id}", "1")).andReturn();
        String expected = "[{\"id\":\"1\",\"customerID\":\"1\",\"restaurantID\":\"1\",\"status\":\"OPEN\",\"items\":[{\"menuItem\":{\"foodItem\":\"Boudin\",\"price\":2.0},\"quantity\":1}]}]";
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetOrdersByRestaurant() throws Exception {
        OrderItem orderItem = new OrderItem(new MenuItem("Boudin", 2d), 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", "1", orderItems);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderService.getByRestaurantID("1")).thenReturn(orders);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/food/order/restaurant/all/{id}", "1")).andReturn();
        String expected = "[{\"id\":\"1\",\"customerID\":\"1\",\"restaurantID\":\"1\",\"status\":\"OPEN\",\"items\":[{\"menuItem\":{\"foodItem\":\"Boudin\",\"price\":2.0},\"quantity\":1}]}]";
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetOpenOrdersByRestaurant() throws Exception {
        OrderItem orderItem = new OrderItem(new MenuItem("Boudin", 2d), 1);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = new Order("1", "1", "1", orderItems);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderService.getOpenOrdersByRestaurantID("1")).thenReturn(orders);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/food/order/restaurant/{id}", "1")).andReturn();
        String expected = "[{\"id\":\"1\",\"customerID\":\"1\",\"restaurantID\":\"1\",\"status\":\"OPEN\",\"items\":[{\"menuItem\":{\"foodItem\":\"Boudin\",\"price\":2.0},\"quantity\":1}]}]";
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void testAddOrder() throws Exception {
        Order order = new Order();
        order.setId("2");
        doReturn(order).when(orderService).addOrder(any());
        MvcResult mvcResult = this.mockMvc.perform(post(
                "/api/food/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"customerID\":\"1\",\"restaurantID\":\"1\",\"status\":\"OPEN\",\"items\":[{\"menuItem\":{\"foodItem\":\"Boudin\",\"price\":2.0},\"quantity\":1}]}")).andReturn();
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testAddInvalidOrderException() throws Exception {
        doThrow(new InvalidOrderException("")).when(orderService).addOrder(any());
        MvcResult mvcResult = this.mockMvc.perform(post(
                "/api/food/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"customerID\":\"1\",\"restaurantID\":\"1\",\"status\":\"OPEN\",\"items\":[{\"menuItem\":{\"foodItem\":\"Boudin\",\"price\":2.0},\"quantity\":1}]}")).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testAddOrderException() throws Exception {
        doThrow(new NullPointerException("")).when(orderService).addOrder(any());
        MvcResult mvcResult = this.mockMvc.perform(post(
                "/api/food/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"customerID\":\"1\",\"restaurantID\":\"1\",\"status\":\"OPEN\",\"items\":[{\"menuItem\":{\"foodItem\":\"Boudin\",\"price\":2.0},\"quantity\":1}]}")).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }

    @Test
    public void testCompleteOrder() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(put(
                "/api/food/order/id/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    public void testOrderNotFound() throws Exception {
        doThrow(new OrderNotFoundException("")).when(orderService).completeOrder("1");
        MvcResult mvcResult = this.mockMvc.perform(put(
                "/api/food/order/id/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 404);
    }

    @Test
    public void testCompleteOrderException() throws Exception {
        doThrow(new NullPointerException("")).when(orderService).completeOrder("1");
        MvcResult mvcResult = this.mockMvc.perform(put(
                "/api/food/order/id/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }
}
