package com.kata.tennis.domain;

import lombok.Data;

@Data
public class Match {
	private Player player1;
	private Player player2;
	private TennisSet set1;
	private boolean finished;
	private Players winner;
}
