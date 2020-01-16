package com.bennett.data.transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class TransactionController {
	
	private final TransactionRepository repository;
	
	TransactionController(TransactionRepository repository){
		this.repository = repository;
	}
	
	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions(){
		Iterator<Transaction> it = repository.findAll().iterator();
		List<Transaction> transactions = new ArrayList<Transaction>();
		while(it.hasNext()) {
			transactions.add(it.next());
		}
		return transactions;
	}
	
	@GetMapping("/users/{userId}/transactions")
	public List<Transaction> getTransactions(@PathVariable("userId") int userId){
		List<Transaction> transactions = repository.findTransactionsById(userId);
		return transactions;
		
	}
}
