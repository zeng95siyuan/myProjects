package com.bennett;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {

	@GetMapping("/")
	public String welcome() {
		return "Server for Spring Experiment \nPath Descriptions: \n/users: List all users \n/users/transactions: List of all transactions \n/users/{userId}/transacations: Transactions of the user specified by the ID" ;
	}
}
