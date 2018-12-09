package tk.codedojo.food.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.exception.RestaurantException;
import tk.codedojo.food.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/food/restaurant")
public class RestaurantController {
    private RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service){
        this.service = service;
    }

    @RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getRestaurants(){
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
