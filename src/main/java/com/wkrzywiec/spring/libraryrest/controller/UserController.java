package com.wkrzywiec.spring.libraryrest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wkrzywiec.spring.libraryrest.assembler.UserResourceAssembler;
import com.wkrzywiec.spring.libraryrest.exception.UserNotFoundException;
import com.wkrzywiec.spring.libraryrest.model.Role;
import com.wkrzywiec.spring.libraryrest.model.User;
import com.wkrzywiec.spring.libraryrest.repository.RoleRepository;
import com.wkrzywiec.spring.libraryrest.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserResourceAssembler userAssembler;
	
	@GetMapping(value="/users",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Resources<Resource<User>> getAllUsers() {
		
		List<Resource<User>> users = userRepository.findAll().stream()
				.map(userAssembler::toResource)
				.collect(Collectors.toList());

		return new Resources<>(users,
				linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
	}
	
	@GetMapping(value="/users/{id}",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<User> getUser(@PathVariable Long id) {
		
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
		
		return userAssembler.toResource(user);
	}
	
	@PostMapping("/users")
	public ResponseEntity<Resource<User>> newUser(@RequestBody User user) throws URISyntaxException{
		
		user.setPassword("$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje");
		user.addRole(roleRepository.getOne(1L));
		
		Resource<User> resource = userAssembler.toResource(userRepository.save(user));
		
		return ResponseEntity
					.created(new URI(resource.getId().expand().getHref()))
					.body(resource);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id){
		
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
		
		userRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
