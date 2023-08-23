package com.admalv.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	private static int newuserid = 0;
	static {
		users.add(new User(++newuserid,"Andy",LocalDate.now().minusYears(20)));
		users.add(new User(++newuserid,"Evelyn",LocalDate.now().minusYears(30)));
		users.add(new User(++newuserid,"Marie",LocalDate.now().minusYears(25)));
		users.add(new User(++newuserid,"Drake",LocalDate.now().minusYears(27)));
	}
	public List<User> findAll() {
		return users;
	}
	public User findOne(int id) {
		// doing this using functional programming
		
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
		
	}
	public User saveUser(User user) {
		user.setId(++newuserid);
		users.add(user);
		return user;
		
	}
	public void DeleteById(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		users.removeIf(predicate);
		
	}
	
}
