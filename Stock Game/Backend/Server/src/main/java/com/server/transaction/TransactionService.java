package com.server.transaction;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.stock.Stock;

/**
 * Methods for transaction functions
 * 
 * @author Feng Lin and Bennett Ray
 *
 */
@Service
public class TransactionService {

	/**
	 * Interface for connecting to transactions table
	 */
	@Autowired
	TransactionRepository repo;

	/**
	 * Get Transactions for a user by id
	 * 
	 * @param userId
	 *            The id of the user whose transactions are being requested
	 * @return List of Transactions from the TransactionRepository method
	 *         findByUserId
	 */
	public List<Transaction> getTransactionsByUserId(int userId) {
		return repo.findByUserId(userId);
	}

	/**
	 * Get the current Time
	 * 
	 * @return Current time in java.sql.Timestamp format
	 */
	public java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}

	/**
	 * Create a new transaction record in the transaction table
	 * @param userId Id of the user who made the transaction
	 * @param stock The stock involved in the transaction
	 * @param buy Boolean value indicating whether a stock was bought or sold
	 * @param num Number of shares involved in the transaction
	 */
	public void save(int userId, Stock stock, boolean buy, int num) {
		Timestamp date = getCurrentTimeStamp();
		double total = num * stock.getPrice();
		repo.save(new Transaction(userId, stock.getSymbol(), date, buy, num, total));
	}

}
