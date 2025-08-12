package com.cleandriver.exception.customerException;

public class CustomerVehicleNotFoundException extends RuntimeException{

    public CustomerVehicleNotFoundException(String message) {
        super(message);
    }
}
