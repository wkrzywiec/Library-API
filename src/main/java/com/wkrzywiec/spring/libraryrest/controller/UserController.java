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

import com.wkrzywiec.spring.libraryrest.assembler.UserResourceAssembler;
import com.wkrzywiec.spring.libraryrest.exception.UserNotFoundException;
import com.wkrzywiec.spring.libraryrest.model.Reserved;
import com.wkrzywiec.spring.libraryrest.model.User;
import com.wkrzywiec.spring.libraryrest.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserResourceAssembler userAssembler;
	
	@GetMapping(value="/users",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Resources<Resource<User>> getAllUsers() {
		
		List<Resource<User>> users = repository.findAll().stream()
				.map(userAssembler::toResource)
				.collect(Collectors.toList());

		return new Resources<>(users,
				linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
	}
	
	@GetMapping(value="/users/{id}",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<User> getUser(@PathVariable Long id) {
		
		User user = repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
		
		return userAssembler.toResource(user);
	}
}
