package net.baleful.coinflip.command;

public class CallIt extends Command {
	public static final String COMMAND = "CallIt";
	
	@Override
	public String getName() {
		return COMMAND;
	}
	
	private boolean heads;
	
	public CallIt() {
		heads = false;
	}
	
	public CallIt(boolean heads) {
		this.heads = heads;
	}

	public boolean isHeads() {
		return heads;
	}

	public void setHeads(boolean heads) {
		this.heads = heads;
	}

}
