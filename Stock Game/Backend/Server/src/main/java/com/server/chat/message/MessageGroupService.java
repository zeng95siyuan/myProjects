package com.server.chat.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.user.Friend;
import com.server.user.FriendRepository;

/**
 * Methods for message group Functions
 * 
 * @author Bennett Ray
 *
 */
@Service
public class MessageGroupService {

	/**
	 * Interface for connecting to MessageGroup table
	 */
	@Autowired
	MessageGroupRepository groups;

	/**
	 * Create a new chat group
	 * 
	 * @param userId
	 *            Id of user creating the chat
	 * @param friendId
	 *            Id of the friend the user is creating a chat with
	 * @return If the users already have a chat together returns "You already have
	 *         an existing chat with this user" Otherwise returns the id of the new
	 *         chat group.
	 */
	public String newChat(int userId, int friendId) {
		if (groups.findByUser1AndUser2(userId, friendId) != null) {
			return "You already have an existing chat with this user";
		} else {
			groups.save(new MessageGroup(userId, friendId));
		}
		return groups.findByUser1AndUser2(userId, friendId).getId() + "";
	}

	/**
	 * Get the group id of 2 users
	 * 
	 * @param user1
	 *            One of the users
	 * @param user2
	 *            The other users
	 * @return The id of the group containing both users
	 */
	public int getGroupId(int user1, int user2) {
		return groups.findByUser1AndUser2(user1, user2).getId();
	}

	/**
	 * Get group by Id
	 * 
	 * @param groupId
	 *            Id of the group requested
	 * @return The MessageGroup found by the MessageGroupRepository method findById
	 */
	public MessageGroup findById(int groupId) {
		return groups.findById(groupId);
	}
}
