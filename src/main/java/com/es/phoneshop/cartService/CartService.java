package com.es.phoneshop.cartService;

import com.es.phoneshop.model.Cart;

public interface CartService {
    Cart getCart();
    void add(long productId, int quantity);
}
