package tk.codedojo.food.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.codedojo.food.beans.Customer;

public class FoodUtil {
    public static Customer CustomerFromCSV(String csv){
        String[] parts = csv.split(",");
        Customer c = new Customer();
        c.setFirstName(parts[0]);
        c.setLastName(parts[1]);
        c.setUserName(parts[2]);
        c.setPassword(new BCryptPasswordEncoder().encode(parts[3]));
        c.setEmail(parts[4]);
        return c;
    }
}
