package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.constants.AppConstants.ATTRIBUTE_ORDER;
import static com.es.phoneshop.constants.AppConstants.ORDER_OVERVIEW_JSP;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secureOrderId = request.getPathInfo().substring(1);
        request.setAttribute(ATTRIBUTE_ORDER, orderDao.getOrderBySecureId(secureOrderId));
        request.getRequestDispatcher(ORDER_OVERVIEW_JSP).forward(request, response);
    }
}
