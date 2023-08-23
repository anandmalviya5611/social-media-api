package com.admalv.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.admalv.rest.webservices.restfulwebservices.jpa.UserJpaRepository;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	private UserDaoService service;
	

	
	public UserResource(UserDaoService service) {
		this.service = service;
	}
	// GET /users
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	// GET /users/{id}
	
	//EntityModel
	//WebMvcLinkBuilder
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		 User user = service.findOne(id);
		 if(user == null) {
			 throw new UserNotFoundException("id:"+ id);
		 }
		 //creating entity model object based on the content(user)
		 EntityModel<User> entityModel = EntityModel.of(user);
		 //utility class to build links
		 WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		 entityModel.add(link.withRel("all-users"));
		 return entityModel;
	}
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		 service.DeleteById(id);
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser=service.saveUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
