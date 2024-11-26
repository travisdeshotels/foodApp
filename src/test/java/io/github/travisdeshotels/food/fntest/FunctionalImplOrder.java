package io.github.travisdeshotels.food.fntest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.beans.MenuItem;
import io.github.travisdeshotels.food.beans.Order;
import io.github.travisdeshotels.food.beans.OrderItem;
import io.github.travisdeshotels.food.constants.FoodAppTestConstants;
import lombok.Getter;
import lombok.Setter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.github.travisdeshotels.food.util.RestUtil.*;

@Getter
@Setter
public class FunctionalImplOrder {
    private String orderId;
    private int responseCode = -1;

    public void cancelOrder(String userName) throws Exception{
        String orderID = this.orderId;
        URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/order/id/" + orderID);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Content-Type", "application/json");
        this.responseCode = conn.getResponseCode();
    }

    public void completeOrder(String userName) throws Exception{
        String orderID = this.orderId;
        String url = "http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/order/id/" + orderID;
        this.responseCode = putData(url, "");
    }

    public void addOrder(String userName) throws Exception{
        Order order = setOrderData(userName);
        URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/order");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(order));
//        int responseCode = postData(url, mapper.writeValueAsString(order), true);
        int responseCode = postData(url, mapper.writeValueAsString(order));

        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    public void customOrder(int quantity, String item, Double price) throws Exception {
        Order order = setOrderData("test01", Collections.singletonList(
                new OrderItem(new MenuItem(item, price), quantity)
        ));
        URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/order");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(order));
//        int responseCode = postData(url, mapper.writeValueAsString(order), true);
        int responseCode = postData(url, mapper.writeValueAsString(order));

        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
        this.setResponseCode(responseCode);
    }

    private Order setOrderData(String userName) throws Exception {
        return setOrderData(userName, Collections.singletonList(
                new OrderItem(new MenuItem("sandwich", 1.0D), 1))
        );
    }

    private Order setOrderData(String userName, List<OrderItem> items) throws Exception {
        Order order = new Order();
        order.setCustomerID(this.getCustomerID(userName));
        order.setRestaurantID(this.getRestaurantID("test01"));
        order.setItems(items);

        return order;
    }

    private String getRestaurantID(String name) throws Exception {
//        return getRestaurant(name).getId();
        return null;
    }

    private String getCustomerID(String userName) throws Exception {
        List<Customer> customers = this.getFilteredCustomers("?userName=" + userName);
        return customers.get(0).getId();
    }

    public int getOrderCount(String userName) throws Exception{
        URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/order/customer/" +
                getCustomerID(userName));
        ObjectMapper mapper = new ObjectMapper();
        Order[] orders = mapper.readValue(url, Order[].class);

        return orders.length;
    }

    private List<Customer> getFilteredCustomers(String filterString) throws Exception {
        URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/customer" + filterString);
        ObjectMapper mapper = new ObjectMapper();
        List<Customer> customers = Arrays.asList(mapper.readValue(url, Customer[].class));
        List<Customer> customersWithoutNulls = customers.parallelStream().filter(
                Objects::nonNull).collect(Collectors.toList());
        if(!customersWithoutNulls.isEmpty()){
            System.out.println(customersWithoutNulls.get(0).toString());
        }

        return customersWithoutNulls;
    }

    public boolean customerExists(String name) throws Exception {
        return !this.getFilteredCustomers("?userName=" + name).isEmpty();
    }

}
