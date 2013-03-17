package net.baleful.coinflip.command;

import net.baleful.coinflip.game.Player;

public abstract class Command {
	public abstract String getName();
	
	private Player player;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
