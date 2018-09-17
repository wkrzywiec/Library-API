package com.wkrzywiec.spring.libraryrest.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.wkrzywiec.spring.libraryrest.controller.UserController;
import com.wkrzywiec.spring.libraryrest.model.User;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {

	@Override
	public Resource<User> toResource(User user) {
		
		return new Resource<> (user,
				linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
				linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
	}
}
