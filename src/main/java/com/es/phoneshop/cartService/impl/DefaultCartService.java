package com.es.phoneshop.cartService.impl;

import com.es.phoneshop.cartService.CartService;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class DefaultCartService implements CartService {
    public static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";
    private static volatile CartService instance;
    private final ProductDao productDao;

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
    public Cart getCart(HttpServletRequest request) {
        synchronized (request.getSession()) {
            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        }
    }

    @Override
    public void add(Cart cart, long productId, int quantity) throws OutOfStockException {
        synchronized (cart) {
            Product product = productDao.getProduct(productId);
            Optional<CartItem> optionalCartItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findAny();
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                int updatedQuantity = quantity + cartItem.getQuantity();
                if (product.getStock() < updatedQuantity) {
                    throw new OutOfStockException(product, updatedQuantity, product.getStock());
                }
                cartItem.setQuantity(quantity + cartItem.getQuantity());
            } else {
                if (product.getStock() < quantity) {
                    throw new OutOfStockException(product, quantity, product.getStock());
                }
                cart.getItems().add(new CartItem(product, quantity));
            }
        }
    }

    @Override
    public int getCurrentQuantity(Cart cart, long productId) {
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findAny()
                .map(CartItem::getQuantity)
                .orElse(0);
    }
}
