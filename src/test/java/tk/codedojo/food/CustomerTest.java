package tk.codedojo.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.mongo.CustomerDaoMongo;
import tk.codedojo.food.exception.CustomerException;
import tk.codedojo.food.exception.UserNameException;
import tk.codedojo.food.service.CustomerServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerTest {
    @Mock
    private CustomerDaoMongo customerDao;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setupMock(){
        customerService = new CustomerServiceImpl(customerDao);
    }

    @Test
    public void testNullUserName(){
        assertThrows(NullPointerException.class, () -> new Customer("4","Smith","Sob",null, "p4ssw0rd", null));
    }

    @Test
    public void testValidSave() throws UserNameException {
        when(customerDao.getCustomerByUserName("bob")).thenReturn(null);
        Customer customer = new Customer("1", "Smith", "Bob", "bob", "p4ssw0rd", null);
        customerService.addCustomer(customer);
    }

    @Test
    public void testDuplicate(){
        when(customerDao.getCustomerByUserName("tom")).thenReturn(new Customer("2", "Smith", "Tom", "tom", "p4ssw0rd", null));
        Customer customer = new Customer("2", "Smith", "Tom", "tom", "p4ssw0rd", null);
        assertThrows(UserNameException.class, () -> customerService.addCustomer(customer));
    }

    @Test
    public void testShortUserName(){
        assertThrows(UserNameException.class, () ->
                customerService.addCustomer(new Customer("3", "Smith", "Rob", "ro", "p4ssw0rd", null)));
    }

    @Test
    public void testEmptyUserName(){
        assertThrows(UserNameException.class, () ->
                customerService.addCustomer(new Customer("5", "Smith", "Cob", "", "p4ssw0rd", null)));
    }

    @Test
    public void testUpdateCustomer() throws CustomerException{
        when(customerDao.findById("1")).thenReturn(Optional.of(
                new Customer("1", "l", "f", "myname", "12345", "")));
        customerService.updateCustomer(new Customer("1", "l", "f", "newname", "12345", ""));
    }

    @Test
    public void testUpdateDuplicateCustomer(){
        when(customerDao.getCustomerByUserName("newname")).thenReturn(new Customer("2","l","f","newname","12345",""));
        when(customerDao.findById("1")).thenReturn(Optional.of(
                new Customer("1", "l", "f", "myname", "12345", "")));
        assertThrows(CustomerException.class, () ->
                customerService.updateCustomer(new Customer("1", "l", "f", "newname", "12345", "")));
    }

    @Test
    public void testUpdateCustomerBadId(){
        assertThrows(CustomerException.class, () -> customerService.updateCustomer(new Customer("2","l","f","newname","12345","my@addre.ss")));
    }
}

