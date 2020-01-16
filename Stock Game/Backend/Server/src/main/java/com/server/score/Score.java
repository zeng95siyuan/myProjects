package com.server.score;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Score Object Class
 * 
 * @author Bennett Ray
 *
 */
@Entity
@Table(name = "`scores`")
public class Score {

	/**
	 * The id of the score entry. Automatically created when the score entry is
	 * created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * Date of the score entry
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date")
	private Date date;

	/**
	 * The id of the user who the score belongs to
	 */
	@Column(name = "user")
	private int userId;

	/**
	 * The score value
	 */
	@Column(name = "portfolio_value")
	private double portfolioValue;

	@Column(name = "cash")
	private double cash;
	
	/**
	 * The rank of the user on that date
	 */
	@Column(name = "ranking")
	private int rank;
	
	@Column(name = "score")
	private double score;

	/**
	 * Empty Constructor
	 */
	public Score() {
	}

	/**
	 * Constructor for creating a new Score object
	 * 
	 * @param date The date that the score was saved
	 * @param userId The id of the use who the score entry belongs to
	 * @param score The player's daily score
	 */

	public Score(Date date, int userId, double portfolioValue, double cash) {
		this.date = date;
		this.userId = userId;
		this.portfolioValue = portfolioValue;
		this.cash = cash;
	}

	/**
	 * Get score id
	 * 
	 * @return id The id of the score object
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set score id
	 * 
	 * @param id The id of the score object to be set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get score date
	 * 
	 * @return The date that the score entry was created
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set score date
	 * 
	 * @param date The date to be set 
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Get user id
	 * 
	 * @return The id of the user who the score entry belongs to
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Set user id
	 * 
	 * @param userId The id of the user to be set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Get score
	 * 
	 * @return The player's score associated with the entry
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Set score
	 * 
	 * @param score The player's score to be set
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * Get rank
	 * 
	 * @return The player's rank in comparison to other players
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Set Rank
	 * 
	 * @param rank The player's rank to be set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	public double getPortfolioValue() {
		return portfolioValue;
	}
	
	public void setPortfolioValue(double portfolioValue) {
		this.portfolioValue = portfolioValue;
	}
	

	/**
	 * @return the cash
	 */
	public double getCash() {
		return cash;
	}

	/**
	 * @param cash the cash to set
	 */
	public void setCash(double cash) {
		this.cash = cash;
	}

	/**
	 * Compares the scores of 2 score objects
	 * 
	 * @param other Another score object
	 * @return If this score is less than the other score object's score returns -1.
	 *         If both scores are equal returns 0. If this score is greater than the
	 *         other score returns 1.
	 */
	public int compareTo(Score other) {
		if (this.getScore() < other.getScore()) {
			return -1;
		} else if (this.getScore() == other.getScore()) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public String toString() {
		return "Score [id=" + id + ", date=" + date + ", userId=" + userId + ", score=" + score + ", rank=" + rank
				+ "]";
	}


}
