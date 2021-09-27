package com.es.phoneshop.web.servlet;

import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.service.RecentlyViewedListService;
import com.es.phoneshop.service.impl.DefaultRecentlyViewedListService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewedListService recentlyViewedListService;
    private static final String ATTRIBUTE_PRODUCTS = "products";
    private static final String PARAM_QUERY = "query";
    private static final String PARAM_SORT = "sort";
    private static final String PARAM_ORDER = "order";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentlyViewedListService = DefaultRecentlyViewedListService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(PARAM_QUERY);
        String sortField = request.getParameter(PARAM_SORT);
        String sortOrder = request.getParameter(PARAM_ORDER);
        request.setAttribute(ATTRIBUTE_PRODUCTS, productDao.findProducts(query, sortField, sortOrder));
        request.setAttribute(ProductDetailsPageServlet.ATTRIBUTE_CART, cartService.getCart(request));
        request.setAttribute(ProductDetailsPageServlet.ATTRIBUTE_RECENTLY_VIEWED_LIST, recentlyViewedListService.getRecentlyViewedList(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
