package com.example.redislab.service;

import com.example.redislab.model.Book;
import com.example.redislab.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import java.util.Map;
import org.springframework.data.redis.core.ValueOperations;




@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CacheMetricsService metricsService;


    @Autowired
    public BookService(BookRepository bookRepository, CacheMetricsService metricsService) {
        this.bookRepository = bookRepository;
        this.metricsService = metricsService;
    }

    @Cacheable(value = "books", key = "#id")
    public Book findById(Long id) {
        // This code only executes on cache miss
        metricsService.incrementMisses();
        return bookRepository.findById(id);
    }

    // Add this new method to manually check for cache hits
    public Book findByIdWithMetrics(Long id) {
        String cacheKey = "books::" + id;
        // Try to get from Redis directly to check for a hit
        ReactiveRedisOperations<Object, Object> redisTemplate = null;
        ValueOperations<String, Book> ops = (ValueOperations<String, Book>) redisTemplate.opsForValue();
        Book book = ops.get(cacheKey);

        if (book != null) {
            // Cache hit
            metricsService.incrementHits();
            return book;
        } else {
            // Use the cached method which will increment the miss counter
            return findById(id);
        }
    }

    public Map<String, Object> getCacheStats() {
        return metricsService.getStats();
    }

    public Book updateBook(Book book) {
        return null;
    }

    public void deleteBook(Long id) {

    }

    public void clearAllCaches() {
    }
}