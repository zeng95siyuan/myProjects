package com.server.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Methods for user functions
 * 
 * @author Bennett Ray
 *
 */
@Service
public class UserService {

	/**
	 * Interface for connecting to user table
	 */
	@Autowired
	private UserRepository users;

	/**
	 * Services for User's Friends
	 */
	@Autowired
	private FriendService friends;

	/**
	 * Services for User's Cash
	 */
	@Autowired
	private BankService userBank;

	/**
	 * Get all users in the user table
	 * 
	 * @return The List of Users from the UserRepository method findAll
	 */
	public List<User> findAll() {
		return users.findAll();
	}

	/**
	 * Login method
	 * 
	 * @param username
	 *            The inputed string corresponding to the username
	 * @param password
	 *            The inputed string corresponding to the password
	 * @return Response Entity containing message if username and/or password are
	 *         incorrect. If username is null returns the message "Username cannot
	 *         be empty". If the password is null returns the message "Password
	 *         cannot be empty". If the username is invalid returns the message
	 *         "User does not exist". If the user is banned returns the message "You
	 *         are banned". If the password is incorrect returns "Incorrect
	 *         Password". Otherwise returns the User object.
	 */
	public ResponseEntity<?> authenticate(String username, String password) {
		if (username == null) {
			return new ResponseEntity<>("Username cannot be empty", HttpStatus.BAD_REQUEST);
		}
		if (password == null) {
			return new ResponseEntity<>("Password cannot be empty", HttpStatus.BAD_REQUEST);
		}
		User user = users.findByUsername(username);
		if (user == null) {
			return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
		}
		if (user.isBanned()) {
			return new ResponseEntity<>("You are banned", HttpStatus.BAD_REQUEST);
		}
		if (!user.getPassword().equals(password)) {
			return new ResponseEntity<>("Incorrect Password", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}

	/**
	 * Creates a new user and saves them to the database
	 * 
	 * @param user
	 *            A user object containing at least the username and password, and
	 *            possible the first and/or last name of the new user
	 * @return If the user object is null returns "User cannot be null". If the
	 *         username of the user object is null returns "Username cannot be
	 *         null". If the password of the user object is null returns "Password
	 *         cannot be null". If the password does not meet the password
	 *         requirements returns "Password must be 8 characters or longer
	 *         Password must contain at least one uppercase Password must contain at
	 *         least one lowercase level". If the username is the save as the
	 *         username of another user returns "Username taken". Otherwise returns
	 *         "User {username} created".
	 */
	public String createUser(User user) {
		if (user == null) {
			return "User cannot be null";
		}
		if (user.getUsername() == null) {
			return "Username cannot be null";
		}
		if (user.getPassword() == null) {
			return "Password cannot be null";
		}
		if (!isValidPassword(user.getPassword())) {
			return "Password must be 8 characters or longer \nPassword must contain at least one uppercase \nPassword must contain at least one lowercase level";
		}
		if (users.findByUsername(user.getUsername()) != null) {
			return "Username taken";
		}
		this.users.save(user);
		userBank.save(user.getId());
		return "User " + user.getUsername() + " created";
	}

	/**
	 * Add user to friends list
	 * 
	 * @param userId
	 *            The id of the user who is adding the friend
	 * @param friendId
	 *            The id of the user who is being added as a friend
	 * @return If the users are already friends returns "You are already friends
	 *         with this user". Otherwise returns "Friend {friend's username}
	 *         added".
	 */
	public String addFriend(int userId, int friendId) {
		User friend = users.findById(friendId);
		if (friends.findUserFriend(userId, friend) != null) {
			return "You are already friends with this user";
		}

		friends.save(userId, friend);
		return "Friend " + friend.getUsername() + " added";
	}

	/**
	 * Remove friend from friends list
	 * 
	 * @param userId
	 *            The id of the user who is removing the friend
	 * @param friendId
	 *            The id of the user who is being removed as a friend
	 * @return If the users are not friends returns "You are not friends with this
	 *         user". Otherwise returns "Friend {friend's username} removed".
	 */
	public String removeFriend(int userId, int friendId) {
		Friend friend = friends.findUserFriend(userId, users.findById(friendId));
		if (friend == null) {
			return "You are not friends with this user";
		}
		friends.delete(friend);
		return "Friend " + friend.getFriend().getUsername() + " removed";
	}

	/**
	 * Gets user with similar information to a search index
	 * 
	 * @param index
	 *            The inputed search term
	 * @return List of Users returned by the UserRepository method friendsSearch
	 */
	public List<User> friendsSearch(String index) {
		return users.friendsSearch(index);
	}

	/**
	 * Returns the user with the provided id
	 * 
	 * @param id
	 *            The id of the expected user
	 * @return The User from the UserRepository method findById
	 */
	public User findById(int id) {
		return users.findById(id);
	}

	/**
	 * Deletes a user from the database
	 * 
	 * @param id
	 *            The id of the user to be deleted
	 * @return If the id is not a valid user id, returns "User with id {id} not
	 *         found". Otherwise returns "User {username} deleted".
	 */
	public String deleteUser(int id) {
		User user = users.findById(id);
		if (user == null) {
			return "User with id " + id + " not found";
		}
		users.delete(user);
		return "User " + user.getUsername() + " deleted";
	}

	/**
	 * Edits a user's details
	 * 
	 * @param id
	 *            The id of the user that will be edited
	 * @param modifiedUser
	 *            The user object containing the new user details
	 * @return If the modifiedUser is null return "User cannot be null". If the
	 *         password does not meet the password requirements returns "Password
	 *         must be 8 characters or longer Password must contain at least one
	 *         uppercase Password must contain at least one lowercase level".
	 *         Otherwise returns "User {username} modified"
	 */
	public String editUser(int id, User modifiedUser) {
		if (modifiedUser == null) {
			return "User cannot be null";
		}

		User user = users.findById(id);
		if (modifiedUser.getPassword() != null) {
			if (!isValidPassword(modifiedUser.getPassword())) {
				return "Password must be 8 characters or longer \nPassword must contain at least one uppercase letter\nPassword must contain at least one lowercase letter";
			}
		}
		user.editUser(modifiedUser);
		users.save(user);
		return "User " + user.getUsername() + " modified";
	}

	/**
	 * Returns the user's cash
	 * 
	 * @param userId
	 *            The id of the user whose cash is being requested
	 * @return The User's cash
	 */
	public Double getCash(int userId) {
		return users.findById(userId).getCash();
	}

	/**
	 * Bans a user
	 * 
	 * @param adminId
	 *            The id of the admin who is banning a user
	 * @param userId
	 *            The id of the user who is being banned
	 * @param isBanned
	 *            Boolean value indicating if the user is being banned or unbanned
	 * @return If the user who is trying to ban a user is not an admin returns "You
	 *         do not have access to these funcitons". Otherwise if isBanned is true
	 *         returns "User {username} banned". If isBanned is false returns "User
	 *         {username} unbanned".
	 */
	public String banUser(int adminId, int userId, boolean isBanned) {
		if (!(users.findById(adminId).isAdmin())) {
			return "You do not have access to these functions";
		}
		User user = users.findById(userId);
		user.setBanned(isBanned);
		users.save(user);
		if (isBanned == true) {
			return "User " + user.getUsername() + " banned";
		} else {
			return "User " + user.getUsername() + " unbanned";
		}
	}

	/**
	 * Promote another user to admin
	 * 
	 * @param adminId
	 *            The id of the admin who is a promoting a user
	 * @param userId
	 *            The id of the user who is being promoted
	 * @return If the user who is trying to promote a user is not an admin returns
	 *         "You do not have access to these functions". If the userId is not a
	 *         valid user returns "User does not exist". Otherwise returns "User
	 *         {username} is now an Admin".
	 */
	public String addAdmin(int adminId, int userId) {
		if (!(users.findById(adminId).isAdmin())) {
			return "You do not have access to these functions";
		}
		User user = users.findById(userId);
		if (user == null) {
			return "User does not exist";
		}
		user.setAdmin(true);
		users.save(user);
		return "User " + user.getUsername() + " is now an Admin";
	}

	/**
	 * Get the friends of the user
	 * 
	 * @param userId
	 *            The id of the user whose friends are being requested
	 * @return The List of Users that are friends with the user
	 */
	public List<User> getFriends(int userId) {
		return users.findById(userId).getFriends();
	}

	/**
	 * Decrements the user's bank according to the number of stocks they can
	 * purchase.
	 * 
	 * @param userId
	 *            The id of the user buying stocks
	 * @param price
	 *            The price of the stock
	 * @param num
	 *            The number of stocks to be purchased
	 * @return Number of stocks to be purchased. If the cost of the stocks is
	 *         greater than the user's cash then the number is decreased to the
	 *         largest number of stocks that can be bought.
	 */
	public int buyStock(int userId, double price, int num) {
		User user = users.findById(userId);

		// prevents purchasing more stocks than the user can afford
		if (price * num > user.getCash()) {
			num = (int) (user.getCash() / price);
		}

		// subtracts cash amount
		user.setCash(user.getCash() - (price * num));

		// update user in table
		users.save(user);
		return num;
	}

	/**
	 * Adds cash to the user's bank after selling stocks
	 * 
	 * @param userId
	 *            The id of the user selling stocks
	 * @param price
	 *            The price of the stock
	 * @param num
	 *            The number of stocks to be purchased
	 */
	public void sellStock(int userId, double price, int num) {
		User user = users.findById(userId);

		// adds cash amount
		user.setCash(user.getCash() + (price * num));

		// update user in table
		users.save(user);
	}

	/**
	 * Get the id of a user by their username
	 * 
	 * @param username
	 *            The username of the user
	 * @return The id of the user
	 */
	public int findIdByUsername(String username) {
		return users.findByUsername(username).getId();
	}

	/**
	 * Checks if password is at least 8 characters long and contains both upper case
	 * and lower case letters
	 * 
	 * @param password
	 *            The inputed password
	 * @return True if the password meets the requirements. False otherwise.
	 */
	private boolean isValidPassword(String password) {
		if (password.length() < 8) {
			return false;
		}
		boolean containsUpperCase = false;
		boolean containsLowerCase = false;
		for (int x = 0; x < password.length(); x++) {
			char c = password.charAt(x);
			if (Character.isUpperCase(c)) {
				containsUpperCase = true;
			}
			if (Character.isLowerCase(c)) {
				containsLowerCase = true;
			}
		}
		if (!containsLowerCase || !containsUpperCase) {
			return false;
		}
		return true;
	}

	
	public int drawStock(int userId, int num) {
		User user = users.findById(userId);
		if (100> user.getCash()) {
			num = 0;
		}
		else
			user.setCash(user.getCash() - 100);
		users.save(user);
		return num;
	}
	
	
	


}
