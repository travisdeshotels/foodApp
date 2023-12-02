package tk.codedojo.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.mongo.CustomerDaoMongo;
import tk.codedojo.food.exception.RestaurantException;
import tk.codedojo.food.rest.controller.RestaurantController;
import tk.codedojo.food.service.CustomerService;
import tk.codedojo.food.service.RestaurantService;
import tk.codedojo.food.service.security.JWTService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RestaurantController.class)
public class RestaurantRestTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private CustomerDaoMongo customerDaoMongo;
    @Autowired
    private RestaurantController restaurantController;
    @MockBean
    private JWTService jwtService;
    @MockBean
    private CustomerService customerService;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Disabled
    @Test
    public void testGetRestaurants() throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Boudin", 1d));
        Restaurant restaurant = new Restaurant("1","Boudreauxs",
                "123 street",menuItems);
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(restaurant);
        when(restaurantService.findAll()).thenReturn(restaurants);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/food/restaurant").accept(MediaType.APPLICATION_JSON);

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String expected = "[{\"id\":\"1\",\"name\":\"Boudreauxs\",\"address\":\"123 street\",\"menuItems\":[{\"foodItem\":\"Boudin\",\"price\":1.0}]}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Disabled
    @Test
    public void testUpdateMenu() throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Boudin", 1d));
        List<MenuItem> newMenuItems = new ArrayList<>();
        newMenuItems.add(new MenuItem("Cracklin", 2d));
        when(restaurantService.updateMenu("1", menuItems)).thenReturn(new Restaurant(
                "1", "Boudreauxs", "123 street", newMenuItems));
        MvcResult result = mockMvc.perform(put(
                "/api/food/restaurant/id/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"foodItem\":\"Boudin\",\"price\":1.0}]")
                ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String expected = "{\"id\":\"1\",\"name\":\"Boudreauxs\",\"address\":\"123 street\",\"menuItems\":[{\"foodItem\":\"Cracklin\",\"price\":2.0}]}";
        assertEquals(response.getStatus(), 200);
        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Disabled
    @Test
    public void testUpdateMenuRestaurantException() throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Boudin", 1d));
        List<MenuItem> newMenuItems = new ArrayList<>();
        newMenuItems.add(new MenuItem("Cracklin", 2d));
        when(restaurantService.updateMenu("1", menuItems)).thenThrow(new RestaurantException(""));
        MvcResult result = mockMvc.perform(put(
                "/api/food/restaurant/id/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"foodItem\":\"Boudin\",\"price\":1.0}]")
        ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(response.getStatus(), 404);
    }

    @Disabled
    @Test
    public void testUpdateMenuOtherException() throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Boudin", 1d));
        List<MenuItem> newMenuItems = new ArrayList<>();
        newMenuItems.add(new MenuItem("Cracklin", 2d));
        when(restaurantService.updateMenu("1", menuItems)).thenThrow(new NullPointerException());
        MvcResult result = mockMvc.perform(put(
                "/api/food/restaurant/id/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"foodItem\":\"Boudin\",\"price\":1.0}]")
        ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(response.getStatus(), 400);
    }

    @Disabled
    @Test
    public void testAddRestaurant() throws Exception {
        MvcResult result = mockMvc.perform(post(
                "/api/food/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1\", \"name\": \"Boudreauxs\", \"address\": \"123 street\", \"menuItems\":[{\"foodItem\": \"Boudin\", \"price\": \"1.0\"}]}")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(response.getStatus(), 201);
    }

    @Disabled
    @Test
    public void testRestaurantExceptionAdd() throws Exception {
        doThrow(new RestaurantException("")).when(restaurantService).addRestaurant(any());
        MvcResult result = mockMvc.perform(post(
                "/api/food/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1\", \"name\": \"Boudreauxs\", \"address\": \"123 street\", \"menuItems\":[{\"foodItem\": \"Boudin\", \"price\": \"1.0\"}]}")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(response.getStatus(), 400);
    }

    @Disabled
    @Test
    public void testAddRestaurantException() throws Exception {
        doThrow(new NullPointerException("")).when(restaurantService).addRestaurant(any());
        MvcResult result = mockMvc.perform(post(
                "/api/food/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1\", \"name\": \"Boudreauxs\", \"address\": \"123 street\", \"menuItems\":[{\"foodItem\": \"Boudin\", \"price\": \"1.0\"}]}")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(response.getStatus(), 400);
    }

}
