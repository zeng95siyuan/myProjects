package com.server.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Timestamp;

/**
 * Transaction Object Class
 * 
 * @author Feng Lin
 *
 */
@Entity
@Table(name = "transactions")
public class Transaction {

	/**
	 * Get the transaction id
	 * 
	 * @return Id of transaction entry
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the transaction id
	 * 
	 * @param id The id to be set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the user id
	 * 
	 * @return The id of the user who made the transaction
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Set the user id
	 * 
	 * @param userId The id of the user to be set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Get the ticker symbol
	 * 
	 * @return The ticker symbol of the stock involved in the transaction
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * Set the ticker symbol
	 * 
	 * @param ticker The ticker symbol to be set
	 */
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	/**
	 * Get the transaction time
	 * 
	 * @return The date of the transaction
	 */
	public Timestamp getdate() {
		return date;
	}

	/**
	 * Set the transaction time
	 * 
	 * @param date The date to be set
	 */
	public void setTime(Timestamp date) {
		this.date = date;
	}

	/**
	 * Get the transaction quantity
	 * 
	 * @return The number of shares involved in the transaction
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Set the transaction quantity
	 * 
	 * @param quantity The number of shares to be set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Get the buy status
	 * 
	 * @return True if the transaction was a purchase. False if the transaction was a sale
	 */
	public boolean getBuy() {
		return buy;
	}

	/**
	 * Set the buy status
	 * 
	 * @param buy The buy value to be set
	 */
	public void setBuy(boolean buy) {
		this.buy = buy;
	}

	/**
	 * Get the transaction price
	 * 
	 * @return The cost of the transaction
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Set the transaction price
	 * 
	 * @param price The cost to be set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Empty Constructor
	 */
	public Transaction() {

	}

	/**
	 * Constructor for creating transaction objects
	 * 
	 * @param userId Id of the user who made the transaction
	 * @param ticker Ticker symbol of the stock involved in the transaction
	 * @param date The date of the transaction
	 * @param buy Boolean value that indicates if the user was buying or selling
	 * @param quantity The number of shares involved in the transaction
	 * @param price The cost of the transaction
	 */
	public Transaction(int userId, String ticker, Timestamp date, boolean buy, int quantity, double price) {
		this.userId = userId;
		this.ticker = ticker;
		this.date = date;
		this.buy = buy;
		this.quantity = quantity;
		this.price = price;
	}

	/**
	 * Id of the transaction. Automatically generated when a transaction object is
	 * created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * The id of the user involved in the transaction
	 */
	@Column(name = "user_id")
	private int userId;

	/**
	 * The Ticker symbol of the stock involved in the transaction
	 */
	@Column(name = "Ticker")
	private String ticker;

	/**
	 * The date the transaction occurred
	 */
	@Column(name = "date")
	private Timestamp date;

	/**
	 * The number of shares involved in the transaction
	 */
	@Column(name = "quantity")
	private int quantity;

	/**
	 * Indicates if the transaction was a purchase or sale
	 */
	@Column(name = "buy")
	private boolean buy;

	/**
	 * The price of the transaction
	 */
	@Column(name = "price")
	private double price;

}
