package com.server.chat.message;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface for accessing the message table
 * @author Bennett Ray
 *
 */
public interface MessageRepository extends CrudRepository<Message, Integer>{
	
	/**
	 * Get all message from the table by group id 
	 * @param groupId  The id of the group
	 * @return List of Message Objects
	 */
	List<Message> findByGroupId(int groupId);
}
