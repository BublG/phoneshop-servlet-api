package com.es.phoneshop.web.servlet;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
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
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static com.es.phoneshop.constants.AppConstants.*;
import static com.es.phoneshop.web.servlet.ProductDetailsPageServlet.*;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewedListService recentlyViewedListService;

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
        request.setAttribute(ATTRIBUTE_CART, cartService.getCart(request));
        request.setAttribute(ATTRIBUTE_RECENTLY_VIEWED_LIST, recentlyViewedListService.getRecentlyViewedList(request));
        request.getRequestDispatcher(PRODUCT_LIST_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productId = Long.parseLong(request.getParameter(PARAM_PRODUCT_ID));
        final Map<Long, String> errors = new HashMap<>();
        int quantity;
        try {
            quantity = parseQuantity(request.getParameter(PARAM_QUANTITY), request.getLocale());
        } catch (ParseException e) {
            errors.put(productId, e.getMessage());
            request.setAttribute(ATTRIBUTE_ERRORS, errors);
            doGet(request, response);
            return;
        }

        Cart cart = cartService.getCart(request);
        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException e) {
            errors.put(productId, String.format("Not enough stock, available: %d. You already have: %d",
                    e.getStockAvailable(), cartService.getCurrentQuantity(cart, productId)));
            request.setAttribute(ATTRIBUTE_ERRORS, errors);
            doGet(request, response);
            return;
        }
        response.sendRedirect(String.format("%s/products?message=Added to cart successfully",
                request.getContextPath()));
    }
}
