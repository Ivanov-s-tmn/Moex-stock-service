package com.ivanov.moexstockservice.exception;

public class BondNotFoundException extends RuntimeException {
    public BondNotFoundException(String message) {
        super(message);
    }
}
