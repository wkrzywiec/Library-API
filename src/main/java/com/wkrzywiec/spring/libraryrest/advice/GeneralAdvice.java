package com.wkrzywiec.spring.libraryrest.advice;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wkrzywiec.spring.libraryrest.model.ErrorRespond;

@ControllerAdvice
public class GeneralAdvice {

	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
		
		ErrorRespond error = new ErrorRespond();
		error.setStatus(HttpStatus.BAD_REQUEST.value() + ", " + HttpStatus.BAD_REQUEST.getReasonPhrase());
		error.setMessage(ex.getMessage());
		error.setDetails("Reason: ConstraintViolationException. This entity has some constrains that doesn't allow to proceed.");
		
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
