package com.es.phoneshop.service;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(Cart cart, long productId, int quantity) throws OutOfStockException;
    void update(Cart cart, long productId, int quantity) throws OutOfStockException;
    void delete(Cart cart, long productId);
    int getCurrentQuantity(Cart cart, long productId);
}
