package tk.codedojo.food.beans;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
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
    @Size(min=1)
    private String customerID;
    @NonNull
    @Size(min=1)
    private String restaurantID;
    @NonNull
    private List<MenuItem> items;
}
