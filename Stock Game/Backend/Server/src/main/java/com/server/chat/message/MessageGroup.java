package com.server.chat.message;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Message Group Object Class
 * 
 * @author Bennett Ray
 *
 */
@Entity
@Table(name = "`groups`") // no idea why that fixes my issues
public class MessageGroup {

	/**
	 * The id of the Message Group. Automatically created when the group is created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * The user id of one user
	 */
	@Column(name = "user_1")
	private int user1;

	/**
	 * The user id of the other user
	 */
	@Column(name = "user_2")
	private int user2;

	/**
	 * Empty constructor
	 */
	public MessageGroup() {

	}

	/**
	 * Constructor for creating a new MessageGroup object
	 * 
	 * @param user1 Id of one user in the group
	 * @param user2 Id of the other user in the group
	 */
	public MessageGroup(int user1, int user2) {
		this.user1 = user1;
		this.user2 = user2;
	}

	/**
	 * Get group id
	 * 
	 * @return The id of the group
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set group id
	 * 
	 * @param id The id of the group to be set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get id of user 1
	 * 
	 * @return The id of user 1
	 */
	public int getUser1() {
		return user1;
	}

	/**
	 * Set id of user 1
	 * 
	 * @param user1 The id of user 1 to be set
	 */
	public void setUser1(int user1) {
		this.user1 = user1;
	}

	/**
	 * Get id of user 2
	 * 
	 * @return The id of user 2
	 */
	public int getUser2() {
		return user2;
	}

	/**
	 * Set id of user 2
	 * 
	 * @param user2 The id of user 2 to be set
	 * 
	 */
	public void setUser2(int user2) {
		this.user2 = user2;
	}

	/**
	 * Get the id of the user not provided as a parameter
	 * @param userId Id of one of the users
	 * @return The id of the other user
	 */
	public int getOtherUser(int userId) {
		if (user1 == userId) {
			return user2;
		} else {
			return user1;
		}

	}

}
