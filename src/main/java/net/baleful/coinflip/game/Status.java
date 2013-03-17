package net.baleful.coinflip.game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Status {
	public static enum GameState {
		WAITING_FOR_PLAYERS,
		TAKING_CALLS,
		ENDING_ROUND
	}
	
	private Map<String, Player> players;
	
	private String roundId;
	
	private GameState state;
	
	private boolean flippedHeads;
	
	public Status() {
		players = new HashMap<String, Player>();
	}
	
	public void reset() {
		roundId = "WAITING_FOR_PLAYERS";
		state = GameState.WAITING_FOR_PLAYERS;
	}
	
	public void startRound() {
		for (Player player : players.values()) {
			player.resetForNewRound();
		}
		
		roundId = UUID.randomUUID().toString();
		
		state = GameState.TAKING_CALLS;
	}
	
	public void endRound(boolean flippedHeads) {
		state = GameState.ENDING_ROUND;
		
		this.flippedHeads = flippedHeads;
	}

	public Map<String, Player> getPlayers() {
		return players;
	}

	public String getRoundId() {
		return roundId;
	}

	public GameState getState() {
		return state;
	}
	
	public boolean getFlippedHeads() {
		return flippedHeads;
	}
}
