package com.wkrzywiec.spring.libraryrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wkrzywiec.spring.libraryrest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
