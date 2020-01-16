package com.server.score;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface for accessing score table
 * 
 * @author Bennett Ray
 *
 */
public interface ScoreRepository extends CrudRepository<Score, Integer> {

	/**
	 * Gets the scores from the table by date
	 * 
	 * @param date The date of the scores
	 * @return List of Score Objects
	 */
	List<Score> findByDate(Date date);

	/**
	 * Gets the scores from the table by user id
	 * 
	 * @param userId The id of the user
	 * @return List of Score Objects
	 */
	List<Score> findByUserId(int userId);

	/**
	 * Gets the score of a user on a certain date
	 * 
	 * @param userId The id of the user
	 * @param date The date of the scores
	 * @return Score Objects
	 */
	Score findByUserIdAndDate(int userId, Date date);

	/**
	 * Gets the scores of all users ordered by rank
	 * 
	 * @param date The date of the scores
	 * @return List of Score Objects
	 */
	List<Score> findByDateOrderByRank(Date date);

	/**
	 * Gets the top ten scores on a certain date
	 * 
	 * @param date The date of the scores
	 * @return List of Score objects
	 */
	List<Score> findTop10ByDateOrderByRank(Date date);


}
