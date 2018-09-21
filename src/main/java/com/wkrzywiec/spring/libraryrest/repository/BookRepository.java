package com.wkrzywiec.spring.libraryrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wkrzywiec.spring.libraryrest.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
