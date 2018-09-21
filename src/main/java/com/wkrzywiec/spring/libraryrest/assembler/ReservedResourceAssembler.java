package com.wkrzywiec.spring.libraryrest.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.wkrzywiec.spring.libraryrest.controller.ReservedController;
import com.wkrzywiec.spring.libraryrest.model.Reserved;

@Component
public class ReservedResourceAssembler implements ResourceAssembler<Reserved, Resource<Reserved>> {

	@Override
	public Resource<Reserved> toResource(Reserved reserved) {
		
		return new Resource<>(reserved,
				linkTo(methodOn(ReservedController.class).getAllReservedBooks()).withRel("reserved"),
				linkTo(methodOn(ReservedController.class).getReservedById(reserved.getId())).withSelfRel());
	}

}
