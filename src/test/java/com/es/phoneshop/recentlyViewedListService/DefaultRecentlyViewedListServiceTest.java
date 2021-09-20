package com.es.phoneshop.recentlyViewedListService;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.RecentlyViewedList;
import com.es.phoneshop.recentlyViewedListService.impl.DefaultRecentlyViewedListService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRecentlyViewedListServiceTest {
    private final Currency usd = Currency.getInstance("USD");
    @Mock
    HttpServletRequest request;
    @Mock
    HttpSession session;
    private RecentlyViewedListService recentlyViewedListService;

    @Before
    public void setup() {
        recentlyViewedListService = DefaultRecentlyViewedListService.getInstance();
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testGetList() {
        when(session.getAttribute(DefaultRecentlyViewedListService.RECENTLY_VIEWED_LIST_SESSION_ATTRIBUTE))
                .thenReturn(null);
        RecentlyViewedList list = recentlyViewedListService.getRecentlyViewedList(request);
        verify(session).getAttribute(DefaultRecentlyViewedListService.RECENTLY_VIEWED_LIST_SESSION_ATTRIBUTE);
        verify(session).setAttribute(eq(DefaultRecentlyViewedListService.RECENTLY_VIEWED_LIST_SESSION_ATTRIBUTE), any());
        assertNotNull(list);
    }

    @Test
    public void testAddToList() {
        RecentlyViewedList list = new RecentlyViewedList();
        Product product = new Product("TEST", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        product.setId(0L);
        recentlyViewedListService.add(list, product);
        assertEquals(1, list.getRecentlyViewedProducts().size());

        recentlyViewedListService.add(list, product);
        assertEquals(1, list.getRecentlyViewedProducts().size());

        Product newProduct = new Product("TEST", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        newProduct.setId(1L);
        recentlyViewedListService.add(list, newProduct);
        assertEquals(2, list.getRecentlyViewedProducts().size());
        assertEquals(new Long(1), list.getRecentlyViewedProducts().getFirst().getId());
    }
}
