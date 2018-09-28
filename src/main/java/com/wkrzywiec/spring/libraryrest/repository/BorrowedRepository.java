package com.wkrzywiec.spring.libraryrest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wkrzywiec.spring.libraryrest.model.Borrowed;

public interface BorrowedRepository extends JpaRepository<Borrowed, Long> {

	@Query("SELECT b FROM Borrowed b WHERE b.user.id = :userId")
	public List<Borrowed> findByUserId(@Param("userId") Long userId);
	
	@Query("SELECT b FROM Borrowed b WHERE b.book.id = :bookId")
	public Borrowed findByBookId(@Param("bookId") Long bookId);
}
