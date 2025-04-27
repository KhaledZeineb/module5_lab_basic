package com.example.redislab.service;

import com.example.redislab.model.Book;
import com.example.redislab.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ConditionalCachingBookService {
    private final BookRepository bookRepository;

    @Autowired
    public ConditionalCachingBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "books", key = "#id",
            condition = "#id <= 100", // Cache only books with ID <= 100
            unless = "#result == null || #result.restricted == true") // Don't cache null or restricted books
    public Book findById(Long id) {
        return bookRepository.findById(id);
    }

    @Cacheable(value = "popularBooks",
            condition = "#book.popularity > 7") // Cache only popular books
    public Book getBookDetails(Book book) {
        // Fetch additional details
        return book;
    }
}
