package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.es.phoneshop.constants.AppConstants.ATTRIBUTE_ORDER;
import static com.es.phoneshop.constants.AppConstants.ORDER_OVERVIEW_JSP;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {
    private final OrderOverviewPageServlet servlet = new OrderOverviewPageServlet();
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ServletConfig config;
    @Mock
    RequestDispatcher requestDispatcher;

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getPathInfo()).thenReturn("/123");
        when(request.getRequestDispatcher(ORDER_OVERVIEW_JSP)).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Order order = new Order();
        order.setSecureId("123");
        OrderDao orderDao = ArrayListOrderDao.getInstance();
        orderDao.save(order);
        servlet.doGet(request, response);
        verify(request).getPathInfo();
        verify(request).setAttribute(eq(ATTRIBUTE_ORDER), eq(order));
        verify(requestDispatcher).forward(request, response);
    }
}
