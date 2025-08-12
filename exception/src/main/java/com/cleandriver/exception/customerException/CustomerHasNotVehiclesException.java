package com.cleandriver.exception.customerException;

public class CustomerHasNotVehiclesException extends RuntimeException{
    public CustomerHasNotVehiclesException(String message) {
        super(message);
    }
}
