package com.leonardobatistacarias.bookservice.repository;

import com.leonardobatistacarias.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
