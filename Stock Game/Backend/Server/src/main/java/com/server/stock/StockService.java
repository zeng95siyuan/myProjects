package com.server.stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Methods for stock functions
 * 
 * @author Bennett Ray
 *
 */
@Service
public class StockService {

	/**
	 * Interface for connecting to stock table
	 */
	@Autowired
	private StockRepository stocks;

	/**
	 * Endpoint for IEX Trading API
	 * https://iextrading.com/developer/docs/#getting-started
	 */
	private final String IEXSTOCK = "https://ws-api.iextrading.com/1.0/stock/";

	/**
	 * Get Stock with real time data by Id
	 * 
	 * @param id
	 *            Id of the requested Stock
	 * @return A JSONArray containing the symbol, company name, primary exchange,
	 *         latest price, change, and change percent for the stock.
	 */
	public JSONObject getStockById(int id) {
		Stock stock = stocks.findById(id);
		try {
			URL url = new URL(IEXSTOCK + stock.getSymbol()
					+ "/quote?filter=symbol,companyName,primaryExchange,latestPrice,change,changePercent");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			JSONParser jsonParser = new JSONParser();
			JSONObject obj = (JSONObject) jsonParser.parse(rd);
			return obj;
		} catch (MalformedURLException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		} catch (ParseException e) {
			e.getMessage();
		}
		return null;	
	}

	/**
	 * Get a stock without real time data by id
	 * 
	 * @param id
	 *            If of the requested stock
	 * @return The id of the stock found from the StockRepository method findById
	 */
	public Stock findById(int id) {
		return stocks.findById(id);
	}

	/**
	 * Search for stocks using an index
	 * 
	 * @param index
	 *            The search term
	 * @return List of Stocks whose ticker and/or company name contains the index
	 */
	public Stock[] searchStocks(String index) {
		return stocks.findBySymbolAndCompanyName(index);
	}

	/**
	 * Get the historic data of a stock from the IEX trading API
	 * 
	 * @param id
	 *            The id of the requested stock
	 * @param range
	 *            The range for the stock data. If none provided the default is 1
	 *            month
	 * @return JSON array containing stock prices and the date of each price
	 */
	public JSONArray getHistoricData(int id, String range) {
		Stock stock = stocks.findById(id);
		range = getValidRange(range);
		String path = stock.getSymbol() + "/chart/" + range;
		return historicDataRequest(path);
	}

	/**
	 * Makes the request to the IEX API for historic stock data
	 * 
	 * @param path
	 *            The url of the request
	 * @return JSON array containing stock prices and the date of each price
	 */
	private JSONArray historicDataRequest(String path) {
		try {
			URL url = new URL(IEXSTOCK + path + "?filter=date,close");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArray = (JSONArray) jsonParser.parse(rd);
			return jsonArray;
		} catch (MalformedURLException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		} catch (ParseException e) {
			e.getMessage();
		}
		return null;
	}

	/**
	 * Checks if the provided range is a valid range according to the IEX API
	 * https://iextrading.com/developer/docs/#chart
	 * 
	 * @param range
	 *            The range of data requested
	 * @return The range provided if it is valid. Otherwise returns the default
	 *         range "1m"
	 */
	private String getValidRange(String range) {
		switch (range) {
		case "5y":
		case "2y":
		case "1y":
		case "ytd":
		case "6m":
		case "3m":
		case "1m":
		case "1d":
			return range;
		default:
			return "1m";
		}
	}

}
