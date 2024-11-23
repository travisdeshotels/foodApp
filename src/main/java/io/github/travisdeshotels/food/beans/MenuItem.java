package io.github.travisdeshotels.food.beans;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuItem {
    @NonNull
    private String foodItem;
    @NonNull
    private Double price;
}
