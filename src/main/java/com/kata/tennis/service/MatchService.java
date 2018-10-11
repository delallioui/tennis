package com.kata.tennis.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.kata.tennis.domain.Game;
import com.kata.tennis.domain.Match;
import com.kata.tennis.domain.MatchScore;
import com.kata.tennis.domain.Player;
import com.kata.tennis.domain.Players;
import com.kata.tennis.domain.TennisSet;

import lombok.Getter;

@Service
public class MatchService {
	
	private static final int MAX_GAMES = 6;
	
	@Getter
	private Match match;
	
	public void setupMatch(String player1, String player2) {
		match = new Match();
		match.setPlayer1(new Player(player1));
		match.setPlayer2(new Player(player2));
		match.setSet1(new TennisSet());
	}
	
	public void score(Players scoringPlayer) {
		switch (scoringPlayer) {
		case PLAYER1:
			scoreForPlayer1();
			break;
		case PLAYER2:
			scoreForPlayer2();
			break;
		default:
			break;
		}
	}
	
	public MatchScore getMatchScore() {
		MatchScore matchScore = new MatchScore();
		matchScore.setMatchFinished(match.isFinished());
		matchScore.setGamesWonByPlayer1(getGameWonByPlayer1(match.getSet1()));
		matchScore.setGamesWonByPlayer2(getGameWonByPlayer2(match.getSet1()));
		matchScore.setPointsWonByPlayer1(getPointsWonByPlayer1(match.getSet1()));
		matchScore.setPointsWonByPlayer2(getPointsWonByPlayer2(match.getSet1()));
		return matchScore;
	}

	private int getPointsWonByPlayer2(TennisSet set) {
		if(Objects.isNull(set) || Objects.isNull(set.getGames()) || set.getGames().isEmpty()) {
			return 0;
		}
		int size = set.getGames().size();
		return mapScore(set.getGames().get(size-1).getScore2());
	}

	private int getPointsWonByPlayer1(TennisSet set) {
		if(Objects.isNull(set) || Objects.isNull(set.getGames()) || set.getGames().isEmpty()) {
			return 0;
		}
		int size = set.getGames().size();
		return mapScore(set.getGames().get(size-1).getScore1());
	}
	
	private int mapScore(int score) {
		if(score <= 0 )
			return 0;
		if(score == 1)
			return 15;
		if(score == 2)
			return 30;
		else
			return 40;
	}

	private int getGameWonByPlayer1(TennisSet set) {
		if(Objects.isNull(set) || Objects.isNull(set.getGames())) {
			return 0;
		}
		long numberGamesWon = set.getGames().stream().filter(game -> game.isFinished() && game.getScore1() > game.getScore2()).count();
		return Math.toIntExact(numberGamesWon);
	}
	
	private int getGameWonByPlayer2(TennisSet set) {
		if(Objects.isNull(set) || Objects.isNull(set.getGames())) {
			return 0;
		}
		long numberGamesWon = set.getGames().stream().filter(game -> game.isFinished() && game.getScore1() < game.getScore2()).count();
		return Math.toIntExact(numberGamesWon);
	}

	private void scoreForPlayer1() {
		List<Game> games = this.match.getSet1().getGames();
		int size = games.size();
		Game lastGame = games.get(size - 1); 
		int score1 = lastGame.getScore1();
		int score2 = lastGame.getScore2();
		
		lastGame.setScore1(score1 + 1);
		
		if(score1 >= 3 && score2 < score1) {
			lastGame.setFinished(true);
			if(!checkMatchIsFinished(match))
				games.add(new Game()); // Only if the match isn't finished yet
			else {
				this.match.setWinner(Players.PLAYER1);
				this.match.setFinished(true);
			}
		}
	}
	
	private void scoreForPlayer2() {
		List<Game> games = this.match.getSet1().getGames();
		int size = games.size();
		Game lastGame = games.get(size - 1); 
		int score1 = lastGame.getScore1();
		int score2 = lastGame.getScore2();
		
		lastGame.setScore2(score2 + 1);
		
		if(score2 >= 3 && score1 < score2) {
			lastGame.setFinished(true);
			if(!checkMatchIsFinished(match))
				games.add(new Game());
			else {
				this.match.setWinner(Players.PLAYER2);
				this.match.setFinished(true);
			}
		}
	}
	
	private boolean checkMatchIsFinished(Match match) {
		return getGameWonByPlayer1(match.getSet1()) >= MAX_GAMES || getGameWonByPlayer2(match.getSet1()) >= MAX_GAMES;
	}
}