package io.github.travisdeshotels.food.rest.controller;

import io.github.travisdeshotels.food.service.CustomerService;
import io.github.travisdeshotels.food.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(method=RequestMethod.GET)
    public Map<String, Object> dumpData(){
        Map<String, Object> dump = new HashMap<>();

        dump.put("Customers", this.customerService.findAll());
        dump.put("Restaurants", this.restaurantService.findAll());

        return dump;
    }
}
