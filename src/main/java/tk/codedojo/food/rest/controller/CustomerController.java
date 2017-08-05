package tk.codedojo.food.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.exception.UserNameException;
import tk.codedojo.food.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/food/customer")
public class CustomerController {
    @Autowired
    private CustomerDao dao;

    @Autowired
    private CustomerService service;

    @RequestMapping(method=RequestMethod.GET)
    public List<Customer> getCustomers(){
        return dao.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer c){
        Logger log = LoggerFactory.getLogger(CustomerController.class.getName());
        try {
            service.addCustomer(c);
        } catch (UserNameException e){
            log.error("UserNameException", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.error("", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.trace("Customer added: " + c.toString());
        return new ResponseEntity(c, HttpStatus.CREATED);
    }
}
