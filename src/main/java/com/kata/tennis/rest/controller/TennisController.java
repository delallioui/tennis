package com.kata.tennis.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kata.tennis.domain.Players;
import com.kata.tennis.rest.dto.MatchScoreDto;
import com.kata.tennis.rest.mapper.MatchScoreMapper;
import com.kata.tennis.service.MatchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Tennis"})
public class TennisController {
	
	private final MatchService matchService;
	private final MatchScoreMapper matchScoreMapper;
	
	@Autowired
	public TennisController(MatchService matchService, MatchScoreMapper matchScoreMapper) {
		this.matchService = matchService;
		this.matchScoreMapper = matchScoreMapper;
	}

	@ResponseBody
	@RequestMapping(value = "/startMatch", method = RequestMethod.POST)
	@ApiOperation(value = "creates new tennis match between two players")
	@ApiResponses(value = {
			@ApiResponse(code=200, message = "succes. match created.")
	})
	public void startNewMatch(
			@ApiParam(value = "the first player name", required = true) @RequestParam(value = "name1") String player1,
			@ApiParam(value = "the second player name", required = true) @RequestParam(value = "name2") String player2) {
		matchService.setupMatch(player1, player2);
	}
	
	@ResponseBody
	@RequestMapping(value = "/scoreOnePoint", method = RequestMethod.PUT)
	@ApiOperation(value = "adds one point for the scoring player.")
	@ApiResponses(value = {
			@ApiResponse(code=200, message = "succes. add one point for the scoring player.")
	})
	public void scoreOnePoint(
			@ApiParam(value = "the player id (1: for first player, 2: for second player)", required = true) @RequestParam(value = "playerId") String playerId) {
		Players player = matchScoreMapper.mapToPlayer(playerId);
		matchService.score(player);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMatchScore", method = RequestMethod.GET)
	@ApiOperation(value = "returns the live match score.")
	@ApiResponses(value = {
			@ApiResponse(code=200, message = "succes. returns the match score.", response = MatchScoreDto.class)
	})
	public MatchScoreDto getMatchScore() {
		return matchScoreMapper.toMatchScoreDto(matchService.getMatchScore());
	}

}
