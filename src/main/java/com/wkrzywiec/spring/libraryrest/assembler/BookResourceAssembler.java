package com.wkrzywiec.spring.libraryrest.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.wkrzywiec.spring.libraryrest.controller.BookController;
import com.wkrzywiec.spring.libraryrest.model.Book;

@Component
public class BookResourceAssembler implements ResourceAssembler<Book, Resource<Book>> {

	@Override
	public Resource<Book> toResource(Book book) {
		
		return new Resource<> (book,
				linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel(),
				linkTo(methodOn(BookController.class).getAllBooks()).withRel("books"));
	}
}
