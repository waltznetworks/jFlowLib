package net.decix.jsflow.header;

import net.decix.util.HeaderParseException;
import net.decix.util.Utility;
import java.util.Vector;

/**
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                   sample sequence number                      |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |        source ID type         |     source ID index value     |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                        sampling rate                          |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                         sample pool                           |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                             drops                             |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                             input                             |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                            output*                            |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  S                     n * counter records                       S
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 *  * broadcast or multicast are handled as follows:
 *    the first bit indicates multiple destinations, the lower order
 *    bits number of interfaces
 *
 * @author Hans Yu
 *
 */
public class FlowSampleHeader {
	private long seqNumber;
	private int sourceIdType;
	private int sourceIdIndexValue;
	private long samplingRate;
	private long samplePool;
	private long drops;
	private long input;
	private long output;
	private long sampleLength;

	private Vector<FlowRecordHeader> flowRecordHeaders;

	public FlowSampleHeader() {
		flowRecordHeaders = new Vector<FlowRecordHeader>();
	}

	public void setSeqNumber(long seqNumber) { this.seqNumber = seqNumber; }

	public void setSourceIdType(int sourceIdType) { this.sourceIdType = sourceIdType; }

	public void setSourceIdIndexValue(int sourceIdIndexValue) { this.sourceIdIndexValue = sourceIdIndexValue; }

	public void setSamplingRate(long samplingRate) { this.samplingRate = samplingRate; }

	public void setSamplePool(long samplePool) { this.samplePool = samplePool; }

	public void setDrops(long drops) { this.drops = drops; }

	public void setInput(long input) { this.input = input; }

	public void setOutput(long output) { this.output = output; }

	public void setSampleLength(long sampleLength) { this.sampleLength = sampleLength; }

	public long getSeqNumber() { return seqNumber; }

	public int getSourceIdType() { return sourceIdType; }

	public int getSourceIdIndexValue() { return sourceIdIndexValue; }

	public long getSamplingRate() { return samplingRate; }

	public long getSamplePool() { return samplePool; }

	public long getDrops() { return drops; }

	public long getInput() { return input; }

	public long getOutput() { return output; }

	public long getSampleLength() { return sampleLength; }

	public void addFlowRecordHeaders(FlowRecordHeader frh) { this.flowRecordHeaders.add(frh); }

	public Vector<FlowRecordHeader> getFlowRecordHeaders() { return flowRecordHeaders; }

	public static FlowSampleHeader parse(byte[] data) throws HeaderParseException {
		try {
			if (data.length < 32) throw new HeaderParseException("Data array too short.");
			FlowSampleHeader fsh = new FlowSampleHeader();
			// sequence number
			byte[] seqNumber = new byte[4];
			System.arraycopy(data, 0, seqNumber, 0, 4);
			fsh.setSeqNumber(Utility.fourBytesToLong(seqNumber));
			// source ID type
			byte[] sourceIdType = new byte[2];
			System.arraycopy(data, 4, sourceIdType, 0, 2);
			fsh.setSourceIdType(Utility.twoBytesToInteger(sourceIdType));
			// source ID index value
			byte[] sourceIdIndexValue = new byte[2];
			System.arraycopy(data, 6, sourceIdIndexValue, 0, 2);
			fsh.setSourceIdIndexValue(Utility.twoBytesToInteger(sourceIdIndexValue));
			// sampling rate
			byte[] samplingRate = new byte[4];
			System.arraycopy(data, 8, samplingRate, 0, 4);
			fsh.setSamplingRate(Utility.fourBytesToLong(samplingRate));
			// sample pool
			byte[] samplePool = new byte[4];
			System.arraycopy(data, 12, samplePool, 0, 4);
			fsh.setSamplePool(Utility.fourBytesToLong(samplePool));
			// drops
			byte[] drops = new byte[4];
			System.arraycopy(data, 16, drops, 0, 4);
			fsh.setDrops(Utility.fourBytesToLong(drops));
			// input
			byte[] input = new byte[4];
			System.arraycopy(data, 20, input, 0, 4);
			fsh.setInput(Utility.fourBytesToLong(input));
			// output
			byte[] output = new byte[4];
			System.arraycopy(data, 24, output, 0, 4);
			fsh.setOutput(Utility.fourBytesToLong(output));
			// length
			byte[] length = new byte[4];
			System.arraycopy(data, 28, length, 0, 4);
			fsh.setSampleLength(Utility.fourBytesToLong(length));

			if (true) {
				System.out.println("sFlow flow sample header info:");
				System.out.println("    flow sample sequence number: " + fsh.getSeqNumber());
				System.out.println("    flow sample source ID type: " + fsh.getSourceIdType());
				System.out.println("    flow sample source ID index value: " + fsh.getSourceIdIndexValue());
				System.out.println("    flow sample sampling rate: " + fsh.getSamplingRate());
				System.out.println("    flow sample sample pool: " + fsh.getSamplePool());
				System.out.println("    flow sample drops: " + fsh.getDrops());
				System.out.println("    flow sample input: " + fsh.getInput());
				System.out.println("    flow sample output: " + fsh. getOutput());
				System.out.println("    flow sample length: " + fsh.getSampleLength());
			}

			int offset = 32;
			for (int i = 0; i < fsh.getSampleLength(); i++) {
				System.out.println("    offset of sample " + i + ": " + offset);
				byte[] subData = new byte[data.length - offset];
				System.arraycopy(data, offset, subData, 0, data.length - offset);
				FlowRecordHeader frh = FlowRecordHeader.parse(subData);
				fsh.addFlowRecordHeaders(frh);
				offset += (frh.getFlowDataLength() + 8);
			}

			return fsh;
		} catch (Exception e) {
			throw new HeaderParseException("Error parsing flow sample: " + e.getMessage());
		}
	}
}
