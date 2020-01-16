package com.server.chat.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for message group functions
 * 
 * @author Bennett Ray
 *
 */
@RestController
@RequestMapping("/chat")
public class MessageGroupController {

	/**
	 * Service methods for MessageGroup functions
	 */
	@Autowired
	private MessageGroupService service;

	/**
	 * New Chat Endpoint
	 * 
	 * @param userId
	 *            Id of user creating the chat
	 * @param friendId
	 *            Id of the friend the user is creating a chat with
	 * @return Message from MessageGroupService method newChat
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/newChat")
	public String newChat(@RequestParam("userId") int userId, @RequestParam("friendId") int friendId) {
		return service.newChat(userId, friendId);
	}

	/**
	 * Get Group Id Endpoint
	 * 
	 * @param user1
	 *            Id of 1 user in the group
	 * @param user2
	 *            Id of another user in the group
	 * 
	 * @return Group id from the MessageGroupService method getGroupId
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/getGroupId")
	public int getGroupId(@RequestParam("user1") int user1, @RequestParam("user2") int user2) {
		return service.getGroupId(user1, user2);
	}

	/**
	 * Get Group Endpoint
	 * @param id Id of the group
	 * @return MessageGroup object from the MessageGroupService method findById
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/getGroup")
	public MessageGroup findById(@RequestParam("id") int id) {
		return service.findById(id);
	}
}
