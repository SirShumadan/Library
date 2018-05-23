package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository repository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.repository = bookRepository;
    }

    @Override
    public void saveBook(Book book) {
        repository.save(book);
    }

    @Override
    public Book getBookById(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public void updateBook(Integer id, String title, String description, String isbn, Integer printYear) {
        Book update = getBookById(id);
        update.setTitle(title);
        update.setDescription(description);
        update.setIsbn(isbn);
        update.setPrintYear(printYear);
        saveBook(update);
    }

    @Override
    public void readBook(Integer id) {
        Book reading = getBookById(id);
        reading.setReadAlready(true);
        saveBook(reading);
    }


    @Override
    public void deleteBook(Integer id) {
        repository.delete(getBookById(id));
    }

    @Override
    public Iterable<Book> findAll() {
        return repository.findAll();
    }
}
