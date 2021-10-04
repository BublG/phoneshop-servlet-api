package com.es.phoneshop.web.servlet;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.web.filter.DosFilter;
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
import java.time.LocalDate;

import static com.es.phoneshop.constants.AppConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    private final CheckoutPageServlet servlet = new CheckoutPageServlet();
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    ServletConfig config;
    @Mock
    RequestDispatcher requestDispatcher;

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(CHECKOUT_JSP)).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute(eq(ATTRIBUTE_ORDER), any());
        verify(request).setAttribute(eq(ATTRIBUTE_PAYMENT_METHODS), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        String[] params = new String[]{PARAM_FIRST_NAME, PARAM_LAST_NAME, PARAM_PHONE, PARAM_DELIVERY_ADDRESS,
                PARAM_DELIVERY_DATE, PARAM_PAYMENT_METHOD};
        for (String param : params) {
            when(request.getParameter(param)).thenReturn(null);
        }
        servlet.doPost(request, response);
        verify(request).setAttribute(eq(ATTRIBUTE_ERRORS), any());
        verify(request).setAttribute(eq(ATTRIBUTE_ORDER), any());
        verify(request).setAttribute(eq(ATTRIBUTE_PAYMENT_METHODS), any());
        verify(requestDispatcher).forward(request, response);

        params = new String[] {PARAM_FIRST_NAME, PARAM_LAST_NAME, PARAM_PHONE, PARAM_DELIVERY_ADDRESS};
        for (String param : params) {
            when(request.getParameter(param)).thenReturn("a");
        }
        when(request.getParameter(PARAM_DELIVERY_DATE)).thenReturn(LocalDate.now().plusDays(2).toString());
        when(request.getParameter(PARAM_PAYMENT_METHOD)).thenReturn(PaymentMethod.CASH.toString());
        servlet.doPost(request, response);
        verify(response).sendRedirect(any());
    }
}
