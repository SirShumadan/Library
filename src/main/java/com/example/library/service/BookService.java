package com.example.library.service;

import com.example.library.entity.Book;

import java.util.List;

public interface BookService {
    void saveBook(Book book);
    Book getBookById(Integer id);
    void updateBook(Integer id, String title, String description, String isbn, Integer printYear);
    void readBook(Integer id);
    void deleteBook(Integer id);
    Iterable<Book> findAll();
}
