package com.es.phoneshop.recentlyViewedListService.impl;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.RecentlyViewedList;
import com.es.phoneshop.recentlyViewedListService.RecentlyViewedListService;

import javax.servlet.http.HttpServletRequest;
import java.util.Deque;

public class DefaultRecentlyViewedListService implements RecentlyViewedListService {
    public static final String RECENTLY_VIEWED_LIST_SESSION_ATTRIBUTE =
            DefaultRecentlyViewedListService.class.getName() + ".recently_viewed_list";
    private static volatile RecentlyViewedListService instance;

    private DefaultRecentlyViewedListService() {
    }

    public static RecentlyViewedListService getInstance() {
        if (instance == null) {
            synchronized (DefaultRecentlyViewedListService.class) {
                if (instance == null) {
                    instance = new DefaultRecentlyViewedListService();
                }
            }
        }
        return instance;
    }

    @Override
    public RecentlyViewedList getRecentlyViewedList(HttpServletRequest request) {
        synchronized (request.getSession()) {
            RecentlyViewedList recentlyViewedList = (RecentlyViewedList) request.getSession()
                    .getAttribute(RECENTLY_VIEWED_LIST_SESSION_ATTRIBUTE);
            if (recentlyViewedList == null) {
                request.getSession().setAttribute(RECENTLY_VIEWED_LIST_SESSION_ATTRIBUTE,
                        recentlyViewedList = new RecentlyViewedList());
            }
            return recentlyViewedList;
        }
    }

    @Override
    public void add(RecentlyViewedList recentlyViewedList, Product product) {
        synchronized (recentlyViewedList) {
            Deque<Product> recentlyViewedProducts = recentlyViewedList.getRecentlyViewedProducts();
            recentlyViewedProducts.removeIf(p -> p.getId().equals(product.getId()));
            recentlyViewedProducts.addFirst(product);
            if (recentlyViewedProducts.size() > 3) {
                recentlyViewedProducts.removeLast();
            }
        }
    }
}
