package com.springboot.transactional.controllers;

import com.springboot.transactional.models.Book;
import com.springboot.transactional.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookServices;

    public BookController(final BookService bookServices) {

        this.bookServices = bookServices;
    }

    @PutMapping(path="/books/{ibsn}")
    public ResponseEntity<Book> createUpdateBook(@PathVariable final String ibsn, @RequestBody final Book book){
        book.setIbsn(ibsn);

        final boolean isBookExists = bookServices.isBookExists(book);
        final Book savedBook  = bookServices.save(book);

        if(isBookExists){
            return new ResponseEntity<Book>(savedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
        }
    }

    @GetMapping(path="/books/{ibsn}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final String ibsn){
        final Optional<Book> foundBook = bookServices.findById(ibsn);
        return foundBook.map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/books")
    public ResponseEntity<Iterable<Book>> listBooks(){
        return new ResponseEntity<Iterable<Book>>(bookServices.listBooks(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{ibsn}")
    public ResponseEntity deleteBook(@PathVariable final String ibsn){
        bookServices.deleteById(ibsn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
