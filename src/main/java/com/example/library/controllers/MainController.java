package com.example.library.controllers;

import com.example.library.entity.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    private BookService service;

    @Autowired
    public MainController(BookService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String main(Model model){
        Iterable<Book> books =  service.findAll();
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        service.deleteBook(id);
        return "redirect:/";
    }

    @GetMapping("/book/{id}")
    public String read(@PathVariable Integer id, Model model){
        Book book = service.getBookById(id);
        service.readBook(id);
        model.addAttribute("book", book);
        return "book";
    }
}
