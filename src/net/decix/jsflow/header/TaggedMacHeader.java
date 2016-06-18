package net.decix.jsflow.header;

import net.decix.util.HeaderBytesException;
import net.decix.util.HeaderParseException;
import net.decix.util.MacAddress;
import net.decix.util.Utility;

/**
 *  The sampler processor should strip preamble out of Ethernet
 *  frames, resulting into the following format:
 *
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                   destination MAC address                     |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |    destination MAC address    |       source MAC address      |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                     source MAC address                        |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |    tag protocol ID (0x8100)   |    tag control information    |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |         Ethernet type         |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 *  Tag protocol ID (TPID) and tag control information (TCI) are
 *  802.1Q VLAN tags. If not used, these two columns can be skipped.
 *  So a standard Ethernet frame is 14 bytes long and the tagged
 *  version is 18 bytes long.
 *
 *  We can tell which network layer protocol the frame is carrying
 *  by reading Ethernet type field.
 *
 * @author Hans Yu
 *
 */

public class TaggedMacHeader extends MacHeader {
	// vlan tag
	private int tpid;
	private int tci;
	
	public int getTCI() {
		return tci;
	}
	
	public int getTpid() {
		return tpid;
	}

	public void setTpid(int tpid) {
		this.tpid = tpid;
	}
	
	public void setTCI(int tci) {
		this.tci = tci;
	}
	
	public static TaggedMacHeader parse(byte data[]) throws HeaderParseException {
		try {
			if (data.length < 18) throw new HeaderParseException("Data array too short.");	
			TaggedMacHeader tmh = new TaggedMacHeader();
			// destination
			byte dstMac[] = new byte[6];
			System.arraycopy(data, 0, dstMac, 0, 6);
			tmh.setDstMac(new MacAddress(dstMac));
			// source
			byte srcMac[] = new byte[6];
			System.arraycopy(data, 6, srcMac, 0, 6);
			tmh.setSrcMac(new MacAddress(srcMac));
			// vlan tag
			byte tpid[] = { (byte) 0x81, (byte) 0x00 };
			tmh.tpid = Utility.twoBytesToInteger(tpid);
			
			byte tci[] = new byte[2];
			System.arraycopy(data, 14, tci, 0, 2);
			tmh.setTCI(Utility.twoBytesToInteger(tci));
			// type
			byte type[] = new byte[2];
			System.arraycopy(data, 16, type, 0, 2);
			tmh.setType(Utility.twoBytesToInteger(type));
			// payload
			byte payload[] = new byte[data.length - 18];
			System.arraycopy(data, 18, payload, 0, data.length - 18);
			tmh.setPayload(payload);

			if (true) {
				System.out.println("sFlow flow record tagged MAC header info:");
				System.out.println("    Tagged MAC header destination: " + tmh.getDstMac().toString());
				System.out.println("    Tagged MAC header source: " + tmh.getSrcMac().toString());
				System.out.println("    Tagged MAC header tagged protocol ID: " + tmh.getTpid());
				System.out.println("    Tagged MAC header tagged control info: " + tmh.getTCI());
				System.out.println("    Tagged MAC header type: " + tmh.getType());
			}

			if (tmh.getType() == TYPE_IPV4) {
				IPv4Header i4h = IPv4Header.parse(payload);
				tmh.setIpv4Header(i4h);
			}

			return tmh;
		} catch (Exception e) {
			throw new HeaderParseException("Error parsing tagged MAC header: " + e.getMessage());
		}		
	}
	
	public byte[] getBytes() throws HeaderBytesException {
		try {
			byte[] data = new byte[18 + payload.length];
			// destination
			System.arraycopy(dstMac.getBytes(), 0, data, 0, 6);
			// source
			System.arraycopy(srcMac.getBytes(), 0, data, 6, 6);
			// vlan tag
			System.arraycopy(Utility.integerToTwoBytes(tpid), 0, data, 12, 2);
			System.arraycopy(Utility.integerToTwoBytes(tci), 0, data, 14, 2);
			// type
			System.arraycopy(Utility.integerToTwoBytes(type), 0, data, 16, 2);
			// payload
			System.arraycopy(payload, 0, data, 18, payload.length);
			
			return data;
		} catch (Exception e) {
			throw new HeaderBytesException("Error while generating the bytes: " + e.getMessage());
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[TaggedMacHeader]: ");
		sb.append(super.toString());
		sb.append(", TPID(VLAN): ");
		sb.append(getTpid());
		sb.append(", TCI(VLAN): ");
		sb.append(getTCI());
		
		return sb.toString();
	}
}
