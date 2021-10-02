package com.es.phoneshop.service.impl;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.service.OrderService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {
    private static volatile OrderService instance;

    private DefaultOrderService() {
    }

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (DefaultOrderService.class) {
                if (instance == null) {
                    instance = new DefaultOrderService();
                }
            }
        }
        return instance;
    }

    @Override
    public Order getOrder(Cart cart) {
        synchronized (cart) {
            Order order = new Order();
            order.setItems(cart.getItems().stream()
                    .map(cartItem -> (CartItem) cartItem.clone())
                    .collect(Collectors.toList()));
            order.setTotalQuantity(cart.getTotalQuantity());
            order.setSubTotal(cart.getTotalCost());
            order.setDeliveryCost(calculateDeliveryCost());
            order.setTotalCost(order.getDeliveryCost().add(order.getSubTotal()));
            return order;
        }
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    private BigDecimal calculateDeliveryCost() {
        return new BigDecimal(10);
    }
}
