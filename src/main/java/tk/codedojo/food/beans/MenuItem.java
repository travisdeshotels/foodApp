package tk.codedojo.food.beans;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class MenuItem {
    @NonNull
    private String foodItem;
    @NonNull
    private Double price;
}
