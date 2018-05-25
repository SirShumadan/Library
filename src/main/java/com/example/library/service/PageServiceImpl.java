package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PageServiceImpl implements PageService {
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortParam;
    private int pageNumber;
    private int rowCount;
    private Pageable page;
    private BookRepository repository;


    private List<Integer> numberOfPages;

    @Autowired
    public PageServiceImpl(BookRepository bookRepository) {
        this.repository = bookRepository;
        sortParam = "title";
        pageNumber = 0;
        rowCount = 10;
        refresh();
    }

    @Override
    public Iterable<Book> getPage(int number){
        setPageNumber(number);
        return repository.findAll(page);
    }

    @Override
    public Iterable<Book> getPage() {
        return repository.findAll(page);
    }

    @Override
    public void previousPage(){
        if(pageNumber <= 0){
            pageNumber = 0;
        }else {
            pageNumber--;
        }
        page = page.previousOrFirst();
    }

    @Override
    public void nextPage(){
        if(pageNumber + 1 == numberOfPages.size())
            return;
        pageNumber++;
        page = page.next();
    }

    @Override
    public void setSortDirection(String direction){
        if(direction.equals("ASC")){
            sortDirection = Sort.Direction.ASC;
        }else if(direction.equals("DESC")){
            sortDirection = Sort.Direction.DESC;
        }
    }

    @Override
    public void setSortParam(String param){
        sortParam = param;
    }

    @Override
    public void setPageNumber(int pageNumber){
        this.pageNumber = pageNumber;
        refresh();
    }


    @Override
    public List<Integer> getNumberOfPages(){
        return numberOfPages;
    }

    private void refresh(){
        page = PageRequest.of(pageNumber, rowCount, sortDirection, sortParam);
        numberOfPages = new ArrayList<Integer>();
        for(int i = 1; i <= repository.findAll(page).getTotalPages(); i++){
            numberOfPages.add(i);
        }
    }
}
