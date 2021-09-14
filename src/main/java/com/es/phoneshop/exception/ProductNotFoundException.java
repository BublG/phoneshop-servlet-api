package com.es.phoneshop.exception;

public class ProductNotFoundException extends RuntimeException {

    private final long id;

    public ProductNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return "Product with id " + id + " not found";
    }
}
