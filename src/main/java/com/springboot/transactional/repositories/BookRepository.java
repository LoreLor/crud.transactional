package com.springboot.transactional.repositories;


import com.springboot.transactional.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {
}
