package com.server.inventory;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.server.stock.Stock;

/**
 * Rest controller for inventory functions
 * 
 * @author Bennett Ray
 *
 */
@RestController
@RequestMapping("/users")
public class InventoryController {

	/**
	 * Service methods for Inventory functions
	 */
	@Autowired
	InventoryService inventory;

	/**
	 * Sell Stock Endpoint
	 * 
	 * @param userId
	 *            Id of the user who is selling stocks
	 * @param stockId
	 *            Id of the stock being sold
	 * @param num
	 *            Number of stocks to be sold
	 * @return Message from InventoryService method sellStock
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/{userId}/portfolio/sell")
	public String sellStock(@PathVariable int userId, @RequestParam int stockId, @RequestParam int num) {
		return inventory.sellStock(userId, stockId, num);
	}

	/**
	 * Buy Stock Endpoint
	 * 
	 * @param userId
	 *            Id of the user who is buying stocks
	 * @param stockId
	 *            Id of the stock being bought
	 * @param num
	 *            Number of stocks to be bought
	 * @return Message from InventoryService method buyStock
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/{userId}/portfolio/buy")
	public String buyStock(@PathVariable int userId, @RequestParam int stockId, @RequestParam int num) {
		return inventory.buyStock(userId, stockId, num);
	}

	/**
	 * Get Portfolio Endpoint
	 * 
	 * @param userId
	 *            Id of the user whose portfolio is being requested
	 * @return List of stocks from the InventoryService method getPortfolio
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{userId}/portfolio")
	public List<Stock> getPortfolio(@PathVariable int userId) {
		return inventory.getPortfolio(userId);
	}

	/**
	 * Get Portfolio Value Endpoint
	 * 
	 * @param userId
	 *            Id of the user who portfolio value is being requested
	 * @return Portfolio value from the InventoryService method getPortfolioValue
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{userId}/portfolioValue")
	public Double getPortfolioValue(@PathVariable int userId) {
		return inventory.getPortfolioValue(userId);
	}


	/**
	 * Gacha Endpoint
	 *
	 * @param userId
	 *            Id of the user who portfolio value is being requested
	 * @param stockIdArr
	 *            Array of stock ID that want to purchased.
	 * @param numArr
	 * 	          Array of numbers draw for each stock.
	 *
	 * @return draw result
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/{userId}/portfolio/draw")
	public List<Stock> drawStock(@PathVariable int userId, @RequestParam int stockIdArr[], @RequestParam int numArr[]) {
		return inventory.drawStock(userId, stockIdArr, numArr);
	}
}
