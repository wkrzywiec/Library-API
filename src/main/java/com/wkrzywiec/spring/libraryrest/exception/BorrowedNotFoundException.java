package com.wkrzywiec.spring.libraryrest.exception;

public class BorrowedNotFoundException extends RuntimeException {

	public BorrowedNotFoundException(Long id) {
		super("Could not find borrowed with id: " + id);
	}
	
	public BorrowedNotFoundException(String message) {
		super(message);
	}
}
