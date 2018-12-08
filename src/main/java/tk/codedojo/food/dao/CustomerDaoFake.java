package tk.codedojo.food.dao;

import org.springframework.stereotype.Repository;
import tk.codedojo.food.beans.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerDaoFake {
    private Map<String, Customer> customerMap;

    public CustomerDaoFake(){
        customerMap = new HashMap<>();
    }

    public Customer getCustomerByUserName(String userName){
        for (Customer c : customerMap.values()){
            if (c.getUserName().equals(userName)){
                return c;
            }
        }
        return null;
    }

    public void save(Customer c){
        customerMap.put(c.getId(), c);
    }

    public Customer findOne(String id){
        return customerMap.get(id);
    }

    public List<Customer> findAll(){
        return new ArrayList<>(customerMap.values());
    }
}
