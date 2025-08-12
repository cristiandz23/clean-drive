package com.cleandriver.exception.appointmentExceptions;

public class InvalidTransitionException extends RuntimeException
{
    public InvalidTransitionException(String message) {
        super(message);
    }
}
