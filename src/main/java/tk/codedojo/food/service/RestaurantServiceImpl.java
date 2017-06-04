package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.RestaurantDao;
import tk.codedojo.food.exception.RestaurantAddressMissingException;
import tk.codedojo.food.exception.RestaurantNotFoundException;
import tk.codedojo.food.exception.RestaurantNotNamedException;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDao dao;

    public void addRestaurant(Restaurant r) throws Exception{
        if(r.getName()==null || "".equals(r.getName())){
            throw new RestaurantNotNamedException("Restaurants must have names!");
        } else if (r.getAddress()==null || "".equals(r.getAddress())){
            throw new RestaurantAddressMissingException("Restaurants must have an address!");
        } else{
            dao.save(r);
        }
    }

    public Restaurant updateMenu(String restaurantID, List<MenuItem> menu) throws Exception{
        Restaurant r = dao.findOne(restaurantID);
        if(r == null){
           throw new RestaurantNotFoundException("Cannot update menu, restaurant id not valid!");
        }
        if(menu == null){
            throw new NullPointerException("Cannot perform update, menu is empty!");
        }
        //TODO validate the menu items
        r.setMenuItems(menu);
        dao.save(r);
        return r;
    }
}
