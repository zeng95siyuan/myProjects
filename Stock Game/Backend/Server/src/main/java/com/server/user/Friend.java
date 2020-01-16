package com.server.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Friend Object Class: 
 * \nContains the User who added the other user as a friend and the user who is being added as a friend
 * @author Bennett Ray
 *
 */
@Entity
@Table(name = "friend")
public class Friend {

	/**
	 * Id of the friend object. Automatically generated when the friend object is created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * Id of the user who added the friend
	 */
	@Column(name = "user_id")
	private int user;

	/**
	 * The user object of the added friend
	 */
	@ManyToOne
	@JoinColumn(name = "friend_id")
	private User friend;

	/**
	 * Empty Constructor
	 */
	public Friend() {

	}

	/**
	 * Constructor for creating a new Friend Object
	 * @param user The user's id
	 * @param friend The user that is being added as a freind
	 */
	public Friend(int user, User friend) {
		this.user = user;
		this.friend = friend;
	}

	/**
	 * Get the id of the friend object
	 * @return Friend object's id
	 */
	@JsonIgnore
	@JsonProperty(value = "id")
	public int getId() {
		return id;
	}

	/**
	 * Set the id of the friend object
	 * @param id Friend object's id to be set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the id of the user who added the friend
	 * @return The id of the user who added the friend
	 */
	@JsonIgnore
	@JsonProperty(value = "friends")
	public int getUser() {
		return user;
	}

	/**
	 * Set the id of the user who added the friend
	 * @param user The id of the user to be set
	 */
	public void setUser(int user) {
		this.user = user;
	}

	/**
	 * Get the user object of the friend
	 * @return The user object of the added friend
	 */
	public User getFriend() {
		return friend;
	}

	/**
	 * Set the user object of the friend
	 * @param friend The user object to be set as the friend
	 */
	public void setFriend(User friend) {
		this.friend = friend;
	}

}
