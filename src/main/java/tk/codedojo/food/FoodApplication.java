package tk.codedojo.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.beans.security.Role;
import tk.codedojo.food.dao.mongo.CustomerDaoMongo;

@SpringBootApplication
public class FoodApplication implements CommandLineRunner {
	@Autowired
	private CustomerDaoMongo dao;

	public static void main(String[] args) {
		SpringApplication.run(FoodApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer admin = dao.getCustomerByRole(Role.ADMIN);
		if(null == admin){
			Customer customer = new Customer();
			customer.setLastName("admin");
			customer.setFirstName("admin");
			customer.setUserName("admin");
			customer.setEmail("admin@fake.gov");
			customer.setRole(Role.ADMIN);
			customer.setPassword(new BCryptPasswordEncoder().encode("admin"));
			dao.save(customer);
		}
	}
}
