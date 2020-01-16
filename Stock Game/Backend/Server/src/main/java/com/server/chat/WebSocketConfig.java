package com.server.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Configures web sockets
 * 
 * @author Vamsi Krishna Calpakkam
 *
 */
@Configuration
public class WebSocketConfig {
	/**
	 * Creates server endpoints
	 * 
	 * @return ServerEndpointExporter
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	/**
	 * Configuration for multiple web socket server endpoints
	 * 
	 * @return CustomConfigurator
	 */
	@Bean
	public CustomConfigurator customConfigurator() {
		return new CustomConfigurator();
	}

}