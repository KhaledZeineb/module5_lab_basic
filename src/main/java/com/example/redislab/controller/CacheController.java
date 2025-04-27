package com.example.redislab.controller;

import com.example.redislab.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/cache")
public class CacheController {
    private final BookService bookService;

    @Autowired
    public CacheController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/stats")
    public Map<String, Object> getCacheStats() {
        return bookService.getCacheStats();
    }
}
