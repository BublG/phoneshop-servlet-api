package com.es.phoneshop.web.listener;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.PriceHistory;
import com.es.phoneshop.model.Product;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductDemoDataServletContextListener implements ServletContextListener {
    private final ProductDao productDao;
    private final SimpleDateFormat format = new SimpleDateFormat(PriceHistory.datePattern, Locale.ENGLISH);

    public ProductDemoDataServletContextListener() {
        this.productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (Boolean.parseBoolean(servletContextEvent.getServletContext().getInitParameter("insertDemoData"))) {
            saveSampleProducts();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void saveSampleProducts() {
        Currency usd = Currency.getInstance("USD");

        List<PriceHistory> priceHistory = getPriceHistoryList(getPriceHistory("19 Jan 2020", new BigDecimal(100)),
                getPriceHistory("10 Sep 2019", new BigDecimal(115)),
                getPriceHistory("8 Jun 2019", new BigDecimal(130)));
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("19 Jan 2020", new BigDecimal(230)),
                getPriceHistory("10 Sep 2019", new BigDecimal(215)),
                getPriceHistory("8 Jun 2019", new BigDecimal(200)));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("19 Jan 2020", new BigDecimal(400)),
                getPriceHistory("10 Sep 2019", new BigDecimal(350)),
                getPriceHistory("8 Jun 2019", new BigDecimal(300)));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("19 Jan 2020", new BigDecimal(240)),
                getPriceHistory("10 Sep 2019", new BigDecimal(225)),
                getPriceHistory("8 Jun 2019", new BigDecimal(200)));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("19 Jan 2020", new BigDecimal(1200)),
                getPriceHistory("10 Sep 2019", new BigDecimal(1000)));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("10 Sep 2019", new BigDecimal(320)));
        productDao.save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("10 Sep 2019", new BigDecimal(420)));
        productDao.save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("10 Sep 2019", new BigDecimal(120)));
        productDao.save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("10 Sep 2019", new BigDecimal(70)));
        productDao.save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("19 Jan 2020", new BigDecimal(170)),
                getPriceHistory("10 Sep 2019", new BigDecimal(180)));
        productDao.save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("10 Sep 2019", new BigDecimal(70)));
        productDao.save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("10 Sep 2019", new BigDecimal(80)),
                getPriceHistory("5 Jun 2018", new BigDecimal(100)));
        productDao.save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg", priceHistory));

        priceHistory = getPriceHistoryList(getPriceHistory("10 Sep 2019", new BigDecimal(150)),
                getPriceHistory("25 Aug 2019", new BigDecimal(155)));
        productDao.save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg", priceHistory));
    }

    private List<PriceHistory> getPriceHistoryList(PriceHistory ... priceHistories) {
        return new LinkedList<>(Arrays.asList(priceHistories));
    }

    private PriceHistory getPriceHistory(String dateStr, BigDecimal price) {
        Date date;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            date = new Date();
        }
        return new PriceHistory(date, price);
    }
}
