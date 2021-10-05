package com.es.phoneshop.web.filter;

import com.es.phoneshop.service.impl.DefaultDosProtectionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DosFilterTest {
    private final DosFilter dosFilter = new DosFilter();
    @Mock
    FilterChain filterChain;
    @Mock
    FilterConfig filterConfig;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;

    @Before
    public void setup() throws ServletException {
        dosFilter.init(filterConfig);
        when(request.getRemoteAddr()).thenReturn("123");
    }

    @Test
    public void testDoFilter() throws IOException, ServletException {
        for (int i = 0; i < DefaultDosProtectionService.THRESHOLD; i++) {
            dosFilter.doFilter(request, response, filterChain);
        }
        verify(filterChain, times(DefaultDosProtectionService.THRESHOLD)).doFilter(request, response);
        dosFilter.doFilter(request, response, filterChain);
        verify(response).sendError(eq(429));
    }
}
