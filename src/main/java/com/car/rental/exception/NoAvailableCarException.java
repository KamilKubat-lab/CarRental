package com.car.rental.exception;

public class NoAvailableCarException extends RuntimeException {
    public NoAvailableCarException(String message) {
        super(message);
    }
}
