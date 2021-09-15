package com.es.phoneshop.comparator;

import com.es.phoneshop.model.Product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ProductSearchComparator implements Comparator<Product> {

    private final String[] words;

    public ProductSearchComparator(String[] words) {
        this.words = words;
    }

    private int getCoincidencePercent(Product p) {
        List<String> descr = Arrays.asList(p.getDescription().toLowerCase().trim().split("\\s+"));
        int c = 0;
        for (String word : words) {
            if (descr.contains(word)) {
                c++;
            }
        }
        return  c * 100 / descr.size();
    }

    @Override
    public int compare(Product o1, Product o2) {
        return getCoincidencePercent(o2) - getCoincidencePercent(o1);
    }
}
