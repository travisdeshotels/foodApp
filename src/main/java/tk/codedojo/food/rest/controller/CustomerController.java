package tk.codedojo.food.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.exception.CustomerException;
import tk.codedojo.food.exception.UserNameException;
import tk.codedojo.food.service.CustomerService;

import java.util.Collections;
import java.util.List;

import static tk.codedojo.food.beans.FoodConstants.API_URL;

@RestController
@RequestMapping(API_URL + "/customer")
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

    @RequestMapping(method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer c){
        Logger log = LoggerFactory.getLogger(CustomerController.class.getName());
        try {
            service.addCustomer(c);
        } catch (UserNameException e){
            log.error("UserNameException", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("Customer added: " + c.toString());
        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }

    @RequestMapping(method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer c){
        Logger log = LoggerFactory.getLogger(CustomerController.class.getName());
        try{
            service.updateCustomer(c);
        } catch (CustomerException e){
            log.error("CustomerException", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("Customer updated: " + c.toString());
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
}
