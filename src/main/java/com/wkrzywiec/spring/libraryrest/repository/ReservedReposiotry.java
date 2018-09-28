package com.wkrzywiec.spring.libraryrest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wkrzywiec.spring.libraryrest.model.Reserved;

public interface ReservedReposiotry extends JpaRepository<Reserved, Long> {

	@Query("SELECT r FROM Reserved r WHERE r.user.id = :userId")
	public List<Reserved> findByUserId(@Param("userId") Long userId);
	
	@Query("SELECT r FROM Reserved r WHERE r.book.id = :bookId")
	public Reserved findByBookId(@Param("bookId") Long bookId);
}
