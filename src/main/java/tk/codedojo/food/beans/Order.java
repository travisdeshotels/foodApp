package tk.codedojo.food.beans;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Order {
    @Id
    private String id;
    @NonNull
    private String customerID;
    @NonNull
    private String restaurantID;
    private OrderStatus status = OrderStatus.OPEN;

    @NonNull
    private List<OrderItem> items;

    public Order(String id, String customerID, String restaurantID, List<OrderItem> items){
        this.id = id;
        this.customerID = customerID;
        this.restaurantID = restaurantID;
        this.items = items;
    }
}
