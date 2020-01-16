package com.server.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.server.inventory.Inventory;
import com.server.inventory.InventoryRepository;
import com.server.inventory.InventoryService;
import com.server.stock.Stock;
import com.server.stock.StockService;
import com.server.transaction.TransactionService;
import com.server.user.UserService;

public class InventoryServiceTest {

	@InjectMocks
	InventoryService service;

	@Mock
	InventoryRepository repo;

	@Mock
	TransactionService transactions;

	@Mock
	StockService stocks;

	@Mock
	UserService userService;

	Stock mockStock1 = new Stock(1, "AAPL", "Apple");
	Stock mockStock2 = new Stock(2, "AMZN", "Amazon");
	Stock mockStock3 = new Stock(3, "AA", "Alcoa");

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void buyTest() {
		Inventory i = new Inventory(1, mockStock1, 1);
		when(repo.findByUserIdAndStockId(1, 1)).thenReturn(i);
		when(userService.buyStock(1, i.getStock().getPrice(), 1)).thenReturn(1);
		when(stocks.findById(1)).thenReturn(mockStock1);

		String result = service.buyStock(1, 1, 1);

		assertEquals("1 share(s) of AAPL purchased", result);
		assertTrue(i.getQuantity() == 2);
		service.buyStock(1, 1, 1);
		assertTrue(i.getQuantity() == 3);
	}

	@Test
	public void sellTest() {
		Inventory i = new Inventory(1, mockStock1, 2);
		when(repo.findByUserIdAndStockId(1, 1)).thenReturn(i);
		when(stocks.findById(1)).thenReturn(mockStock1);

		String result = service.sellStock(1, 1, 1);
		assertEquals("1 share(s) of AAPL sold", result);
		assertTrue(i.getQuantity() == 1);

		when(repo.findByUserIdAndStockId(1, 1)).thenReturn(null);
		result = service.sellStock(1, 1, 1);
		assertEquals("User does not own any shares of AAPL", result);
	}

	@Test
	public void getPortfolioTest() {
		Inventory i1 = new Inventory(1, mockStock1, 2);
		Inventory i2 = new Inventory(2, mockStock2, 3);
		Inventory i3 = new Inventory(3, mockStock3, 5);
		
		when(repo.findByUserId(1)).thenReturn(Arrays.asList(i1, i2, i3));
		
		List<Stock> stocks = service.getPortfolio(1);
		assertTrue(mockStock1.equal(stocks.get(0)));
		assertTrue(mockStock2.equal(stocks.get(1)));
		assertTrue(mockStock3.equal(stocks.get(2)));
		
		assertTrue(stocks.get(0).getQuantity() == 2);
		assertTrue(stocks.get(1).getQuantity() == 3);
		assertTrue(stocks.get(2).getQuantity() == 5);
	}

}
