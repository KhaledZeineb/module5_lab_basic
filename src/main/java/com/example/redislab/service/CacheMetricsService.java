package com.example.redislab.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;
import java.util.HashMap;
import java.util.Map;

@Service
public class CacheMetricsService {
    private final AtomicLong cacheHits = new AtomicLong(0);
    private final AtomicLong cacheMisses = new AtomicLong(0);

    public void incrementHits() {
        cacheHits.incrementAndGet();
    }

    public void incrementMisses() {
        cacheMisses.incrementAndGet();
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("hits", cacheHits.get());
        stats.put("misses", cacheMisses.get());
        long total = cacheHits.get() + cacheMisses.get();
        double ratio = total > 0 ? (double) cacheHits.get() / total : 0;
        stats.put("ratio", ratio);
        return stats;
    }
}