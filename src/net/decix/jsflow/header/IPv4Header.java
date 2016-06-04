/*
 * This file is part of jsFlow.
 *
 * Copyright (c) 2016 Waltznetworks - All rights reserved.
 * 
 * Author: Linqi Guo <linqi@waltznetworks.com>
 *
 * This software is licensed under the Apache License, version 2.0. A copy of 
 * the license agreement is included in this distribution.
 */
package net.decix.jsflow.header;

import net.decix.util.HeaderBytesException;
import net.decix.util.HeaderParseException;
import net.decix.util.Utility;

public class IPv4Header {
	protected int protocol;
	protected long dstIP;
	protected long srcIP;
	protected int srcPort;
	protected int dstPort;
	protected int tcpFlags;
	protected int tos;
	protected byte offcut[];

	public int getProtocol() {
		return protocol;
	}

	public long getSrcIP() {
		return srcIP;
	}

	public long getDstIP() {
		return dstIP;
	}
	
	public int getSrcPort() {
		return srcPort;
	}
	
	public int getDstPort() {
		return dstPort;
	}

	public int getTcpFlags() {
		return tcpFlags;
	}
	
	public int getTos() {
		return tos;
	}

	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	
	public void setSrcIP(long srcIP) {
		this.srcIP = srcIP;
	}
	
	public void setDstIP(long dstIP) {
		this.dstIP = dstIP;
	}

	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}
	
	public void setDstPort(int dstPort) {
		this.dstPort = dstPort;
	}
	
	public void setTcpFlags(int tcpFlags) {
		this.tcpFlags = tcpFlags;
	}
	
	public void setTos(int tos) {
		this.tos = tos;
	}

	public void setOffCut(byte offcut[]) {
		this.offcut = offcut;
	}

	public static IPv4Header parse(byte data[]) throws HeaderParseException {
		try {
			if (data.length < 18) throw new HeaderParseException("Data array too short.");
			
			IPv4Header ip = new IPv4Header();
			// protocol
			byte protocol[] = new byte[2];
			System.arraycopy(data, 0, protocol, 0, 2);
			ip.setProtocol(Utility.twoBytesToInteger(protocol));
			// srcIP
			byte srcIP[] = new byte[4];
			System.arraycopy(data, 2, srcIP, 0, 4);
			ip.setSrcIP(Utility.fourBytesToLong(srcIP));
			// dstIP
			byte dstIP[] = new byte[4];
			System.arraycopy(data, 6, dstIP, 0, 4);
			ip.setDstIP(Utility.fourBytesToLong(dstIP));
			// src_port
			byte srcPort[] = new byte[2];
			System.arraycopy(data, 10, srcPort, 0, 2);
			ip.setSrcPort(Utility.twoBytesToInteger(srcPort));
			// dst_port
			byte dstPort[] = new byte[2];
			System.arraycopy(data, 12, dstPort, 0, 2);
			ip.setDstPort(Utility.twoBytesToInteger(dstPort));
			// tcp_flags
			byte tcpFlags[] = new byte[2];
			System.arraycopy(data, 14, tcpFlags, 0, 2);
			ip.setTcpFlags(Utility.twoBytesToInteger(tcpFlags));
			// tos
			byte tos[] = new byte[2];
			System.arraycopy(data, 16, tos, 0, 2);
			ip.setTos(Utility.twoBytesToInteger(tos));
			// offcut
			byte offcut[] = new byte[data.length - 18];
			System.arraycopy(data, 18, offcut, 0, data.length - 18);
			ip.setOffCut(offcut);
			return ip;
		} catch (Exception e) {
			throw new HeaderParseException("Parse error: " + e.getMessage());
		}		
	}
	
	public byte[] getBytes() throws HeaderBytesException {
		try {
			byte[] data = new byte[18 + offcut.length];
			// protocol
			System.arraycopy(Utility.integerToTwoBytes(protocol), 0, data, 0, 2);
			// srcIP
			System.arraycopy(Utility.longToFourBytes(srcIP), 0, data, 2, 4);
			// dstIP
			System.arraycopy(Utility.longToFourBytes(dstIP), 0, data, 6, 4);
			// src_port
			System.arraycopy(Utility.integerToTwoBytes(srcPort), 0, data, 10, 2);
			// dst_port
			System.arraycopy(Utility.integerToTwoBytes(dstPort), 0, data, 12, 2);
			// tcp_flags
 		    System.arraycopy(Utility.integerToTwoBytes(tcpFlags), 0, data, 14, 2);
			// tos
			System.arraycopy(Utility.integerToTwoBytes(tos), 0, data, 16, 2);
            // offcut
			System.arraycopy(offcut, 0, data, 18, offcut.length);
			
			return data;
		} catch (Exception e) {
			throw new HeaderBytesException("Error while generating the bytes: " + e.getMessage());
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[IPv4Header]: ");
		sb.append(", Protocol: ");
		sb.append(getProtocol());
		sb.append(", Source IP: ");
		sb.append(getSrcIP());
		sb.append(", Destination IP: ");
		sb.append(getDstIP());
		sb.append(", Source Port: ");
		sb.append(getSrcPort());
		sb.append(", Destination Port: ");
		sb.append(getDstPort());
		sb.append(", tcpFlags: ");
		sb.append(getTcpFlags());
		sb.append(", type of service: ");
		sb.append(getTos());
		sb.append(", OFFCUT: ");
		sb.append(offcut.length);
		
		return sb.toString();
	}
}
