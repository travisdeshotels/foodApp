package io.github.travisdeshotels.food.fntest.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.travisdeshotels.food.constants.FoodAppTestConstants;
import io.github.travisdeshotels.food.fntest.FunctionalImpl;
import io.github.travisdeshotels.food.fntest.FunctionalImplOrder;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FunctionalSteps {
    private FunctionalImpl impl = new FunctionalImpl();
    private FunctionalImplOrder orderImpl = new FunctionalImplOrder();

    @Before
    public void setup(){
        orderImpl.setOrderId("-1");
    }

    @After
    public void cleanup(){
        orderImpl.setOrderId("-1");
    }

    @Given("the application is running")
    public void theApplicationIsRunning() {
        System.out.println("Start the server before running tests.");
    }

    @When("I try the healthcheck endpoint")
    public void iTryTheHealthcheckEndpoint() throws Exception {
        URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/healthcheck");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        impl.setResponseCode(conn.getResponseCode());
        conn.disconnect();
    }

    @Given("restaurant {word} exists")
    public void aRestaurantExists(String name) throws Exception {
        assert impl.restaurantExists(name);
    }

    @And("customer {word} exists")
    public void aCustomerExists(String userName) throws Exception{
        if (!orderImpl.customerExists(userName)){
            impl.addCustomer(userName);
        }
    }

    @And("customer {word} places an order")
    public void customerPlacesAnOrder(String userName) throws Exception {
        orderImpl.addOrder(userName);
    }

    @Then("customer {word} cancels their order")
    public void customerCancelsTheirOrder(String userName) throws  Exception{
        orderImpl.cancelOrder(userName);
    }

    @And("a response of {int} is returned")
    public void aResponseOfIsReturned(int responseCode) {
        assertEquals(responseCode, impl.getResponseCode());
        impl.setResponseCode(-1);
    }

    @And("customer {word} has no orders")
    public void theCustomerHasNoOrders(String userName) throws Exception {
        assertEquals(0, orderImpl.getOrderCount(userName));
    }

    @When("{word} restaurant is renamed to {word}")
    public void restaurantIsRenamed(String name, String newName) throws Exception{
        impl.restaurantIsRenamed(name, newName);
    }

    @Given("restaurant {word} does not exist")
    public void restaurantTestDoesNotExist(String name) throws Exception{
        assertNull(impl.getRestaurant(name));
    }

    @Then("customer {word} completes their order")
    public void customerTestCompletesTheirOrder(String customer) throws Exception {
        orderImpl.completeOrder(customer);
    }

    @When("{word} menu is updated")
    public void testMenuIsUpdated(String restaurant) throws Exception {
        impl.updateMenu(restaurant, false);
    }

    @When("{word} menu is updated without a menu")
    public void testMenuIsUpdatedWithoutAMenu(String restaurant) throws Exception {
        impl.updateMenu(restaurant, true);
    }

    @And("customer {word} orders {int} of {string} at {string}")
    public void customerTestOrdersItem(String name, int quantity, String item, String price) throws Exception {
        orderImpl.customOrder(quantity, item, Double.valueOf(price));
    }

    @Given("restaurant owner has logged in")
    public void restaurantOwnerHasLoggedIn() throws Exception {
        impl.restaurantLogin();
    }
}
