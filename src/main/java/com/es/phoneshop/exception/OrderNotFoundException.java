package com.es.phoneshop.exception;

public class OrderNotFoundException extends RuntimeException {
    private final String id;

    public OrderNotFoundException(String message, String id) {
        super(message);
        this.id = id;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " (id = " + id + " )";
    }
}
