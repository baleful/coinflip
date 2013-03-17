package net.baleful.coinflip.game;


public interface EventHandler {
	void playerJoined(Status status, Player player);

	void playerLeft(Status status, Player player);

	void roundStarted(Status status);

	void roundEnded(Status status);
	
	void callResolved(Status status, Player player);

	void gameInfoRequested(Status status, Player player);

	void errorOccurred(Status status, Player player, String errorMessage);
}
