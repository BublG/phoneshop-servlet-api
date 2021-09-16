package com.es.phoneshop.exception;

public class ProductNotFoundException extends RuntimeException {

    private final long id;

    public ProductNotFoundException(String message, long id) {
        super(message);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " (id = " + id + " )";
    }
}
