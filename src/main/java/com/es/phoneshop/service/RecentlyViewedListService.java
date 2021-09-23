package com.es.phoneshop.service;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.RecentlyViewedList;

import javax.servlet.http.HttpServletRequest;

public interface RecentlyViewedListService {
    RecentlyViewedList getRecentlyViewedList(HttpServletRequest request);
    void add(RecentlyViewedList recentlyViewedList, Product product);
}
