package com.server.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.stock.Stock;

/**
 * Inventory Object Class
 * 
 * @author Bennett Ray
 *
 */
@Entity
@Table(name = "inventory")
public class Inventory {

	/**
	 * The id of the inventory entry. Automatically created when the inventory entry
	 * is created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * The number of stocks owned
	 */
	@Column(name = "quantity")
	private int quantity;

	/**
	 * The stock associated with the inventory entry
	 */
	@OneToOne
	@JoinColumn(name = "stock_id")
	private Stock stock;

	/**
	 * The user id associated with the inventory entry
	 */
	@Column(name = "user_id")
	private int userId;

	/**
	 * Empty Constructor
	 */
	public Inventory() {
	}

	/**
	 * Constructor for creating a new Inventory Object
	 * 
	 * @param userId The id of the user who the inventory entry belongs to
	 * @param stock  The stock the user owns
	 */
	public Inventory(int userId, Stock stock) {
		this.userId = userId;
		this.stock = stock;
		this.quantity = 0;
	}

	/**
	 * Constructor for testing
	 * 
	 * @param userId   The id of the user who the inventory entry belongs to
	 * @param stock    The stock the user owns
	 * @param quantity The number of shares of the stocks the user owns
	 */
	public Inventory(int userId, Stock stock, int quantity) {
		this.userId = userId;
		this.stock = stock;
		this.quantity = quantity;
	}

	/**
	 * Get inventory id
	 * 
	 * @return Id of the inventory entry
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set inventory id
	 * 
	 * @param id Id of the inventory entry to be set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get user id
	 * 
	 * @return Id of the user who the inventory entry belongs to
	 */
	@JsonIgnore
	@JsonProperty(value = "user")
	public int getUserId() {
		return userId;
	}

	/**
	 * Set user id
	 * 
	 * @param userId The id of the user to be set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Get stock. Sets the quantity before returning
	 * 
	 * @return The stock object associated with the inventory entry
	 */
	public Stock getStock() {
		stock.setQuantity(quantity);
		return stock;
	}

	/**
	 * Set stock
	 * 
	 * @param stock The stock to be set for the inventory entry
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}

	/**
	 * Get quantity
	 * 
	 * @return The number of shares the user owns of the stock
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Set quantity
	 * 
	 * @param quantity The number of shares to be set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
