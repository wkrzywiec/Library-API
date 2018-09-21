package com.wkrzywiec.spring.libraryrest.exception;

public class BookNotFoundException extends RuntimeException {

	public BookNotFoundException(Long id) {
		super("Could not find book with id: " + id);
	}
}
