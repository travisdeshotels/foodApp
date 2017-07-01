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

    @Autowired
    private RestaurantDao dao;

    public void addRestaurant(Restaurant r) throws RestaurantException{
        if(r.getName()==null || "".equals(r.getName())){
            throw new RestaurantException("Restaurants must have names!");
        } else if (r.getAddress()==null || "".equals(r.getAddress())){
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
        //TODO validate the menu items
        r.setMenuItems(menu);
        dao.save(r);
        return r;
    }
}
