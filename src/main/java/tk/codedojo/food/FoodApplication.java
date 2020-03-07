package tk.codedojo.food;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.codedojo.food.dao.fake.CustomerDaoFake;
import tk.codedojo.food.dao.fake.RestaurantDaoFake;

@SpringBootApplication
public class FoodApplication {
	@Getter
	private static RestaurantDaoFake restaurantDaoFake;
	@Getter
	private static CustomerDaoFake customerDaoFake;

	static {
		restaurantDaoFake = new RestaurantDaoFake();
		customerDaoFake = new CustomerDaoFake();
	}

	public static void main(String[] args) {
		SpringApplication.run(FoodApplication.class, args);
	}
}
