package com.springboot.transactional.services;

import com.springboot.transactional.models.Book;

import java.util.Optional;

public interface BookService {
    boolean isBookExists(Book book);

    Book save (Book book);

    Optional<Book> findById(String ibsn);

    Iterable<Book> listBooks();

    void deleteById(String id);
}
