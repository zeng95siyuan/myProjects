package com.server.chat.message;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * Decoder for incoming message objects
 * @author Bennett Ray
 *
 */
public class MessageDecoder implements Decoder.Text<Message> {

	/**
	 * Format of incoming message objects
	 */
	private static Gson gson = new Gson();

	/**
	 * Auto-generated Destroy method
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * Auto-generated init method
	 */
	@Override
	public void init(EndpointConfig enpointConfig) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Decode message
	 * @param s Message in string format
	 * @return Message in Message object format
	 * @throws DecodeException
	 */
	@Override
	public Message decode(String s) throws DecodeException {
		return gson.fromJson(s, Message.class);
	}

	/**
	 * Checks if the incoming sting is null
	 * @param s Incoming string message 
	 * @return True if the message is not null. Otherwise false
	 */
	@Override
	public boolean willDecode(String s) {
		return s != null;
	}

}
