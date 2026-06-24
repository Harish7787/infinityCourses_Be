package com.payment_gateway.Course.exception;



public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}