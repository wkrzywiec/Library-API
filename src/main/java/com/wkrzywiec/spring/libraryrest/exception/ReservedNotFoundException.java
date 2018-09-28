package com.wkrzywiec.spring.libraryrest.exception;

public class ReservedNotFoundException extends RuntimeException {

	public ReservedNotFoundException(Long id) {
		super("Could not find reservation with id: " + id);
	}
	
	public ReservedNotFoundException(String message) {
		super(message);
	}
}
