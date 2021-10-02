package com.es.phoneshop.web.servlet;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.service.impl.DefaultOrderService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.es.phoneshop.constants.AppConstants.*;

public class CheckoutPageServlet extends HttpServlet {
    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = DefaultOrderService.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute(ATTRIBUTE_ORDER, orderService.getOrder(cart));
        request.setAttribute(ATTRIBUTE_PAYMENT_METHODS, orderService.getPaymentMethods());
        request.getRequestDispatcher(CHECKOUT_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);
        Map<String, String> errors = new HashMap<>();

        setRequiredParam(request, PARAM_FIRST_NAME, errors, order::setFirstName);
        setRequiredParam(request, PARAM_LAST_NAME, errors, order::setLastName);
        setRequiredParam(request, PARAM_PHONE, errors, order::setPhone);
        setRequiredParam(request, PARAM_DELIVERY_ADDRESS, errors, order::setDeliveryAddress);
        setPaymentMethod(request, errors, order);
        setDeliveryDate(request, errors, order);

        if (!errors.isEmpty()) {
            request.setAttribute(ATTRIBUTE_ERRORS, errors);
            request.setAttribute(ATTRIBUTE_ORDER, order);
            request.setAttribute(ATTRIBUTE_PAYMENT_METHODS, orderService.getPaymentMethods());
            request.getRequestDispatcher(CHECKOUT_JSP).forward(request, response);
        }
    }

    private void setRequiredParam(HttpServletRequest request, String param, Map<String, String> errors,
                                  Consumer<String> consumer) {
        String value = request.getParameter(param);
        if (value == null || value.isEmpty()) {
            errors.put(param, "Value is required");
        } else {
            consumer.accept(value);
        }
    }

    private void setPaymentMethod(HttpServletRequest request, Map<String, String> errors, Order order) {
        String method = request.getParameter(PARAM_PAYMENT_METHOD);
        if (method == null || method.isEmpty()) {
            errors.put(PARAM_PAYMENT_METHOD, "Value is required");
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(method));
        }
    }

    private void setDeliveryDate(HttpServletRequest request, Map<String, String> errors, Order order) {
        String date = request.getParameter(PARAM_DELIVERY_DATE);
        if (date == null || date.isEmpty()) {
            errors.put(PARAM_DELIVERY_DATE, "Value is required");
        } else {
            try {
                LocalDate deliveryDate = LocalDate.parse(date);
                LocalDate maxDate = LocalDate.now().plusYears(1);
                LocalDate minDate = LocalDate.now().plusDays(1);
                if (deliveryDate.isAfter(maxDate) || deliveryDate.isBefore(minDate)) {
                    errors.put(PARAM_DELIVERY_DATE, "The earliest available date - tomorrow, the latest - in a year");
                } else {
                    order.setDeliveryDate(deliveryDate);
                }
            } catch (DateTimeParseException e) {
                errors.put(PARAM_DELIVERY_DATE, "Date cannot be parsed");
            }
        }
    }
}
