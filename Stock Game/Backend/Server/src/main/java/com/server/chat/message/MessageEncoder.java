package com.server.chat.message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * Encoder for outgoing message objects
 * @author Bennett Ray
 *
 */
public class MessageEncoder implements Encoder.Text<Message> {

	/**
	 * Format of outgoing message objects
	 */
	private static Gson gson = new Gson();
	
	/**
	 * Auto-generated init method
	 */
	@Override
	public void init(EndpointConfig endpointConfig) {

	}

	/**
	 * Auto-generated Destroy method
	 */
	@Override
	public void destroy() {

	}

	/**
	 * Encodes the message object
	 * @param message Outgoing Message object to be encoded
	 * @return Outgoing message as a string
	 * @throws EncodeException
	 */
	@Override
	public String encode(Message message) throws EncodeException {
		return gson.toJson(message);
	}

}