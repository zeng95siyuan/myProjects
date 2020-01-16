package com.server.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Sever endpoint for group chat functions
 * @author Vamsi Krishna Calpakkam and Bennett Ray
 *
 */
@ServerEndpoint(value = "/livechat/{groupId}/{username}", configurator = CustomConfigurator.class)
@Component
public class GroupChatServer {

	/**
	 * Map of all sessions and the group ids associated with the sections
	 */
	private static Map<Session, Integer> sessionGroupIdMap = new HashMap<>();
	
	/**
	 * Map of all group ids and a map containing the usernames in each group and their sessions
	 */
	private static Map<Integer, Map<String, Session>> groupIdSessionMap = new HashMap<>();

	/**
	 * Map of all sessions and the usernames associated with the sections
	 */
	private static Map<Session, String> sessionUsernameMap = new HashMap<>();

	/**
	 * Logger for keeping track of logs
	 */
	private final Logger logger = LoggerFactory.getLogger(GroupChatServer.class);

	
	/**
	 * User joins a group. Adds the users to the session maps.
	 * @param session The user's session
	 * @param groupId The id of the group they joined 
	 * @param username The user's username
	 * @throws IOException e
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("groupId") int groupId, @PathParam("username") String username)
			throws IOException {
		logger.info("Entered into Open");

		sessionGroupIdMap.put(session, groupId);
		if (!groupIdSessionMap.containsKey(groupId)) {
			groupIdSessionMap.put(groupId, new HashMap<String, Session>());
		}
		groupIdSessionMap.get(groupId).put(username, session);

		sessionUsernameMap.put(session, username);

		String message = "Server: " + username + " has Joined the Chat";
		broadcastToGroup(message, groupId);

	}

	/**
	 * User sends a message
	 * @param session The user's session
	 * @param message The user's message to be sent
	 * @throws IOException e
	 */
	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		// Handle new messages
		logger.info("Entered into Message: Got Message:" + message);
		String username = sessionUsernameMap.get(session);
		int groupId = sessionGroupIdMap.get(session);
		if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
		{
			String destUsername = message.split(" ")[0].substring(1);
			if (groupIdSessionMap.get(groupId).get(destUsername) == null) {// invalid username provided
				sendMessageToParticularUser("Server: " + username, "Invalid Username" + destUsername, groupId);
			} else {
				sendMessageToParticularUser(destUsername, "[DM] " + username + ": " + message, groupId);
				sendMessageToParticularUser(username, "[DM] " + username + ": " + message, groupId);
			}
		} else // Message to whole chat
		{
			broadcastToGroup(username + ": " + message, sessionGroupIdMap.get(session));
		}
	}

	/**
	 * User leaves the group. Removes the user from the sessionMaps.
	 * @param session The user's session
	 * @throws IOException e
	 */
	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

		String username = sessionUsernameMap.get(session);
		int groupId = sessionGroupIdMap.get(session);
		sessionGroupIdMap.remove(session);
		groupIdSessionMap.get(groupId).remove(username);
		sessionUsernameMap.remove(session);

		String message = "Server: " + username + " disconnected";
		broadcastToGroup(message, groupId);
	}

	/**
	 * An error occurs during the session
	 * @param session The user's session
	 * @param throwable t
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		try {
			session.getBasicRemote().sendText("Disconnected due to Error");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Entered into Error");
	}

	/**
	 * Send a message to a certain user
	 * @param username The username of the recipient
	 * @param message The message to be sent
	 * @param groupId The id of the group the user belongs to
	 */
	private void sendMessageToParticularUser(String username, String message, int groupId) {

		try {
			Session session = groupIdSessionMap.get(groupId).get(username);
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	/**
	 * Send a message to an entire group
	 * @param message The message to be sent
	 * @param groupId The id of the group the user belongs to
	 * @throws IOException e
	 */
	private static void broadcastToGroup(String message, int groupId) throws IOException {
		groupIdSessionMap.get(groupId).forEach((username, session) -> {
			synchronized (session) {
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}
}
