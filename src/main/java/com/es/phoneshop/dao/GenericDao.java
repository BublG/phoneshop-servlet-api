package com.es.phoneshop.dao;

import com.es.phoneshop.exception.ModelNotFoundException;
import com.es.phoneshop.model.ItemWithId;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public abstract class GenericDao<T extends ItemWithId> {
    protected final List<T> items;
    private long maxId;
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();
    protected final Lock readLock = lock.readLock();
    protected final Lock writeLock = lock.writeLock();

    public GenericDao() {
        items = new ArrayList<>();
    }

    public T get(Long id) {
        readLock.lock();
        try {
            return items.stream()
                    .filter(item -> id.equals(item.getId()))
                    .findAny()
                    .orElseThrow(() -> new ModelNotFoundException("Model with given id not found"));
        } finally {
            readLock.unlock();
        }
    }

    public void save(T item) {
        writeLock.lock();
        try {
            if (item.getId() != null) {
                IntStream.range(0, items.size())
                        .filter(i -> item.getId().equals(items.get(i).getId()))
                        .findAny()
                        .ifPresent(i -> items.set(i, item));
            } else {
                item.setId(maxId++);
                items.add(item);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void delete(Long id) {
        writeLock.lock();
        try {
            items.removeIf(product -> id.equals(product.getId()));
        } finally {
            writeLock.unlock();
        }
    }
}
