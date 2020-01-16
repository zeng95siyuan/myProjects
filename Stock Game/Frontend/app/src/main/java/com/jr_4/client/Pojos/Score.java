package com.jr_4.client.Pojos;

import android.support.annotation.NonNull;

public class Score {
    private int scoreId, userId, rank;
    private String date;
    private double score;

    public Score(int scoreId, int userId, int rank, String date, double score) {
        this.scoreId = scoreId;
        this.userId = userId;
        this.rank = rank;
        this.date = date;
        this.score = score;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @NonNull
    @Override
    public String toString() {
        return "User ID: "+userId+"; "+"Score: "+score+"; Rank: "+rank;
    }
}
