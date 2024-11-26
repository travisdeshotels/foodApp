package io.github.travisdeshotels.food.fntest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.beans.LoginResponse;
import io.github.travisdeshotels.food.beans.MenuItem;
import io.github.travisdeshotels.food.beans.Restaurant;
import io.github.travisdeshotels.food.beans.RestaurantInfo;
import io.github.travisdeshotels.food.beans.security.SignUpRequest;
import io.github.travisdeshotels.food.constants.FoodAppTestConstants;
import io.github.travisdeshotels.food.util.RestUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static io.github.travisdeshotels.food.util.RestUtil.getFilteredRestaurants;
import static io.github.travisdeshotels.food.util.RestUtil.postData;
import static io.github.travisdeshotels.food.util.RestUtil.postDataForToken;
import static io.github.travisdeshotels.food.util.RestUtil.putDataWithToken;


public class FunctionalImpl {
    @Getter @Setter
    private int responseCode = -1;
    private String token;

    public Restaurant getRestaurant(String name) throws Exception {
        return RestUtil.getRestaurant(name, this.token);
    }

    public void updateMenu(String restaurant, boolean isEmpty) throws Exception {
        String id = RestUtil.getRestaurant(restaurant, this.token).getId();
        List<MenuItem> menu = new ArrayList<>();
        if (!isEmpty) {
            menu.add(new MenuItem("bean", 1D));
            menu.add(new MenuItem("slice of bread", 1.5D));
            menu.add(new MenuItem("pea", 10.1D));
            menu.add(new MenuItem("sandwich", 1.0D));
        }
        ObjectMapper mapper = new ObjectMapper();
        this.responseCode = putDataWithToken(
                "http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/restaurant/id/" + id,
                mapper.writeValueAsString(menu), this.token);
    }

    public boolean restaurantExists(String name) throws Exception {
        return !getFilteredRestaurants("?name=" + name, this.token).isEmpty();
    }

    public void addRestaurant(String name) throws Exception{
        URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/auth/signup");

        SignUpRequest request = new SignUpRequest();
        request.setFirstName("First");
        request.setLastName("Last");
        request.setEmail("owner@rest.net");
        request.setPassword("password");
        request.setUserName("myUserName");
        request.setRestaurantInfo(new RestaurantInfo("test01", "123 awful st"));
        ObjectMapper mapper = new ObjectMapper();
        int responseCode = postData(url, mapper.writeValueAsString(request));

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    public void addCustomer(String userName) throws Exception{
        URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/customer");
        Customer customer = new Customer();
        customer.setUserName(userName);
        customer.setPassword("mypassword");
        ObjectMapper mapper = new ObjectMapper();
        int responseCode = postData(url, mapper.writeValueAsString(customer));

        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    public void restaurantIsRenamed(String name, String newName) throws Exception {
        Restaurant r = RestUtil.getRestaurant(name, this.token);
        if (r == null){
            r = new Restaurant();
            r.setId("-1");
        } else{
            r.setName(newName);
        }
        URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/restaurant/update/" + r.getId());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + this.token);
        OutputStream os = conn.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        os.write(mapper.writeValueAsString(r).getBytes());
        os.flush();
        this.setResponseCode(conn.getResponseCode());
    }

    public void restaurantLogin() throws Exception {
        // loop to retry login after creating the account when it doesn't exist
        for (int i=0;i<4;i++) {
            URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/auth/signin");
            String data = new ObjectMapper().writeValueAsString(new Object() {
                @Getter final String email = "owner@rest.net";
                @Getter final String password = "password";
            });
            LoginResponse loginResponse = postDataForToken(url, data);
            if (loginResponse != null) {
                System.out.println(loginResponse.getToken());
                this.token = loginResponse.getToken();
                break;
            } else {
                System.out.println("Login was not successful. Attempting to create the restaurant owner's account");
                addRestaurant("test01");
            }
        }
    }
}
