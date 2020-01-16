package com.server.stock;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for stock functions
 * @author Bennett Ray
 *
 */
@RestController
@RequestMapping("/stock")
public class StockController {
	
	/**
	 * Service methods for stock functions
	 */
	@Autowired
	private StockService stockService;

	/**
	 * Get Stock By Id Endpoint
	 * @param stockId Id of the requested stock
	 * @return Stock as a JSONObject from the StockService method getStockById
	 */
	@RequestMapping(method = RequestMethod.GET)
	public JSONObject getStockById(@RequestParam int stockId) {
		return stockService.getStockById(stockId);
	}
	
	/**
	 * Stock Search Endpoint
	 * @param index The search term
	 * @return List of Stocks from the StockService method searchStocks
	 */
	@RequestMapping(method = RequestMethod.GET, path="/search")
	public Stock[] searchStocks(String index) {
		return stockService.searchStocks(index);
	}
	
	/**
	 * Get Historic Stock Data Endpoint
	 * @param stockId The id of the requested stock
	 * @return JSONArray of Stock Date from the StockService method getHistoricData
	 */
	@RequestMapping(method = RequestMethod.GET, path="/historicData")
	public JSONArray getHistoricData(@RequestParam int stockId){
		return stockService.getHistoricData(stockId, null);
	}
	
	/**
	 * Get Historic Stock Date With Range Endpoint
	 * @param stockId The id of the requested stock
	 * @param range The range of data requested
	 * @return JSONArray of Stock Date from the StockService method getHistoricData
	 */
	@RequestMapping(method = RequestMethod.GET, path="/historicData/{range}")
	public JSONArray getHistoricDataWithRange(@RequestParam int stockId, @PathVariable String range){
		return stockService.getHistoricData(stockId, range);
	}
	
	
	
	
	
	
}
