package com.es.phoneshop.cartService;

import com.es.phoneshop.cartService.impl.DefaultCartService;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class DefaultCartServiceTest {
    private final Currency usd = Currency.getInstance("USD");
    private ProductDao productDao;
    private CartService cartService;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Test
    public void testAddToCart() throws OutOfStockException {
        Product product = new Product("TEST", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        productDao.save(product);
        Cart cart = new Cart();
        cartService.add(cart, product.getId(), 1);
        assertEquals(1, cart.getItems().size());

        cartService.add(cart, product.getId(), 3);
        assertEquals(1, cart.getItems().size());
        assertEquals(4, cart.getItems().get(0).getQuantity());
    }
}
