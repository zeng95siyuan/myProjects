package com.server.score;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.server.inventory.InventoryService;
import com.server.user.User;
import com.server.user.UserService;

/**
 * Methods for score functions
 * 
 * @author Bennett Ray
 *
 */
@Service
public class ScoreService {

	/**
	 * Service method for user functions
	 */
	@Autowired
	UserService userService;

	/**
	 * Interface for connecting to score table
	 */
	@Autowired
	ScoreRepository repo;

	/**
	 * Service method for inventory functions
	 */
	@Autowired
	InventoryService inventoryService;

	/**
	 * Cron job to add user scores to scores table at the end of the day. Really
	 * inefficient. Needs some serious work
	 */
	@Scheduled(cron = "0 0 23 * * *")
	public void updateScore() {
		LocalDate today = LocalDate.now();
		List<User> users = userService.findAll();
		List<Score> scores = new ArrayList<>();
		users.forEach((user) -> {
			int userId = user.getId();
			scores.add(
					new Score(Date.valueOf(today), userId, inventoryService.getPortfolioValue(userId), user.getCash()));
		});

		scores.forEach((score) -> {
			double[] values = new double[7];
			int userId = score.getUserId();
			double newScore = score.getPortfolioValue();
			LocalDate previousDate = today.plusDays(-1);
			Score s = repo.findByUserIdAndDate(userId, Date.valueOf(previousDate));
			int i = 0;
			while (s != null) {
				double oldScore = s.getPortfolioValue();
				if (oldScore == 0) {
					values[i] = 0;
				} else {
					values[i] = (newScore - oldScore) / (oldScore / 100);
				}
				i++;
				newScore = oldScore;
				previousDate = previousDate.plusDays(-1);
				s = repo.findByUserIdAndDate(userId, Date.valueOf(previousDate));
			}
			double total = 0.0;
			for (int j = 0; j < i; j++) {
				total += values[j];
			}
			double averageIncrease = (total / (double) i) * (Math.sqrt(score.getCash() * 0.0001) * 100);
			DecimalFormat df = new DecimalFormat("#.##");
			averageIncrease = Double.parseDouble(df.format(averageIncrease));
			score.setScore(averageIncrease);

		});

		Collections.sort(scores, (a, b) -> b.compareTo(a));
		for (int i = 0; i < scores.size(); i++) {
			scores.get(i).setRank(i + 1);
			repo.save(scores.get(i));
		}

	}

	/**
	 * Add user scores to scores table manually
	 */
	public void updateScoreManually(Date date) {
		LocalDate today = date.toLocalDate();
		List<User> users = userService.findAll();
		List<Score> scores = new ArrayList<>();
		users.forEach((user) -> {
			int userId = user.getId();
			scores.add(
					new Score(Date.valueOf(today), userId, inventoryService.getPortfolioValue(userId), user.getCash()));
		});

		scores.forEach((score) -> {
			double[] values = new double[7];
			int userId = score.getUserId();
			double newScore = score.getPortfolioValue();
			LocalDate previousDate = today.plusDays(-1);
			Score s = repo.findByUserIdAndDate(userId, Date.valueOf(previousDate));
			int i = 0;
			while (s != null) {
				double oldScore = s.getPortfolioValue();
				if (oldScore == 0) {
					values[i] = 0;
				} else {
					values[i] = (newScore - oldScore) / (oldScore / 100);
				}
				i++;
				newScore = oldScore;
				previousDate = previousDate.plusDays(-1);
				s = repo.findByUserIdAndDate(userId, Date.valueOf(previousDate));
			}
			double total = 0.0;
			for (int j = 0; j < i; j++) {
				total += values[j];
			}
			double averageIncrease = (total / (double) i) * (Math.sqrt(score.getCash() * 0.0001) * 100);
			DecimalFormat df = new DecimalFormat("#.##");
			averageIncrease = Double.parseDouble(df.format(averageIncrease));
			score.setScore(averageIncrease);

		});

		Collections.sort(scores, (a, b) -> b.compareTo(a));
		for (int i = 0; i < scores.size(); i++) {
			scores.get(i).setRank(i + 1);
			repo.save(scores.get(i));
		}
	}


	/**
	 * Get top ten Scores for a certain date
	 * 
	 * @param date
	 *            Date of scores requested
	 * @return List of Top Ten Score Ordered by rank
	 */
	public List<Score> getTopTen(java.sql.Date date) {
		return repo.findTop10ByDateOrderByRank(date);
	}

	/**
	 * Get all Scores for a certain date
	 * 
	 * @param date
	 *            Date if scores requested
	 * @return List of all Scores for a date by rank
	 */
	public List<Score> getAllByDate(java.sql.Date date) {
		return repo.findByDateOrderByRank(date);
	}

	/**
	 * Get all Scores for a certain user
	 * 
	 * @param userId
	 *            Id of user whose scores are being requested
	 * @return List of all Scores for the user
	 */
	public List<Score> getAllByUser(int userId) {
		return repo.findByUserId(userId);
	}

	/**
	 * Get the scores of all friends
	 * 
	 * @param date
	 *            Date of scores requested
	 * @param userId
	 *            Id of user whose friends' scores are being requested
	 * @return List of Scores of the user's friends
	 */
	public List<Score> getFriendsScores(java.sql.Date date, int userId) {
		List<User> friends = userService.getFriends(userId);
		List<Score> scores = new ArrayList<>();
		scores.add(repo.findByUserIdAndDate(userId, date));
		friends.forEach((user) -> {
			if (repo.findByUserIdAndDate(user.getId(), date) != null) {
				scores.add(repo.findByUserIdAndDate(user.getId(), date));
			}
		});
		Collections.sort(scores, (a, b) -> b.compareTo(a));
		return scores;
	}

}
