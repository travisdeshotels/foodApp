package tk.codedojo.food.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import tk.codedojo.food.FoodApplication;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.fake.RestaurantDaoFake;
import tk.codedojo.food.exception.RestaurantException;
import tk.codedojo.food.service.RestaurantService;
import tk.codedojo.food.service.RestaurantServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static tk.codedojo.food.beans.FoodConstants.API_URL;

@RestController
@RequestMapping(API_URL + "/restaurant")
public class RestaurantController {
    private RestaurantService service;

    public RestaurantController(){
        this.service = new RestaurantServiceImpl(FoodApplication.getRestaurantDaoFake());
    }

    @RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getRestaurants(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name){
        if (id != null && !id.isEmpty()){
            return Collections.singletonList(service.findOne(id));
        } else if(name != null && !name.isEmpty()){
            return Collections.singletonList(service.findByName(name));
        }
        return service.findAll();
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<String> addRestaurant(@RequestBody Restaurant r){
        Logger log = LoggerFactory.getLogger(RestController.class.getName());
        try {
            service.addRestaurant(r);
        } catch (RestaurantException e){
            log.error("RestaurantException", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.error("", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.trace("Restaurant added: " + r.toString());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/id/{id}")
    public ResponseEntity<Restaurant> updateMenu(@PathVariable("id") String id, @RequestBody List<MenuItem> menu){
        Restaurant r;
        Logger log = LoggerFactory.getLogger(RestController.class.getName());
        try {
            r = service.updateMenu(id, menu);
        } catch (RestaurantException e){
            log.error("RestaurantException", e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            log.error("", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.trace("Menu updated for Restaurant: " + r.toString());
        return new ResponseEntity(r, HttpStatus.OK);
    }
}
