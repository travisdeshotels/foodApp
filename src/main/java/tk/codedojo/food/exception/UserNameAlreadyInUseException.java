package tk.codedojo.food.exception;

public class UserNameAlreadyInUseException extends Exception {
    public UserNameAlreadyInUseException(String message){
        super(message);
    }
}
