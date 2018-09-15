package com.wkrzywiec.spring.libraryrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wkrzywiec.spring.libraryrest.exception.UserNotFoundException;
import com.wkrzywiec.spring.libraryrest.model.User;
import com.wkrzywiec.spring.libraryrest.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return repository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
	}
	
}
