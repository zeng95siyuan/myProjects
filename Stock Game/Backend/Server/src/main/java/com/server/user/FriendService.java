package com.server.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Methods for friend functions
 * @author Bennett Ray
 *
 */
@Service
public class FriendService {

	/**
	 * Interface for connecting to friends table
	 */
	@Autowired
	FriendRepository repo;

	/**
	 * Save a new Friend object to the table
	 * @param userId The id of the user adding the friend
	 * @param friend The user being added as a friend
	 */
	public void save(int userId, User friend) {
		repo.save(new Friend(userId, friend));
	}

	/**
	 * Delete the friend object from the table
	 * @param friend Friend object being deleted
	 */
	public void delete(Friend friend) {
		repo.delete(friend);
	}

	/**
	 * Get Friend by id's of two users
	 * @param userId The id of the user who added the friend
	 * @param friend The user who was added as a friend
	 * @return Friend from the FriendRepository method findByUserAndFriend
	 */
	public Friend findUserFriend(int userId, User friend) {
		return repo.findByUserAndFriend(userId, friend);
	}

}
