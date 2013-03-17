package net.baleful.coinflip.command;

public class LeaveGame extends Command {
	public static final String COMMAND = "LeaveGame";
	
	@Override
	public String getName() {
		return COMMAND;
	}
}
