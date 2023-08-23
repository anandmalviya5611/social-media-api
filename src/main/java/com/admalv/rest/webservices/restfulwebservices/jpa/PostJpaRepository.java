package com.admalv.rest.webservices.restfulwebservices.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admalv.rest.webservices.restfulwebservices.user.Post;

public interface PostJpaRepository extends JpaRepository<Post,Integer>{
	
}
