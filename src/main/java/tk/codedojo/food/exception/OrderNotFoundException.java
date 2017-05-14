package tk.codedojo.food.exception;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String message){
        super(message);
    }
}
