package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.exception.UserNameException;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerDao dao;

    private static final int MIN_USERNAME_LENGTH = 3;

    public boolean usernameInUse(String username){
        return dao.getCustomerByUserName(username)!=null;
    }

    public void addCustomer(Customer c) throws UserNameException{
        if(c.getUserName()==null || "".equals(c.getUserName())) {
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
}
