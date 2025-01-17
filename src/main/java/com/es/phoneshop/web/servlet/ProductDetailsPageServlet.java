package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.RecentlyViewedList;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.RecentlyViewedListService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.service.impl.DefaultRecentlyViewedListService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static com.es.phoneshop.constants.AppConstants.*;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewedListService recentlyViewedListService;

    public static int parseQuantity(String quantityStr, Locale locale) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(locale);
        int quantity = format.parse(quantityStr).intValue();
        String s = format.format(quantity).replaceAll((char) 160 + "", "");
        // NumberFormat in russian locale adds a No-Break-Space symbol with code 160 when format, like
        // 1000 -> 1 000
        if (!s.equals(quantityStr)) {
            throw new ParseException("Not a number", 0);
        }
        if (quantity < 0) {
            throw new ParseException("Quantity can't be negative or zero", 0);
        }
        return quantity;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentlyViewedListService = DefaultRecentlyViewedListService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productDao.getProduct(parseProductId(request));
        RecentlyViewedList recentlyViewedList = recentlyViewedListService.getRecentlyViewedList(request);
        request.setAttribute(ATTRIBUTE_PRODUCT, product);
        request.setAttribute(ATTRIBUTE_RECENTLY_VIEWED_LIST, recentlyViewedList);
        request.getRequestDispatcher(PRODUCT_JSP).forward(request, response);
        recentlyViewedListService.add(recentlyViewedList, product);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productId = parseProductId(request);
        int quantity;
        try {
            quantity = parseQuantity(request.getParameter(PARAM_QUANTITY), request.getLocale());
        } catch (ParseException e) {
            request.setAttribute(ATTRIBUTE_ERROR, e.getMessage());
            doGet(request, response);
            return;
        }

        Cart cart = cartService.getCart(request);
        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException e) {
            request.setAttribute(ATTRIBUTE_ERROR, String.format("Not enough stock, available: %d. You already have: %d",
                    e.getStockAvailable(), cartService.getCurrentQuantity(cart, productId)));
            doGet(request, response);
            return;
        }
        response.sendRedirect(String.format("%s/products/%d?message=Added to cart successfully",
                request.getContextPath(), productId));
    }

    private long parseProductId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }
}
