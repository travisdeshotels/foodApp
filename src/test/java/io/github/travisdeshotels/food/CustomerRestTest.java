package io.github.travisdeshotels.food;


import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.beans.security.Role;
import io.github.travisdeshotels.food.dao.CustomerDaoMongo;
import io.github.travisdeshotels.food.rest.controller.CustomerController;
import io.github.travisdeshotels.food.service.CustomerService;
import io.github.travisdeshotels.food.service.security.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import io.github.travisdeshotels.food.exception.CustomerException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerRestTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private CustomerController customerController;
    @MockBean
    private CustomerDaoMongo customerDaoMongo;
    @MockBean
    private JWTService jwtService;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Disabled
    @Test
    public void testGetCustomer() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("1", "Orr", "Richard", "Ricky", "p4ssw0rd", "", Role.USER, null));
        when(customerService.findAll()).thenReturn(customers);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/food/customer").accept(MediaType.APPLICATION_JSON);

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        String expected = "[{\"id\":\"1\",\"lastName\":\"Orr\",\"firstName\":\"Richard\",\"userName\":\"Ricky\"}]";
        System.err.println(result.getResponse().getContentAsString());
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Disabled
    @Test
    public void testAddCustomer() throws Exception {
        String content = "{\"lastName\" : \"Orr\",\"firstName\" : \"Richard\",\"userName\" : \"Ricky\",\"password\" : \"p4ssw0rd\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/food/customer").accept(MediaType.APPLICATION_JSON).
                content(content).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Disabled
    @Test
    public void testUpdateCustomer() throws Exception{
        String content = "{\"id\" : \"1\",\"lastName\" : \"last\",\"firstName\" : \"first\",\"userName\" : \"myusername\",\"password\" : \"p4ssw0rd\",\"email\" : \"e@ma.il\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/food/customer").accept(MediaType.APPLICATION_JSON).
                content(content).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Disabled
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


//    @Test
//    public void testAddDuplicateCustomer() throws Exception {
//        doThrow(new UserNameException("")).when(customerService).addCustomer(any());
//
//        String content = "{\"lastName\" : \"Orr\",\"firstName\" : \"Richard\",\"userName\" : \"Ricky\"}";
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/food/customer").accept(MediaType.APPLICATION_JSON).
//                content(content).contentType(MediaType.APPLICATION_JSON);
//        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
//        MockHttpServletResponse response = result.getResponse();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
//    }

//    @Test
//    public void testAddCustomerFail() throws Exception {
//        doThrow(new NullPointerException("")).when(customerService).addCustomer(any());
//
//        String content = "{\"lastName\" : \"Orr\",\"firstName\" : \"Richard\",\"userName\" : \"Ricky\"}";
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/food/customer").accept(MediaType.APPLICATION_JSON).
//                content(content).contentType(MediaType.APPLICATION_JSON);
//        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
//        MockHttpServletResponse response = result.getResponse();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
//    }
}
