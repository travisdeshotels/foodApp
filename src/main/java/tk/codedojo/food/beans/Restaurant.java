package tk.codedojo.food.beans;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Restaurant {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String address;
    private List<MenuItem> menuItems;
}
