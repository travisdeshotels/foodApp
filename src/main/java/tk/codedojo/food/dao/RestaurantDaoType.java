package tk.codedojo.food.dao;

import tk.codedojo.food.beans.Restaurant;

import java.util.List;

public interface RestaurantDaoType {
    void save(Restaurant restaurant);
    Restaurant findOne(String id);
    List<Restaurant> findAll();
}
