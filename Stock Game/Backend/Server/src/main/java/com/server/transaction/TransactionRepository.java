package com.server.transaction;


import java.util.List;

import java.sql.Timestamp;
import org.springframework.data.repository.CrudRepository;


/**
 * Interface for accessing transaction table
 * @author Feng Lin
 *
 */
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

	/**
	 * Get transaction from table by id
	 * @param id Id of requested transaction
	 * @return Transaction object
	 */
	Transaction findById(int id);
	
	/**
	 * Get a user's transactions from the table
	 * @param userId The id of the user whose transactions are being requested
	 * @return List of Transaction objects
	 */
	List<Transaction> findByUserId(int userId);
	
	



	

	

	
	
	
	
	
	
}