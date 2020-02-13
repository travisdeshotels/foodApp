package tk.codedojo.food;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.fake.RestaurantDaoFake;
import tk.codedojo.food.exception.RestaurantException;
import tk.codedojo.food.service.RestaurantServiceImpl;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantTest {
    @Mock
    private RestaurantDaoFake restaurantDao;
    private RestaurantServiceImpl restaurantService;

    @Before
    public void setupMock(){
        restaurantService = new RestaurantServiceImpl(restaurantDao);
    }

    @Test
    public void testMockCreation(){
        assertNotNull(restaurantDao);
    }

    @Test (expected = NullPointerException.class)
    public void testNullName(){
        Restaurant restaurant = new Restaurant("1", null, "123 street", null);
    }

    @Test (expected = RestaurantException.class)
    public void testBlankName() throws RestaurantException {
        restaurantService.addRestaurant(new Restaurant("2", "", "123 street", null));
    }

    @Test (expected = NullPointerException.class)
    public void testNullAddress(){
        Restaurant restaurant = new Restaurant("3", "joe's", null, null);
    }

    @Test (expected = RestaurantException.class)
    public void testBlankAddress() throws RestaurantException {
        restaurantService.addRestaurant(new Restaurant("4", "joe's", "", null));
    }

    @Test
    public void testValidAdd() throws RestaurantException {
        restaurantService.addRestaurant(new Restaurant("5", "joe's", "123 street", null));
    }

    @Test (expected = RestaurantException.class)
    public void testNullRestaurantUpdate() throws RestaurantException {
        when(restaurantDao.findOne("6")).thenReturn(null);
        restaurantService.updateMenu("6", new ArrayList<MenuItem>());
    }

    @Test (expected = RestaurantException.class)
    public void testNullMenuUpdate() throws RestaurantException {
        when(restaurantDao.findOne("7")).thenReturn(new Restaurant("7", "moe's", "234 street", new ArrayList<MenuItem>()));
        restaurantService.updateMenu("7", null);
    }

    @Test (expected = RestaurantException.class)
    public void testBadPrice() throws RestaurantException {
        ArrayList<MenuItem> items = new ArrayList<>();
        MenuItem item = new MenuItem("fooditem",-1d);
        items.add(item);
        when(restaurantDao.findOne("8")).thenReturn(new Restaurant("8", "moe's", "234 street", null));
        restaurantService.updateMenu("8", items);
    }

    @Test (expected = RestaurantException.class)
    public void testBadItemName() throws RestaurantException {
        ArrayList<MenuItem> items = new ArrayList<>();
        MenuItem item = new MenuItem("",1d);
        items.add(item);
        when(restaurantDao.findOne("9")).thenReturn(new Restaurant("9", "moe's", "234 street", null));
        restaurantService.updateMenu("9", items);
    }

    @Test
    public void testValidUpdate() throws RestaurantException {
        ArrayList<MenuItem> items = new ArrayList<>();
        MenuItem item = new MenuItem("foditem",1d);
        items.add(item);
        when(restaurantDao.findOne("10")).thenReturn(new Restaurant("10", "moe's", "234 street", null));
        restaurantService.updateMenu("10", items);
    }
}
