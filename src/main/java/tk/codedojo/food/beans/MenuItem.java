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
    @Size(min=1)
    private String foodItem;

    @NonNull
    @Min(value=0L)
    private Double price;
    //TODO have price getter return properly formatted price
}
