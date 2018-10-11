package com.kata.tennis.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TennisSet {
	private List<Game> games;

	public TennisSet() {
		this.games = new ArrayList<>();
		this.games.add(new Game());
	}	
}
