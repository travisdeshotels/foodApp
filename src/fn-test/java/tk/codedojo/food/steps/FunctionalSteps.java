package tk.codedojo.food.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tk.codedojo.food.beans.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class FunctionalSteps {
    private int responseCode = -1;

    @Given("the application is running")
    public void theApplicationIsRunning() {
        System.out.println("Start the server before running tests.");
    }

    @When("I try the healthcheck endpoint")
    public void iTryTheHealthcheckEndpoint() throws Exception {
        URL url = new URL("http://localhost:8080/api/food/healthcheck");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        this.responseCode = conn.getResponseCode();
        conn.disconnect();
    }

    @Then("I get a response of {int}")
    public void iGetAResponseOf(int ok) {
        if (this.responseCode != ok) {
            throw new RuntimeException("Failed : HTTP error code : ");
        }
    }

    @Given("restaurant {word} exists")
    public void aRestaurantExists(String name) throws Exception {
        if (!this.restaurantExists(name)){
            this.addRestaurant(name);
        }
    }

    @And("customer {word} exists")
    public void aCustomerExists(String userName) throws Exception{
        if (!this.customerExists(userName)){
            this.addCustomer(userName);
        }
    }

    @And("customer places an order")
    public void customerPlacesAnOrder() throws Exception {
        this.addOrder();
    }

    @Then("customer cancels their order")
    public void customerCancelsTheirOrder() throws  Exception{
        this.cancelOrder();
    }

    @And("a response of {int} is returned")
    public void aResponseOfIsReturned(int responseCode) {
        assertEquals(responseCode, this.responseCode);
    }

    @And("the customer has no orders")
    public void theCustomerHasNoOrders() {
    }

    @When("restaurant is renamed")
    public void restaurantIsRenamed() {
    }

    @Given("no restaurants exist")
    public void noRestaurantsExist() {
    }

    private void cancelOrder() throws Exception{
        String orderID = getOrderID();
        URL url = new URL("http://localhost:8080/api/food/order/id/" + orderID);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Content-Type", "application/json");
        this.responseCode = conn.getResponseCode();
    }

    private String getOrderID() throws Exception{
        URL url = new URL("http://localhost:8080/api/food/order/customer/" +
                getCustomerID("test01"));
        ObjectMapper mapper = new ObjectMapper();
        List<Order> orders = Arrays.asList(mapper.readValue(url, Order[].class));
        if(!orders.isEmpty()){
            System.out.println(orders.get(0).toString());
        }

        return orders.get(0).getId();
    }

    private boolean restaurantExists(String name) throws Exception {
        return !this.getFilteredRestaurants("?name=" + name).isEmpty();
    }

    private boolean customerExists(String name) throws Exception {
        return !this.getFilteredCustomers("?userName=" + name).isEmpty();
    }

    private void addRestaurant(String name) throws Exception{
        URL url = new URL("http://localhost:8080/api/food/restaurant");
        Restaurant r = new Restaurant();
        r.setName(name);
        r.setAddress("my street");
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("sandwich", 1D));
        r.setMenuItems(menuItems);
        ObjectMapper mapper = new ObjectMapper();
        int responseCode = this.postData(url, mapper.writeValueAsString(r));

        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    private void addCustomer(String userName) throws Exception{
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

    private void addOrder() throws Exception{
        Order order = setOrderData();
        URL url = new URL("http://localhost:8080/api/food/order");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(order));
        int responseCode = this.postData(url, mapper.writeValueAsString(order));

        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    private Order setOrderData() throws Exception {
        Order order = new Order();
        order.setCustomerID(this.getCustomerID("test01"));
        order.setRestaurantID(this.getRestaurantID("test01"));
        order.setComplete(false);
        OrderItem orderItem = new OrderItem(new MenuItem("sandwich", 1.0D), 1);
        order.setItems(Collections.singletonList(orderItem));

        return order;
    }

    private String getRestaurantID(String name) throws Exception {
        List<Restaurant> restaurants = this.getFilteredRestaurants("?name=" + name);
        return restaurants.get(0).getId();
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

    private int postData(URL url, String data) throws Exception{
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();

        return conn.getResponseCode();
    }

    private int getRestaurantCount() throws Exception{
        URL url = new URL("http://localhost:8080/api/food/restaurant");
        ObjectMapper mapper = new ObjectMapper();
        Restaurant[] restaurants = mapper.readValue(url, Restaurant[].class);
        System.out.println(restaurants[0].toString());
        return restaurants.length;
    }

    private int getCustomerCount() throws Exception{
        URL url = new URL("http://localhost:8080/api/food/customer");
        ObjectMapper mapper = new ObjectMapper();
        Customer[] customers = mapper.readValue(url, Customer[].class);
        System.out.println(customers[0].toString());
        return customers.length;
    }
}
