package net.decix.jnetflowV9.detector;

import java.net.DatagramPacket;

import net.decix.jipfix.header.MessageHeader;

public interface MissingDataRecordDetector {
	public void detectMissing(DatagramPacket dp, MessageHeader mh);
}
