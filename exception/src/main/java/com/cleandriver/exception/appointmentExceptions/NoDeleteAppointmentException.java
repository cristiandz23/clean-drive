package com.cleandriver.exception.appointmentExceptions;

public class NoDeleteAppointmentException extends RuntimeException{
    public NoDeleteAppointmentException(String message) {
        super(message);
    }
}
