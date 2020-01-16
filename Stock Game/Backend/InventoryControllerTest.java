package com.server.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.server.pojo.Inventory;
import com.server.pojo.Stock;
import com.server.services.InventoryService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = InventoryController.class)
public class InventoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private InventoryService inventoryService;

	Stock mockStock1 = new Stock("AAPL", "Apple", 1);
	Stock mockStock2 = new Stock("AMZN", "Amazon", 3);
	Stock mockStock3 = new Stock("AA", "Alcoa", 2);

	Inventory i1;
	Inventory i2;

	@Before
	public void setUp() {
		i1 = new Inventory(1, mockStock1, 1);
		mockStock1.setId(1);
		i2 = new Inventory(1, mockStock2, 3);
		mockStock2.setId(2);
		mockStock3.setId(3);
	}

	@Test
	public void getPortfolioTest() throws Exception {

		when(inventoryService.getPortfolio(1)).thenReturn(Arrays.asList(mockStock1, mockStock2));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/1/portfolio")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(jsonPath("$[0].id").exists())
				.andExpect(jsonPath("$[0].symbol").value("AAPL")).andExpect(jsonPath("$[0].company").value("Apple"))
				.andExpect(jsonPath("$[0].price").exists()).andExpect(jsonPath("$[0].quantity").value(1))
				.andExpect(jsonPath("$[1].id").exists()).andExpect(jsonPath("$[1].symbol").value("AMZN"))
				.andExpect(jsonPath("$[1].company").value("Amazon")).andExpect(jsonPath("$[1].price").exists())
				.andExpect(jsonPath("$[1].quantity").value(3));
	}

	@Test
	public void buyTest() throws Exception {

		// buy 1 stock of a stock already owned
		String mockString = "1 stock(s) of AAPL purchased";
		when(inventoryService.buyStock(1, 1, 1)).thenReturn(mockString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/1/portfolio/buy")
				.accept(MediaType.APPLICATION_JSON).param("stockId", "1").param("num", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(mockString, result.getResponse().getContentAsString());
		// buy stock that is not owned
		mockString = "1 stock(s) of AA purchased";
		when(inventoryService.buyStock(1, 3, 1)).thenReturn(mockString);
		requestBuilder = MockMvcRequestBuilders.post("/users/1/portfolio/buy").accept(MediaType.APPLICATION_JSON)
				.param("stockId", "3").param("num", "1").contentType(MediaType.APPLICATION_JSON);

		result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(mockString, result.getResponse().getContentAsString());

		when(inventoryService.getPortfolio(1)).thenReturn(Arrays.asList(mockStock1, mockStock2, mockStock3));

		requestBuilder = MockMvcRequestBuilders.get("/users/1/portfolio").accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(jsonPath("$[2].id").exists());
	}

	@Test
	public void sellTest() throws Exception {
		// sell a stock
		String mockString = "1 stock(s) of AAPL sold";

		when(inventoryService.sellStock(1, 1, 1)).thenReturn(mockString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/1/portfolio/sell")
				.accept(MediaType.APPLICATION_JSON).param("stockId", "1").param("num", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(mockString, result.getResponse().getContentAsString());

		// try to sell a stock not owned
		mockString = "User does not own and shares of AA";
		when(inventoryService.sellStock(1, 3, 1)).thenReturn(mockString);

		requestBuilder = MockMvcRequestBuilders.post("/users/1/portfolio/sell").accept(MediaType.APPLICATION_JSON)
				.param("stockId", "3").param("num", "1").contentType(MediaType.APPLICATION_JSON);
		result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(mockString, result.getResponse().getContentAsString());
	}

}
