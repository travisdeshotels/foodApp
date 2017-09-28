package tk.codedojo.food;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.exception.UserNameException;
import tk.codedojo.food.service.CustomerServiceImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTest {
    @Mock
    private CustomerDao customerDao;
    @InjectMocks
    private CustomerServiceImpl customerService = new CustomerServiceImpl();

    @Before
    public void setupMock(){
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
}
