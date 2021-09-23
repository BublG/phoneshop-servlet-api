package com.es.phoneshop.service;

import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCartServiceTest {
    private final Currency usd = Currency.getInstance("USD");
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    @Before
    public void setup() {
        cartService = DefaultCartService.getInstance();
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testGetCart() {
        when(session.getAttribute(DefaultCartService.CART_SESSION_ATTRIBUTE)).thenReturn(null);
        Cart cart = cartService.getCart(request);
        verify(session).getAttribute(DefaultCartService.CART_SESSION_ATTRIBUTE);
        verify(session).setAttribute(eq(DefaultCartService.CART_SESSION_ATTRIBUTE), any());
        assertNotNull(cart);
    }

    @Test
    public void testAddToCart() throws OutOfStockException {
        Product product = new Product("TEST", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        product.setId(0L);
        Cart cart = new Cart();
        cartService.add(cart, product.getId(), 1);
        assertEquals(1, cart.getItems().size());

        cartService.add(cart, product.getId(), 3);
        assertEquals(1, cart.getItems().size());
        assertEquals(4, cart.getItems().get(0).getQuantity());
    }
}
