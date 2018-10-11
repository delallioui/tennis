package com.kata.tennis.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MatchScoreDto {
	@JsonProperty("matchFinished")
	private boolean matchFinished;
	@JsonProperty("gamesWonByPlayer1")
	private int gamesWonByPlayer1;
	@JsonProperty("gamesWonByPlayer2")
	private int gamesWonByPlayer2;
	@JsonProperty("pointsWonByPlayer1")
	private int pointsWonByPlayer1;
	@JsonProperty("pointsWonByPlayer2")
	private int pointsWonByPlayer2;
}
