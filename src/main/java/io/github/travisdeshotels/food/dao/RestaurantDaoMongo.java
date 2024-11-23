package io.github.travisdeshotels.food.dao;

import io.github.travisdeshotels.food.beans.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDaoMongo extends MongoRepository<Restaurant, String> {
    public Restaurant findByName(String name);
}
