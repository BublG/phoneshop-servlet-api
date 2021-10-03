package com.es.phoneshop.service.impl;

import com.es.phoneshop.service.DosProtectionService;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
    private static volatile DosProtectionService instance;
    private static final long THRESHOLD = 20;
    private final Map<String, Long> countMap;

    private DefaultDosProtectionService() {
        countMap = new ConcurrentHashMap<>();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                countMap.clear();
            }
        }, 60000, 60000);
    }

    public static DosProtectionService getInstance() {
        if (instance == null) {
            synchronized (DefaultDosProtectionService.class) {
                if (instance == null) {
                    instance = new DefaultDosProtectionService();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {
        Long count = countMap.get(ip);
        if (count == null) {
            count = 1L;
        } else {
            if (count > THRESHOLD) {
                return false;
            }
            count++;
        }
        countMap.put(ip, count);
        return true;
    }
}
