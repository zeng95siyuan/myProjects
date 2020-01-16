package com.server.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User Object Class
 * 
 * @author Bennett Ray
 *
 */
@Entity
@Table(name = "users")
public class User {

	/**
	 * The id of the user. Automatically created when the user is created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * The username of the user.
	 */
	@Column(name = "USERNAME")
	private String username;

	/**
	 * The password of the user
	 */
	@Column(name = "PASSWORD")
	private String password;

	/**
	 * The first name of the user
	 */
	@Column(name = "FIRST_NAME")
	private String firstName;

	/**
	 * The last name of the user
	 */
	@Column(name = "LAST_NAME")
	private String lastName;

	/**
	 * The user's bank containing their cash
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Bank bank;

	/**
	 * List of Friends
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Friend> friends;

	/**
	 * Boolean value indicating if the user is an admin
	 */
	@Column(name = "ADMIN")
	private boolean admin;

	/**
	 * Boolean value indicating if the user is banned
	 */
	@Column(name = "BANNED")
	private boolean banned;

	/**
	 * Empty Constructor
	 */
	public User() {

	}

	/**
	 * Constructor for creating a new User object
	 * @param username The username of the user
	 * @param password The password of the user
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor for testing
	 * @param id Id of the user
	 * @param username The username of the user
	 * @param password The password of the user
	 * @param cash The cash of the user
	 */
	public User(int id, String username, String password, Double cash) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.bank = new Bank(id, cash);
	}

	/**
	 * Get user id
	 * @return The user's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set User id
	 * @param id The user id to be set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get username
	 * @return The username of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username
	 * @param username The username to be set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get password
	 * @return The password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password
	 * @param password The password to be set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get first name
	 * @return The first name of the user
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set first name
	 * @param firstName The first name to be set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get last name
	 * @return The last name of the user
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set last name
	 * @param lastName The last name to be set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get banned status
	 * @return True if the user is banned. False if the user if not banned
	 */
	public boolean isBanned() {
		return banned;
	}

	/**
	 * Set banned status
	 * @param banned Value indicating whether the user will be banned or unbanned
	 */
	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	/**
	 * Get friends of the user
	 * @return List of user objects of each of the user's friends
	 */
	@JsonIgnore
	@JsonProperty(value = "friends")
	public List<User> getFriends() {
		List<User> userFriends = new ArrayList<>();
		;
		for (Friend friend : friends) {
			userFriends.add(friend.getFriend());
		}
		return userFriends;
	}

	/**
	 * Set friends
	 * @param friends The list of friends to be set as the user's friends
	 */
	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	/**
	 * Get the user's cash
	 * @return The user's cash amount
	 */
	@JsonIgnore
	@JsonProperty(value = "cash")
	public double getCash() {
		return bank.getCash();
	}

	/**
	 * Set the user's cash
	 * @param cash The user's cash to be set
	 */
	public void setCash(Double cash) {
		this.bank.setCash(cash);
	}

	/**
	 * Get user's admin status
	 * @return True if the user is an admin. False if the user is not an admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Set user's admin status
	 * @param admin Boolean value indicating the admin status of the user
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * Set the user's fields
	 * @param user User object containing the new fields for the user
	 */
	public void editUser(User user) {
		if (user.password != null) {
			this.password = user.password;
		}
		if (user.firstName != null) {
			this.firstName = user.firstName;
		}
		if (user.lastName != null) {
			this.lastName = user.lastName;
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", admin=" + admin + ", banned=" + banned + "]";
	}

}
