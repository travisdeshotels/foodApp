package tk.codedojo.food.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tk.codedojo.food.beans.Restaurant;

@Repository
public interface RestaurantDaoMongo extends MongoRepository<Restaurant, String> {
    public Restaurant findByName(String name);
}
