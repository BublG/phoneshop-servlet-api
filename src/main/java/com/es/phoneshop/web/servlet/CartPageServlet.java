package com.es.phoneshop.web.servlet;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static com.es.phoneshop.web.servlet.ProductDetailsPageServlet.ATTRIBUTE_CART;
import static com.es.phoneshop.web.servlet.ProductDetailsPageServlet.PARAM_QUANTITY;

public class CartPageServlet extends HttpServlet {
    public static final String PARAM_PRODUCT_ID = "productId";
    private static final String CART_JSP = "/WEB-INF/pages/cart.jsp";
    public static final String ATTRIBUTE_ERRORS = "errors";
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ATTRIBUTE_CART, cartService.getCart(request));
        request.getRequestDispatcher(CART_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        String[] productIds = request.getParameterValues(PARAM_PRODUCT_ID);
        String[] quantities = request.getParameterValues(PARAM_QUANTITY);
        Map<Long, String> errors = new HashMap<>();

        if (productIds != null) {
            for (int i = 0; i < productIds.length; i++) {
                long productId = Long.parseLong(productIds[i]);
                int quantity;
                try {
                    quantity = ProductDetailsPageServlet.parseQuantity(quantities[i], request.getLocale());
                    cartService.update(cart, productId, quantity);
                } catch (ParseException e) {
                    errors.put(productId, "Not a number");
                } catch (OutOfStockException e1) {
                    errors.put(productId, String.format("Not enough stock, available: %d. You already have: %d",
                            e1.getStockAvailable(), cartService.getCurrentQuantity(cart, productId)));
                }
            }
        }
        if (errors.isEmpty()) {
            response.sendRedirect(String.format("%s/cart?message=Cart updated successfully",
                    request.getContextPath()));
        } else {
            request.setAttribute(ATTRIBUTE_ERRORS, errors);
            doGet(request, response);
        }
    }
}