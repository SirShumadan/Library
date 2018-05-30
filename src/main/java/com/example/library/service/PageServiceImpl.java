package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.PageState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PageServiceImpl implements PageService {
    private BookRepository repository;
    private PageState state;

    @Override
    public void setShowAlreadyRead(Integer showAlreadyRead) {
        state.setShowAlreadyRead(showAlreadyRead);
    }

    @Autowired
    public PageServiceImpl(BookRepository bookRepository, PageState state) {
        this.state = state;
        this.repository = bookRepository;
        state.setSortParam("id");
        state.setSortDirection(Sort.Direction.ASC);
        state.setPageNumber(0);
        state.setRowCount(10);
        refresh();
    }


    @Override
    public Page<Book> getPage() {
        return generatePage();
    }

    @Override
    public void previousPage() {
        if (state.getPageNumber() <= 0) {
            state.setPageNumber(0);
        } else {
            state.setPageNumber(state.getPageNumber() - 1);
        }
        state.setPage(state.getPage().previousOrFirst());
    }

    @Override
    public void nextPage() {
        if (state.getPageNumber() + 1 == state.getNumberOfPages().size())
            return;
        state.setPageNumber(state.getPageNumber() + 1);
        state.setPage(state.getPage().next());
    }

    @Override
    public void setSortDirection(String direction) {
        if (direction.equals("ASC")) {
            state.setSortDirection(Sort.Direction.ASC);
        } else if (direction.equals("DESC")) {
            state.setSortDirection(Sort.Direction.DESC);
        }
        refresh();
    }

    @Override
    public void setSortParam(String param) {
        if (param != null) {
            state.setSortParam(param);
            refresh();
        }
    }

    @Override
    public void setPageNumber(int pageNumber) {
        state.setPageNumber(pageNumber);
        refresh();
    }

    @Override
    public void setSearchCriterion(String searchCriterion) {
        state.setSearchCriterion(searchCriterion);
    }

    @Override
    public void setSearchValue(String searchValue) {
       state.setSearchValue(searchValue);
    }

    @Override
    public List<Integer> getNumberOfPages() {
        return state.getNumberOfPages();
    }


    @Override
    public Page<Book> clean() {
        state.setSortDirection(Sort.Direction.ASC);
        state.setPageNumber(0);
        state.setSearchValue("");
        state.setSearchCriterion("");
        state.setSortParam("id");
        state.setShowAlreadyRead(0);
        refresh();
        return generatePage();
    }

    private void refresh() {
        state.setPage(PageRequest.of(state.getPageNumber(), state.getRowCount(), state.getSortDirection(), state.getSortParam()));
        List<Integer> numberOfPages = new ArrayList<>();
        for (int i = 1; i <= generatePage().getTotalPages(); i++) {
            numberOfPages.add(i);
        }
        state.setNumberOfPages(numberOfPages);
    }

    private Page<Book> generatePage() {
        if (state.getSearchCriterion() == null || state.getSearchCriterion().equals("")) {
            if (state.getShowAlreadyRead() == 2) {
                return repository.findByReadAlready(false, state.getPage());
            } else if (state.getShowAlreadyRead() == 1) {
                return repository.findByReadAlready(true, state.getPage());
            } else {
                return repository.findAll(state.getPage());
            }
        } else if (state.getSearchCriterion().equals("title")) {
            if (state.getShowAlreadyRead() == 2) {
                return repository.findByTitleIgnoreCaseContainsAndReadAlready(state.getSearchValue(), false, state.getPage());
            } else if (state.getShowAlreadyRead() == 1) {
                return repository.findByTitleIgnoreCaseContainsAndReadAlready(state.getSearchValue(), true, state.getPage());
            } else {
                return repository.findByTitleContainsIgnoreCase(state.getSearchValue(), state.getPage());
            }
        } else if (state.getSearchCriterion().equals("author")) {
            if (state.getShowAlreadyRead() == 2) {
                return repository.findByAuthorIgnoreCaseContainsAndReadAlready(state.getSearchValue(), false, state.getPage());
            } else if (state.getShowAlreadyRead() == 1) {
                return repository.findByAuthorIgnoreCaseContainsAndReadAlready(state.getSearchValue(), true, state.getPage());
            } else {
                return repository.findByAuthorContainsIgnoreCase(state.getSearchValue(), state.getPage());
            }
        } else {
            return repository.findAll(state.getPage());
        }
    }
}
