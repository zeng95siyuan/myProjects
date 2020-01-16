package com.bennett.data.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private final UserRepository repository;
	Iterator<User> it;

	UserController(UserRepository repository){
		this.repository = repository;
	}
	
	@GetMapping("/users")
	public List<String> getUsers() {
		it = this.repository.findAll().iterator();
		List<String> users = new ArrayList<String>();
		while(it.hasNext()) {
			users.add(it.next().toString());
		}
		return users;
	}
	
	@GetMapping("/firstnames")
	public List<String> getFirstNames() {
		it = this.repository.findAll().iterator();
		List<String> firstNames = new ArrayList<String>();
		while(it.hasNext()) {
			firstNames.add(it.next().getFirstName());
		}
		return firstNames;
	}
	
}
