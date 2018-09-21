package com.wkrzywiec.spring.libraryrest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wkrzywiec.spring.libraryrest.exception.BorrowedNotFoundException;

@ControllerAdvice
public class BorrowedNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(BorrowedNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String borrowedNotFoundHandler(BorrowedNotFoundException ex) {
		return ex.getMessage();
	}
}
