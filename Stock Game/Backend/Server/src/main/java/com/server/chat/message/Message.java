package com.server.chat.message;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Message Object Class
 * 
 * @author Bennett Ray
 *
 */
@Entity
@Table(name = "messages")
public class Message {

	/**
	 * Id of the message. Automatically generated when the message object is created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * The username of the user who sent the message
	 */
	@Column(name = "user_from")
	private String userFrom;

	/**
	 * The username of the user who the message is intended for
	 */
	@Column(name = "user_to")
	private String userTo;

	/**
	 * The time that the message was sent
	 */
	@Column(name = "time")
	private Timestamp date;

	/**
	 * The message to be sent to the user
	 */
	@Column(name = "message")
	private String message;

	/**
	 * The id of the group that the users belong to
	 */
	@Column(name = "groupId")
	private int groupId;

	/**
	 * Empty Constructor
	 */
	public Message() {

	}

	/**
	 * Constructor for creating a new Message Object
	 * 
	 * @param userFrom The username of the sender
	 * @param userTo The username of the recipient
	 * @param message The message to be sent
	 * @param groupId The id of the group the users belong to
	 */
	public Message(String userFrom, String userTo, String message, int groupId) {
		this.userFrom = userFrom;
		this.userTo = userTo;
		this.date = getCurrentTimeStamp();
		this.message = message;
		this.groupId = groupId;
	}

	/**
	 * Get the message id
	 * 
	 * @return Id of the message
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the message id
	 * 
	 * @param id Id of the message to be sent
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the username of the sender
	 * @return Username of the sender
	 */
	public String getUserFrom() {
		return userFrom;
	}

	/**
	 * Set the username of the sender
	 * @param userFrom Username of the sender to be set
	 */
	public void setUserFromId(String userFrom) {
		this.userFrom = userFrom;
	}

	/**
	 * Get the username of the recipient
	 * @return Username of the recipient
	 */
	public String getUserTo() {
		return userTo;
	}

	/**
	 * Set the username of the recipient
	 * @param userTo Username of the recipient to be set
	 */
	public void setUserToId(String userTo) {
		this.userTo = userTo;
	}

	/**
	 * Get the time of the message
	 * @return The message date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * Set the time of the message
	 * @param date The message date to be set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * Get the message content
	 * @return The message that was sent
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message content
	 * @param message The message to be set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get the current timestamp
	 * @return 
	 */
	private Timestamp getCurrentTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}

}
