package com.server.score;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for score functions
 * @author Bennett Ray
 *
 */
@RequestMapping("/scores")
@RestController
public class ScoreController {

	/**
	 * Service method for score functions
	 */
	@Autowired
	ScoreService service;
	
	/**
	 * **Manual Testing Only**
	 * Get Score By Date Endpoint
	 * @param date Date of scores requested
	 * @return List of Scores from ScoreService method getAllByDate
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/score")
	public List<Score>getScoreByDate(@RequestParam Date date) {
		service.updateScoreManually(date);
		return service.getAllByDate(date);
	}
	
	/**
	 * Get Top Ten Scores Endpoint
	 * @param date Date of scores requested
	 * @return List of top ten Scores from ScoreService method getTopTen
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/leaderboards")
	public List<Score> getTopTen(@RequestParam Date date){
		return service.getTopTen(date);
	}
	
	/**
	 * Get All Scores By Date Endpoint
	 * @param date Date of scores requested
	 * @return List of Scores from ScoreService method getAllByDate
	 */
	@RequestMapping(method = RequestMethod.GET, path="/getAllByDate")
	public List<Score> getAllByDate(@RequestParam Date date){
		return service.getAllByDate(date);
	}
	
	/**
	 * Get All Scores By User Endpoint
	 * @param userId Id of user whose scores are being requested
	 * @return List of Scores from ScoreService method getAllByUser
	 */
	@RequestMapping(method = RequestMethod.GET, path="/getAllByUser")
	public List<Score> getAllByUser(@RequestParam int userId){
		return service.getAllByUser(userId);
	}
	
	/**
	 * Get Friends Score Endpoint
	 * @param date Date of scores requested
	 * @param userId Id of user whose friends' scores are being requested
	 * @return List of Scores from ScoreService method getFriendsScores
	 */
	@RequestMapping(method = RequestMethod.GET, path="/getFriends")
	public List<Score> getFriendsScore(@RequestParam Date date, @RequestParam int userId){
		return service.getFriendsScores(date, userId);
	}
}
