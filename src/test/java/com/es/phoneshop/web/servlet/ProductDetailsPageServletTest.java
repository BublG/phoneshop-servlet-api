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
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static com.es.phoneshop.constants.AppConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    private final ProductDao productDao = ArrayListProductDao.getInstance();
    private final ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
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

    @Before
    public void setup() throws ServletException {
        servlet.init(servletConfig);
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("TEST", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/0");
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).getPathInfo();
        verify(request).setAttribute(eq(ATTRIBUTE_PRODUCT), any());
        verify(request).setAttribute(eq(ATTRIBUTE_RECENTLY_VIEWED_LIST), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getLocale()).thenReturn(Locale.getDefault());
        when(request.getParameter(PARAM_QUANTITY)).thenReturn("a");
        servlet.doPost(request, response);

        verify(request).setAttribute(eq(ATTRIBUTE_ERROR), eq("Not a number"));

        verify(request).setAttribute(eq(ATTRIBUTE_PRODUCT), any());
        verify(request).setAttribute(eq(ATTRIBUTE_RECENTLY_VIEWED_LIST), any());

        when(request.getParameter(PARAM_QUANTITY)).thenReturn("50");
        servlet.doPost(request, response);

        verify(request).setAttribute(eq(ATTRIBUTE_ERROR), eq("Not enough stock, available: " + 30
                + ". You already have: " + 0));


        when(request.getParameter(PARAM_QUANTITY)).thenReturn("1");
        servlet.doPost(request, response);
        verify(response).sendRedirect(any());
    }
}