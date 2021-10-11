package com.es.phoneshop.dao;

import com.es.phoneshop.enums.AdvancedSearchOption;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException;
    List<Product> findProducts(String query, String sortField, String sortOrder);
    void save(Product product);
    void delete(Long id);
    List<Product> findProductsByAdvancedSearch(String query, BigDecimal minPrice, BigDecimal maxPrice,
                                               AdvancedSearchOption option);
}
