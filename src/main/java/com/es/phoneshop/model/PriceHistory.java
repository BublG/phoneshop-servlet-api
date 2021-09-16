package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PriceHistory {

    public static final String datePattern = "d MMM yyyy";
    private Date date;
    private BigDecimal price;

    public PriceHistory() {
    }

    public PriceHistory(Date date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate() {
        SimpleDateFormat format = new SimpleDateFormat(datePattern, Locale.ENGLISH);
        return format.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
