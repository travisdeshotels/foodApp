package io.github.travisdeshotels.food;

import io.github.travisdeshotels.food.beans.MenuItem;
import io.github.travisdeshotels.food.beans.Restaurant;
import io.github.travisdeshotels.food.dao.RestaurantDaoMongo;
import io.github.travisdeshotels.food.service.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.github.travisdeshotels.food.exception.RestaurantException;

import java.util.ArrayList;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantTest {
    @Mock
    private RestaurantDaoMongo restaurantDao;
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    public void setupMock(){
        restaurantService = new RestaurantServiceImpl(restaurantDao);
    }

    @Test
    public void testNullName(){
        assertThrows(NullPointerException.class, () ->
                new Restaurant("1", null, "123 street", null));
    }

    @Test
    public void testBlankName(){
        assertThrows(RestaurantException.class, () ->
                restaurantService.addRestaurant(new Restaurant("2", "", "123 street", null)));
    }

    @Test
    public void testNullAddress(){
        assertThrows(NullPointerException.class, () ->
                new Restaurant("3", "joe's", null, null));
    }

    @Test
    public void testBlankAddress(){
        assertThrows(RestaurantException.class, () ->
                restaurantService.addRestaurant(new Restaurant("4", "joe's", "", null)));
    }

    @Test
    public void testValidAdd() throws RestaurantException {
        when(restaurantDao.save(any())).thenReturn(new Restaurant("5", "joe's", "123 street", null));
        String id = restaurantService.addRestaurant(new Restaurant("5", "joe's", "123 street", null));
        assertEquals("5", id);
    }

    @Test
    public void testNullRestaurantUpdate() throws RestaurantException {
        when(restaurantDao.findById("6")).thenReturn(Optional.empty());
        assertThrows(RestaurantException.class, () ->
                restaurantService.updateMenu("6", new ArrayList<>()));
    }

    @Test
    public void testNullMenuUpdate() throws RestaurantException {
        when(restaurantDao.findById("7")).thenReturn(Optional.of(
                new Restaurant("7", "moe's", "234 street", new ArrayList<>())));
        assertThrows(RestaurantException.class, () ->
                restaurantService.updateMenu("7", null));
    }

    @Test
    public void testBadPrice() throws RestaurantException {
        ArrayList<MenuItem> items = new ArrayList<>();
        MenuItem item = new MenuItem("fooditem",-1d);
        items.add(item);
        when(restaurantDao.findById("8")).thenReturn(Optional.of(
                new Restaurant("8", "moe's", "234 street", null)));
        assertThrows(RestaurantException.class, () ->
                restaurantService.updateMenu("8", items));

    }

    @Test
    public void testBadItemName() throws RestaurantException {
        ArrayList<MenuItem> items = new ArrayList<>();
        MenuItem item = new MenuItem("",1d);
        items.add(item);
        when(restaurantDao.findById("9")).thenReturn(Optional.of(
                new Restaurant("9", "moe's", "234 street", null)));
        assertThrows(RestaurantException.class, () ->
                restaurantService.updateMenu("9", items));
    }

    @Test
    public void testValidUpdate() throws RestaurantException {
        ArrayList<MenuItem> items = new ArrayList<>();
        MenuItem item = new MenuItem("foditem",1d);
        items.add(item);
        when(restaurantDao.findById("10")).thenReturn(Optional.of(
                new Restaurant("10", "moe's", "234 street", null)));
        restaurantService.updateMenu("10", items);
    }
}
