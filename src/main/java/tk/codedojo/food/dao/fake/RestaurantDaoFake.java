package tk.codedojo.food.dao.fake;

import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.RestaurantDaoType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RestaurantDaoFake implements RestaurantDaoType {
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
