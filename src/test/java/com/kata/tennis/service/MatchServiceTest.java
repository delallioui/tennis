package com.kata.tennis.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

import com.kata.tennis.domain.MatchScore;
import com.kata.tennis.domain.Players;

public class MatchServiceTest {

	private MatchService matchService;
	
	@Before
	public void init() {
		matchService = new MatchService();
	}
	
	@Test
	public void should_setup_the_match_given_players_names() {
		// Given --
		String name1 = "player a";
		String name2 = "player b";
		// When --
		matchService.setupMatch(name1, name2);
		// Then --
		assertThat(matchService.getMatch().getPlayer1().getName()).isEqualTo(name1);
		assertThat(matchService.getMatch().getPlayer2().getName()).isEqualTo(name2);
		assertThat(matchService.getMatch().getSet1().getGames().size()).isEqualTo(1);
	}
	
	@Test
	public void should_add_score_for_the_player1() {
		// Given --
		matchService.setupMatch("a", "b");
		// When --
		matchService.score(Players.PLAYER1);
		// Then --
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore1()).isEqualTo(1);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore2()).isEqualTo(0);
	}
	
	@Test
	public void should_add_score_for_the_player2() {
		// Given --
		matchService.setupMatch("a", "b");
		// When --
		matchService.score(Players.PLAYER2);
		// Then --
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore2()).isEqualTo(1);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore1()).isEqualTo(0);
	}
	
	@Test
	public void should_finish_the_game_and_create_a_new_one_given_player1_scored_4_times() {
		// Given --
		matchService.setupMatch("a", "b");
		// When --
		matchService.score(Players.PLAYER1); //15
		matchService.score(Players.PLAYER1); //30
		matchService.score(Players.PLAYER1); //40
		matchService.score(Players.PLAYER1); //win
		// Then --
		assertThat(matchService.getMatch().getSet1().getGames().size()).isEqualTo(2);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore1()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore2()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).isFinished()).isTrue();
		assertThat(matchService.getMatch().getSet1().getGames().get(1).getScore1()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(1).getScore2()).isEqualTo(0);
	}
	
	@Test
	public void should_finish_the_game_and_create_a_new_one_given_player2_scored_4_times() {
		// Given --
		matchService.setupMatch("a", "b");
		// When --
		matchService.score(Players.PLAYER2); //15
		matchService.score(Players.PLAYER2); //30
		matchService.score(Players.PLAYER2); //40
		matchService.score(Players.PLAYER2); //win
		// Then --
		assertThat(matchService.getMatch().getSet1().getGames().size()).isEqualTo(2);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore1()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore2()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).isFinished()).isTrue();
		assertThat(matchService.getMatch().getSet1().getGames().get(1).getScore1()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(1).getScore2()).isEqualTo(0);
	}
	
	@Test
	public void should_not_finish_the_game_given_players_are_in_deuce_and_player1_scores() {
		// Given --
		matchService.setupMatch("a", "b");
		// When --
		for(int i=0; i<4; i++) { // deuce
			matchService.score(Players.PLAYER1);
			matchService.score(Players.PLAYER2);
		}
		matchService.score(Players.PLAYER1);
		// Then --
		assertThat(matchService.getMatch().getSet1().getGames().size()).isEqualTo(1);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore1()).isEqualTo(5);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore2()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).isFinished()).isFalse();
	}
	
	@Test
	public void should_not_finish_the_game_given_players_are_in_deuce_and_player2_scores() {
		// Given --
		matchService.setupMatch("a", "b");
		// When --
		for(int i=0; i<4; i++) { // deuce
			matchService.score(Players.PLAYER1);
			matchService.score(Players.PLAYER2);
		}
		matchService.score(Players.PLAYER2);
		// Then --
		assertThat(matchService.getMatch().getSet1().getGames().size()).isEqualTo(1);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore1()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore2()).isEqualTo(5);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).isFinished()).isFalse();
	}
	
	@Test
	public void sould_finish_the_match_when_player1_wins() {
		// Given --
		matchService.setupMatch("a", "b");
		// When --
		for(int i=0; i<24; i++) { // player1 wins cuz he scored 6*4 = 24 points.
			matchService.score(Players.PLAYER1);
		}
		// Then --
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore1()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore2()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().getSet1().getGames().get(1).getScore1()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(1).getScore2()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(1).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().getSet1().getGames().get(2).getScore1()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(2).getScore2()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(2).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().getSet1().getGames().get(3).getScore1()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(3).getScore2()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(3).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().getSet1().getGames().get(4).getScore1()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(4).getScore2()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(4).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().getSet1().getGames().get(5).getScore1()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(5).getScore2()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(5).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().isFinished()).isTrue();
		assertThat(matchService.getMatch().getWinner()).isEqualTo(Players.PLAYER1);
	}
	
	@Test
	public void sould_finish_the_match_when_player2_wins() {
		// Given --
		matchService.setupMatch("a", "b");
		// When --
		for(int i=0; i<24; i++) { // player2 wins cuz he scored 6*4 = 24 points.
			matchService.score(Players.PLAYER2);
		}
		// Then --
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore1()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).getScore2()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(0).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().getSet1().getGames().get(1).getScore1()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(1).getScore2()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(1).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().getSet1().getGames().get(2).getScore1()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(2).getScore2()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(2).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().getSet1().getGames().get(3).getScore1()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(3).getScore2()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(3).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().getSet1().getGames().get(4).getScore1()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(4).getScore2()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(4).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().getSet1().getGames().get(5).getScore1()).isEqualTo(0);
		assertThat(matchService.getMatch().getSet1().getGames().get(5).getScore2()).isEqualTo(4);
		assertThat(matchService.getMatch().getSet1().getGames().get(5).isFinished()).isTrue();
		
		assertThat(matchService.getMatch().isFinished()).isTrue();
		assertThat(matchService.getMatch().getWinner()).isEqualTo(Players.PLAYER2);
	}
	
	@Test
	public void should_return_match_score_after_scoring_one_point_by_player1() {
		// Given --
		matchService.setupMatch("a", "b");
		matchService.score(Players.PLAYER1);
		// When --
		MatchScore matchScore = matchService.getMatchScore();
		// Then --
		assertThat(matchScore.isMatchFinished()).isFalse();
		assertThat(matchScore.getGamesWonByPlayer1()).isEqualTo(0);
		assertThat(matchScore.getGamesWonByPlayer2()).isEqualTo(0);
		assertThat(matchScore.getPointsWonByPlayer1()).isEqualTo(15);
		assertThat(matchScore.getPointsWonByPlayer2()).isEqualTo(0);
	}
	
	@Test
	public void should_return_match_score_after_scoring_one_point_by_player2() {
		// Given --
		matchService.setupMatch("a", "b");
		matchService.score(Players.PLAYER2);
		// When --
		MatchScore matchScore = matchService.getMatchScore();
		// Then --
		assertThat(matchScore.isMatchFinished()).isFalse();
		assertThat(matchScore.getGamesWonByPlayer1()).isEqualTo(0);
		assertThat(matchScore.getGamesWonByPlayer2()).isEqualTo(0);
		assertThat(matchScore.getPointsWonByPlayer1()).isEqualTo(0);
		assertThat(matchScore.getPointsWonByPlayer2()).isEqualTo(15);
	}
	
	@Test
	public void should_return_match_score_after_scoring_one_game_and_one_point_by_player1() {
		// Given --
		matchService.setupMatch("a", "b");
		for(int i=0; i<4; i++) { //deuce
			matchService.score(Players.PLAYER1);
			matchService.score(Players.PLAYER2);
		}
		matchService.score(Players.PLAYER1); // Advantage
		matchService.score(Players.PLAYER1); // win 1st game
		matchService.score(Players.PLAYER1); // 15 - 0 in 2nd game
		// When --
		MatchScore matchScore = matchService.getMatchScore();
		// Then --
		assertThat(matchScore.isMatchFinished()).isFalse();
		assertThat(matchScore.getGamesWonByPlayer1()).isEqualTo(1);
		assertThat(matchScore.getGamesWonByPlayer2()).isEqualTo(0);
		assertThat(matchScore.getPointsWonByPlayer1()).isEqualTo(15);
		assertThat(matchScore.getPointsWonByPlayer2()).isEqualTo(0);
	}
	
	@Test
	public void should_return_match_score_after_scoring_one_game_and_one_point_by_player2() {
		// Given --
		matchService.setupMatch("a", "b");
		for(int i=0; i<4; i++) { //deuce
			matchService.score(Players.PLAYER1);
			matchService.score(Players.PLAYER2);
		}
		matchService.score(Players.PLAYER2); // Advantage
		matchService.score(Players.PLAYER2); // win 1st game
		matchService.score(Players.PLAYER2); // 15 - 0 in 2nd game
		// When --
		MatchScore matchScore = matchService.getMatchScore();
		// Then --
		assertThat(matchScore.isMatchFinished()).isFalse();
		assertThat(matchScore.getGamesWonByPlayer1()).isEqualTo(0);
		assertThat(matchScore.getGamesWonByPlayer2()).isEqualTo(1);
		assertThat(matchScore.getPointsWonByPlayer1()).isEqualTo(0);
		assertThat(matchScore.getPointsWonByPlayer2()).isEqualTo(15);
	}
	
	@Test
	public void should_return_match_score_after_scoring_Three_games_for_each_player() {
		// Given --
		matchService.setupMatch("a", "b");
		for(int i=0; i<4; i++) { //1 - 0
			matchService.score(Players.PLAYER1);
		}
		for(int i=0; i<4; i++) { //1 - 1
			matchService.score(Players.PLAYER2);
		}
		for(int i=0; i<4; i++) { //1 - 2
			matchService.score(Players.PLAYER2);
		}
		for(int i=0; i<4; i++) { //1 - 3
			matchService.score(Players.PLAYER2);
		}
		for(int i=0; i<4; i++) { //2 - 3
			matchService.score(Players.PLAYER1);
		}
		for(int i=0; i<4; i++) { //3 - 3
			matchService.score(Players.PLAYER1);
		}
		// When --
		MatchScore matchScore = matchService.getMatchScore();
		// Then --
		assertThat(matchScore.isMatchFinished()).isFalse();
		assertThat(matchScore.getGamesWonByPlayer1()).isEqualTo(3);
		assertThat(matchScore.getGamesWonByPlayer2()).isEqualTo(3);
		assertThat(matchScore.getPointsWonByPlayer1()).isEqualTo(0);
		assertThat(matchScore.getPointsWonByPlayer2()).isEqualTo(0);
	}
}










