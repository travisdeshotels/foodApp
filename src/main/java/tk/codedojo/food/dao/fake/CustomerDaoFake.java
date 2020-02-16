package tk.codedojo.food.dao.fake;

import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDaoType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDaoFake implements CustomerDaoType {
    private Map<String, Customer> customerMap;
    private int id;

    public CustomerDaoFake(){
        customerMap = new HashMap<>();
        this.id = 1;
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
        if (c.getId() == null) {
            c.setId(String.valueOf(this.id++));
        }
        customerMap.put(c.getId(), c);
    }

    public Customer findOne(String id){
        return customerMap.get(id);
    }

    public List<Customer> findAll(){
        return new ArrayList<>(customerMap.values());
    }
}
