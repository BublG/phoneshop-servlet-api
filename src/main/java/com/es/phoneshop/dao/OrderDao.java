package com.es.phoneshop.dao;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.Order;

public interface OrderDao {
    Order getOrder(Long id) throws OrderNotFoundException;
    Order getOrderBySecureId(String secureId) throws OrderNotFoundException;
    void save(Order order);
}
