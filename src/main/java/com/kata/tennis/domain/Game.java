package com.kata.tennis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
	private int score1;
	private int score2;
	
	private boolean finished;
}
