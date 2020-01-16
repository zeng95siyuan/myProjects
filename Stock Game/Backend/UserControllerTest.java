package com.server.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

import com.server.pojo.Bank;
import com.server.pojo.User;
import com.server.services.InventoryService;
import com.server.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	User user1 = new User("bray", "bray");
	User user2 = new User("test", "test");

	@Before
	public void setup() {
		userService.createUser(user1);
		user1.setId(1);
		userService.createUser(user2);
		user2.setBanned(true);
	}

	@Test
	public void loginTest() throws Exception {

		// Successful login
		String mockString = user1.toString();
		when(userService.authenticate(user1.getUsername(), user1.getPassword())).thenReturn(mockString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login").accept(MediaType.APPLICATION_JSON)
				.param("username", user1.getUsername()).param("password", user1.getPassword())
				.contentType(MediaType.APPLICATION_JSON);

		String result = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();

		assertEquals(result, mockString);

		// Incorrect Username
		mockString = "User does not exist";
		when(userService.authenticate("bray1", user1.getPassword())).thenReturn(mockString);
		requestBuilder = MockMvcRequestBuilders.post("/users/login").accept(MediaType.APPLICATION_JSON)
				.param("username", "bray1").param("password", user1.getPassword())
				.contentType(MediaType.APPLICATION_JSON);

		result = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();

		assertEquals(mockString, result);

		// Incorrect Password
		mockString = "Incorrect Password";
		when(userService.authenticate(user1.getUsername(), "bray1")).thenReturn(mockString);
		requestBuilder = MockMvcRequestBuilders.post("/users/login").accept(MediaType.APPLICATION_JSON)
				.param("username", user1.getUsername()).param("password", "bray1")
				.contentType(MediaType.APPLICATION_JSON);

		result = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();

		assertEquals(mockString, result);

		// Banned user tries to login
		mockString = "You are banned";
		when(userService.authenticate(user2.getUsername(), user2.getPassword())).thenReturn(mockString);
		requestBuilder = MockMvcRequestBuilders.post("/users/login").accept(MediaType.APPLICATION_JSON)
				.param("username", user2.getUsername()).param("password", user2.getPassword())
				.contentType(MediaType.APPLICATION_JSON);

		result = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();

		assertEquals(mockString, result);

	}

}
