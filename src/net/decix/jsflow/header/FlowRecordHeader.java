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
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |       data format: enterprise         |  data format: format  |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                        flow data length                       |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  S                           flow data                           S
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 *  enterprise = 0 --> standard
 *             > 0 --> vendor specified format
 *  format = 1|2|3|4|5|1001|1002|1003|1004|1005|1006|1007|1008|1009|
 *           1010|1011|1012
 *
 * @author Hans Yu
 *
 */

public class FlowRecordHeader {
	public static final int RAW_PACKET_HEADER = 1;
	public static final int ETHERNET_FRAME_DATA = 2;
	public static final int IPV4_DATA = 3;
	public static final int IPV6_DATA = 4;
	public static final int EXTENDED_SWITCH_DATA = 1001;
	public static final int EXTENDED_ROUTER_DATA = 1002;
	public static final int EXTENDED_GATEWAY_DATA = 1003;
	public static final int EXTENDED_USER_DATA = 1004;
	public static final int EXTENDED_URL_DATA = 1005;
	public static final int EXTENDED_MPLS_DATA = 1006;
	public static final int EXTENDED_NAT_DATA = 1007;
	public static final int EXTENDED_MPLS_TUNNEL = 1008;
	public static final int EXTENDED_MPLS_VC = 1009;
	public static final int EXTENDED_MPLS_FEC = 1010;
	public static final int EXTENDED_MPLS_LVP_FEC = 1011;
	public static final int EXTENDED_VLAN_TUNNEL = 1012;

	private long flowDataFormat; // 20 bit enterprise & 12 bit format; standard enterprise 0, format 1, 2, 3, 4, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012 
	private long flowDataLength; // in byte
	
	private RawPacketHeader rawPacket;
	private EthernetFrameData ethernetFrameData;
	private IPv4Data ipv4Data;
	
	public FlowRecordHeader() {
	}

	public long getFlowDataFormat() {
		return flowDataFormat;
	}

	public long getFlowDataLength() {
		return flowDataLength;
	}

	public void setFlowDataFormat(long flowDataFormat) {
		this.flowDataFormat = flowDataFormat;
	}

	public void setFlowDataLength(long flowDataLength) {
		this.flowDataLength = flowDataLength;
	}
	
	public void setRawPacketHeader(RawPacketHeader rawPacket) {
		this.rawPacket = rawPacket;
	}

	public void setEthernetFrameData(EthernetFrameData ethernetFrameData) {
		this.ethernetFrameData = ethernetFrameData;
	}

	public void setIpv4Data(IPv4Data ipv4Data) {
		this.ipv4Data = ipv4Data;
	}
	
	public RawPacketHeader getRawPacketHeader() {
		return rawPacket;
	}

	public EthernetFrameData getEthernetFrameData() { return ethernetFrameData; }

	public IPv4Data getIpv4Data() {
		return ipv4Data;
	}
	
	public static FlowRecordHeader parse(byte[] data) throws HeaderParseException {
		try {
			if (data.length < 8) throw new HeaderParseException("Data array too short.");
			FlowRecordHeader frh = new FlowRecordHeader();
			// format
			byte[] format = new byte[4];
			System.arraycopy(data, 0, format, 0, 4);
			frh.setFlowDataFormat(Utility.fourBytesToLong(format));
			// length
			byte[] length = new byte[4];
			System.arraycopy(data, 4, length, 0, 4);
			frh.setFlowDataLength(Utility.fourBytesToLong(length));

			byte[] subData = new byte[(int) frh.getFlowDataLength()];
			System.arraycopy(data, 8, subData, 0, (int) frh.getFlowDataLength());

			if (true) {
				System.out.println("sFlow flow record header info:");
				System.out.println("    flow data format: " + frh.getFlowDataFormat());
				System.out.println("    flow data length: " + frh.getFlowDataLength());
			}

			/* According to sFlow v5 specification:
			 *   The preferred format for reporting packet header information is the
			 *   sampled_header (RAW_PACKET_HEADER). However, if the packet header
			 *   is not available to the sampling process, then one or more of
			 *   sampled_ethernet (ETHERNET_FRAME_DATA), sampled_ipv4 (IPV4_DATA),
			 *   sampled_ipv6 (IPV6_DATA) may be used.
			 *
			 *   Hence, if the packet header is available, then a flow sample will
			 *   contain only one flow record and that only record is the
			 *   sampled_header (RAW_PACKET_HEADER). Only if the packet header is
			 *   not available will the sampling process export more than one flow
			 *   record in a flow sample.
			 */
			if (frh.getFlowDataFormat() == RAW_PACKET_HEADER) {
				RawPacketHeader rph = RawPacketHeader.parse(subData);
				frh.setRawPacketHeader(rph);
			} else if (frh.getFlowDataFormat() == ETHERNET_FRAME_DATA) {
				EthernetFrameData efd = EthernetFrameData.parse(subData);
				frh.setEthernetFrameData(efd);
			} else if (frh.getFlowDataFormat() == IPV4_DATA) {
				IPv4Data i4d = IPv4Data.parse(subData);
				frh.setIpv4Data(i4d);
			} else {
				System.err.println("Flow data format not yet supported: " + frh.getFlowDataFormat());
			}

			return frh;
		} catch (Exception e) {
			throw new HeaderParseException("Error parsing flow record: " + e.getMessage());
		}
	}
	
	public byte[] getBytes() throws HeaderBytesException {
		try {
			byte[] rawPacketBytes = rawPacket.getBytes();
			byte[] data = new byte[8 + rawPacketBytes.length];
			// format
			System.arraycopy(Utility.longToFourBytes(flowDataFormat), 0, data, 0, 4);
			// length
			System.arraycopy(Utility.longToFourBytes(flowDataLength), 0, data, 4, 4);
			
			// raw packet header
			System.arraycopy(rawPacketBytes, 0, data, 8, rawPacketBytes.length);
			return data;
		} catch (Exception e) {
			throw new HeaderBytesException("Error while generating the bytes: " + e.getMessage());
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[FlowRecordHeader]:");
		sb.append("Format: ");
		sb.append(getFlowDataFormat());
		sb.append(", Length: ");
		sb.append(getFlowDataLength());
		sb.append(", ");
		sb.append(getRawPacketHeader());
		
		return sb.toString();
	}
}
