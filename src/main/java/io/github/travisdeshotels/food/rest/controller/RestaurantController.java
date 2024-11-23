package io.github.travisdeshotels.food.rest.controller;

import io.github.travisdeshotels.food.beans.FoodConstants;
import io.github.travisdeshotels.food.beans.MenuItem;
import io.github.travisdeshotels.food.beans.Restaurant;
import io.github.travisdeshotels.food.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import io.github.travisdeshotels.food.exception.RestaurantException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(FoodConstants.API_URL + "/restaurant")
public class RestaurantController {
    private RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service){
        this.service = service;
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

//    @RequestMapping(method=RequestMethod.POST)
//    public ResponseEntity<String> addRestaurant(@RequestBody Restaurant r){
//        Logger log = LoggerFactory.getLogger(RestController.class.getName());
//        try {
//            service.addRestaurant(r);
//        } catch (RestaurantException e){
//            log.error("RestaurantException", e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (Exception e){
//            log.error("", e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        log.trace("Restaurant added: " + r.toString());
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    @RequestMapping(method=RequestMethod.PUT, value="/update/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String id, @RequestBody Restaurant r){
        try {
            service.updateRestaurant(r, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RestaurantException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method=RequestMethod.PUT, value="/id/{id}")
    public ResponseEntity<Restaurant> updateMenu(@PathVariable("id") String id, @RequestBody List<MenuItem> menu){
        Logger log = LoggerFactory.getLogger(RestaurantController.class.getName());
        Restaurant r;
        try {
            r = service.updateMenu(id, menu);
        } catch (RestaurantException e) {
            log.error("RestaurantException", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("Menu updated for Restaurant: " + r.toString());

        return new ResponseEntity<>(r, HttpStatus.OK);
    }
}
