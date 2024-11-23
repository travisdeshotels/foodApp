package io.github.travisdeshotels.food.rest.controller;

import io.github.travisdeshotels.food.beans.FoodConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FoodConstants.API_URL + "/healthcheck")
public class HealthController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("Application status: OK", HttpStatus.OK);
    }
}
