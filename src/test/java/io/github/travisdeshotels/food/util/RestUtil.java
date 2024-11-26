package io.github.travisdeshotels.food.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.travisdeshotels.food.beans.LoginResponse;
import io.github.travisdeshotels.food.beans.Restaurant;
import io.github.travisdeshotels.food.constants.FoodAppTestConstants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RestUtil {
//    private static int postData(URL url, String data) throws Exception {
//        return postData(url, data, false);
//    }

    public static int postData(URL url, String data/*,
                                boolean isPlacingOrder*/)
            throws Exception{
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();
//        if(isPlacingOrder) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            this.orderId = reader.readLine();
//        }

        return conn.getResponseCode();
    }

    public static LoginResponse postDataForToken(URL url, String data) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return new ObjectMapper().readValue(conn.getInputStream(), LoginResponse.class);
        } else {
            return null;
        }
    }

    public static int postDataWithToken(URL url, String data, String token) throws Exception{
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();

        return conn.getResponseCode();
    }


    public static int putData(String url, String data) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();

        return conn.getResponseCode();
    }

    public static int putDataWithToken(String url, String data, String token) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();

        return conn.getResponseCode();
    }

    public static Restaurant getRestaurant(String name, String token) throws Exception {
        List<Restaurant> restaurants = getFilteredRestaurants("?name=" + name, token);
        if (!restaurants.isEmpty()){
            return restaurants.get(0);
        } else{
            return null;
        }
    }

    public static List<Restaurant> getFilteredRestaurants(String filterString, String token) throws Exception {
        URL url = new URL("http://" + FoodAppTestConstants.SERVICE_HOST + ":8080/api/food/restaurant" + filterString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        InputStream response = conn.getInputStream();
        System.out.println(conn.getResponseCode());
        ObjectMapper mapper = new ObjectMapper();
        List<Restaurant> restaurants = mapper.readValue(response, new TypeReference<>() {
        });

        if(!restaurants.isEmpty()){
            System.out.println(restaurants.get(0).toString());
        }

        return restaurants;
    }

}
