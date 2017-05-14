package tk.codedojo.food.exception;

public class RestaurantNotFoundException extends Exception {
    public RestaurantNotFoundException(String message){
        super(message);
    }
}
