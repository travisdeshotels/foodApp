package tk.codedojo.food.rest.controller;

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
import tk.codedojo.food.exception.UserNameAlreadyInUseException;
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
    public ResponseEntity<String> addCustomer(@RequestBody Customer c){
        try {
            service.addCustomer(c);
        } catch (UserNameAlreadyInUseException e){
            e.printStackTrace();
            //TODO log exception
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e){
            e.printStackTrace();
            //TODO log exception
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            //TODO log exception
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
