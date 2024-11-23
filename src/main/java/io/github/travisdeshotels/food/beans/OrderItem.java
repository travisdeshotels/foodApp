package io.github.travisdeshotels.food.beans;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderItem {
    @NonNull
    MenuItem menuItem;
    @NonNull
    Integer quantity;
}
