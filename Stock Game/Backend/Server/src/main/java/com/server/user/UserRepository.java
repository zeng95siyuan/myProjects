package com.server.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Interface for accessing user table
 * @author Bennett Ray
 *
 */
public interface UserRepository extends CrudRepository<User, Integer> {

	/**
	 * Gets the user from the table by username
	 * @param username The username of the user
	 * @return User object
	 */
	User findByUsername(String username);

	/**
	 * Gets user from the table by id
	 * @param userId The id of the user
	 * @return User object
	 */
	User findById(int userId);
	
	/**
	 * Gets all users from the table
	 */
	List<User> findAll();

	/**
	 * Finds the users whose username, fist name, and/or last name contains the index
	 * @param index The search term
	 * @return List of Users
	 */
	@Query("SELECT user FROM User user WHERE user.username LIKE %:index% OR user.firstName LIKE %:index% OR user.lastName LIKE %:index%")
	List<User> friendsSearch(@Param("index") String index);
	
	
}
