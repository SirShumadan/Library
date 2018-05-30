package com.example.library.service;

import com.example.library.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PageService {
    Page<Book> getPage();
    void previousPage();
    void nextPage();
    void setSortDirection(String direction);
    void setSortParam(String param);
    void setPageNumber(int pageNumber);
    List<Integer> getNumberOfPages();
    void setSearchCriterion(String SearchCriterion);
    void setSearchValue(String SearchValue);
    Page<Book> clean();
    void setShowAlreadyRead(Integer showAlreadyRead);
}
