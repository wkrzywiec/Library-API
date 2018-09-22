package com.wkrzywiec.spring.libraryrest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wkrzywiec.spring.libraryrest.exception.UserNotFoundException;
import com.wkrzywiec.spring.libraryrest.model.ErrorRespond;

@ControllerAdvice
public class UserAdvice {

	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> userNotFoundHandler(UserNotFoundException ex) {
		
		ErrorRespond error = new ErrorRespond();
		error.setStatus(HttpStatus.NOT_FOUND.value() + ", " + HttpStatus.NOT_FOUND.getReasonPhrase());
		error.setMessage(ex.getMessage());
		error.setDetails("You can't make any action on a non-existing resource");
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
}
