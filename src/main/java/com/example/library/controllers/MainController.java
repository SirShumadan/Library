package com.example.library.controllers;

import com.example.library.entity.Book;
import com.example.library.service.BookService;
import com.example.library.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private BookService bookService;
    private PageService pageService;

    @Autowired
    public MainController(BookService bookService, PageService pageService) {
        this.bookService = bookService;
        this.pageService = pageService;
    }



    @GetMapping("/")
    public String main(Model model){
        Iterable<Book> books =  pageService.getPage();
        List<Integer> numbers = pageService.getNumberOfPages();
        model.addAttribute("books", books);
        model.addAttribute("numbers", numbers);
        return "index";
    }

    @GetMapping("/next")
    public String next(){
        pageService.nextPage();
        return "redirect:/";
    }

    @GetMapping("/previous")
    public String previous(){
        pageService.previousPage();
        return "redirect:/";
    }

    @GetMapping("/{page}")
    public String toPage(@PathVariable Integer page, Model model){
        Iterable<Book> books = pageService.getPage(page);
        model.addAttribute("books", books);
        return "redirect:/";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        bookService.deleteBook(id);
        return "redirect:/";
    }

    @GetMapping("/book/{id}")
    public String read(@PathVariable Integer id, Model model){
        Book book = bookService.getBookById(id);
        bookService.readBook(id);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/create")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "create";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Book editBook = bookService.getBookById(id);
        model.addAttribute("book", editBook);
        return "edit";
    }

    @PostMapping("/save")
    public String update(@ModelAttribute Book book){
        bookService.saveBook(book);
        return "redirect:/";
    }

    @GetMapping("/home")
    public String home(Model model){
        pageService.setPageNumber(0);
        Iterable<Book> books = pageService.getPage();
        model.addAttribute("books", books);
        return "redirect:/";
    }

    @GetMapping("/style.css") //костыль. очередной.
    public String css(){
        return "../static/style.css";
    }

}
