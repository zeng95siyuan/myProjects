package com.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.server.inventory.Inventory;
import com.server.inventory.InventoryRepository;
import com.server.inventory.InventoryService;
import com.server.stock.Stock;
import com.server.stock.StockRepository;
import com.server.stock.StockService;
import com.server.transaction.Transaction;
import com.server.transaction.TransactionRepository;
import com.server.transaction.TransactionService;
import com.server.user.UserRepository;
import com.server.user.UserService;;

public class TransactionServiceTest {
	@InjectMocks
	InventoryService service;

	@Mock
	InventoryRepository repo;

	@Mock
	TransactionService transactions;

	@Mock
	StockService stocks;

	@Mock
	TransactionRepository transrepo;

	@Mock
	StockRepository stockrepo;

	@Mock
	UserService userService;

	@Mock
	UserRepository userRepo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void TransactionTest() {
		String ticker = "AAPL";
		Timestamp date = transactions.getCurrentTimeStamp();
		double price = 100.89;

		Transaction trans = new Transaction(1, ticker, date, true, 1, price);
		when(transrepo.findById(1)).thenReturn(trans);

		assertEquals(1, trans.getUserId());
		assertEquals("AAPL", trans.getTicker());
		assertEquals(date, trans.getdate());
		assertEquals(true, trans.getBuy());
		assertEquals(1, trans.getQuantity());

	}

	@Test
	public void getPortfolioValueTest() {
		Stock mockStock1 = new Stock(1, "AAPL", "Apple");
		Stock mockStock2 = new Stock(2, "AMZN", "Amazon");
		Stock mockStock3 = new Stock(3, "AA", "Alcoa");

		Inventory i1 = new Inventory(1, mockStock1, 2);
		Inventory i2 = new Inventory(1, mockStock2, 1);
		Inventory i3 = new Inventory(1, mockStock3, 1);

		mockStock1.setPrice();
		mockStock2.setPrice();
		mockStock3.setPrice();

		double value = mockStock1.getPrice() * 2 + mockStock2.getPrice() + mockStock3.getPrice();
		when(repo.findByUserId(1)).thenReturn(Arrays.asList(i1, i2, i3));
		when(repo.findQuantityByUserIdAndStockId(1, 1)).thenReturn(i1.getQuantity());

		double a = service.getPortfolioValue(1);

		assertTrue(service.getPortfolioValue(1) == Math.round(value * 100.0) / 100.0);

	}
}
