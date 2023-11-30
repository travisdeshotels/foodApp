package tk.codedojo.food.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Restaurant {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String address;
    private List<MenuItem> menuItems;
    public Restaurant(RestaurantInfo r){
        this.name = r.getRestaurantName();
        this.address = r.getRestaurantAddress();
    }
}
