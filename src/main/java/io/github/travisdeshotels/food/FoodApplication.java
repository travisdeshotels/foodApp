package io.github.travisdeshotels.food;

import io.github.travisdeshotels.food.beans.Customer;
import io.github.travisdeshotels.food.beans.security.Role;
import io.github.travisdeshotels.food.dao.CustomerDaoMongo;
import io.github.travisdeshotels.food.util.FoodUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodApplication implements CommandLineRunner {
	@Autowired
	private CustomerDaoMongo dao;

	@Value("${admin.account.data}")
	private String adminData;

	public static void main(String[] args) {
		SpringApplication.run(FoodApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer admin = dao.getCustomerByRole(Role.ADMIN);
		if(null == admin){
			Customer adminCustomer = FoodUtil.CustomerFromCSV(this.adminData);
			adminCustomer.setRole(Role.ADMIN);
			dao.save(adminCustomer);
		}
	}
}
