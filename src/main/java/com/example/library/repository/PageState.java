package com.example.library.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageState {
    private Sort.Direction sortDirection;
    private String sortParam;
    private String searchCriterion;
    private String searchValue;
    private int showAlreadyRead;
    private int pageNumber;
    private int rowCount;
    private Pageable page;
    private List<Integer> numberOfPages;

    public PageState() {

    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortParam() {
        return sortParam;
    }

    public void setSortParam(String sortParam) {
        this.sortParam = sortParam;
    }

    public String getSearchCriterion() {
        return searchCriterion;
    }

    public void setSearchCriterion(String searchCriterion) {
        this.searchCriterion = searchCriterion;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getShowAlreadyRead() {
        return showAlreadyRead;
    }

    public void setShowAlreadyRead(int showAlreadyRead) {
        this.showAlreadyRead = showAlreadyRead;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public Pageable getPage() {
        return page;
    }

    public void setPage(Pageable page) {
        this.page = page;
    }

    public List<Integer> getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(List<Integer> numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
