package net.decix.jnetflowV9.header;

public abstract class AbstractHeader {

	protected int length;

	protected void setLength(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}

}
