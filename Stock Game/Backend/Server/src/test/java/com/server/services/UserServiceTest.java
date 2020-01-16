package com.server.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.server.user.BankService;
import com.server.user.Friend;
import com.server.user.FriendService;
import com.server.user.User;
import com.server.user.UserRepository;
import com.server.user.UserService;

public class UserServiceTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository repo;

	@Mock
	FriendService friends;

	@Mock
	BankService bank;

	User user1 = new User(1, "bray", "Bray@8080", 1000.0);
	User user2 = new User(2, "test", "Test@8080", 100.0);

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void buyWithInRange() {
		when(repo.findById(1)).thenReturn(user1);
		when(repo.findById(2)).thenReturn(user2);

		int numPurchased = userService.buyStock(1, 100.0, 1);
		assertEquals(numPurchased, 1);
		assertTrue(user1.getCash() == 900.0);

		numPurchased = userService.buyStock(2, 100.0, 1);
		assertEquals(numPurchased, 1);
		assertTrue(user2.getCash() == 0.0);
	}

	@Test
	public void buyOutOfRange() {
		when(repo.findById(1)).thenReturn(user1);
		when(repo.findById(2)).thenReturn(user2);

		int numPurchased = userService.buyStock(1, 501, 2);
		assertEquals(numPurchased, 1);
		assertTrue(user1.getCash() == 499.0);

		numPurchased = userService.buyStock(2, 101, 1);
		assertEquals(numPurchased, 0);
		assertTrue(user2.getCash() == 100.0);
	}

	@Test
	public void sell() {
		when(repo.findById(1)).thenReturn(user1);
		when(repo.findById(2)).thenReturn(user2);

		userService.sellStock(1, 100.0, 1);
		assertTrue(user1.getCash() == 1100.0);

		userService.sellStock(2, 100.0, 3);
		assertTrue(user2.getCash() == 400.0);
	}

	@Test
	public void createValidUser() {
		when(repo.findByUsername("bray")).thenReturn(null);

		String response = userService.createUser(user1);
		assertEquals(response, "User bray created");
	}

	@Test
	public void createInvalidUser() {
		User user = new User(1, null, null, 0.0);

		String response = userService.createUser(user);
		assertEquals(response, "Username cannot be null");

		user.setUsername("bray");
		response = userService.createUser(user);
		assertEquals(response, "Password cannot be null");

		user.setPassword("bray@8080");
		response = userService.createUser(user);
		assertEquals(response,
				"Password must be 8 characters or longer \nPassword must contain at least one uppercase \nPassword must contain at least one lowercase level");

		when(repo.findByUsername("bray")).thenReturn(user1);
		user.setPassword("Bray@8080");
		response = userService.createUser(user);
		assertEquals(response, "Username taken");
	}

	@Test
	public void addFriend() {
		when(friends.findUserFriend(1, user2)).thenReturn(null);
		when(repo.findById(2)).thenReturn(user2);

		String response = userService.addFriend(1, 2);
		assertEquals(response, "Friend test added");
	}

	@Test
	public void addFriendAgain() {
		when(friends.findUserFriend(1, user2)).thenReturn(new Friend(1, user2));
		when(repo.findById(2)).thenReturn(user2);

		String response = userService.addFriend(1, 2);
		assertEquals(response, "You are already friends with this user");
	}

	@Test
	public void removeFriend() {
		when(repo.findById(2)).thenReturn(user2);
		when(friends.findUserFriend(1, user2)).thenReturn(new Friend(1, user2));

		String response = userService.removeFriend(1, 2);
		assertEquals(response, "Friend test removed");
	}

	@Test
	public void removeFriendAgain() {
		when(repo.findById(2)).thenReturn(user2);
		when(friends.findUserFriend(1, user2)).thenReturn(null);
		
		String response = userService.removeFriend(1, 2);
		assertEquals(response, "You are not friends with this user");
	}

}
