package com.server.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for user functions
 * @author Bennett Ray
 *
 */
@RestController
public class UserController {

	/**
	 * Service methods for user functions
	 */
	@Autowired
	private UserService userService;

	/**
	 * Find All Users Endpoint
	 * @return List of Users from UserService method findAll 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public List<User> findAll() {
		return userService.findAll();
	}
	
	/**
	 * Login Endpoint
	 * @param username The inputed string corresponding to the username
	 * @param password The inputed string corresponding to the password
	 * @return ResponseEntity containing the error message if the credentials are incorrect or the User object if the credentials are correct
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users/login")
	public ResponseEntity<?> authenticate(@RequestParam String username, String password) {
		return userService.authenticate(username, password);
	}

	/**
	 * Create a New User Endpoint
	 * @param user A user object containing at least the username and password, and possible the first and/or last name of the new user
	 * @return Message from the UserService method createUser
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users/createUser")
	public String createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	/**
	 * Add Friend Enpoint
	 * @param userId The id of the user who is adding the friend
	 * @param friendId The id of the user who is being added as a friend
	 * @return Message from the UserService method addFriend
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users/addFriend")
	public String addFriends(@RequestParam int userId, @RequestParam int friendId) {
		return userService.addFriend(userId, friendId);
	}

	/**
	 * Remove Friend Endpoint
	 * @param userId The id of the user who is removing the friend
	 * @param friendId The id of the user who is being removed as a friend
	 * @return Message from the UserService method removeFriend
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users/removeFriend")
	public String removeFriends(@RequestParam int userId, @RequestParam int friendId) {
		return userService.removeFriend(userId, friendId);
	}

	/**
	 * Friends Search Endpoint
	 * @param index A string that will be used to search for users
	 * @return List of Users returned from the UserService method friendsSearch
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/users/friendsSearch")
	public List<User> friendsSearch(@RequestParam String index) {
		return userService.friendsSearch(index);
	}

	/**
	 * Find User By Id Endpoint
	 * @param userId The id of the expected user
	 * @return The User from the UserService method findById
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/users/{userId}")
	public User findById(@PathVariable int userId) {
		return userService.findById(userId);
	}
	
	/**
	 * Delete User Endpoint
	 * @param userId The id of the user who will be deleted
	 * @return Message from the UserService method deleteUser
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/users/{userId}/deleteUser")
	public String deleteUser(@PathVariable int userId) {
		return userService.deleteUser(userId);
	}

	/**
	 * Edit User Endpoint
	 * @param userId The id of the user who will be edited
	 * @param user A user object containing the new details of the user
	 * @return Message from the UserService method editUser
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users/{userId}/editUser")
	public String editUser(@PathVariable int userId, @RequestBody User user) {
		return userService.editUser(userId, user);
	}
	
	/**
	 * Get Cash Endpoint
	 * @param userId The id of the user whose cash is being requested
	 * @return Cash from UserService method getCash
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/users/{userId}/cash")
	public Double getCash(@PathVariable int userId) {
		return userService.getCash(userId);
	}

	/**
	 * Ban User Endpoint
	 * @param adminId The id of the admin who is banning a user
	 * @param userId The id of the user who is being banned
	 * @return Message from UserService method banUser
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users/{adminId}/banUser")
	public String banUser(@PathVariable int adminId, @RequestParam int userId) {
		return userService.banUser(adminId, userId, true);
	}

	/**
	 * Unban User Endpoint
	 * @param adminId The id of the admin who is unbanning a user
	 * @param userId The id of the user who is being unbanned
	 * @return Message from UserService method banUser
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users/{adminId}/unbanUser")
	public String unbanUser(@PathVariable int adminId, @RequestParam int userId) {
		return userService.banUser(adminId, userId, false);
	}

	/**
	 * Add Admin Endpoint
	 * @param adminId The id of the admin who is a promoting a user
	 * @param userId The id of the user who is being promoted
	 * @return Message from UserService method addAdmin
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users/{adminId}/addAdmin")
	public String addAdmin(@PathVariable int adminId, @RequestParam int userId) {
		return userService.addAdmin(adminId, userId);
	}

	/**
	 * Get Friends Endpoint
	 * @param userId The id of the user whose friends are being requested
	 * @return List of Users from the UserService method getFriends
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/users/{userId}/friends")
	public List<User> getFriends(@PathVariable int userId) {
		return userService.getFriends(userId);
	}

	

}
