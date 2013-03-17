package net.baleful.coinflip.game;

public class Config {
	private int secondsToTakeCalls;
	private int secondsToEndRound;
	
	public Config() {
		
	}

	public Config(int secondsToTakeCalls, int secondsToEndRound) {
		super();
		this.secondsToTakeCalls = secondsToTakeCalls;
		this.secondsToEndRound = secondsToEndRound;
	}

	public int getSecondsToTakeCalls() {
		return secondsToTakeCalls;
	}

	public void setSecondsToTakeCalls(int secondsToTakeCalls) {
		this.secondsToTakeCalls = secondsToTakeCalls;
	}

	public int getSecondsToEndRound() {
		return secondsToEndRound;
	}

	public void setSecondsToEndRound(int secondsToEndRound) {
		this.secondsToEndRound = secondsToEndRound;
	}
}
