package tk.codedojo.food;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.exception.RestaurantException;
import tk.codedojo.food.rest.controller.RestaurantController;
import tk.codedojo.food.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class RestaurantRestTest {
    private MockMvc mockMvc;
    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

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

    @Test
    public void testAddRestaurant() throws Exception {
        MvcResult result = mockMvc.perform(post(
                "/api/food/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1\", \"name\": \"Boudreauxs\", \"address\": \"123 street\", \"menuItems\":[{\"foodItem\": \"Boudin\", \"price\": \"1.0\"}]}")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(response.getStatus(), 201);
    }

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
