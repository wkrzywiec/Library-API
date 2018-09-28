package com.wkrzywiec.spring.libraryrest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wkrzywiec.spring.libraryrest.assembler.BorrowedResourceAssembler;
import com.wkrzywiec.spring.libraryrest.exception.BorrowedNotFoundException;
import com.wkrzywiec.spring.libraryrest.model.Borrowed;
import com.wkrzywiec.spring.libraryrest.repository.BorrowedRepository;

@RestController
public class BorrowedController {

	@Autowired
	BorrowedRepository repository;
	
	@Autowired
	BorrowedResourceAssembler assembler;
	
	@GetMapping(value="/borrowed",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Resources<Resource<Borrowed>> getAllBorrowedBooks(){
		
		List<Resource<Borrowed>> borrowed = repository.findAll().stream()
				.map(assembler::toResource)
				.collect(Collectors.toList())
		;
		
		return new Resources<>(borrowed,
				linkTo(methodOn(BorrowedController.class).getAllBorrowedBooks()).withSelfRel());
	}
	
	@GetMapping(value="/borrowed/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Borrowed> getBorrowedById(@PathVariable Long id){
		
		Borrowed borrowed  = repository.findById(id)
				.orElseThrow(() -> new BorrowedNotFoundException(id));
		
		return assembler.toResource(borrowed);
	}
	
	@GetMapping(value="/borrowed/users/{userId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Resources<Resource<Borrowed>> getAllBorrowedBooksForUser(@PathVariable Long userId) {

		List<Resource<Borrowed>> borrowed = repository.findByUserId(userId).stream()
					.map(assembler::toResource)
					.collect(Collectors.toList())
		;
		
		if (borrowed.isEmpty()) 
			throw new BorrowedNotFoundException("User with and Id: " + userId + " has not borrowed any book.");

		return new Resources<>(borrowed,
				linkTo(methodOn(BorrowedController.class).getAllBorrowedBooks()).withSelfRel());
	}
	
	@GetMapping(value="/borrowed/books/{bookId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Borrowed> getBorrowedForBook(@PathVariable Long bookId) {

		Optional.ofNullable(repository.findByBookId(bookId))
			.orElseThrow(() -> new BorrowedNotFoundException("Book with an Id: " + bookId + " is not borrowed."));
		
		Borrowed borrowed = repository.findByBookId(bookId);

		return assembler.toResource(borrowed);
	}
}
