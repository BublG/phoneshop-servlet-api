package com.es.phoneshop.service;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Order;

import java.util.List;

public interface OrderService {
    Order getOrder(Cart cart);
    List<PaymentMethod> getPaymentMethods();
}
