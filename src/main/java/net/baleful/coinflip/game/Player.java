package net.baleful.coinflip.game;

public class Player {
	private String id;
	
	private boolean heads;
	
	private boolean callMade;
	
	public Player(String id) {
		this.id = id;
		
		heads = false;
		
		callMade = false;
	}

	public String getId() {
		return id;
	}

	public boolean isHeads() {
		return heads;
	}

	public void setHeads(boolean heads) {
		this.heads = heads;
		
		this.callMade = true;
	}

	public boolean isCallMade() {
		return callMade;
	}
	
	public void resetForNewRound() {
		heads = false;
		
		callMade = false;
	}
	
	public boolean isWinner(boolean flippedHeads) {
		return (callMade && flippedHeads == heads);
	}
}
