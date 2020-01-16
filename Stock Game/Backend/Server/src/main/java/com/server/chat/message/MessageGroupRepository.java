package com.server.chat.message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for connecting to message group table
 * 
 * @author Bennett Ray
 *
 */
public interface MessageGroupRepository extends CrudRepository<MessageGroup, Integer> {

	/**
	 * Gets the message group from the table by id
	 * 
	 * @param groupId The id of the group
	 * @return MessageGroup object
	 */
	MessageGroup findById(@Param("groupId") int groupId);

	/**
	 * Gets the message group from the table by 2 user ids
	 * 
	 * @param user1 Id of one of the users in the group
	 * @param user2 Id of the other user in the group
	 * @return MessageGroup object
	 */
	@Query("SELECT messageGroup FROM MessageGroup messageGroup WHERE (messageGroup.user1 = :user1 AND messageGroup.user2 = :user2) OR (messageGroup.user1 = :user2 AND messageGroup.user2 = :user1)")
	@Transactional(readOnly = true)
	MessageGroup findByUser1AndUser2(@Param("user1") int user1, @Param("user2") int user2);

}
