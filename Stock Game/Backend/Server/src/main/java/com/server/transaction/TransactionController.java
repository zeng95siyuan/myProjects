package com.server.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Rest controller for transaction functions
 * 
 * @author Feng Lin
 *
 */
@RestController
@RequestMapping("/users")
public class TransactionController {

	/**
	 * Service methods for transaction functions
	 */
	@Autowired
	TransactionService transaction;

	/**
	 * Get Transactions By Id Endpoint
	 * @param userId The id of the user whose transactions are being requested
	 * @return List of Transactions from the TransactionService method getTransactionsByUserId
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{userId}/transaction")
	public List<Transaction> getTransactionsByUserId(@PathVariable int userId) {
		return transaction.getTransactionsByUserId(userId);

	}

}
