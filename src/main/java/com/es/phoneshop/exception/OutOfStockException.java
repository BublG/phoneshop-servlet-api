package com.es.phoneshop.exception;

import com.es.phoneshop.model.Product;

public class OutOfStockException extends Exception {
    private Product product;
    private int stockRequested;
    private int stockAvailable;

    public OutOfStockException(Product product, int stockRequested, int stockAvailable) {
        this.product = product;
        this.stockRequested = stockRequested;
        this.stockAvailable = stockAvailable;
    }

    public int getStockAvailable() {
        return stockAvailable;
    }
}
