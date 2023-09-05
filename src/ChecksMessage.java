
public class ChecksMessage implements Message {

	private int checks;
	private boolean isChanged;
	
	public ChecksMessage(int checks,boolean isChanged) {
		this.checks = checks;
		this.isChanged = isChanged;
	}

	public int getChecks() {
		return checks;
	}
	
	public boolean getIsChanged() {
		return this.isChanged;
	}
}
