package tk.codedojo.food;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.rest.controller.CustomerController;
import tk.codedojo.food.service.CustomerService;
import tk.codedojo.food.exception.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class CustomerRestTest {
    private MockMvc mockMvc;
    @Mock
    private CustomerService customerService;
    @Mock
    private CustomerDao customerDao;
    @InjectMocks
    private CustomerController customerController;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetCustomer() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("1", "Orr", "Richard", "Ricky", "p4ssw0rd", ""));
        when(customerDao.findAll()).thenReturn(customers);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/food/customer").accept(MediaType.APPLICATION_JSON);

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        String expected = "[{\"id\":\"1\",\"lastName\":\"Orr\",\"firstName\":\"Richard\",\"userName\":\"Ricky\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testAddCustomer() throws Exception {
        String content = "{\"lastName\" : \"Orr\",\"firstName\" : \"Richard\",\"userName\" : \"Ricky\",\"password\" : \"p4ssw0rd\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/food/customer").accept(MediaType.APPLICATION_JSON).
                content(content).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void testUpdateCustomer() throws Exception{
        String content = "{\"id\" : \"1\",\"lastName\" : \"last\",\"firstName\" : \"first\",\"userName\" : \"myusername\",\"password\" : \"p4ssw0rd\",\"email\" : \"e@ma.il\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/food/customer").accept(MediaType.APPLICATION_JSON).
                content(content).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testUpdateCustomerException() throws Exception{
        doThrow(new CustomerException("")).when(customerService).updateCustomer(any());

        String content = "{\"id\" : \"1\",\"lastName\" : \"last\",\"firstName\" : \"first\",\"userName\" : \"myusername\",\"password\" : \"p4ssw0rd\",\"email\" : \"e@ma.il\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/food/customer").accept(MediaType.APPLICATION_JSON).
                content(content).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }


    @Test
    public void testAddDuplicateCustomer() throws Exception {
        doThrow(new UserNameException("")).when(customerService).addCustomer(any());

        String content = "{\"lastName\" : \"Orr\",\"firstName\" : \"Richard\",\"userName\" : \"Ricky\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/food/customer").accept(MediaType.APPLICATION_JSON).
                content(content).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testAddCustomerFail() throws Exception {
        doThrow(new NullPointerException("")).when(customerService).addCustomer(any());

        String content = "{\"lastName\" : \"Orr\",\"firstName\" : \"Richard\",\"userName\" : \"Ricky\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/food/customer").accept(MediaType.APPLICATION_JSON).
                content(content).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}
