package com.wkrzywiec.spring.libraryrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wkrzywiec.spring.libraryrest.model.Reserved;

public interface ReservedReposiotry extends JpaRepository<Reserved, Long> {

}
