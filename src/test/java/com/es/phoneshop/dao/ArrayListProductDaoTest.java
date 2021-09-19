package com.es.phoneshop.dao;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testSaveNewProduct() throws ProductNotFoundException {
        Product product = new Product("TEST", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        productDao.save(product);
        Product savedProduct = productDao.getProduct(product.getId());

        assertNotNull(product.getId());
        assertNotNull(savedProduct);
        assertEquals("TEST", savedProduct.getCode());
    }

    @Test
    public void testUpdateProduct() throws ProductNotFoundException {
        Product product = new Product("TEST", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        productDao.save(product);
        Product savedProduct = productDao.getProduct(product.getId());
        savedProduct.setDescription("Xiaomi");
        productDao.save(savedProduct);

        assertEquals(product.getId(), savedProduct.getId());
        assertEquals("Xiaomi", productDao.getProduct(product.getId()).getDescription());
    }

    @Test
    public void testFindProductWithNotZeroStock() {
        int sizeBefore = productDao.findProducts(null, null, null).size();
        Product product1 = new Product("TEST1", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        Product product2 = new Product("TEST2", "Apple iPhone 6", new BigDecimal(1000), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        Product product3 = new Product("TEST3", "Apple iPhone 6", new BigDecimal(1000), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        productDao.save(product1);
        productDao.save(product2);
        productDao.save(product3);
        List<Product> products = productDao.findProducts(null, null, null);

        assertEquals(productDao.findProducts(null, null, null).size() - 1, sizeBefore);
        assertEquals("TEST1", products.get(products.size() - 1).getCode());
        for (Product p : products)
            assertTrue(p.getStock() > 0);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteProduct() throws ProductNotFoundException {
        Product product = new Product("TEST", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        productDao.save(product);
        productDao.delete(product.getId());
        productDao.getProduct(product.getId());
    }
}
