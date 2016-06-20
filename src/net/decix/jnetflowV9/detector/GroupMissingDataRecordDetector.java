package net.decix.jnetflowV9.detector;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Logger;

import net.decix.jipfix.header.DataRecord;
import net.decix.jipfix.header.L2IPDataRecord;
import net.decix.jipfix.header.MessageHeader;
import net.decix.jipfix.header.SetHeader;

public class GroupMissingDataRecordDetector implements MissingDataRecordDetector {
	private final static Logger LOGGER = Logger.getLogger(GroupMissingDataRecordDetector.class.getName());
	
	private HashMap<IPObservationDomain, GroupMissingSequenceNumberChecker> sequenceNumberCheckers = new HashMap<IPObservationDomain, GroupMissingSequenceNumberChecker>();
	
	public synchronized void detectMissing(DatagramPacket dp, MessageHeader mh) {
		// handle sequence number check
		IPObservationDomain currentIPObservationDomain = new IPObservationDomain(dp.getAddress(), mh.getObservationDomainID());
		GroupMissingSequenceNumberChecker gmsnc = new GroupMissingSequenceNumberChecker(currentIPObservationDomain);
		
		try {
			if (currentIPObservationDomain.getAddress().equals(InetAddress.getByName("10.102.0.16")) && (currentIPObservationDomain.getObservationDomain() == 100663296)) {
				if (sequenceNumberCheckers.containsKey(currentIPObservationDomain)) {
					gmsnc = sequenceNumberCheckers.get(currentIPObservationDomain);
				} else {
					sequenceNumberCheckers.put(currentIPObservationDomain, gmsnc);
				}

				long numberDataRecords = 0;
				for (SetHeader sh : mh.getSetHeaders()) {
					for (DataRecord dr : sh.getDataRecords()) {
						if (dr instanceof L2IPDataRecord) numberDataRecords++;
					}
				}
				for (int i = 0; i < numberDataRecords; i++) {
					gmsnc.addSequenceNumber(mh.getSequenceNumber() + i);
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}