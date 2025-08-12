package com.cleandriver.exception.employedException;

public class EmployedIsNotActiveException extends RuntimeException{
    public EmployedIsNotActiveException(String message) {
        super(message);
    }
}
