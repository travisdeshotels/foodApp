package tk.codedojo.food.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.RestaurantDao;
import tk.codedojo.food.exception.RestaurantNotFoundException;
import tk.codedojo.food.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/food/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantDao dao;

    @Autowired
    private RestaurantService service;

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
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/id/{id}")
    public ResponseEntity<Restaurant> updateMenu(@PathVariable("id") String id, @RequestBody List<MenuItem> menu){
        Restaurant r;
        try {
            r = service.updateMenu(id, menu);
        } catch (RestaurantNotFoundException e){
            //TODO log error
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            //TODO log error
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(r, HttpStatus.OK);
    }
}
