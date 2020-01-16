package com.server.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Methods for bank functions
 * @author Bennett Ray
 *
 */
@Service
public class BankService {
	
	/**
	 * Interface for connecting to bank table
	 */
	@Autowired
	BankRepository repo;
	
	/**
	 * Save a bank object to the table
	 * @param userId Id of the user whose bank is being saved
	 */
	public void save(int userId) {
		repo.save(new Bank(userId));
	}

}
