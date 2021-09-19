package com.es.phoneshop.web.servlet;

import com.es.phoneshop.cartService.CartService;
import com.es.phoneshop.cartService.impl.DefaultCartService;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.recentlyViewedListService.RecentlyViewedListService;
import com.es.phoneshop.recentlyViewedListService.impl.DefaultRecentlyViewedListService;

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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentlyViewedListService = DefaultRecentlyViewedListService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortField = request.getParameter("sort");
        String sortOrder = request.getParameter("order");
        request.setAttribute("products", productDao.findProducts(query, sortField, sortOrder));
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("recentlyViewedList", recentlyViewedListService.getRecentlyViewedList(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
