package com.example.redislab.service;

import com.example.redislab.model.Book;
import com.example.redislab.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import java.util.ArrayList;
import java.util.List;


public class BookBatchService {
    private final BookRepository bookRepository;

    @Autowired
    public BookBatchService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "batchBooks", key = "'batch_' + #batchId")
    public List<Book> getBatchBooks(String batchId, int count) {
        List<Book> books = new ArrayList<>();
        // Simulate generating a batch of books
        for (int i = 0; i < count; i++) {
            Book book = new Book();
            book.setId(Long.parseLong(batchId + i));
            book.setTitle("Batch Book " + i);
            book.setAuthor("Batch Author");
            books.add(book);
        }
        return books;
    }

    public void loadBatches(int numberOfBatches, int booksPerBatch) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfBatches; i++) {
            getBatchBooks(String.valueOf(i), booksPerBatch);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time to load " + (numberOfBatches * booksPerBatch) +
                " books: " + (endTime - startTime) + " ms");
    }
}
