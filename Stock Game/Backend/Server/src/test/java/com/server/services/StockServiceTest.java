package com.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;



import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.server.inventory.InventoryRepository;
import com.server.inventory.InventoryService;
import com.server.stock.Stock;
import com.server.stock.StockRepository;
import com.server.stock.StockService;
import com.server.transaction.TransactionService;
import com.server.user.UserService;


public class StockServiceTest {
	@InjectMocks
	InventoryService service;

	@Mock
	InventoryRepository repo;

	@Mock
	TransactionService transactions;

	@Mock
	StockService stocks;
	
	@Mock
	StockRepository stockrepo;

	@Mock
	UserService userService;
	

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void StockTest() {
		
		Stock mockStock1 = new Stock(1, "AAPL", "Apple");
		when(stockrepo.findById(1)).thenReturn(mockStock1);
		
		
		assertTrue(1 == mockStock1.getId());
		assertEquals("AAPL",mockStock1.getSymbol());
		assertEquals("Apple",mockStock1.getCompany());
		
	}

	Stock mockStock1 = new Stock(1, "AAPL", "Apple");
}
