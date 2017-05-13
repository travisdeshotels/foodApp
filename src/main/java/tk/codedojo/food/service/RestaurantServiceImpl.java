package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.RestaurantDao;
import tk.codedojo.food.exception.RestaurantAddressMissingException;
import tk.codedojo.food.exception.RestaurantNotNamedException;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantDao dao;

    public boolean addRestaurant(Restaurant r) throws Exception{
        if(r.getName()==null || "".equals(r.getName())){
            throw new RestaurantNotNamedException("Restaurants must have names!");
        } else if (r.getAddress()==null || "".equals(r.getAddress())){
            throw new RestaurantAddressMissingException("Restaurants must have an address!");
        } else{
            dao.save(r);
            return true;
        }
    }
}
