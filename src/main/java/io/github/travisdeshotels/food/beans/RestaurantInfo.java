package io.github.travisdeshotels.food.beans;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RestaurantInfo {
    private String restaurantName;
    private String restaurantAddress;
}
