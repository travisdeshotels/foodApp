package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.exception.CustomerException;
import tk.codedojo.food.exception.UserNameException;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    private CustomerDao dao;

    private static final int MIN_USERNAME_LENGTH = 3;

    @Autowired
    public CustomerServiceImpl(CustomerDao dao){
        this.dao = dao;
    }

    public boolean usernameInUse(String username){
        return dao.getCustomerByUserName(username)!=null;
    }

    public void addCustomer(Customer c) throws UserNameException{
        if("".equals(c.getUserName())) {
            throw new UserNameException("You must provide a username!");
        } else if(c.getUserName().length() < MIN_USERNAME_LENGTH){
            throw new UserNameException("Username must be at least " + MIN_USERNAME_LENGTH + " characters!");
        } else{
            //username not already in use
            if(!this.usernameInUse(c.getUserName())){
                dao.save(c);
            } else{
                throw new UserNameException("This username is already in use!");
            }
        }
    }

    public void updateCustomer(Customer c) throws CustomerException {
        Customer old = dao.findOne(c.getId());

        if(old == null){
            throw new CustomerException("Customer id is invalid! : "+ c.toString());
        }
        if(!old.getUserName().equals(c.getUserName())){
            //check if new username is available
            if(usernameInUse(c.getUserName())){
                throw new CustomerException("Desired username is already in use! : " + c.toString());
            }
        }
        dao.save(c);
    }

    public List<Customer> findAll(){
        return dao.findAll();
    }
}
