package tk.codedojo.food.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.RestaurantDao;
import tk.codedojo.food.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/food/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantDao dao;

    @Autowired
    RestaurantService service;

    @RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getRestaurants(){
        return dao.findAll();
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<String> addRestaurant(@RequestBody Restaurant r){
        try {
            service.addRestaurant(r);
        } catch (Exception e){
            //TODO add more catch clauses with logging
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
