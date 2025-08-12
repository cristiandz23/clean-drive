package com.cleandriver.exception.paymentException;

public class PaymentProcessingException extends RuntimeException{
    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentProcessingException(String message) {
        super(message);
    }
}
