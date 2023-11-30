package tk.codedojo.food.service;

import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.exception.RestaurantException;

import java.util.List;

public interface RestaurantService {
    String addRestaurant(Restaurant r) throws RestaurantException;
    Restaurant updateMenu(String restaurantID, List<MenuItem> menu) throws RestaurantException;
    List<Restaurant> findAll();
    Restaurant findOne(String id);
    Restaurant findByName(String name);
    Restaurant updateRestaurant(Restaurant r, String id) throws RestaurantException;
}
