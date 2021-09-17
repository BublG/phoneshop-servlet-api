package com.es.phoneshop.cartService.impl;

import com.es.phoneshop.cartService.CartService;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;

public class DefaultCartService implements CartService {
    private static volatile CartService instance;
    private final ProductDao productDao;
    private Cart cart;

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static CartService getInstance() {
        if (instance == null) {
            synchronized (DefaultCartService.class) {
                if (instance == null) {
                    instance = new DefaultCartService();
                }
            }
        }
        return instance;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void add(long productId, int quantity) {
        Product product = productDao.getProduct(productId);
        cart.getItems().add(new CartItem(product, quantity));
    }
}
