package net.baleful.coinflip.command;

public class JoinGame extends Command {
	public static final String COMMAND = "JoinGame";
	
	@Override
	public String getName() {
		return COMMAND;
	}
}
