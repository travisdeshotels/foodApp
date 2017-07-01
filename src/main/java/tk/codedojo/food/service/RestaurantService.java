package tk.codedojo.food.service;

import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.exception.RestaurantException;

import java.util.List;

public interface RestaurantService {
    void addRestaurant(Restaurant r) throws RestaurantException;
    Restaurant updateMenu(String restaurantID, List<MenuItem> menu) throws RestaurantException;
}
