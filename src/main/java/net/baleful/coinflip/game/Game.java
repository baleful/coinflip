package net.baleful.coinflip.game;

import java.util.Random;

import net.baleful.coinflip.command.*;

public class Game {
	private EventHandler eventHandler;

	private Status status;
	
	private Random random;

	public Game(EventHandler eventHandler) {
		this.eventHandler = eventHandler;

		status = new Status();
		
		random = new Random();
	}

	public boolean addPlayer(JoinGame command) {
		Player player = command.getPlayer();

		if (status.getPlayers().containsKey(player.getId())) {
			eventHandler
					.errorOccurred(status, player, "PLAYER_ALREADY_IN_GAME");
			return false;
		}

		status.getPlayers().put(player.getId(), player);

		eventHandler.playerJoined(status, player);

		return true;
	}

	public boolean removePlayer(LeaveGame command) {
		Player player = command.getPlayer();

		if (!status.getPlayers().containsKey(player.getId())) {
			eventHandler.errorOccurred(status, player, "PLAYER_NOT_IN_GAME");
			return false;
		}

		status.getPlayers().remove(player.getId());

		return true;
	}

	public void call(CallIt command) {
		Player player = command.getPlayer();

		if (!status.getPlayers().containsKey(player.getId())) {
			eventHandler.errorOccurred(status, player, "PLAYER_NOT_IN_GAME");

			return;
		}

		player.setHeads(command.isHeads());
	}

	public void getGameInfo(GetGameInfo command) {
		eventHandler.gameInfoRequested(status, command.getPlayer());
	}

	public void startRound() {
		status.startRound();

		eventHandler.roundStarted(status);
	}

	public void endRound() {
		status.endRound(random.nextBoolean());
		
		eventHandler.roundEnded(status);
		
		for (Player player : status.getPlayers().values()) {
			eventHandler.callResolved(status, player);
		}
	}

	public void reset() {
		status.reset();
	}

	public boolean isGameEmpty() {
		return status.getPlayers().isEmpty();
	}
	
	public boolean haveAllPlayersMadeCalls() {
		for(Player player : status.getPlayers().values()) {
			if (!player.isCallMade()) {
				return false;
			}
		}
		
		return true;
	}

	public void illegalCommand(Command command) {
		eventHandler.errorOccurred(status, command.getPlayer(),
				command.getName() + " not allowed during "
						+ status.getState().toString() + " state.");
	}
}
