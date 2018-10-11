package com.kata.tennis.rest.mapper;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.kata.tennis.domain.MatchScore;
import com.kata.tennis.domain.Players;
import com.kata.tennis.rest.dto.MatchScoreDto;
import com.kata.tennis.rest.exception.BadDataArgumentException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatchScoreMapper {
	
	public MatchScoreDto toMatchScoreDto(MatchScore matchScore) {
		if(Objects.isNull(matchScore)) {
			return null;
		}
		MatchScoreDto matchScoreDto = new MatchScoreDto();
		matchScoreDto.setMatchFinished(matchScore.isMatchFinished());
		matchScoreDto.setGamesWonByPlayer1(matchScore.getGamesWonByPlayer1());
		matchScoreDto.setGamesWonByPlayer2(matchScore.getGamesWonByPlayer2());
		matchScoreDto.setPointsWonByPlayer1(matchScore.getPointsWonByPlayer1());
		matchScoreDto.setPointsWonByPlayer2(matchScore.getPointsWonByPlayer2());
		return matchScoreDto;
	}
	
	public Players mapToPlayer(String playerId) {
		try {
			int code = Integer.parseInt(playerId);
			if(code != 1 && code != 2) {
				String message = "the player Id must be 1 or 2";
				log.warn(message);
				throw new BadDataArgumentException(message);
			}
			return code == 1 ? Players.PLAYER1 : Players.PLAYER2;
		} catch (NumberFormatException e) {
			String message = e.getMessage();
			log.warn(message);
			throw new BadDataArgumentException(message, e);
		}
	}
}
