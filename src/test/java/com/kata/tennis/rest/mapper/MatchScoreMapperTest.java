package com.kata.tennis.rest.mapper;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.kata.tennis.domain.MatchScore;
import com.kata.tennis.domain.Players;
import com.kata.tennis.rest.dto.MatchScoreDto;
import com.kata.tennis.rest.exception.BadDataArgumentException;

public class MatchScoreMapperTest {
	private MatchScoreMapper matchScoreMapper;
	
	@Before
	public void init() {
		matchScoreMapper = new MatchScoreMapper();
	}
	
	@Test
	public void should_return_the_player1_given_its_code() {
		// Given --
		String code = "1";
		// When --
		Players players = matchScoreMapper.mapToPlayer(code);
		// Then --
		Assertions.assertThat(players).isEqualTo(Players.PLAYER1);
	}
	
	@Test
	public void should_return_the_player2_given_its_code() {
		// Given --
		String code = "2";
		// When --
		Players players = matchScoreMapper.mapToPlayer(code);
		// Then --
		Assertions.assertThat(players).isEqualTo(Players.PLAYER2);
	}
	
	@Test
	public void should_throw_BadDataArgumentException_given_non_numeric_code() {
		// Given --
		String code = "xx";
		// Then --
		Assertions.assertThatExceptionOfType(BadDataArgumentException.class)
        .isThrownBy(
        		// When --
        		() -> matchScoreMapper.mapToPlayer(code)
        	);
	}
	
	@Test
	public void should_throw_BadDataArgumentException_given_numeric_code_that_is_not_1_or_2() {
		// Given --
		String code = "5";
		// When --
		BadDataArgumentException exception = Assertions.catchThrowableOfType(() -> matchScoreMapper.mapToPlayer(code), BadDataArgumentException.class);
		// Then --
		Assertions.assertThat(exception.getMessage()).isEqualTo("the player Id must be 1 or 2");
	}
	
	@Test
	public void should_return_null_matchScoreDto_given_null_matchScore() {
		// Given --
		MatchScore matchScore = null;
		// When --
		MatchScoreDto matchScoreDto = matchScoreMapper.toMatchScoreDto(matchScore);
		// Then --
		Assertions.assertThat(matchScoreDto).isNull();
	}
	
	@Test
	public void should_return_matchScoreDto_given_matchScore() {
		// Given --
		MatchScore matchScore = new MatchScore();
		matchScore.setGamesWonByPlayer1(1);
		matchScore.setGamesWonByPlayer2(2);
		matchScore.setPointsWonByPlayer1(30);
		matchScore.setPointsWonByPlayer2(40);
		matchScore.setMatchFinished(false);
		// When --
		MatchScoreDto matchScoreDto = matchScoreMapper.toMatchScoreDto(matchScore);
		// Then --
		Assertions.assertThat(matchScoreDto.getGamesWonByPlayer1()).isEqualTo(1);
		Assertions.assertThat(matchScoreDto.getGamesWonByPlayer2()).isEqualTo(2);
		Assertions.assertThat(matchScoreDto.getPointsWonByPlayer1()).isEqualTo(30);
		Assertions.assertThat(matchScoreDto.getPointsWonByPlayer2()).isEqualTo(40);
		Assertions.assertThat(matchScoreDto.isMatchFinished()).isEqualTo(false);
	}
	
}





