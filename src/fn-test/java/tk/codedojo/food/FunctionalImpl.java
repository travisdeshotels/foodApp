package tk.codedojo.food;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import tk.codedojo.food.beans.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class FunctionalImpl {
    @Getter @Setter
    private int responseCode = -1;
    @Getter @Setter
    private String orderId;

    public void updateMenu(String restaurant, boolean isEmpty) throws Exception {
        String id = this.getRestaurant(restaurant).getId();
        List<MenuItem> menu = new ArrayList<>();
        if (!isEmpty) {
            menu.add(new MenuItem("bean", 1D));
            menu.add(new MenuItem("slice of bread", 1.5D));
            menu.add(new MenuItem("pea", 10.1D));
            menu.add(new MenuItem("sandwich", 1.0D));
        }
        ObjectMapper mapper = new ObjectMapper();
        this.responseCode = this.putData("http://localhost:8080/api/food/restaurant/id/" + id,
                mapper.writeValueAsString(menu));
    }

    public void cancelOrder(String userName) throws Exception{
        String orderID = this.orderId;
        URL url = new URL("http://localhost:8080/api/food/order/id/" + orderID);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Content-Type", "application/json");
        this.responseCode = conn.getResponseCode();
    }

    public void completeOrder(String userName) throws Exception{
        String orderID = this.orderId;
        String url = "http://localhost:8080/api/food/order/id/" + orderID;
        this.responseCode = this.putData(url, "");
    }

    public boolean restaurantExists(String name) throws Exception {
        return !this.getFilteredRestaurants("?name=" + name).isEmpty();
    }

    public boolean customerExists(String name) throws Exception {
        return !this.getFilteredCustomers("?userName=" + name).isEmpty();
    }

    public void addRestaurant(String name) throws Exception{
        URL url = new URL("http://localhost:8080/api/food/restaurant");
        Restaurant r = new Restaurant();
        r.setName(name);
        r.setAddress("my street");
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("sandwich", 1.0D));
        r.setMenuItems(menuItems);
        ObjectMapper mapper = new ObjectMapper();
        int responseCode = this.postData(url, mapper.writeValueAsString(r));

        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    public void addCustomer(String userName) throws Exception{
        URL url = new URL("http://localhost:8080/api/food/customer");
        Customer customer = new Customer();
        customer.setUserName(userName);
        customer.setPassword("mypassword");
        ObjectMapper mapper = new ObjectMapper();
        int responseCode = this.postData(url, mapper.writeValueAsString(customer));

        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    public void addOrder(String userName) throws Exception{
        Order order = setOrderData(userName);
        URL url = new URL("http://localhost:8080/api/food/order");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(order));
        int responseCode = this.postData(url, mapper.writeValueAsString(order), true);

        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    public void restaurantIsRenamed(String name, String newName) throws Exception {
        Restaurant r = this.getRestaurant(name);
        if (r == null){
            r = new Restaurant();
            r.setId("-1");
        } else{
            r.setName(newName);
        }
        URL url = new URL("http://localhost:8080/api/food/restaurant/update/" + r.getId());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        os.write(mapper.writeValueAsString(r).getBytes());
        os.flush();
        this.setResponseCode(conn.getResponseCode());
    }

    private Order setOrderData(String userName) throws Exception {
        Order order = new Order();
        order.setCustomerID(this.getCustomerID(userName));
        order.setRestaurantID(this.getRestaurantID("test01"));
        OrderItem orderItem = new OrderItem(new MenuItem("sandwich", 1.0D), 1);
        order.setItems(Collections.singletonList(orderItem));

        return order;
    }

    public Restaurant getRestaurant(String name) throws Exception {
        List<Restaurant> restaurants = this.getFilteredRestaurants("?name=" + name);
        if (!restaurants.isEmpty()){
            return restaurants.get(0);
        } else{
            return null;
        }
    }

    private String getRestaurantID(String name) throws Exception {
        return getRestaurant(name).getId();
    }

    private String getCustomerID(String userName) throws Exception {
        List<Customer> customers = this.getFilteredCustomers("?userName=" + userName);
        return customers.get(0).getId();
    }

    private List<Restaurant> getFilteredRestaurants(String filterString) throws Exception {
        URL url = new URL("http://localhost:8080/api/food/restaurant" + filterString);
        ObjectMapper mapper = new ObjectMapper();
        List<Restaurant> restaurants = Arrays.asList(mapper.readValue(url, Restaurant[].class));
        List<Restaurant> restaurantsWithoutNulls = restaurants.parallelStream().filter(
                Objects::nonNull).collect(Collectors.toList());
        if(!restaurantsWithoutNulls.isEmpty()){
            System.out.println(restaurantsWithoutNulls.get(0).toString());
        }

        return restaurantsWithoutNulls;
    }

    private List<Customer> getFilteredCustomers(String filterString) throws Exception {
        URL url = new URL("http://localhost:8080/api/food/customer" + filterString);
        ObjectMapper mapper = new ObjectMapper();
        List<Customer> customers = Arrays.asList(mapper.readValue(url, Customer[].class));
        List<Customer> customersWithoutNulls = customers.parallelStream().filter(
                Objects::nonNull).collect(Collectors.toList());
        if(!customersWithoutNulls.isEmpty()){
            System.out.println(customersWithoutNulls.get(0).toString());
        }

        return customersWithoutNulls;
    }

    private int postData(URL url, String data) throws Exception {
        return postData(url, data, false);
    }

    private int postData(URL url, String data, boolean isPlacingOrder) throws Exception{
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        if(isPlacingOrder) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            this.orderId = reader.readLine();
        }

        return conn.getResponseCode();
    }

    private int putData(String url, String data) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();

        return conn.getResponseCode();
    }

    public int getOrderCount(String userName) throws Exception{
        URL url = new URL("http://localhost:8080/api/food/order/customer/" +
                getCustomerID(userName));
        ObjectMapper mapper = new ObjectMapper();
        Order[] orders = mapper.readValue(url, Order[].class);

        return orders.length;
    }
}
