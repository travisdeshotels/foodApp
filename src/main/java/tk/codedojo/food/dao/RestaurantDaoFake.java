package tk.codedojo.food.dao;

import org.springframework.stereotype.Repository;
import tk.codedojo.food.beans.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RestaurantDaoFake {
    private Map<String, Restaurant> restaurantMap;

    public RestaurantDaoFake(){
        restaurantMap = new HashMap<>();
    }

    public void save(Restaurant r){
        restaurantMap.put(r.getId(), r);
    }

    public Restaurant findOne(String id){
        return restaurantMap.get(id);
    }

    public List<Restaurant> findAll(){
        return new ArrayList<>(restaurantMap.values());
    }
}
