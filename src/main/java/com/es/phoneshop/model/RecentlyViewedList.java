package com.es.phoneshop.model;

import java.util.Deque;
import java.util.LinkedList;

public class RecentlyViewedList {
    private final Deque<Product> recentlyViewedProducts;

    public RecentlyViewedList() {
        this.recentlyViewedProducts = new LinkedList<>();
    }

    public Deque<Product> getRecentlyViewedProducts() {
        return recentlyViewedProducts;
    }

    @Override
    public String toString() {
        return recentlyViewedProducts.toString();
    }
}
