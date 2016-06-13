package net.decix.jsflow.header;

import net.decix.util.HeaderParseException;
import net.decix.util.Utility;

import javax.rmi.CORBA.Util;
import java.util.Vector;

/**
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                   sample sequence number                      |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |        source ID type         |     source ID index value     |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                  number of counter record                     |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  S                     n * counter records                       S
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 *  enterprise = 0 --> standard
 *             > 0 --> vendor specified format
 * @author Hans Yu
 *
 */
public class CounterSampleHeader {
	private long seqNumber;
	private int sourceIdType;
	private int sourceIdIndexValue;
	private long sampleLength;

	private Vector<CounterRecordHeader> counterRecordHeaders;

	public CounterSampleHeader() {
	}

	public void setSeqNumber(long seqNumber) {
		this.seqNumber = seqNumber;
	}

	public void setSourceIdType(int sourceIdType) {
		this.sourceIdType = sourceIdType;
	}

	public void setSourceIdIndexValue(int sourceIdIndexValue) {
		this.sourceIdIndexValue = sourceIdIndexValue;
	}

	public void setSampleLength(long sampleLength) {
		this.sampleLength = sampleLength;
	}

	public long getSeqNumber() {
		return seqNumber;
	}

	public int getSourceIdType() {
		return sourceIdType;
	}

	public int getSourceIdIndexValue() {
		return sourceIdIndexValue;
	}

	public long getSampleLength() {
		return sampleLength;
	}

	public void addCounterRecordHeaders(CounterRecordHeader crh) {
		counterRecordHeaders.add(crh);
	}

	public Vector<CounterRecordHeader> getCounterRecordHeaders() {
		return counterRecordHeaders;
	}

	public static CounterSampleHeader parse(byte[] data) throws HeaderParseException {
		try {
			if (data.length < 12) throw new HeaderParseException("Data array too short.");
			CounterSampleHeader csh = new CounterSampleHeader();
			// sequence number
			byte[] seqNumber = new byte[4];
			System.arraycopy(data, 0, seqNumber, 0, 4);
			csh.setSeqNumber(Utility.fourBytesToLong(seqNumber));
			// source ID type
			byte[] sourceIdType = new byte[2];
			System.arraycopy(data, 4, sourceIdType, 0, 2);
			csh.setSourceIdType(Utility.twoBytesToInteger(sourceIdType));
			// source ID index value
			byte[] souceIdIndexValue = new byte[2];
			System.arraycopy(data, 6, souceIdIndexValue, 0, 2);
			csh.setSourceIdIndexValue(Utility.twoBytesToInteger(souceIdIndexValue));
			// length
			byte[] length = new byte[4];
			System.arraycopy(data, 8, length, 0, 4);
			csh.setSampleLength(Utility.fourBytesToLong(length));

			if (true) {
				System.out.println("sFlow counter sample header info:");
				System.out.println("    counter sample sequence number: " + csh.getSeqNumber());
				System.out.println("    counter sample source ID type: " + csh.getSourceIdType());
				System.out.println("    counter sample source ID index value: " + csh.getSourceIdIndexValue());
				System.out.println("    counter sample length: " + csh.getSampleLength());
			}

			int offset = 12;
			for (int i = 0; i < csh.getSampleLength(); i++) {
				byte[] subData = new byte[data.length - offset];
				System.arraycopy(data, offset, subData, 0, data.length - offset);
				CounterRecordHeader crh = CounterRecordHeader.parse(subData);
				csh.addCounterRecordHeaders(crh);
				offset += crh.getCounterDataLength();
			}

			return csh;
		} catch (Exception e) {
			throw new HeaderParseException("Parse error: " + e.getMessage());
		}
	}

}
