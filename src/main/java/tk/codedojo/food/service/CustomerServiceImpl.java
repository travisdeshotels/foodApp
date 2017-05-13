package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.exception.UserNameAlreadyInUseException;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerDao dao;

    public boolean addCustomer(Customer c) throws Exception{
        if(c.getUserName()==null || "".equals(c.getUserName()) ){
            throw new NullPointerException();
        } else{
            //username not already in use
            if(dao.getCustomerByUserName(c.getUserName())==null){
                dao.save(c);
                return true;
            } else{
                throw new UserNameAlreadyInUseException("This username is already in use!");
            }
        }
    }
}
