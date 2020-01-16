package com.server.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Bank Object Class
 * @author Bennett Ray
 *
 */
@Entity
@Table(name = "bank")
public class Bank {

	/**
	 * Id of the Bank account (equivalent to the user id)
	 * 
	 */
	@Id
	@Column(name = "id")
	private int id;

	/**
	 * Cash in the bank account
	 */
	@Column(name = "cash")
	private Double cash;

	/**
	 * The user who this bank belongs to
	 */
	@OneToOne(mappedBy = "bank")
	private User user;

	/**
	 * Empty Constructor
	 */
	public Bank() {

	}

	/**
	 * Constructor for creating a new Bank Object
	 * @param id The user's id
	 */
	public Bank(int id) {
		this.id = id;
		this.cash = 10000.0;
	}
	
	
	/**
	 * Testing Constructor
	 * @param id The user's id
	 * @param cash The user's cash.
	 */
	public Bank(int id, Double cash) {
		this.id = id;
		this.cash = cash;
	}

	/**
	 * Get the cash 
	 * @return User's cash amount
	 */
	public double getCash() {
		return cash;
	}

	/**
	 * Set the cash
	 * @param cash User's new cash amount
	 */
	public void setCash(Double cash) {
		this.cash = cash;
	}

}
