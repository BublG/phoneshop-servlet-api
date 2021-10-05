package com.es.phoneshop.service;

import com.es.phoneshop.service.impl.DefaultDosProtectionService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultDosProtectionServiceTest {
    private DosProtectionService dosProtectionService;

    @Before
    public void setup() {
        dosProtectionService = DefaultDosProtectionService.getInstance();
    }

    @Test
    public void isAllowedTest() {
        String ip = "123";
        for (int i = 0; i < DefaultDosProtectionService.THRESHOLD; i++) {
            assertTrue(dosProtectionService.isAllowed(ip));
        }
        assertFalse(dosProtectionService.isAllowed(ip));
    }
}
