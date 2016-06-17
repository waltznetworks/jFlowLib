package net.decix.jsflow.header;

import net.decix.util.Address;
import net.decix.util.HeaderBytesException;
import net.decix.util.HeaderParseException;
import net.decix.util.Utility;

/**
 *
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |  ver. |  IHL  |type of service|         total length          |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |        identification         |flags|     fragment offset     |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  | time to leave |    protocol   |       header checksum         |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                         source address                        |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                      destination address                      |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |               IP options and padding (not common)             |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 *
 * @author Hans Yu
 *
 */
public class IPv4Header {
	protected static int ipVersion;     // should always be 4
	protected static int headerLength;  // RFC 791: 5 * 32 bits <= headerLength <= 15 * 32 bits
	protected static int tos;
	protected static int totalLength;
	protected static int id;
	protected static byte[] flagsAndFragmentOffset;
	protected static int ttl;
	protected static int protocol;      // RFC 790
	protected static int checksum;
	protected static Address srcIp;
	protected static Address dstIp;
	// 20 bytes so far
	protected static byte[] optionsAndPadding;
	// 24 bytes at most
	protected static byte[] payload;

	public IPv4Header() {
	}

	public void setVersion(int version) { this.ipVersion = version;	}

	public void setHeaderLength(int headerLength) { this.headerLength = headerLength; }

	public void setTos(int tos) { this.tos = tos; }

	public void setTotalLength(int totalLength) { this.totalLength = totalLength; }

	public void setId(int id) { this.id = id; }

	public void setFlagsAndFragmentOffset(byte[] flagsAndFragmentOffset) { this.flagsAndFragmentOffset = flagsAndFragmentOffset; }

	public void setTtl(int ttl) { this.ttl = ttl; }

	public void setProtocol(int protocol) { this.protocol = protocol; }

	public void setChecksum(int checksum) { this.checksum = checksum; }

	public void setSrcIp(Address srcIp) { this.srcIp = srcIp; }

	public void setDstIp(Address dstIp) { this.dstIp = dstIp; }

	public void setOptionsAndPadding(byte[] optionsAndPadding) { this.optionsAndPadding = optionsAndPadding; }

	public void setPayload(byte[] payload) { this.payload = payload; }

	public int getVersion() { return ipVersion; }

	public int getHeaderLength() { return headerLength; }

	public int getTos() { return tos; }

	public int getTotalLength() { return totalLength; }

	public int getId() { return id; }

	public byte[] getFlagsAndFragmentOffset() { return flagsAndFragmentOffset; }

	public int getTtl() { return ttl; }

	public int getProtocol() { return protocol; }

	public int getChecksum() { return checksum; }

	public Address getSrcIp() { return srcIp; }

	public Address getDstIp() { return dstIp; }

	public byte[] getOptionsAndPadding() { return optionsAndPadding; }

	public byte[] getPayload() { return payload; }

	public static IPv4Header parse(byte[] data) throws HeaderParseException {
		try {
			if (data.length < 20) throw new HeaderParseException("Data array too short.");
			IPv4Header i4h = new IPv4Header();

			byte ipVersionAndHeaderLength = data[0];
			// version
			i4h.setVersion(Utility.oneByteToInteger(ipVersionAndHeaderLength) / 4);
			// header length
			i4h.setHeaderLength(Utility.oneByteToInteger(ipVersionAndHeaderLength) % 4);
			// tos
			byte tos = data[1];
			i4h.setTos(Utility.oneByteToInteger(tos));
			// total length (packet length)
			byte[] totalLength = new byte[2];
			System.arraycopy(data, 2, totalLength, 0, 2);
			i4h.setTotalLength(Utility.twoBytesToInteger(totalLength));
			// identification
			byte[] identification = new byte[2];
			System.arraycopy(data, 4, identification, 0, 2);
			i4h.setId(Utility.twoBytesToInteger(identification));
			// flags and fragment offset
			byte[] flagsAndFragmentOffset = new byte[2];
			System.arraycopy(data, 6, flagsAndFragmentOffset, 0, 2);
			i4h.setFlagsAndFragmentOffset(flagsAndFragmentOffset);
			// ttl
			byte ttl = data[8];
			i4h.setTtl(Utility.oneByteToInteger(ttl));
			// protocol
			byte protocol = data[9];
			i4h.setProtocol(Utility.oneByteToInteger(protocol));
			// checksum
			byte[] checksum = new byte[2];
			System.arraycopy(data, 10, checksum, 0, 2);
			i4h.setChecksum(Utility.twoBytesToInteger(checksum));
			// source IP
			byte[] srcIp = new byte[4];
			System.arraycopy(data, 12, srcIp, 0, 4);
			i4h.setSrcIp(new Address(srcIp));
			// destination IP
			byte[] dstIp = new byte[4];
			System.arraycopy(data, 16, dstIp, 0, 4);
			i4h.setDstIp(new Address(dstIp));
			// IP options and padding
			int offset = 20;
			if (i4h.getHeaderLength() > 20) {
				byte[] optionsAndPadding = new byte[4];
				System.arraycopy(data, 20, optionsAndPadding, 0, 4);
				i4h.setOptionsAndPadding(optionsAndPadding);
				offset = 24;
			}

			if (true) {
				System.out.println("sFlow flow record IPv4 header info:");
				System.out.println("    IPv4 header version: " + i4h.getVersion());
				System.out.println("    IPv4 header length: " + i4h.getHeaderLength());
				System.out.println("    IPv4 type of service: " + i4h.getTos());
				System.out.println("    IPv4 packet length: " + i4h.getTotalLength());
				System.out.println("    IPv4 packet ID: " + i4h.getId());
				System.out.println("    IPv4 packet TTL: " + i4h.getTtl());
				System.out.println("    IPv4 packet protocol: " + i4h.getProtocol());
				System.out.println("    IPv4 packet checksum: " + i4h.getChecksum());
				System.out.println("    IPv4 packet source IP: " + i4h.getSrcIp().toString());
				System.out.println("    IPv4 packet destination IP: " + i4h.getDstIp().toString());
			}
			// payload
			byte[] payload = new byte[data.length - offset];
			System.arraycopy(data, offset, payload, 0, data.length - offset);
			i4h.setPayload(payload);

			return i4h;
		} catch (Exception e) {
			throw new HeaderParseException("Error parsing IPv4 header: " + e.getMessage());
		}
	}

	public byte[] getBytes() throws HeaderBytesException {
		try {
			int offset = 20;
			if (headerLength > 20)
				offset = 24;

			byte[] data = new byte[offset + payload.length];

			// version
			// header length
			data[0] = Utility.integerToOneByte(ipVersion * 4 + headerLength);
			// tos
			data[1] = Utility.integerToOneByte(tos);
			// total length (packet length)
			System.arraycopy(Utility.integerToTwoBytes(totalLength), 0, data, 2, 2);
			// identification
			System.arraycopy(Utility.integerToTwoBytes(id), 0, data, 4, 2);
			// flags and fragment offset
			System.arraycopy(flagsAndFragmentOffset, 0, data, 6, 2);
			// ttl
			data[8] = Utility.integerToOneByte(ttl);
			// protocol
			data[9] = Utility.integerToOneByte(protocol);
			// checksum
			System.arraycopy(Utility.integerToTwoBytes(checksum), 0, data, 10, 2);
			// source IP
			System.arraycopy(srcIp.getBytes(), 0, data, 12, 4);
			// destination IP
			System.arraycopy(dstIp.getBytes(), 0, data, 16, 4);
			// IP options and padding
			if (offset == 24)
				System.arraycopy(optionsAndPadding, 0, data, 20, 4);
			// payload
			System.arraycopy(payload, 0, data, offset, payload.length);

			return data;
		} catch (Exception e) {
			throw new HeaderBytesException("Error while generating the bytes: " + e.getMessage());
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[IPv4 header]: ");
		sb.append(", Version: ");
		sb.append(getVersion());
		sb.append(", Header length: ");
		sb.append(getHeaderLength());
		sb.append(", Type of service: ");
		sb.append(getTos());
		sb.append(", Packet length: ");
		sb.append(getTotalLength());
		sb.append(", Identification: ");
		sb.append(getId());
		sb.append(", Ttl: ");
		sb.append(getTtl());
		sb.append(", Protocol: ");
		sb.append(getProtocol());
		sb.append(", Checksum: ");
		sb.append(getChecksum());
		sb.append(", Source IP: ");
		sb.append(getSrcIp().toString());
		sb.append(", Destination IP: ");
		sb.append(getDstIp().toString());

		return sb.toString();
	}
}
