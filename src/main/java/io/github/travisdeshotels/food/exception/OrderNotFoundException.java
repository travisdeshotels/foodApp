package io.github.travisdeshotels.food.exception;

public class OrderNotFoundException extends OrderException {
    public OrderNotFoundException(String message){
        super(message);
    }
}
