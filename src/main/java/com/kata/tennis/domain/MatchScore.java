package com.kata.tennis.domain;

import lombok.Data;

@Data
public class MatchScore {
	private boolean matchFinished;
	private int gamesWonByPlayer1;
	private int gamesWonByPlayer2;
	private int pointsWonByPlayer1;
	private int pointsWonByPlayer2;
}
