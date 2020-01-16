package com.bennett.data.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bennett.model.BaseEntity;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "stock_name")
	private String stockName;

	@Column(name = "stock_quantity")
	private int stockQuantity;

	@Column(name = "stock_price")
	private double stockPrice;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(int stockPrice) {
		this.stockPrice = stockPrice;
	}

	public String toString() {
		return "Transaction id: " + id + " User id: " + userId + " Stock Name: " + stockName + " Stock Quantity: "
				+ stockQuantity + " Stock Price: " + stockPrice;
	}
}
