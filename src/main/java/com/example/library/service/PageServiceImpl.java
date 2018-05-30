package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PageServiceImpl implements PageService {
    private Sort.Direction sortDirection;
    private String sortParam;
    private String searchCriterion;
    private String searchValue;
    private int showAlreadyRead;
    private int pageNumber;
    private int rowCount;
    private Pageable page;
    private BookRepository repository;

    private List<Integer> numberOfPages;

    // TODO возможно стоит выделить отдельный класс для хранения состояния странички


    @Override
    public void setShowAlreadyRead(Integer showAlreadyRead) {
        this.showAlreadyRead = showAlreadyRead;
    }

    @Autowired
    public PageServiceImpl(BookRepository bookRepository) {
        this.repository = bookRepository;
        sortParam = "id";
        sortDirection = Sort.Direction.ASC;
        pageNumber = 0;
        rowCount = 10;
        refresh();
    }


    private Page<Book> generatePage() {
        if (searchCriterion == null || searchCriterion.equals("")) {
            if (showAlreadyRead == 2) {
                return repository.findByReadAlready(false, page);
            } else if (showAlreadyRead == 1) {
                return repository.findByReadAlready(true, page);
            } else {
                return repository.findAll(page);
            }
        } else if (searchCriterion.equals("title")) {
            if (showAlreadyRead == 2) {
                return repository.findByTitleIgnoreCaseContainsAndReadAlready(searchValue, false, page);
            } else if (showAlreadyRead == 1) {
                return repository.findByTitleIgnoreCaseContainsAndReadAlready(searchValue, true, page);
            } else {
                return repository.findByTitleContainsIgnoreCase(searchValue, page);
            }
        } else if (searchCriterion.equals("author")) {
            if (showAlreadyRead == 2) {
                return repository.findByAuthorIgnoreCaseContainsAndReadAlready(searchValue, false, page);
            } else if (showAlreadyRead == 1) {
                return repository.findByAuthorIgnoreCaseContainsAndReadAlready(searchValue, true, page);
            } else {
                return repository.findByAuthorContainsIgnoreCase(searchValue, page);
            }
        } else {
            return repository.findAll(page);
        }
    }

    @Override
    public Page<Book> getPage() {
        return generatePage();
    }

    @Override
    public void previousPage() {
        if (pageNumber <= 0) {
            pageNumber = 0;
        } else {
            pageNumber--;
        }
        page = page.previousOrFirst();
    }

    @Override
    public void nextPage() {
        if (pageNumber + 1 == numberOfPages.size())
            return;
        pageNumber++;
        page = page.next();
    }

    @Override
    public void setSortDirection(String direction) {
        if (direction.equals("ASC")) {
            sortDirection = Sort.Direction.ASC;
        } else if (direction.equals("DESC")) {
            sortDirection = Sort.Direction.DESC;
        }
        refresh();
    }

    @Override
    public void setSortParam(String param) {
        if (param != null) {
            sortParam = param;
            refresh();
        }
    }

    @Override
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        refresh();
    }

    @Override
    public void setSearchCriterion(String searchCriterion) {
        this.searchCriterion = searchCriterion;
    }

    @Override
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    @Override
    public List<Integer> getNumberOfPages() {
        return numberOfPages;
    }

    private void refresh() {
        page = PageRequest.of(pageNumber, rowCount, sortDirection, sortParam);
        numberOfPages = new ArrayList<>();
        for (int i = 1; i <= generatePage().getTotalPages(); i++) {
            numberOfPages.add(i);
        }
    }

    @Override
    public Page<Book> clean() {
        sortDirection = Sort.Direction.ASC;
        pageNumber = 0;
        searchValue = "";
        searchCriterion = "";
        sortParam = "id";
        showAlreadyRead = 0;
        refresh();
        return generatePage();
    }
}
