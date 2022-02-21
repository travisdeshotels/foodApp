package tk.codedojo.food;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDaoType;
import tk.codedojo.food.dao.mongo.CustomerDaoMongo;
import tk.codedojo.food.exception.CustomerException;
import tk.codedojo.food.exception.UserNameException;
import tk.codedojo.food.service.CustomerServiceImpl;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTest {
    @Mock
    private CustomerDaoMongo customerDao;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Before
    public void setupMock(){
        customerService = new CustomerServiceImpl(customerDao);
    }

    @Test
    public void testMockCreation(){
        assertNotNull(customerDao);
    }

    @Test(expected = NullPointerException.class)
    public void testNullUserName(){
        Customer customer = new Customer("4","Smith","Sob",null, "p4ssw0rd", null);
    }

    @Test
    public void testValidSave() throws UserNameException {
        when(customerDao.getCustomerByUserName("bob")).thenReturn(null);
        Customer customer = new Customer("1", "Smith", "Bob", "bob", "p4ssw0rd", null);
        customerService.addCustomer(customer);
    }

    @Test(expected = UserNameException.class)
    public void testDuplicate() throws UserNameException{
        when(customerDao.getCustomerByUserName("tom")).thenReturn(new Customer("2", "Smith", "Tom", "tom", "p4ssw0rd", null));
        Customer customer = new Customer("2", "Smith", "Tom", "tom", "p4ssw0rd", null);
        customerService.addCustomer(customer);
    }

    @Test(expected = UserNameException.class)
    public void testShortUserName() throws UserNameException{
        customerService.addCustomer(new Customer("3", "Smith", "Rob", "ro", "p4ssw0rd", null));
    }

    @Test(expected = UserNameException.class)
    public void testEmptyUserName() throws UserNameException{
        customerService.addCustomer(new Customer("5", "Smith", "Cob", "", "p4ssw0rd", null));
    }

    @Test
    public void testUpdateCustomer() throws CustomerException{
        when(customerDao.findById("1")).thenReturn(Optional.of(
                new Customer("1", "l", "f", "myname", "12345", "")));
        customerService.updateCustomer(new Customer("1", "l", "f", "newname", "12345", ""));
    }

    @Test(expected = CustomerException.class)
    public void testUpdateDuplicateCustomer() throws CustomerException{
        when(customerDao.getCustomerByUserName("newname")).thenReturn(new Customer("2","l","f","newname","12345",""));
        when(customerDao.findById("1")).thenReturn(Optional.of(
                new Customer("1", "l", "f", "myname", "12345", "")));
        customerService.updateCustomer(new Customer("1", "l", "f", "newname", "12345", ""));
    }

    @Test(expected = CustomerException.class)
    public void testUpdateCustomerBadId() throws CustomerException{
        customerService.updateCustomer(new Customer("2","l","f","newname","12345","my@addre.ss"));
    }
}

