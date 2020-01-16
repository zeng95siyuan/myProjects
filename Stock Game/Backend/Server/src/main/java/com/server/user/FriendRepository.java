package com.server.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface for accessing the friends table
 * @author Bennett Ray
 *
 */
public interface FriendRepository extends CrudRepository<Friend, Integer>{

	/**
	 * Get Friend from table by the ids of both users
	 * @param userId The id of the user who added the friend
	 * @param friend The id of the user who was added as a friend
	 * @return Friend Object
	 */
	Friend findByUserAndFriend(int userId, User friend);

}
