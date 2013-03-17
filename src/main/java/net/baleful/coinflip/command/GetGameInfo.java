package net.baleful.coinflip.command;

public class GetGameInfo extends Command {
	public static final String COMMAND = "GetGameInfo";
	
	@Override
	public String getName() {
		return COMMAND;
	}
}
