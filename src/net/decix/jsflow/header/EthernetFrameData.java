package net.decix.jsflow.header;

import net.decix.util.HeaderBytesException;
import net.decix.util.HeaderParseException;
import net.decix.util.MacAddress;
import net.decix.util.Utility;

/**
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                            length                             |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                      source mac address                       |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |      source mac address       |           pad bytes           |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                    destination mac address                    |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |   destination mac address     |           pad bytes           |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                             type                              |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 *  @author Hans Yu
 */
public class EthernetFrameData {
	private long ethernetFrameDataLength;
	private MacAddress srcMac;
	private MacAddress dstMac;
	private long type;

	public EthernetFrameData() {
	}

	public void setLength(long length) { this.ethernetFrameDataLength = length; }

	public void setSrcMac(MacAddress srcMac) { this.srcMac = srcMac; }

	public void setDstMac(MacAddress dstMac) { this.dstMac = dstMac; }

	public void setType(long type) { this.type = type; }

	public long getLength() { return ethernetFrameDataLength; }

	public MacAddress getSrcMac() { return srcMac; }

	public MacAddress getDstMac() { return dstMac; }

	public long getType() { return type; }

	public static EthernetFrameData parse(byte[] data) throws HeaderParseException {
		try {
			if (data.length < 24) throw new HeaderParseException("Ethernet frame data too short.");
			EthernetFrameData efd = new EthernetFrameData();
			// length
			byte[] ethernetFrameDataLength = new byte[4];
			System.arraycopy(data, 0, ethernetFrameDataLength, 0, 4);
			efd.setLength(Utility.fourBytesToLong(ethernetFrameDataLength));
			// source MAC address
			byte[] srcMac = new byte[6];
			System.arraycopy(data, 4, srcMac, 0, 6);
			efd.setSrcMac(new MacAddress(srcMac));
			// padding
			// destination MAC address
			byte[] dstMac = new byte[6];
			System.arraycopy(data, 12, dstMac, 0, 6);
			efd.setDstMac(new MacAddress(dstMac));
			// padding
			// type
			byte[] type = new byte[4];
			System.arraycopy(data, 20, type, 0, 4);
			efd.setType(Utility.fourBytesToLong(type));

			return efd;
		} catch (Exception e) {
			throw new HeaderParseException("Error parsing Ethernet frame data: " + e.getMessage());
		}
	}

	public byte[] getBytes() throws HeaderBytesException {
		try {
			byte[] data = new byte[24];
			// length
			System.arraycopy(Utility.longToFourBytes(ethernetFrameDataLength), 0, data, 0, 4);
			// source MAC address
			System.arraycopy(srcMac.getBytes(), 0, data, 4, 6);
			// padding
			System.arraycopy(Utility.integerToTwoBytes(0), 0, data, 10, 2);
			// destination MAC address
			System.arraycopy(dstMac.getBytes(), 0, data, 12, 6);
			// padding
			System.arraycopy(Utility.integerToTwoBytes(0), 0, data, 18, 2);
			// type
			System.arraycopy(Utility.longToFourBytes(type), 0, data, 20, 4);

			return data;
		} catch (Exception e) {
			throw new HeaderBytesException("Error while generating the bytes: " + e.getMessage());
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[EthernetFrameData]: ");
		sb.append("Length: ");
		sb.append(getLength());
		sb.append(", Source MAC address: ");
		sb.append(getSrcMac().toString());
		sb.append(", Destination MAC address: ");
		sb.append(getDstMac().toString());
		sb.append(", Type: ");
		sb.append(getType());

		return sb.toString();
	}
}
