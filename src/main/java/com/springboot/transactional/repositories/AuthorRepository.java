package com.springboot.transactional.repositories;

import com.springboot.transactional.models.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
