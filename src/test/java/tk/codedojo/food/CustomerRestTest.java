package tk.codedojo.food;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.dao.OrderDao;
import tk.codedojo.food.dao.RestaurantDao;
import tk.codedojo.food.service.CustomerService;
import tk.codedojo.food.service.CustomerServiceImpl;
import tk.codedojo.food.service.OrderService;
import tk.codedojo.food.service.RestaurantService;
import tk.codedojo.food.exception.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CustomerRestTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private CustomerDao customerDao;
    @MockBean
    private OrderDao orderDao;
    @MockBean
    private OrderService orderService;
    @MockBean
    private RestaurantDao restaurantDao;
    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void testGetCustomer() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("1", "Orr", "Richard", "Ricky"));
        when(customerDao.findAll()).thenReturn(customers);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/food/customer").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":\"1\",\"lastName\":\"Orr\",\"firstName\":\"Richard\",\"userName\":\"Ricky\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testAddCustomer() throws Exception {
        String content = "{\"lastName\" : \"Orr\",\"firstName\" : \"Richard\",\"userName\" : \"Ricky\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/food/customer").accept(MediaType.APPLICATION_JSON).
                content(content).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void testAddDuplicateCustomer() throws Exception {
        //Customer myCustomer = new Customer("1", "Orr", "Richard", "Ricky");
        Customer myCustomer = mock(Customer.class);

        when(customerService.addCustomer(myCustomer)).thenThrow(new UserNameException(""));

        String content = "{\"lastName\" : \"Orr\",\"firstName\" : \"Richard\",\"userName\" : \"Ricky\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/food/customer").accept(MediaType.APPLICATION_JSON).
                content(content).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}
