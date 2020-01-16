package com.server.chat;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

/**
 * Web Socket Endpoint Configurator
 * @author Vamsi Krishna Calpakkam
 *
 */
public class CustomConfigurator extends ServerEndpointRegistration.Configurator implements ApplicationContextAware {

	/**
	 * Configuration mechanism that manages server endpoints
	 */
	private static volatile BeanFactory context;

	/**
	 * Gets web socket endpoints
	 * @param endpoint Endpoint of web socket server
	 * @return Endpoint context
	 * @throws InstantiationException
	 */
	@Override
	public <T> T getEndpointInstance(Class<T> endpoint) throws InstantiationException {
		return context.getBean(endpoint);
	}

	/**
	 * Register endpoint
	 * @param applicationContext Server context for registering endpoint
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CustomConfigurator.context = applicationContext;

	}

}
