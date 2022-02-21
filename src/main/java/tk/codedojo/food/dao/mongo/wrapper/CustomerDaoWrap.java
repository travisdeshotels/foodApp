package tk.codedojo.food.dao.mongo.wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDaoType;
import tk.codedojo.food.dao.mongo.CustomerDaoMongo;

import java.util.List;

@Component
public class CustomerDaoWrap implements CustomerDaoType {
    private CustomerDaoMongo daoMongo;

    @Autowired
    public CustomerDaoWrap(CustomerDaoMongo daoMongo){
        this.daoMongo = daoMongo;
    }

    @Override
    public Customer getCustomerByUserName(String username){
        return daoMongo.getCustomerByUserName(username);
    }

    @Override
    public void save(Customer customer) {
        daoMongo.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return daoMongo.findAll();
    }

    @Override
    public Customer findOne(String id) {
        return daoMongo.findById(id).isPresent() ? daoMongo.findById(id).get() : null;
    }
}
