package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
    Page<Book> findByTitleContainsIgnoreCase(String value, Pageable page);
    Page<Book> findByAuthorContainsIgnoreCase(String value, Pageable page);
    Page<Book> findByAuthorIgnoreCaseContainsAndReadAlready(String value, Boolean readAlready, Pageable page);
    Page<Book> findByTitleIgnoreCaseContainsAndReadAlready(String value, Boolean readAlready, Pageable page);
    Page<Book> findByReadAlready(Boolean readAlready, Pageable page);
}
