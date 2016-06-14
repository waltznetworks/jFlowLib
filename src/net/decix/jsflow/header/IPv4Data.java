package net.decix.jsflow.header;

import net.decix.util.HeaderBytesException;
import net.decix.util.HeaderParseException;
import net.decix.util.Address;
import net.decix.util.Utility;

/**
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                       IP packet length                        |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                   transport layer protocol                    |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                       source IP address                       |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                     destination IP address                    |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                           source port                         |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                        destination port                       |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                            TCP flags                          |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                         type of service                       |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 * @author Hans Yu
 */
public class IPv4Data {
	private long length;
	private long protocol;
	private Address srcIpAddr;
	private Address dstIpAddr;
	private long srcPort;
	private long dstPort;
	private long tcpFlags;
	private long tos;

	public void setLength(long packetLength) { this.length = packetLength; }

	public void setProtocol(long protocol) { this.protocol = protocol; }

	public void setSrcIpAddr(Address srcIpAddr) { this.srcIpAddr = srcIpAddr; }

	public void setDstIpAddr(Address dstIpAddr) { this.dstIpAddr = dstIpAddr; }

	public void setSrcPort(long srcPort) { this.srcPort = srcPort; }

	public void setDstPort(long dstPort) { this.dstPort = dstPort; }

	public void setTcpFlags(long tcpFlags) { this.tcpFlags = tcpFlags; }

	public void setTos(long tos) { this.tos = tos; }

	public long getLength() { return length; }

	public long getProtocol() { return protocol; }

	public Address getSrcIpAddr() { return srcIpAddr; }

	public Address getDstIpAddr() { return dstIpAddr; }

	public long getSrcPort() { return srcPort; }

	public long getDstPort() { return dstPort; }

	public long getTcpFlags() { return tcpFlags; }

	public long getTos() { return tos; }

	public static IPv4Data parse(byte[] data) throws HeaderParseException {
		try {
			if (data.length < 32) throw new HeaderParseException("IPv4 data too short.");
			IPv4Data i4d = new IPv4Data();
			// length
			byte[] length = new byte[4];
			System.arraycopy(data, 0, length, 0, 4);
			i4d.setLength(Utility.fourBytesToLong(length));
			// protocol
			byte[] protocol = new byte[4];
			System.arraycopy(data, 4, protocol, 0, 4);
			i4d.setProtocol(Utility.fourBytesToLong(protocol));
			// source IP address
			byte[] srcIpAddr = new byte[4];
			System.arraycopy(data, 8, srcIpAddr, 0, 4);
			i4d.setSrcIpAddr(new Address(srcIpAddr));
			// destination IP address
			byte[] dstIpAddr = new byte[4];
			System.arraycopy(data, 12, dstIpAddr, 0, 4);
			i4d.setDstIpAddr(new Address(dstIpAddr));
			// source port
			byte[] srcPort = new byte[4];
			System.arraycopy(data, 16, srcPort, 0, 4);
			i4d.setSrcPort(Utility.fourBytesToLong(srcPort));
			// destination port
			byte[] dstPort = new byte[4];
			System.arraycopy(data, 20, dstPort, 0, 4);
			i4d.setDstPort(Utility.fourBytesToLong(dstPort));
			// TCP flags
			byte[] tcpFlags = new byte[4];
			System.arraycopy(data, 24, tcpFlags, 0, 4);
			i4d.setTcpFlags(Utility.fourBytesToLong(tcpFlags));
			// type of service
			byte[] tos = new byte[4];
			System.arraycopy(data, 28, tos, 0, 4);
			i4d.setTos(Utility.fourBytesToLong(tos));

			return i4d;
		} catch (Exception e) {
			throw new HeaderParseException("Error parsing IPv4 data: " + e.getMessage());
		}
	}

	public byte[] getBytes() throws HeaderBytesException {
		try {
			byte[] data = new byte[32];
			// length
			System.arraycopy(Utility.longToFourBytes(length), 0, data, 0, 4);
			// protocol
			System.arraycopy(Utility.longToFourBytes(protocol), 0, data, 4, 4);
			// source IP address
			System.arraycopy(srcIpAddr.getBytes(), 0, data, 8, 4);
			// destination IP address
			System.arraycopy(dstIpAddr.getBytes(), 0, data, 12, 4);
			// source port
			System.arraycopy(Utility.longToFourBytes(srcPort), 0, data, 16, 4);
			// destination port
			System.arraycopy(Utility.longToFourBytes(dstPort), 0, data, 20, 4);
			// TCP flags
			System.arraycopy(Utility.longToFourBytes(tcpFlags), 0, data, 24, 4);
			// type os service
			System.arraycopy(Utility.longToFourBytes(tos), 0, data, 28, 4);

			return data;
		} catch (Exception e) {
			throw new HeaderBytesException("Error while generating the bytes: " + e.getMessage());
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[IPv4Data]: ");
		sb.append("Length: ");
		sb.append(getLength());
		sb.append(", Protocol: ");
		sb.append(getProtocol());
		sb.append(", Source IP address: ");
		sb.append(getSrcIpAddr().toString());
		sb.append(", Destination IP address: ");
		sb.append(getDstIpAddr().toString());
		sb.append(", Source port: ");
		sb.append(getSrcPort());
		sb.append(", Destination port: ");
		sb.append(getDstPort());
		sb.append(", TCP flags: ");
		sb.append(getTcpFlags());
		sb.append(", Type of service: ");
		sb.append(getTos());

		return sb.toString();
	}
}
