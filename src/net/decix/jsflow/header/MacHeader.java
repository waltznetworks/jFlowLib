/*
 * This file is part of jsFlow.
 *
 * Copyright (c) 2009 DE-CIX Management GmbH <http://www.de-cix.net> - All rights
 * reserved.
 * 
 * Author: Thomas King <thomas.king@de-cix.net>
 *
 * This software is licensed under the Apache License, version 2.0. A copy of 
 * the license agreement is included in this distribution.
 */
package net.decix.jsflow.header;

import net.decix.util.HeaderBytesException;
import net.decix.util.HeaderParseException;
import net.decix.util.Utility;

/**
 *  The sampler processor should strip preamble out of Ethernet
 *  frames.
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

public class MacHeader {
	protected long destination;
	protected long source;
	protected int type;
	protected byte offcut[];

	public long getDestination() {
		return destination;
	}

	public long getSource() {
		return source;
	}

	public int getType() {
		return type;
	}

	public void setDestination(long destination) {
		this.destination = destination;
	}

	public void setSource(long source) {
		this.source = source;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void setOffCut(byte offcut[]) {
		this.offcut = offcut;
	}

	public static MacHeader parse(byte data[]) throws HeaderParseException {
		try {
			if (data.length < 14) throw new HeaderParseException("Data array too short.");
			
			if ((data[12] == (byte) (0x81 & 0xFF)) && (data[13] == (byte) (0x00 & 0xFF))) {
				return TaggedMacHeader.parse(data);
			}
			
			MacHeader m = new MacHeader();
			// destination
			byte destination[] = new byte[6];
			System.arraycopy(data, 0, destination, 0, 6);
			m.setDestination(Utility.sixBytesToLong(destination));
			// source
			byte source[] = new byte[6];
			System.arraycopy(data, 6, source, 0, 6);
			m.setSource(Utility.sixBytesToLong(source));
			// type
			byte type[] = new byte[2];
			System.arraycopy(data, 12, type, 0, 2);
			m.setType(Utility.twoBytesToInteger(type));
			// offcut
			byte offcut[] = new byte[data.length - 14];
			System.arraycopy(data, 14, offcut, 0, data.length - 14);
			m.setOffCut(offcut);
			return m;
		} catch (Exception e) {
			throw new HeaderParseException("Parse error: " + e.getMessage());
		}		
	}
	
	public byte[] getBytes() throws HeaderBytesException {
		try {
			byte[] data = new byte[14 + offcut.length];
			// destination
			System.arraycopy(Utility.longToSixBytes(destination), 0, data, 0, 6);
			// source
			System.arraycopy(Utility.longToSixBytes(source), 0, data, 6, 6);
			// type
			System.arraycopy(Utility.integerToTwoBytes(type), 0, data, 12, 2);
			// offcut
			System.arraycopy(offcut, 0, data, 14, offcut.length);
			
			return data;
		} catch (Exception e) {
			throw new HeaderBytesException("Error while generating the bytes: " + e.getMessage());
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[MacHeader]: ");
		sb.append(", Destination: ");
		sb.append(getDestination());
		sb.append(", Source: ");
		sb.append(getSource());
		sb.append(", Type: ");
		sb.append(getType());
		sb.append(", OFFCUT: ");
		sb.append(offcut.length);
		
		return sb.toString();
	}
}
