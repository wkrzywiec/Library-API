package com.wkrzywiec.spring.libraryrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wkrzywiec.spring.libraryrest.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
