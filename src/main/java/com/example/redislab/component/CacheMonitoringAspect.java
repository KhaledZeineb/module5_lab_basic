package com.example.redislab.component;

import com.example.redislab.service.CacheMetricsService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Component
public class CacheMonitoringAspect {

    private final CacheMetricsService metricsService;
    private final CacheManager cacheManager;
    private final SimpleKeyGenerator keyGenerator = new SimpleKeyGenerator();

    @Autowired
    public CacheMonitoringAspect(CacheMetricsService metricsService, CacheManager cacheManager) {
        this.metricsService = metricsService;
        this.cacheManager = cacheManager;
    }

    @Around("@annotation(cacheable)")
    public Object monitorCacheMethod(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
        String cacheName = cacheable.value()[0];
        Cache cache = cacheManager.getCache(cacheName);

        if (cache != null) {
            // Generate a key similar to how Spring would
            Object key = keyGenerator.generate(
                    joinPoint.getTarget(),
                    ((MethodSignature) joinPoint.getSignature()).getMethod(),
                    joinPoint.getArgs()
            );

            // Check if the value is in cache
            if (cache.get(key) != null) {
                metricsService.incrementHits();
            } else {
                metricsService.incrementMisses();
            }
        }

        return joinPoint.proceed();
    }
}