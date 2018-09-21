package com.wkrzywiec.spring.libraryrest.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.wkrzywiec.spring.libraryrest.controller.BorrowedController;
import com.wkrzywiec.spring.libraryrest.model.Borrowed;

@Component
public class BorrowedResourceAssembler implements ResourceAssembler<Borrowed, Resource<Borrowed>> {

	@Override
	public Resource<Borrowed> toResource(Borrowed borrowed) {
		
		return new Resource<>(borrowed,
				linkTo(methodOn(BorrowedController.class).getAllBorrowedBooks()).withRel("borrowed"),
				linkTo(methodOn(BorrowedController.class).getBorrowedById(borrowed.getId())).withSelfRel());
	}

}
