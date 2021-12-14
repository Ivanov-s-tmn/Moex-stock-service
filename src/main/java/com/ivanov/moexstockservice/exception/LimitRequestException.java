package com.ivanov.moexstockservice.exception;

public class LimitRequestException extends RuntimeException {

    public LimitRequestException(String message) {
        super(message);
    }
}
