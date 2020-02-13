package tk.codedojo.food.dao.fake;

import tk.codedojo.food.beans.Order;
import tk.codedojo.food.dao.OrderDaoType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoFake implements OrderDaoType{
    private Map<String, Order> orderMap;

    public OrderDaoFake(){
        orderMap = new HashMap<>();
    }

    public Order findOne(String id){
        return orderMap.get(id);
    }

    public void save(Order order){
        orderMap.put(order.getId(), order);
    }

    public List<Order> findAll(){
        return new ArrayList<>(orderMap.values());
    }

    public List<Order> getOpenOrdersByCustomerID(String customerID){
        return null;
    }

    public List<Order> getByCustomerID(String customerID){
        return null;
    }

    public List<Order> getOpenOrdersByRestaurantID(String restaurantID){
        return null;
    }

    public List<Order> getByRestaurantID(String restaurantID){
        return null;
    }


}
