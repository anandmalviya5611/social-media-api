package com.admalv.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.admalv.rest.webservices.restfulwebservices.jpa.PostJpaRepository;
import com.admalv.rest.webservices.restfulwebservices.jpa.UserJpaRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	private UserJpaRepository repository;
	private PostJpaRepository postrepository;
	
	public UserJpaResource(UserJpaRepository repository, PostJpaRepository postrepository) {
		//this.service = service;
		this.repository = repository;
		this.postrepository = postrepository;
		}
	// GET /users
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return repository.findAll();
	}
	
	// GET /users/{id}
	
	//EntityModel
	//WebMvcLinkBuilder
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		 Optional<User> user = repository.findById(id);
		 if(user.isEmpty()) {
			 throw new UserNotFoundException("id:"+ id);
		 }
		 //creating entity model object based on the content(user)
		 //getting user object from the optional requires the use of get method 
		 EntityModel<User> entityModel = EntityModel.of(user.get());
		 //utility class to build links
		 WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		 entityModel.add(link.withRel("all-users"));
		 return entityModel;
	}
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		 repository.deleteById(id);
	}
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id) {
		 
		//process breakdown:
		//find the user
		 Optional<User> user = repository.findById(id);
		 if(user.isEmpty()) {
			 throw new UserNotFoundException("id:"+ id);
		 }
		//return the posts using getter method 
		 return user.get().getPosts();

	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser=repository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostsForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		 
		//process breakdown:
		//find the user
		 Optional<User> user = repository.findById(id);
		 if(user.isEmpty()) {
			 throw new UserNotFoundException("id:"+ id);
		 }
		 //setting the user for the post object
		 post.setUser(user.get());
		 Post savedPost = postrepository.save(post);
		 
		 //creating the URI
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedPost.getId())
					.toUri();
			return ResponseEntity.created(location).build();

	}
	/*@GetMapping("/jpa/users/{user_id}/posts/{post_id}")
	public Post getPostById(@PathVariable int user_id, @PathVariable int post_id) {
	User user =	repository.findById(user_id);
		
	
		
	}*/
}
