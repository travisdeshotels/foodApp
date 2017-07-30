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

    @Test
    public void testSave() throws UserNameException {
        Customer customer = new Customer();
        when(customerDao.save(customer)).thenReturn(null);
        when(customerDao.getCustomerByUserName("bob")).thenReturn(null);
        when(customerDao.getCustomerByUserName("tom")).thenReturn(new Customer("2", "Smith", "Tom", "tom"));

        customer = new Customer("1", "Smith", "Bob", "bob");
        customerService.addCustomer(customer);

        customer = new Customer("2", "Smith", "Tom", "tom");
        try {
            customerService.addCustomer(customer);
            assert(false);
        } catch (UserNameException e){
            assert(true);
        }
    }
}
