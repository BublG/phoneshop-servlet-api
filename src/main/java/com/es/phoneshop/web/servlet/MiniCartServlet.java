package com.es.phoneshop.web.servlet;

import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.web.servlet.ProductDetailsPageServlet.ATTRIBUTE_CART;

public class MiniCartServlet extends HttpServlet {
    private CartService cartService;
    private static final String MINICART_JSP = "/WEB-INF/pages/minicart.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ATTRIBUTE_CART, cartService.getCart(request));
        request.getRequestDispatcher(MINICART_JSP).include(request, response);
    }
}
