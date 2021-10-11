package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.AdvancedSearchOption;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.es.phoneshop.constants.AppConstants.*;

public class AdvancedSearchPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter(PARAM_SEARCH_OPTION) == null) {
            request.setAttribute(ATTRIBUTE_SEARCH_OPTIONS, AdvancedSearchOption.values());
            request.getRequestDispatcher(ADVANCED_SEARCH_JSP).forward(request, response);
            return;
        }
        String query = request.getParameter(PARAM_QUERY);
        AdvancedSearchOption option =
                AdvancedSearchOption.valueOf(request.getParameter(PARAM_SEARCH_OPTION));
        Map<String, String> errors = new HashMap<>();
        BigDecimal minPrice = getPriceParam(PARAM_MIN_PRICE, request, errors);
        BigDecimal maxPrice = getPriceParam(PARAM_MAX_PRICE, request, errors);
        if (errors.isEmpty()) {
            request.setAttribute(ATTRIBUTE_PRODUCTS, productDao
                    .findProductsByAdvancedSearch(query, minPrice, maxPrice, option));
        } else {
            request.setAttribute(ATTRIBUTE_ERRORS, errors);
        }
        request.setAttribute(ATTRIBUTE_SEARCH_OPTIONS, AdvancedSearchOption.values());
        request.getRequestDispatcher(ADVANCED_SEARCH_JSP).forward(request, response);
    }

    private BigDecimal getPriceParam(String param, HttpServletRequest request,
                                     Map<String, String> errors) {
        BigDecimal price = null;
        try {
            price = parsePrice(request.getParameter(param), request.getLocale());
        } catch (ParseException e) {
            errors.put(PARAM_MIN_PRICE, e.getMessage());
        }
        return price;
    }

    private BigDecimal parsePrice(String priceStr, Locale locale) throws ParseException {
        if (priceStr.trim().isEmpty()) {
            return null;
        }
        NumberFormat format = NumberFormat.getInstance(locale);
        BigDecimal price = new BigDecimal(format.parse(priceStr).intValue());
        String s = format.format(price).replaceAll((char) 160 + "", "");
        // NumberFormat in russian locale adds a No-Break-Space symbol with code 160 when format, like
        // 1000 -> 1 000
        if (!s.equals(priceStr)) {
            throw new ParseException("Not a number", 0);
        }
        return price;
    }
}
