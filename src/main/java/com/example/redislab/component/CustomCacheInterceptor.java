package com.example.redislab.component;

import com.example.redislab.service.CacheMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.stereotype.Component;

@Component
public class CustomCacheInterceptor extends CacheInterceptor {

    private final CacheMetricsService metricsService;

    @Autowired
    public CustomCacheInterceptor(CacheMetricsService metricsService,
                                  CacheOperationSource cacheOperationSource) {
        this.metricsService = metricsService;
        setCacheOperationSource(cacheOperationSource);
    }

    @Override
    protected Object invokeOperation(CacheOperationInvoker invoker) {
        try {
            // If we're here with no exception, it means we got a cache hit
            metricsService.incrementHits();
            return super.invokeOperation(invoker);
        } catch (Exception e) {
            metricsService.incrementMisses();
            throw e;
        }
    }
}