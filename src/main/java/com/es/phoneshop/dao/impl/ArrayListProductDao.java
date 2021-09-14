package com.es.phoneshop.dao.impl;

import com.es.phoneshop.comparator.ProductDescriptionAndPriceComparator;
import com.es.phoneshop.comparator.ProductSearchComparator;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayListProductDao implements ProductDao {
    private final List<Product> products;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private long maxId;
    private static volatile ProductDao instance;

    private ArrayListProductDao() {
        products = new ArrayList<>();
    }

    public static synchronized ProductDao getInstance() {
        ProductDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        readLock.lock();
        try {
            return products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny()
                    .orElseThrow(() -> new ProductNotFoundException(id));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Product> findProducts(String query, String sortField, String sortOrder) {
        readLock.lock();
        String[] words = (query == null) ? new String[]{} : query.toLowerCase().trim().split("\\s+");
        try {
            List<Product> list = products.stream()
                    .filter(p -> p.getPrice() != null && p.getStock() > 0)
                    .filter(p -> words.length == 0 || Arrays.stream(words)
                            .anyMatch(w -> p.getDescription().toLowerCase().contains(w)))
                    .sorted(new ProductSearchComparator(words))
                    .collect(Collectors.toList());
            if (sortField != null) {
                list.sort(new ProductDescriptionAndPriceComparator(sortField, sortOrder));
            }
            return list;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void save(Product product) {
        writeLock.lock();
        try {
            if (product.getId() != null) {
                IntStream.range(0, products.size())
                        .filter(i -> product.getId().equals(products.get(i).getId()))
                        .findAny()
                        .ifPresent(i -> products.set(i, product));
            } else {
                product.setId(maxId++);
                products.add(product);
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(Long id) {
        writeLock.lock();
        try {
            products.removeIf(product -> id.equals(product.getId()));
        } finally {
            writeLock.unlock();
        }
    }
}
