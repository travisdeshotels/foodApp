package tk.codedojo.food.dao.mongo.wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.RestaurantDaoType;
import tk.codedojo.food.dao.mongo.RestaurantDaoMongo;

import java.util.List;

@Component
public class RestaurantDaoWrapper implements RestaurantDaoType {
    private RestaurantDaoMongo dao;

    @Autowired
    public RestaurantDaoWrapper(RestaurantDaoMongo restaurantDaoMongo){
        this.dao =restaurantDaoMongo;
    }

    @Override
    public void save(Restaurant restaurant) {
        this.dao.save(restaurant);
    }

    @Override
    public Restaurant findOne(String id) {
        return this.dao.findById(id).isPresent() ? this.dao.findById(id).get() : null;
    }

    @Override
    public List<Restaurant> findAll() {
        return this.dao.findAll();
    }

    @Override
    public Restaurant findByName(String name){
        return this.dao.findByName(name);
    }
}
