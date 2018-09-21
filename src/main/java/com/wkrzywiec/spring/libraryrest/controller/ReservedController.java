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

import com.wkrzywiec.spring.libraryrest.assembler.ReservedResourceAssembler;
import com.wkrzywiec.spring.libraryrest.exception.ReservedNotFoundException;
import com.wkrzywiec.spring.libraryrest.model.Reserved;
import com.wkrzywiec.spring.libraryrest.repository.ReservedReposiotry;

@RestController
public class ReservedController {

	@Autowired
	private ReservedReposiotry repository;
	
	@Autowired
	private ReservedResourceAssembler assembler;
	
	@GetMapping(value="/reserved", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Resources<Resource<Reserved>> getAllReservedBooks() {
		
		List<Resource<Reserved>> reservations = repository.findAll().stream()
				.map(assembler::toResource)
				.collect(Collectors.toList())
		;
		
		return new Resources<>(reservations,
				linkTo(methodOn(ReservedController.class).getAllReservedBooks()).withSelfRel());
	}
	
	@GetMapping(value="/reserved/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Reserved> getReservedById(@PathVariable Long id){
		
		Reserved reserved  = repository.findById(id)
				.orElseThrow(() -> new ReservedNotFoundException(id));
		
		return assembler.toResource(reserved);
	}
}
