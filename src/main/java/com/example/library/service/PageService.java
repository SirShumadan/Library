package com.example.library.service;

import com.example.library.entity.Book;

import java.util.List;

public interface PageService {
    Iterable<Book> getPage(int number);
    Iterable<Book> getPage();
    void previousPage();
    void nextPage();
    void setSortDirection(String direction);
    void setSortParam(String param);
    void setPageNumber(int pageNumber);
    List<Integer> getNumberOfPages();
}
