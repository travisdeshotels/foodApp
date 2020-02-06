package tk.codedojo.food.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static tk.codedojo.food.beans.FoodConstants.API_URL;

@RestController
@RequestMapping(API_URL + "/healthcheck")
public class HealthController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("Application status: OK", HttpStatus.OK);
    }
}
