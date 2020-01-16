package com.server.stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.server.inventory.Inventory;

/**
 * Stock Class Object
 * 
 * @author Bennett Ray
 *
 */
@Entity
@Table(name = "Stock")
public class Stock {

	/**
	 * Endpoint for IEX Trading API
	 * https://iextrading.com/developer/docs/#getting-started
	 */
	@Transient
	private final String IEXSTOCK = "https://ws-api.iextrading.com/1.0/stock/";

	/**
	 * Empty Constructor
	 */
	public Stock() {

	}

	/**
	 * Testing Constructor
	 * 
	 * @param id Id of the stock object
	 * @param symbol Ticker symbol of the stock
	 * @param company The name of the company of the stock
	 */
	public Stock(int id, String symbol, String company) {
		this.id = id;
		this.symbol = symbol;
		this.company = company;
	}

	/**
	 * Gacha Constructor
	 *
	 * @param symbol Ticker symbol of the stock
	 * @param price The price of the stock
	 * @param quantity The quantity of the stock
	 */
	public Stock(String symbol, Double price, Integer quantity) {
		this.symbol = symbol;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * Id of the stock
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * Ticker symbol of the stock
	 */
	@Column(name = "Symbol")
	private String symbol;

	/**
	 * Company name of the stock
	 */
	@Column(name = "Company_Name")
	private String company;

	/**
	 * The price of the stock (not in stock table)
	 */
	@Transient
	private Double price;

	/**
	 * Quantity of the stock (not in stock table) Only a field to make passing to
	 * front end cleaner
	 */
	@Transient
	private Integer quantity;

	/**
	 * Any inventory entry that includes this stock
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Inventory inventory;

	/**
	 * Get the stock id
	 * 
	 * @return Id of stock object
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the stock id
	 * 
	 * @param id The stock id to be set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the stock symbol
	 * 
	 * @return Ticker symbol of the stock
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Set the stock symbol
	 * 
	 * @param symbol The ticker symbol to be set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Get the stock company
	 * 
	 * @return The stock's company name
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * Set the stock company
	 * 
	 * @param company The stock's company name to be set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * Get the stock quantity. Set in Inventory Service
	 * 
	 * @return The number of stocks owned
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Set the stock quantity
	 * 
	 * @param quantity The number of stocks to be set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Get the stock price. Calls setPrice() to get the real time price
	 * 
	 * @return The current price of the stock
	 */
	public double getPrice() {
		this.setPrice();
		return price;
	}

	/**
	 * Set the real time price of the stock
	 */
	public void setPrice() {
		try {
			URL url = new URL(IEXSTOCK + this.getSymbol() + "/price");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			this.price = Double.parseDouble(rd.readLine());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * **TESTING METHOD** Check if the id, symbol, and company of 2 stocks are equal
	 * 
	 * @param stock The stock to be compared to this stock object
	 * @return True if the stocks are equal. Otherwise returns false
	 */
	public boolean equal(Stock stock) {
		return (this.id == stock.id && this.symbol.equals(stock.symbol) && this.company.equals(stock.company));
	}

}
