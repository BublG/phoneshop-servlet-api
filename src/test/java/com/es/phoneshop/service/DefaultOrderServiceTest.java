package com.es.phoneshop.service;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.service.impl.DefaultOrderService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class DefaultOrderServiceTest {
    private OrderService orderService;
    private OrderDao orderDao;

    @Before
    public void setup() {
        orderService = DefaultOrderService.getInstance();
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Test
    public void testGetPaymentMethods() {
        assertArrayEquals(PaymentMethod.values(), orderService.getPaymentMethods().toArray());
    }

    @Test
    public void testPlaceOrder() {
        Order order = new Order();
        orderService.placeOrder(order);
        assertNotNull(order.getSecureId());

        Order placedOrder = orderDao.getOrderBySecureId(order.getSecureId());
        assertNotNull(placedOrder);
        assertEquals(order.getId(), placedOrder.getId());
    }

    @Test
    public void testGetOrder() {
        Cart cart = new Cart();
        BigDecimal cartTotalCost = new BigDecimal(100);
        int totalQuantity = 3;
        cart.setTotalCost(cartTotalCost);
        cart.setTotalQuantity(totalQuantity);
        Order order = orderService.getOrder(cart);

        assertEquals(cartTotalCost, order.getSubTotal());
        assertEquals(totalQuantity, order.getTotalQuantity());
        assertEquals(cartTotalCost, order.getTotalCost().subtract(order.getDeliveryCost()));
    }
}
