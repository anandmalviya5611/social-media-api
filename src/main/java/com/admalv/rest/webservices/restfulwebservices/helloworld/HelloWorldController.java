package com.admalv.rest.webservices.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//annotation to handle http requests
@RestController
public class HelloWorldController {
	
	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
		
	}
	
	//mapping the controller using the 'GET' method to the /greeting url
	@GetMapping(path="/greeting")
	public String greeting() {
		return "Hello World" ;
	}
	
	//returning a bean(json) instead of string
	@GetMapping(path="/greetingjson")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hello world");
	}
	
	//capturing the values of path parameters using @PathVariable annotations 
	@GetMapping(path="/greeting/path-variable/{name}")
	public HelloWorldBean helloWorldusingpathvariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	
	}
	@GetMapping(path="/greeting-internationalized")
	public String greetingInternationalized() {
		//utility method to get locale from the request header
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale );
		}
	}

