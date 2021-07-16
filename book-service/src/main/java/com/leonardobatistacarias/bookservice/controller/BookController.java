package com.leonardobatistacarias.bookservice.controller;

import com.leonardobatistacarias.bookservice.model.Book;
import com.leonardobatistacarias.bookservice.repository.BookRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/book-service")
public class BookController {

    private final Environment environment;
    private final BookRepository bookRepository;

    public BookController(Environment environment, BookRepository bookRepository) {
        this.environment = environment;
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
        var book = bookRepository.getById(id);

        if(book == null) throw new RuntimeException("Book not found");

        var port = this.environment.getProperty("local.server.port");
        book.setEnvironment(port);
        return book;
    }


}
