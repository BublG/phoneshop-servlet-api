package com.es.phoneshop.web.servlet;

import com.es.phoneshop.cartService.CartService;
import com.es.phoneshop.cartService.impl.DefaultCartService;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.RecentlyViewedList;
import com.es.phoneshop.recentlyViewedListService.RecentlyViewedListService;
import com.es.phoneshop.recentlyViewedListService.impl.DefaultRecentlyViewedListService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewedListService recentlyViewedListService;

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
        request.setAttribute("product", product);
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("recentlyViewedList", recentlyViewedList);
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        recentlyViewedListService.add(recentlyViewedList, product);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productId = parseProductId(request);
        int quantity;
        try {
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            quantity = format.parse(request.getParameter("quantity")).intValue();
        } catch (ParseException e) {
            request.setAttribute("error", "Not a number");
            doGet(request, response);
            return;
        }

        Cart cart = cartService.getCart(request);
        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException e) {
            request.setAttribute("error", "Not enough stock, available: " + e.getStockAvailable() +
                    ".\nYou already have: " + cartService.getCurrentQuantity(cart, productId));
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/products/" + productId
                + "?message=Added to cart successfully");
    }

    private long parseProductId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }
}
