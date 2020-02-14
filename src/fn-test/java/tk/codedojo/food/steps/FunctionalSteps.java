package tk.codedojo.food.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.HttpURLConnection;
import java.net.URL;

public class FunctionalSteps {
    private int responseCode = 500;

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

    @Given("a restaurant exists")
    public void aRestaurantExists() {
    }

    @And("a customer exists")
    public void aCustomerExists() {
    }

    @And("customer places an order")
    public void customerPlacesAnOrder() {
    }

    @Then("customer cancels their order")
    public void customerCancelsTheirOrder() {
    }

    @And("a response of {int} is returned")
    public void aResponseOfIsReturned(int arg0) {
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
}
