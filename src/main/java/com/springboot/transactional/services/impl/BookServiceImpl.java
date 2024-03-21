package com.springboot.transactional.services.impl;

import com.springboot.transactional.models.Author;
import com.springboot.transactional.models.Book;
import com.springboot.transactional.repositories.AuthorRepository;
import com.springboot.transactional.repositories.BookRepository;
import com.springboot.transactional.services.BookService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(final BookRepository bookRepository, final AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Override
    public Book save(final Book book) {
        if (null == book.getAuthor()) {
            throw new RuntimeException("Author must be provided.");
        }
        final Author bookAuthor = book.getAuthor();

        // 1 save book without author (guardo libro sin autor
        book.setAuthor(null);
        final Book savedBook = bookRepository.save(book);

        // create/retrieve author
        if(null == bookAuthor.getId()){
            final Author author = authorRepository.save(bookAuthor);
            savedBook.setAuthor(author);

        } else {
            final Author author = authorRepository.findById(bookAuthor.getId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            savedBook.setAuthor(author);
        }

        // 3. Save the Book with the Author and return
        return bookRepository.save(savedBook);
    }
    // esta es la secuencia cuando uso transacciones => en cascada

    @Override
    public Optional<Book> findById(String ibsn){

        return bookRepository.findById(ibsn);
    }

    @Override
    public Iterable<Book> listBooks(){

        return bookRepository.findAll();
    }

    @Override
    public boolean isBookExists(Book book){

        return bookRepository.existsById(book.getIbsn());
    }

    @Override
    public void deleteById(String ibsn){
        try{
            bookRepository.deleteById(ibsn);
        } catch(final EmptyResultDataAccessException ex){
            log.debug("Attempted to delete non-existing book", ex);
        }
    }
}
