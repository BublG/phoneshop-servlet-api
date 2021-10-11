package com.es.phoneshop.dao.impl;

import com.es.phoneshop.comparator.ProductDescriptionAndPriceComparator;
import com.es.phoneshop.comparator.ProductSearchComparator;
import com.es.phoneshop.dao.GenericDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enums.AdvancedSearchOption;
import com.es.phoneshop.exception.ModelNotFoundException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao extends GenericDao<Product> implements ProductDao {
    private static volatile ProductDao instance;

    private ArrayListProductDao() {
    }

    public static ProductDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        try {
            return get(id);
        } catch (ModelNotFoundException e) {
            throw new ProductNotFoundException("Product with given id not found", id);
        }
    }

    @Override
    public List<Product> findProducts(String query, String sortField, String sortOrder) {
        readLock.lock();
        String[] words = (query == null) ? new String[]{} : query.toLowerCase().trim().split("\\s+");
        try {
            List<Product> list = items.stream()
                    .filter(p -> p.getPrice() != null && p.getStock() > 0)
                    .filter(p -> words.length == 0 || Arrays.stream(words)
                            .anyMatch(w -> p.getDescription().toLowerCase().contains(w)))
                    .sorted(new ProductSearchComparator(words))
                    .collect(Collectors.toList());
            if (sortField != null) {
                list.sort(new ProductDescriptionAndPriceComparator(sortField.toUpperCase(), sortOrder.toUpperCase()));
            }
            return list;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Product> findProductsByAdvancedSearch(String query, BigDecimal minPrice, BigDecimal maxPrice, AdvancedSearchOption option) {
        readLock.lock();
        String[] words = (query == null) ? new String[]{} :
                query.toLowerCase().trim().split("\\s+");
        try {
            return searchProductsByDescription(option, words)
                    .filter(product -> minPrice == null || product.getPrice().compareTo(minPrice) >= 0)
                    .filter(product -> maxPrice == null || product.getPrice().compareTo(maxPrice) <= 0)
                    .sorted(new ProductSearchComparator(words))
                    .collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    private Stream<Product> searchProductsByDescription(AdvancedSearchOption option, String[] words) {
        if (option == AdvancedSearchOption.ANY_WORD) {
            return items.stream()
                    .filter(p -> p.getPrice() != null && p.getStock() > 0)
                    .filter(p -> words.length == 0 || Arrays.stream(words)
                            .anyMatch(w -> p.getDescription().toLowerCase().contains(w)));

        } else {
            return items.stream()
                    .filter(p -> p.getPrice() != null && p.getStock() > 0)
                    .filter(p -> words.length == 0 || Arrays.stream(words)
                            .allMatch(w -> p.getDescription().toLowerCase().contains(w)));
        }
    }
}
