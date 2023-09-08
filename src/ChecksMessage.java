
public class ChecksMessage implements Message {

	private int gain;
	private boolean isChanged;
	
	public ChecksMessage(int gain,boolean isChanged) {
		this.gain = gain;
		this.isChanged = isChanged;
	}

	public int getGain() {
		return gain;
	}
	
	public boolean getIsChanged() {
		return this.isChanged;
	}
}
