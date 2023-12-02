package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.RestaurantDaoMongo;
import tk.codedojo.food.exception.*;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantDaoMongo dao;

    @Autowired
    public RestaurantServiceImpl(RestaurantDaoMongo dao){
        this.dao = dao;
    }

    public String addRestaurant(Restaurant r) throws RestaurantException{
        String restaurantId;
        if(r.getName().isEmpty()){
            throw new RestaurantException("Restaurants must have names!");
        } else if (r.getAddress().isEmpty()){
            throw new RestaurantException("Restaurants must have an address!");
        } else{
            restaurantId = dao.save(r).getId();
        }
        return restaurantId;
    }

    public Restaurant updateMenu(String id, List<MenuItem> menu) throws RestaurantException{
        Restaurant r = dao.findById(id).isPresent() ? dao.findById(id).get() : null;
        if(r == null){
           throw new RestaurantException("Cannot update menu, restaurant id not valid!");
        }
        if(CollectionUtils.isEmpty(menu)){
            throw new RestaurantException("Cannot perform update, menu is empty!");
        }
        for (MenuItem item : menu){
            if(item.getPrice()<0){
                throw new RestaurantException("Menu items must have valid prices!");
            } else if ("".equals(item.getFoodItem())){
                throw new RestaurantException("Menu items must have valid names!");
            }
        }
        r.setMenuItems(menu);
        dao.save(r);
        return r;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant r, String id) throws RestaurantException {
        if(dao.findByName(r.getName()) != null){
            throw new RestaurantException("Restaurant name already exists!");
        } else {
            dao.save(r);
        }

        return r;
    }

    @Override
    public List<Restaurant> findAll() {
        return dao.findAll();
    }

    @Override
    public Restaurant findOne(String id) {
        return dao.findById(id).isPresent() ? dao.findById(id).get() : null;
    }

    @Override
    public Restaurant findByName(String name) {
        return dao.findByName(name);
    }
}
