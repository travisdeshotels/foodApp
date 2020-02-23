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
	@Getter
	private static boolean cancelOrderFeatureEnabled;
	@Getter
	private static boolean renameRestaurantFeatureEnabled;

	static {
		restaurantDaoFake = new RestaurantDaoFake();
		customerDaoFake = new CustomerDaoFake();
		cancelOrderFeatureEnabled = Boolean.parseBoolean(System.getenv("CANCEL_ORDER"));
		renameRestaurantFeatureEnabled = Boolean.parseBoolean(System.getenv("RENAME_RESTAURANT"));
	}

	public static void main(String[] args) {
		SpringApplication.run(FoodApplication.class, args);
	}
}
