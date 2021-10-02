package com.es.phoneshop.web.servlet;
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
public class CartPageServletTest {
    private final CartPageServlet servlet = new CartPageServlet();
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private ServletConfig config;
    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void setup() throws ServletException {
        servlet.init(config);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute(eq(ATTRIBUTE_CART), any());
        verify(request).getRequestDispatcher(CART_JSP);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(request).getParameterValues(eq(PARAM_PRODUCT_ID));
        verify(request).getParameterValues(eq(PARAM_QUANTITY));
        verify(response).sendRedirect(any());

        when(request.getParameterValues(PARAM_PRODUCT_ID)).thenReturn(new String[] {"0", "1"});
        when(request.getParameterValues(PARAM_QUANTITY)).thenReturn(new String[] {"5", "8"});
        servlet.doPost(request, response);

        verify(request).setAttribute(eq(ATTRIBUTE_ERRORS), any());
        verify(servlet).doGet(request, response);
    }
}
