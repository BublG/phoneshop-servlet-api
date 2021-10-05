package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.Product;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.es.phoneshop.constants.AppConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPriceHistoryServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpSession session;

    private final ProductDao productDao = ArrayListProductDao.getInstance();
    private final ProductPriceHistoryServlet servlet = new ProductPriceHistoryServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(servletConfig);
        productDao.save(new Product());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter(PARAM_PRODUCT_ID)).thenReturn("0");
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).getParameter(PARAM_PRODUCT_ID);
        verify(request).setAttribute(eq(ATTRIBUTE_PRODUCT), any());
        verify(request).setAttribute(eq(ATTRIBUTE_RECENTLY_VIEWED_LIST), any());
        verify(requestDispatcher).forward(request, response);
    }
}