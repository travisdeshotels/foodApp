package io.github.travisdeshotels.food.beans;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RestaurantInfo {
    @NonNull
    private String restaurantName;
    @NonNull
    private String restaurantAddress;
}
