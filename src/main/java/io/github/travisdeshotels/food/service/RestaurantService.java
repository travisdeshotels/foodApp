package io.github.travisdeshotels.food.service;

import io.github.travisdeshotels.food.beans.MenuItem;
import io.github.travisdeshotels.food.beans.Restaurant;
import io.github.travisdeshotels.food.exception.RestaurantException;

import java.util.List;

public interface RestaurantService {
    String addRestaurant(Restaurant r) throws RestaurantException;
    Restaurant updateMenu(String restaurantID, List<MenuItem> menu) throws RestaurantException;
    List<Restaurant> findAll();
    Restaurant findOne(String id);
    Restaurant findByName(String name);
    Restaurant updateRestaurant(Restaurant r, String id) throws RestaurantException;
}
