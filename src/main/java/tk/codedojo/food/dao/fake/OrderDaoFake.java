package tk.codedojo.food.dao.fake;

import tk.codedojo.food.beans.Order;
import tk.codedojo.food.dao.OrderDaoType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoFake implements OrderDaoType{
    private Map<String, Order> orderMap;
    private int id;

    public OrderDaoFake(){
        orderMap = new HashMap<>();
        this.id = 1;
    }

    public Order findOne(String id){
        return orderMap.get(id);
    }

    public void save(Order order){
        if (order.getId() == null) {
            order.setId(String.valueOf(this.id++));
        }
        orderMap.put(order.getId(), order);
    }

    public List<Order> findAll(){
        return new ArrayList<>(orderMap.values());
    }

    public List<Order> getOpenOrdersByCustomerID(String customerID){
        List<Order> orders = new ArrayList<>();
        for (Order o : orderMap.values()){
            if (customerID.equals(o.getCustomerID())){
                orders.add(o);
            }
        }
        return orders;
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
