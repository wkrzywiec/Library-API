package com.wkrzywiec.spring.libraryrest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
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
}
