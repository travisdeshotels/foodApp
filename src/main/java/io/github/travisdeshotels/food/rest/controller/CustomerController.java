package io.github.travisdeshotels.food.rest.controller;

import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.beans.FoodConstants;
import io.github.travisdeshotels.food.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.travisdeshotels.food.exception.CustomerException;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(FoodConstants.API_URL + "/customer")
public class CustomerController {
    private CustomerService service;

    @Autowired
    public CustomerController(CustomerService service){
        this.service = service;
    }

    @RequestMapping(method=RequestMethod.GET)
    public List<Customer> getCustomers(
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String userName){
            if (id != null && !id.isEmpty()){
                return Collections.singletonList(service.findOne(id));
            } else if(userName != null && !userName.isEmpty()){
                return Collections.singletonList(service.GetByUserName(userName));
            }
            return service.findAll();
    }

//    @RequestMapping(method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Customer> addCustomer(@RequestBody Customer c){
//        Logger log = LoggerFactory.getLogger(CustomerController.class.getName());
//        try {
//            service.addCustomer(c);
//        } catch (UserNameException e){
//            log.error("UserNameException", e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (Exception e){
//            log.error("", e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        log.trace("Customer added: " + c.toString());
//        return new ResponseEntity<>(c, HttpStatus.CREATED);
//    }

    @RequestMapping(method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer c){
        try{
            service.updateCustomer(c);
        } catch (CustomerException e){
            log.error("CustomerException", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("Customer updated: {}", c.toString());
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
}
