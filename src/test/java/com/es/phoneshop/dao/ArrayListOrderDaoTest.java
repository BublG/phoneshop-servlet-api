package com.es.phoneshop.dao;


import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.DefaultOrderService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayListOrderDaoTest {
    private OrderDao orderDao;
    private OrderService orderService;

    @Before
    public void setup() {
        orderDao = ArrayListOrderDao.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Test
    public void testSaveOrder() {
        Order order = new Order();
        orderDao.save(order);
        assertNotNull(order.getId());

        Order savedOrder = orderDao.getOrder(order.getId());
        assertNotNull(savedOrder);
        assertEquals(order.getId(), savedOrder.getId());
    }

    @Test
    public void testGetOrderBySecureId() {
        Order order = new Order();
        orderService.placeOrder(order);
        assertNotNull(order.getSecureId());

        Order savedOrder = orderDao.getOrderBySecureId(order.getSecureId());
        assertNotNull(savedOrder);
        assertEquals(order.getId(), savedOrder.getId());
        assertEquals(order.getSecureId(), savedOrder.getSecureId());
    }
}
