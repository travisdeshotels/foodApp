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

    private Boolean complete;

    @NonNull
    private List<OrderItem> items;
}
