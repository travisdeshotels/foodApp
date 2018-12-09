package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.RestaurantDao;
import tk.codedojo.food.exception.*;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantDao dao;

    @Autowired
    public RestaurantServiceImpl(RestaurantDao dao){
        this.dao = dao;
    }

    public void addRestaurant(Restaurant r) throws RestaurantException{
        if("".equals(r.getName())){
            throw new RestaurantException("Restaurants must have names!");
        } else if ("".equals(r.getAddress())){
            throw new RestaurantException("Restaurants must have an address!");
        } else{
            dao.save(r);
        }
    }

    public Restaurant updateMenu(String restaurantID, List<MenuItem> menu) throws RestaurantException{
        Restaurant r = dao.findOne(restaurantID);
        if(r == null){
           throw new RestaurantException("Cannot update menu, restaurant id not valid!");
        }
        if(menu == null){
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
    public List<Restaurant> findAll() {
        return dao.findAll();
    }
}
