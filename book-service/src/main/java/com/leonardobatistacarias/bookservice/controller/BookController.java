package com.leonardobatistacarias.bookservice.controller;

import com.leonardobatistacarias.bookservice.model.Book;
import com.leonardobatistacarias.bookservice.proxy.CambioProxy;
import com.leonardobatistacarias.bookservice.repository.BookRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/book-service")
public class BookController {

    private final Environment environment;
    private final BookRepository bookRepository;
    private final CambioProxy cambioProxy;

    public BookController(Environment environment, BookRepository bookRepository, CambioProxy cambioProxy) {
        this.environment = environment;
        this.bookRepository = bookRepository;
        this.cambioProxy = cambioProxy;
    }

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
        var book = bookRepository.getById(id);

        if (book == null) throw new RuntimeException("Book not found");

        Map<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);
        var response = cambioProxy.getCambio(book.getPrice(), "USD", currency);

        var port = this.environment.getProperty("local.server.port");
        book.setEnvironment("Book port: " + port + " Cambio Port " + response.getEnvironment());
        book.setPrice(response.getConvertedValue());
        return book;
    }

//    @GetMapping(value = "/{id}/{currency}")
//    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
//        var book = bookRepository.getById(id);
//
//        if (book == null) throw new RuntimeException("Book not found");
//
//        Map<String, String> params = new HashMap<>();
//        params.put("amount", book.getPrice().toString());
//        params.put("from", "USD");
//        params.put("to", currency);
//        var response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
//
//        var port = this.environment.getProperty("local.server.port");
//        book.setEnvironment(port);
//        book.setPrice(response.getBody().getConvertedValue());
//        return book;
//    }


}
