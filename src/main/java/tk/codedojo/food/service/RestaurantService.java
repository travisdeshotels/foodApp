package tk.codedojo.food.service;

import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;

import java.util.List;

public interface RestaurantService {
    void addRestaurant(Restaurant r) throws Exception;
    Restaurant updateMenu(String restaurantID, List<MenuItem> menu) throws Exception;
}
