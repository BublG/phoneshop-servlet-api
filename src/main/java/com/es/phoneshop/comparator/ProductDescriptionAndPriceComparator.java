package com.es.phoneshop.comparator;

import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

import java.util.Comparator;

public class ProductDescriptionAndPriceComparator implements Comparator<Product> {

    private final SortField sortField;
    private final SortOrder sortOrder;

    public ProductDescriptionAndPriceComparator(String sortField, String sortOrder) {
        this.sortField = SortField.valueOf(sortField);
        this.sortOrder = SortOrder.valueOf(sortOrder);
    }

    @Override
    public int compare(Product o1, Product o2) {
        if (SortField.description == sortField) {
            return SortOrder.asc == sortOrder ?
                    o1.getDescription().compareTo(o2.getDescription())
                    : o2.getDescription().compareTo(o1.getDescription());
        }
        return SortOrder.asc == sortOrder ?
                o1.getPrice().compareTo(o2.getPrice())
                : o2.getPrice().compareTo(o1.getPrice());
    }
}
