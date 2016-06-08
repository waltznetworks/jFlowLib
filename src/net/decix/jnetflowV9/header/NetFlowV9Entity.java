package net.decix.jnetflowV9.header;

import net.decix.util.HeaderBytesException;

public interface NetFlowV9Entity {
	public String toString();
	public byte[] getBytes() throws HeaderBytesException;
}
