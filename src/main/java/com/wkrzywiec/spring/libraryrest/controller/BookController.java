package com.wkrzywiec.spring.libraryrest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wkrzywiec.spring.libraryrest.assembler.BookResourceAssembler;
import com.wkrzywiec.spring.libraryrest.exception.BookNotFoundException;
import com.wkrzywiec.spring.libraryrest.model.Book;
import com.wkrzywiec.spring.libraryrest.repository.BookRepository;

@RestController
public class BookController {

	@Autowired
	private BookRepository repository;
	
	@Autowired
	private BookResourceAssembler assembler;
	
	@GetMapping(value="/books",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Resources<Resource<Book>> getAllBooks() {
		
		List<Resource<Book>> books = repository.findAll().stream()
				.map(assembler::toResource)
				.collect(Collectors.toList());

		return new Resources<>(books,
				linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel());
	}
	
	@GetMapping(value="/books/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Book> getBookById(@PathVariable Long id){
		
		Book book  = repository.findById(id)
				.orElseThrow(() -> new BookNotFoundException(id));
		
		return assembler.toResource(book);
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id){
		
		Book book = repository.findById(id)
				.orElseThrow(() -> new BookNotFoundException(id));
		
		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
