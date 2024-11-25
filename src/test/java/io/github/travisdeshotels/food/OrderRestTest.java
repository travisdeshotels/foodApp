package io.github.travisdeshotels.food;

import io.github.travisdeshotels.food.beans.MenuItem;
import io.github.travisdeshotels.food.beans.Order;
import io.github.travisdeshotels.food.beans.OrderItem;
import io.github.travisdeshotels.food.dao.CustomerDaoMongo;
import io.github.travisdeshotels.food.rest.controller.OrderController;
import io.github.travisdeshotels.food.service.CustomerService;
import io.github.travisdeshotels.food.service.OrderService;
import io.github.travisdeshotels.food.service.security.JWTService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import io.github.travisdeshotels.food.exception.InvalidOrderException;
import io.github.travisdeshotels.food.exception.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderController.class)
public class OrderRestTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @Autowired
    private OrderController orderController;
    @MockBean
    private CustomerDaoMongo customerDaoMongo;
    @MockBean
    private JWTService jwtService;
    @MockBean
    private CustomerService customerService;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Disabled
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

    @Disabled
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

    @Disabled
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

    @Disabled
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

    @Disabled
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

    @Disabled
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

    @Disabled
    @Test
    public void testAddInvalidOrderException() throws Exception {
        doThrow(new InvalidOrderException("")).when(orderService).addOrder(any());
        MvcResult mvcResult = this.mockMvc.perform(post(
                "/api/food/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"customerID\":\"1\",\"restaurantID\":\"1\",\"status\":\"OPEN\",\"items\":[{\"menuItem\":{\"foodItem\":\"Boudin\",\"price\":2.0},\"quantity\":1}]}")).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Disabled
    @Test
    public void testAddOrderException() throws Exception {
        doThrow(new NullPointerException("")).when(orderService).addOrder(any());
        MvcResult mvcResult = this.mockMvc.perform(post(
                "/api/food/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"customerID\":\"1\",\"restaurantID\":\"1\",\"status\":\"OPEN\",\"items\":[{\"menuItem\":{\"foodItem\":\"Boudin\",\"price\":2.0},\"quantity\":1}]}")).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }

    @Disabled
    @Test
    public void testCompleteOrder() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(put(
                "/api/food/order/id/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Disabled
    @Test
    public void testOrderNotFound() throws Exception {
        doThrow(new OrderNotFoundException("")).when(orderService).completeOrder("1");
        MvcResult mvcResult = this.mockMvc.perform(put(
                "/api/food/order/id/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 404);
    }

    @Disabled
    @Test
    public void testCompleteOrderException() throws Exception {
        doThrow(new NullPointerException("")).when(orderService).completeOrder("1");
        MvcResult mvcResult = this.mockMvc.perform(put(
                "/api/food/order/id/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }
}
