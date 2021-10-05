package com.es.phoneshop.web.servlet;

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

import static com.es.phoneshop.constants.AppConstants.*;

public class ProductPriceHistoryServlet extends HttpServlet {
    private ProductDao productDao;
    private RecentlyViewedListService recentlyViewedListService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedListService = DefaultRecentlyViewedListService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter(PARAM_PRODUCT_ID);
        request.setAttribute(ATTRIBUTE_PRODUCT, productDao.getProduct(Long.parseLong(id)));
        request.setAttribute(ATTRIBUTE_RECENTLY_VIEWED_LIST, recentlyViewedListService.getRecentlyViewedList(request));
        request.getRequestDispatcher(PRICE_HISTORY_JSP).forward(request, response);
    }
}
