package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
                    .filter(item -> item.getProduct() != null && item.getProduct().getId().equals(productId))
                    .findAny();
            int updatedQuantity = quantity + optionalCartItem.map(CartItem::getQuantity).orElse(0);
            if (product.getStock() < updatedQuantity) {
                throw new OutOfStockException(product, updatedQuantity, product.getStock());
            }
            if (optionalCartItem.isPresent()) {
                optionalCartItem.get().setQuantity(updatedQuantity);
            } else {
                cart.getItems().add(new CartItem(product, quantity));
            }
        }
        recalculateCart(cart);
    }

    @Override
    public void update(Cart cart, long productId, int quantity) throws OutOfStockException {
        synchronized (cart) {
            Product product = productDao.getProduct(productId);
            CartItem cartItem = cart.getItems().stream()
                    .filter(item -> item.getProduct() != null && item.getProduct().getId().equals(productId))
                    .findAny().get();
            if (product.getStock() < quantity) {
                throw new OutOfStockException(product, quantity, product.getStock());
            }
            cartItem.setQuantity(quantity);
        }
        recalculateCart(cart);
    }

    @Override
    public void delete(Cart cart, long productId) {
        synchronized (cart) {
            cart.getItems().removeIf(item -> item.getProduct() != null &&
                    item.getProduct().getId().equals(productId));
            recalculateCart(cart);
        }
    }

    @Override
    public int getCurrentQuantity(Cart cart, long productId) {
        synchronized (cart) {
            return cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findAny()
                    .map(CartItem::getQuantity)
                    .orElse(0);
        }
    }

    private void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity)
                .reduce(0, Integer::sum));
        cart.setTotalCost(cart.getItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice()
                        .multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(new BigDecimal(0), BigDecimal::add));
    }

    @Override
    public void clearCart(HttpServletRequest request) {
        synchronized (request.getSession()) {
            request.getSession().removeAttribute(CART_SESSION_ATTRIBUTE);
        }
    }
}
