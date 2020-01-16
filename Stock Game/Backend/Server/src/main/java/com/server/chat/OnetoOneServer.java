package com.server.chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.server.chat.message.Message;
import com.server.chat.message.MessageDecoder;
import com.server.chat.message.MessageEncoder;
import com.server.chat.message.MessageGroup;
import com.server.chat.message.MessageGroupService;
import com.server.chat.message.MessageRepository;
import com.server.user.UserService;

/**
 * Server endpoint for chatting with another user. Messages are saved to the
 * message table so that when the user js online, they can view messages sent
 * when they were offline.
 * 
 * @author Vamsi Krishna Calpakkam and Bennett Ray
 *
 */
@ServerEndpoint(value = "/chat/{groupId}/{username}", configurator = CustomConfigurator.class, decoders = MessageDecoder.class, encoders = MessageEncoder.class)
@Component
public class OnetoOneServer {

	/**
	 * Map of all sessions and the group ids associated with the sections
	 */
	private static Map<Session, Integer> sessionGroupIdMap = new HashMap<>();

	/**
	 * Map of all group ids and a map containing the usernames in each group and
	 * their sessions
	 */
	private static Map<Integer, Map<String, Session>> groupIdSessionMap = new HashMap<>();

	/**
	 * Map of all sessions and the usernames associated with the sections
	 */
	private static Map<Session, String> sessionUsernameMap = new HashMap<>();

	/**
	 * Interface for connecting to the messages table
	 */
	@Autowired
	MessageRepository messages;

	/**
	 * Service for Message Group functions
	 */
	@Autowired
	MessageGroupService service;

	/**
	 * Service for User functions
	 */
	@Autowired
	UserService users;

	/**
	 * Logger for keeping track of logs
	 */
	private final Logger logger = LoggerFactory.getLogger(OnetoOneServer.class);

	/**
	 * User joins the chat. Adds the users to the session maps.
	 * 
	 * @param session
	 *            The user's session
	 * @param groupId
	 *            The id of the group they joined
	 * @param username
	 *            The user's username
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("groupId") int groupId, @PathParam("username") String username)
			throws Exception {
		logger.info("Entered into Open");
		MessageGroup group = service.findById(groupId);
		int userId = users.findIdByUsername(username);
		if (userId != group.getUser1() && userId != group.getUser2()) {
			sendErrorMessage(username, session, "You are not a member of this group");
		}
		sessionGroupIdMap.put(session, groupId);
		if (groupIdSessionMap.get(groupId) == null) {
			groupIdSessionMap.put(groupId, new HashMap<String, Session>());
		}
		groupIdSessionMap.get(groupId).put(username, session);
		sessionUsernameMap.put(session, username);

		sendAllMessages(username, groupId, messages.findByGroupId(groupId));// Broadcast previous messages in chat
		String text = "Server: " + username + " is online";
		Message message = new Message("server", username, text, groupId);
		messages.save(message);

		sendMessageToGroup(groupId, message);

	}

	/**
	 * User leaves the group. Removes the user from the sessionMaps.
	 * 
	 * @param session
	 *            The user's session
	 * @throws IOException
	 */
	@OnClose
	public void onClose(Session session) throws Exception {
		logger.info("Entered into Close");
		String username = sessionUsernameMap.get(session);
		int groupId = sessionGroupIdMap.get(session);
		sessionGroupIdMap.remove(session);
		groupIdSessionMap.get(groupId).remove(username);
		sessionUsernameMap.remove(session);

		String text = "Server: " + username + " is offline";
		Message message = new Message("server", username, text, groupId);
		messages.save(message);

		sendMessageToGroup(groupId, message);
	}

	/**
	 * An error occurs during the session
	 * 
	 * @param session
	 *            The user's session
	 * @param throwable
	 * @throws Exception
	 */
	@OnError
	public void onError(Session session, Throwable throwable) throws Exception {
		try {
			session.getBasicRemote().sendText("Disconnected due to Error");
		} catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
		logger.info("Entered into Error");
	}

	/**
	 * User sends a message
	 * 
	 * @param session
	 *            The user's session
	 * @param text
	 *            The user's message to be sent
	 * @throws IOException
	 */
	@OnMessage
	public void onMessage(Session session, String text) throws Exception {
		logger.info("Entered into Message: Got Message:" + text);

		String userFrom = sessionUsernameMap.get(session);
		int groupId = sessionGroupIdMap.get(session);

		MessageGroup group = service.findById(groupId);
		String userTo = users.findById(group.getOtherUser(users.findIdByUsername(userFrom))).getUsername();

		Message message = new Message(userFrom, userTo, text, groupId);
		messages.save(message);

		if (groupIdSessionMap.get(groupId).get(userTo) == null) {// user is not online
			sendMessageToParticularUser(userFrom, groupId, message);
		} else {
			sendMessageToGroup(groupId, message);// user is online
		}
	}

	/**
	 * Sends all the messages that have been sent while the user has not been in the
	 * group
	 * 
	 * @param userTo
	 *            The user who sent the messages
	 * @param groupId
	 *            The id of the group
	 * @param allMessages
	 *            The messages to be sent
	 */
	private void sendAllMessages(String userTo, int groupId, List<Message> allMessages) {
		try {
			Session session = groupIdSessionMap.get(groupId).get(userTo);
			for (Message message : allMessages) {
				session.getBasicRemote().sendObject(message);
			}
		} catch (IOException | EncodeException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	/**
	 * Sends an error message only to the user who is joining a group. Message is
	 * not saved in message table.
	 * 
	 * @param userTo
	 * @param session
	 * @param message
	 */
	private void sendErrorMessage(String userTo, Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a message to everyone in the group. Saves the message in the message
	 * table
	 * 
	 * @param groupId
	 *            The id of the group
	 * @param message
	 *            The message to be sent
	 */
	private void sendMessageToGroup(int groupId, Message message) {
		groupIdSessionMap.get(groupId).forEach((user, session) -> {
			synchronized (session) {
				try {
					session.getBasicRemote().sendObject(message);
				} catch (IOException | EncodeException e) {
					logger.info("Exception: " + e.getMessage().toString());
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Sends a message to a specific user. Saves the message in the message table
	 * 
	 * @param username
	 *            The username of the recipient
	 * @param groupId
	 *            The id of the group the user belongs to
	 * @param message
	 *            The message to be sent
	 */
	private void sendMessageToParticularUser(String username, int groupId, Message message) {

		try {
			Session session = groupIdSessionMap.get(groupId).get(username);
			session.getBasicRemote().sendObject(message);
		} catch (IOException | EncodeException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}
}
