package tk.codedojo.food.dao.fake;

import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.RestaurantDaoType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RestaurantDaoFake implements RestaurantDaoType {
    private Map<String, Restaurant> restaurantMap;
    private int id;

    public RestaurantDaoFake(){
        restaurantMap = new HashMap<>();
        this.id = 1;
    }

    @Override
    public void save(Restaurant r){
        if (r.getId() == null) {
            r.setId(String.valueOf(this.id++));
        }
        restaurantMap.put(r.getId(), r);
    }

    @Override
    public Restaurant findOne(String id){
        return restaurantMap.get(id);
    }

    @Override
    public List<Restaurant> findAll(){
        return new ArrayList<>(restaurantMap.values());
    }

    @Override
    public Restaurant findByName(String name) {
        for (String id : restaurantMap.keySet()){
            Restaurant r = restaurantMap.get(id);
            if (name.equals(r.getName())){
                return r;
            }
        }

        return null;
    }
}
