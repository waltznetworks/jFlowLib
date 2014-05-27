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
package net.decix.jsflow;

import net.decix.jsflow.header.SflowHeader;
import junit.framework.TestCase;

public class SflowHeaderTest extends TestCase {
	private byte[] data1 = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0xc0, (byte) 0xa8, (byte) 0x66, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x07,
			 (byte) 0x15, (byte) 0xb9, (byte) 0x28, (byte) 0x85, (byte) 0x2c, (byte) 0xfe, (byte) 0x8b, (byte) 0x68, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc4, (byte) 0x4f, (byte) 0x31, (byte) 0xef, (byte) 0xc0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x05, (byte) 0x00, (byte) 0x3b,
			 (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x11, (byte) 0xc7, (byte) 0x87, (byte) 0xae, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			 (byte) 0x14, (byte) 0x11, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x05, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x90, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x05, (byte) 0xf2,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x1c, (byte) 0xf9, (byte) 0xad, (byte) 0x79, (byte) 0x32, (byte) 0x00, (byte) 0x1c,
			 (byte) 0xb0, (byte) 0xb4, (byte) 0x4b, (byte) 0x00, (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x05, (byte) 0xdc, (byte) 0x66, (byte) 0x28,
			 (byte) 0x40, (byte) 0x00, (byte) 0x3d, (byte) 0x06, (byte) 0xc1, (byte) 0x0a, (byte) 0xc3, (byte) 0xda, (byte) 0xb5, (byte) 0xca, (byte) 0x5c, (byte) 0x73, (byte) 0x3a, (byte) 0xd1, (byte) 0x00, (byte) 0x50,
			 (byte) 0xc6, (byte) 0x85, (byte) 0xe5, (byte) 0x19, (byte) 0xee, (byte) 0xf5, (byte) 0xd7, (byte) 0x8e, (byte) 0xc6, (byte) 0xe5, (byte) 0x50, (byte) 0x10, (byte) 0x00, (byte) 0x36, (byte) 0x2e, (byte) 0xa5,
			 (byte) 0x00, (byte) 0x00, (byte) 0x29, (byte) 0x02, (byte) 0x3d, (byte) 0x19, (byte) 0x01, (byte) 0x5b, (byte) 0x64, (byte) 0x27, (byte) 0xd4, (byte) 0xc1, (byte) 0x20, (byte) 0x8d, (byte) 0x0b, (byte) 0x30,
			 (byte) 0x9d, (byte) 0x31, (byte) 0x32, (byte) 0x79, (byte) 0x32, (byte) 0x20, (byte) 0xa9, (byte) 0xfc, (byte) 0xe8, (byte) 0xc1, (byte) 0x1e, (byte) 0x38, (byte) 0xe0, (byte) 0xe4, (byte) 0x80, (byte) 0x12,
			 (byte) 0xc4, (byte) 0xc9, (byte) 0x32, (byte) 0x71, (byte) 0x30, (byte) 0xb6, (byte) 0x8f, (byte) 0x15, (byte) 0x2e, (byte) 0x90, (byte) 0x52, (byte) 0x71, (byte) 0x7c, (byte) 0x88, (byte) 0x60, (byte) 0x8b,
			 (byte) 0x5c, (byte) 0xdb, (byte) 0x45, (byte) 0x43, (byte) 0x31, (byte) 0x00, (byte) 0x70, (byte) 0x35, (byte) 0x5f, (byte) 0xd1, (byte) 0x7a, (byte) 0x76, (byte) 0x73, (byte) 0xa3, (byte) 0x2b, (byte) 0x19,
			 (byte) 0x95, (byte) 0x33, (byte) 0x02, (byte) 0x74, (byte) 0x3d, (byte) 0x64, (byte) 0xe5, (byte) 0x62, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc4,
			 (byte) 0x32, (byte) 0x8e, (byte) 0xad, (byte) 0x3a, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x0d, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00,
			 (byte) 0x3a, (byte) 0xc5, (byte) 0x2b, (byte) 0x17, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x06, (byte) 0x05, (byte) 0x00, (byte) 0x3b,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x0d, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x90, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x05, (byte) 0xf2, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x1d, (byte) 0xb5, (byte) 0x28, (byte) 0x70, (byte) 0x2a, (byte) 0x00, (byte) 0x11, (byte) 0x5d, (byte) 0x99, (byte) 0x3c, (byte) 0x00,
			 (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x05, (byte) 0xdc, (byte) 0xe4, (byte) 0xd4, (byte) 0x40, (byte) 0x00, (byte) 0x3e, (byte) 0x06,
			 (byte) 0x84, (byte) 0xbc, (byte) 0x52, (byte) 0x64, (byte) 0xdc, (byte) 0x25, (byte) 0x5d, (byte) 0x53, (byte) 0x41, (byte) 0xae, (byte) 0x00, (byte) 0x50, (byte) 0xd7, (byte) 0x28, (byte) 0xbf, (byte) 0x9f,
			 (byte) 0x5f, (byte) 0x80, (byte) 0x8e, (byte) 0xda, (byte) 0x8f, (byte) 0xfa, (byte) 0x50, (byte) 0x10, (byte) 0x80, (byte) 0x52, (byte) 0xe8, (byte) 0x45, (byte) 0x00, (byte) 0x00, (byte) 0xac, (byte) 0xb8,
			 (byte) 0x1c, (byte) 0x9d, (byte) 0xdc, (byte) 0x0e, (byte) 0xd5, (byte) 0xa8, (byte) 0x70, (byte) 0x60, (byte) 0x50, (byte) 0xaa, (byte) 0xd0, (byte) 0x16, (byte) 0x9b, (byte) 0xf1, (byte) 0x86, (byte) 0x40,
			 (byte) 0x9f, (byte) 0xaf, (byte) 0xe5, (byte) 0xf0, (byte) 0x30, (byte) 0x3c, (byte) 0xd3, (byte) 0x50, (byte) 0xa8, (byte) 0xe7, (byte) 0x28, (byte) 0xa5, (byte) 0x48, (byte) 0x41, (byte) 0xf3, (byte) 0x1a,
			 (byte) 0x88, (byte) 0x22, (byte) 0x90, (byte) 0x42, (byte) 0x4c, (byte) 0xe1, (byte) 0xae, (byte) 0xe3, (byte) 0xf0, (byte) 0x69, (byte) 0xa1, (byte) 0x60, (byte) 0xd8, (byte) 0xf8, (byte) 0x4c, (byte) 0xdb,
			 (byte) 0x10, (byte) 0x98, (byte) 0xae, (byte) 0xcc, (byte) 0xf8, (byte) 0x4d, (byte) 0x06, (byte) 0xd2, (byte) 0x87, (byte) 0x92, (byte) 0xaa, (byte) 0xe3, (byte) 0xb0, (byte) 0x8c, (byte) 0x10, (byte) 0xca,
			 (byte) 0xa1, (byte) 0xb4, (byte) 0x2c, (byte) 0x3a, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x98, (byte) 0x4f, (byte) 0x31, (byte) 0xef, (byte) 0xc1,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x05, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x11, (byte) 0xc7, (byte) 0xbb, (byte) 0x44,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x18, (byte) 0x15, (byte) 0x00, (byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			 (byte) 0x10, (byte) 0x05, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x64,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x56, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x52,
			 (byte) 0x00, (byte) 0x12, (byte) 0xf2, (byte) 0x93, (byte) 0x1a, (byte) 0x00, (byte) 0x00, (byte) 0x14, (byte) 0xf6, (byte) 0xc9, (byte) 0x33, (byte) 0xfc, (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x02,
			 (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0xd9, (byte) 0x0c, (byte) 0x40, (byte) 0x00, (byte) 0x3a, (byte) 0x06, (byte) 0xce, (byte) 0x10, (byte) 0x4d, (byte) 0x6f,
			 (byte) 0x9b, (byte) 0xf1, (byte) 0xc1, (byte) 0xf7, (byte) 0xee, (byte) 0x42, (byte) 0xe3, (byte) 0x5e, (byte) 0xf3, (byte) 0x0e, (byte) 0x3c, (byte) 0x18, (byte) 0xea, (byte) 0x5c, (byte) 0xe5, (byte) 0x28,
			 (byte) 0x89, (byte) 0x55, (byte) 0xb0, (byte) 0x10, (byte) 0x04, (byte) 0x01, (byte) 0x46, (byte) 0x1e, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x08, (byte) 0x0a, (byte) 0x00, (byte) 0x07,
			 (byte) 0xe8, (byte) 0x0e, (byte) 0x00, (byte) 0x11, (byte) 0x15, (byte) 0xfa, (byte) 0x01, (byte) 0x01, (byte) 0x05, (byte) 0x0a, (byte) 0xe5, (byte) 0x28, (byte) 0x8e, (byte) 0xfd, (byte) 0xe5, (byte) 0x28,
			 (byte) 0x9a, (byte) 0x1a, (byte) 0x00, (byte) 0x0c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc4, (byte) 0x32, (byte) 0x8e, (byte) 0xad, (byte) 0x3b,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x0d, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x3a, (byte) 0xc5, (byte) 0x89, (byte) 0x40,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x0d, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			 (byte) 0x10, (byte) 0x0d, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x90,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x05, (byte) 0xf2, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80,
			 (byte) 0x00, (byte) 0x1d, (byte) 0xb5, (byte) 0x28, (byte) 0x70, (byte) 0x2a, (byte) 0x00, (byte) 0x17, (byte) 0xcb, (byte) 0xd9, (byte) 0xb5, (byte) 0x28, (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x02,
			 (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x05, (byte) 0xdc, (byte) 0xbc, (byte) 0xbe, (byte) 0x40, (byte) 0x00, (byte) 0x3e, (byte) 0x06, (byte) 0x7f, (byte) 0xcb, (byte) 0x55, (byte) 0x19,
			 (byte) 0x82, (byte) 0x2e, (byte) 0xc3, (byte) 0x03, (byte) 0x60, (byte) 0x47, (byte) 0xb1, (byte) 0xa1, (byte) 0x00, (byte) 0x19, (byte) 0xd4, (byte) 0x8d, (byte) 0xe2, (byte) 0x69, (byte) 0x39, (byte) 0xaa,
			 (byte) 0x23, (byte) 0x49, (byte) 0x80, (byte) 0x10, (byte) 0x00, (byte) 0x2e, (byte) 0x7d, (byte) 0xe8, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x08, (byte) 0x0a, (byte) 0x5c, (byte) 0x51,
			 (byte) 0xe9, (byte) 0x39, (byte) 0x4e, (byte) 0x21, (byte) 0x49, (byte) 0x6b, (byte) 0x51, (byte) 0x72, (byte) 0x58, (byte) 0x77, (byte) 0x37, (byte) 0x70, (byte) 0x77, (byte) 0x74, (byte) 0x4c, (byte) 0x63,
			 (byte) 0x76, (byte) 0x4a, (byte) 0x49, (byte) 0x7a, (byte) 0x47, (byte) 0x53, (byte) 0x61, (byte) 0x65, (byte) 0x54, (byte) 0x6c, (byte) 0x35, (byte) 0x58, (byte) 0x4a, (byte) 0x79, (byte) 0x57, (byte) 0x59,
			 (byte) 0x31, (byte) 0x47, (byte) 0x75, (byte) 0x37, (byte) 0x47, (byte) 0x2f, (byte) 0x49, (byte) 0x31, (byte) 0x41, (byte) 0x76, (byte) 0x48, (byte) 0x62, (byte) 0x48, (byte) 0x70, (byte) 0x54, (byte) 0x75,
			 (byte) 0x42, (byte) 0x38, (byte) 0x2b, (byte) 0x65, (byte) 0x4e, (byte) 0x37, (byte) 0x5a, (byte) 0x39, (byte) 0x57, (byte) 0x38, (byte) 0x64, (byte) 0x0d, (byte) 0x0a, (byte) 0x61, (byte) 0x71, (byte) 0x41,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc4, (byte) 0x4f, (byte) 0x31, (byte) 0xef, (byte) 0xc2, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			 (byte) 0x10, (byte) 0x05, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x11, (byte) 0xc8, (byte) 0x02, (byte) 0x70, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x04, (byte) 0xc0, (byte) 0x37, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x05, (byte) 0x00, (byte) 0x3b,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x90, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
			 (byte) 0x00, (byte) 0x00, (byte) 0x05, (byte) 0xde, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x0e, (byte) 0xd6, (byte) 0xc9,
			 (byte) 0xa0, (byte) 0x66, (byte) 0x00, (byte) 0x14, (byte) 0x1b, (byte) 0x36, (byte) 0xb0, (byte) 0x00, (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x40,
			 (byte) 0x05, (byte) 0xc8, (byte) 0x60, (byte) 0xdb, (byte) 0x40, (byte) 0x00, (byte) 0x7c, (byte) 0x06, (byte) 0x2e, (byte) 0x0b, (byte) 0x4e, (byte) 0x3f, (byte) 0xe5, (byte) 0x99, (byte) 0x4f, (byte) 0xb7,
			 (byte) 0xe6, (byte) 0x79, (byte) 0x04, (byte) 0xcc, (byte) 0xc8, (byte) 0x66, (byte) 0xf4, (byte) 0x40, (byte) 0xb0, (byte) 0xba, (byte) 0xfa, (byte) 0xcb, (byte) 0xee, (byte) 0x3a, (byte) 0x50, (byte) 0x10,
			 (byte) 0xfa, (byte) 0x6a, (byte) 0x32, (byte) 0xaa, (byte) 0x00, (byte) 0x00, (byte) 0x1a, (byte) 0x63, (byte) 0x8b, (byte) 0xe3, (byte) 0xf6, (byte) 0xe1, (byte) 0x10, (byte) 0x44, (byte) 0x76, (byte) 0x5a,
			 (byte) 0x4c, (byte) 0x1e, (byte) 0xa7, (byte) 0x72, (byte) 0x8f, (byte) 0xa8, (byte) 0x3c, (byte) 0xe9, (byte) 0xed, (byte) 0x1a, (byte) 0xa8, (byte) 0x49, (byte) 0xc4, (byte) 0x5f, (byte) 0x73, (byte) 0x83,
			 (byte) 0xef, (byte) 0xd0, (byte) 0xde, (byte) 0x10, (byte) 0xfa, (byte) 0xbf, (byte) 0x08, (byte) 0xb0, (byte) 0xad, (byte) 0x6e, (byte) 0x0c, (byte) 0x3a, (byte) 0x6c, (byte) 0x1d, (byte) 0xf7, (byte) 0xbb,
			 (byte) 0x2a, (byte) 0xe5, (byte) 0x22, (byte) 0x6c, (byte) 0x21, (byte) 0x0f, (byte) 0x18, (byte) 0x58, (byte) 0x40, (byte) 0x2e, (byte) 0x69, (byte) 0xeb, (byte) 0x6d, (byte) 0xee, (byte) 0xec, (byte) 0x68,
			 (byte) 0xc2, (byte) 0xd7, (byte) 0x23, (byte) 0x42, (byte) 0x62, (byte) 0x5b, (byte) 0x77, (byte) 0x1b, (byte) 0xba, (byte) 0x9f, (byte) 0x36, (byte) 0x63, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc4, (byte) 0x06, (byte) 0x2c, (byte) 0x27, (byte) 0xd6, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x09, (byte) 0x00, (byte) 0x3b,
			 (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x82, (byte) 0x62, (byte) 0x8d, (byte) 0xbf, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			 (byte) 0x12, (byte) 0x05, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x09, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x90, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x05, (byte) 0xa2,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x0a, (byte) 0x8b, (byte) 0x0b, (byte) 0xda, (byte) 0x00, (byte) 0x00, (byte) 0x19,
			 (byte) 0xe2, (byte) 0xb3, (byte) 0xd5, (byte) 0xba, (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x05, (byte) 0x8c, (byte) 0x2f, (byte) 0x3e,
			 (byte) 0x00, (byte) 0x00, (byte) 0x77, (byte) 0x06, (byte) 0x03, (byte) 0xc4, (byte) 0xd9, (byte) 0x72, (byte) 0x50, (byte) 0xe0, (byte) 0xc3, (byte) 0xd4, (byte) 0x1d, (byte) 0x43, (byte) 0x00, (byte) 0x50,
			 (byte) 0x5c, (byte) 0x0d, (byte) 0x57, (byte) 0xc7, (byte) 0xe3, (byte) 0x50, (byte) 0xbe, (byte) 0xb8, (byte) 0xf3, (byte) 0x8f, (byte) 0x50, (byte) 0x10, (byte) 0xfc, (byte) 0x5c, (byte) 0x91, (byte) 0xe7,
			 (byte) 0x00, (byte) 0x00, (byte) 0x62, (byte) 0x32, (byte) 0x39, (byte) 0x6b, (byte) 0x62, (byte) 0x47, (byte) 0x56, (byte) 0x7a, (byte) 0x49, (byte) 0x48, (byte) 0x4e, (byte) 0x68, (byte) 0x62, (byte) 0x47,
			 (byte) 0x46, (byte) 0x6b, (byte) 0x50, (byte) 0x47, (byte) 0x4a, (byte) 0x79, (byte) 0x49, (byte) 0x43, (byte) 0x38, (byte) 0x2b, (byte) 0x54, (byte) 0x43, (byte) 0x78, (byte) 0x53, (byte) 0x50, (byte) 0x43,
			 (byte) 0x39, (byte) 0x77, (byte) 0x50, (byte) 0x67, (byte) 0x30, (byte) 0x4b, (byte) 0x50, (byte) 0x48, (byte) 0x41, (byte) 0x2b, (byte) 0x54, (byte) 0x47, (byte) 0x39, (byte) 0x6f, (byte) 0x61, (byte) 0x57,
			 (byte) 0x78, (byte) 0x6c, (byte) 0x61, (byte) 0x58, (byte) 0x44, (byte) 0x44, (byte) 0x70, (byte) 0x44, (byte) 0x78, (byte) 0x69, (byte) 0x63, (byte) 0x69, (byte) 0x41, (byte) 0x76, (byte) 0x50, (byte) 0x6c,
			 (byte) 0x4e, (byte) 0x68, (byte) 0x62, (byte) 0x47, (byte) 0x31, (byte) 0x76, (byte) 0x62, (byte) 0x69, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x88,
			 (byte) 0x4f, (byte) 0x31, (byte) 0xef, (byte) 0xc3, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x05, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00,
			 (byte) 0x11, (byte) 0xc8, (byte) 0x30, (byte) 0xc7, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1c, (byte) 0x25, (byte) 0x00, (byte) 0x3f,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x05, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x54, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x46, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04,
			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x42, (byte) 0x00, (byte) 0x0e, (byte) 0x39, (byte) 0x01, (byte) 0x15, (byte) 0xa8, (byte) 0x00, (byte) 0x1d, (byte) 0xb5, (byte) 0xa6, (byte) 0x88, (byte) 0xa4,
			 (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x00, (byte) 0x30, (byte) 0x86, (byte) 0xaa, (byte) 0x40, (byte) 0x00, (byte) 0x7a, (byte) 0x06,
			 (byte) 0x83, (byte) 0x82, (byte) 0x54, (byte) 0x36, (byte) 0xe8, (byte) 0x3a, (byte) 0xd5, (byte) 0x17, (byte) 0xe5, (byte) 0x12, (byte) 0x0c, (byte) 0xd5, (byte) 0x01, (byte) 0xbd, (byte) 0xd2, (byte) 0x57,
			 (byte) 0x2b, (byte) 0x57, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x70, (byte) 0x02, (byte) 0x40, (byte) 0x00, (byte) 0x40, (byte) 0x43, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x04,
			 (byte) 0x05, (byte) 0xb4, (byte) 0x01, (byte) 0x01, (byte) 0x04, (byte) 0x02, (byte) 0xaa, (byte) 0xaa
	};
	
	byte data2[] = {
			-20, -72, 76, -20, -53, -63, 57, -25, -39, -69, -89, 16, -77, -111, -26, 
			-91, 40, -28, 33, -76, -41, -8, 87, -40, -84, 38, -9, -71, -18, -8, 
			57, -14, -20, 66, 77, 60, 85, 80, 119, 12, 71, 17, -71, 47, 5, 
			58, -87, -95, 90, -26, -111, -29, 67, -1, 108, 56, -62, 29, 92, -16, 
			-10, -86, 71, 61, 97, -57, 107, 68, 0, 0, 0, 3, 0, 0, 0, 
			-60, 0, 44, -82, 4, 0, 0, 0, 0, 6, 9, 0, 59, 0, 0, 
			64, 0, 43, -108, -126, -115, 0, 0, 0, 0, 0, 0, 0, 0, 6, 
			5, 0, 59, 0, 0, 0, 0, 6, 9, 0, 59, 0, 0, 0, 1, 
			0, 0, 0, 1, 0, 0, 0, -112, 0, 0, 0, 1, 0, 0, 5, 
			-62, 0, 0, 0, 4, 0, 0, 0, -128, 0, 22, -100, -14, -48, 0, 
			0, -48, 4, 22, 104, 0, -127, 0, 0, 2, 8, 0, 69, 0, 5, 
			-84, 7, 113, 64, 0, 126, 6, 85, -112, 79, -115, -96, 3, 84, 51, 
			86, -121, 105, -106, 12, 102, 7, -77, -38, 30, -49, 28, 118, 7, 80, 
			16, -2, -5, 83, 61, 0, 0, 110, -41, -62, -80, -77, 32, 3, -22, 
			-1, -115, -82, 95, -54, 39, 17, -98, -77, -122, 60, 120, 42, 59, 67, 
			50, -50, -83, 83, 115, 21, 54, -100, 31, -17, 86, 2, 107, -18, -128, 
			-1, 106, 6, 90, 53, 32, 114, 44, -61, -107, -118, -40, 113, 43, 41, 
			13, 50, 53, -60, -102, 27, -72, -71, 46, 9, -28, -110, -33, -51, 67, 
			-71, 88, 0, 0, 0, 3, 0, 0, 0, -108, 0, 32, -30, -13, 0, 
			0, 0, 0, 6, 17, 0, 59, 0, 0, 64, 0, 56, -49, 75, -48, 
			0, 0, 0, 0, 0, 0, 0, 0, 6, 5, 0, 59, 0, 0, 0, 
			0, 6, 17, 0, 59, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 
			0, 96, 0, 0, 0, 1, 0, 0, 0, 82, 0, 0, 0, 4, 0, 
			0, 0, 78, 0, 20, 28, -45, 16, 0, 0, 24, 115, -125, 45, 54, 
			-127, 0, 0, 2, 8, 0, 69, 0, 0, 60, -62, 75, 64, 0, 121, 
			6, -112, 125, 89, 111, -111, 37, 82, -101, 113, -61, 10, -47, 122, 112, 
			-17, 78, 69, 48, -33, 88, 20, -103, 80, 24, -1, -1, -44, -106, 0, 
			0, -31, -15, -37, 22, 12, -46, 17, 35, 50, 31, 14, -50, 30, -50, 
			15, -17, 50, -31, 0, -13, 0, 59, 0, 0, 0, 3, 0, 0, 0, 
			-60, 0, 67, -32, -71, 0, 0, 0, 0, 6, 5, 0, 59, 0, 0, 
			64, 0, -8, 86, -65, 32, 0, 0, 0, 0, 0, 0, 0, 0, 8, 
			13, 0, 59, 0, 0, 0, 0, 6, 5, 0, 59, 0, 0, 0, 1, 
			0, 0, 0, 1, 0, 0, 0, -112, 0, 0, 0, 1, 0, 0, 5, 
			-14, 0, 0, 0, 4, 0, 0, 0, -128, 0, 12, -37, -4, 127, 2, 
			0, 12, -50, -38, 123, 64, -127, 0, 0, 2, 8, 0, 69, 0, 5, 
			-36, 50, 74, 64, 0, 58, 6, -122, 0, 86, 55, 12, -106, 78, 97, 
			-47, -93, 0, 80, 6, 121, -105, 13, 120, 40, -62, 85, -78, 24, 80, 
			16, 29, 40, 19, -125, 0, 0, 86, 73, 2, -70, 38, -33, -75, -49, 
			119, 100, 74, 43, 40, 116, 60, -23, -87, -80, 126, 11, 22, 109, 43, 
			-5, 106, 62, 79, -22, -119, 56, -109, -122, 65, 60, 12, 69, 21, -58, 
			-75, 109, -20, -106, 37, 96, -61, -69, 96, 115, -86, 88, 89, 103, -100, 
			103, -21, 49, 82, -124, -73, -119, -28, 46, -99, -86, -15, -17, 0, 11, 
			-123, -99, 0, 0, 0, 3, 0, 0, 0, -60, 0, 76, -114, 68, 0, 
			0, 0, 0, 6, 13, 0, 59, 0, 0, 64, 0, 35, -72, 103, -118, 
			0, 0, 0, 0, 0, 0, 0, 0, 2, 5, 0, 59, 0, 0, 0, 
			0, 6, 13, 0, 59, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 
			0, -112, 0, 0, 0, 1, 0, 0, 0, -103, 0, 0, 0, 4, 0, 
			0, 0, -128, 0, 33, -40, -81, 112, -22, 0, 29, -75, 47, -13, 98, 
			-127, 0, 0, 2, 8, 0, 69, 0, 0, -125, -16, -112, 0, 0, 53, 
			17, 46, 93, 82, -125, -97, -118, 79, -126, 36, -19, 33, -88, -118, -63, 
			0, 111, 106, -77, 100, 49, 58, 97, 100, 50, 58, 105, 100, 50, 48, 
			58, -37, 32, 11, -28, -121, 52, 10, 3, -22, -119, 82, 6, 69, 2, 
			5, 102, -66, -21, -50, -123, 54, 58, 116, 97, 114, 103, 101, 116, 50, 
			48, 58, -37, 106, -2, 26, 28, 118, -16, 83, -121, -29, 83, 74, -100, 
			11, 13, -53, 115, -91, 110, 92, 101, 49, 58, 113, 57, 58, 102, 105, 
			110, 100, 95, 110, 111, 100, 101, 49, 58, 116, 52, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
	};
	
	byte pkt2[] = {
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0xc0, (byte) 0xa8, (byte) 0x66, (byte) 0x02, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x05, (byte) 0x12, (byte) 0xbd, (byte) 0xca, (byte) 0x45, (byte) 0xab, (byte) 0xe6, 
			(byte) 0xc1, (byte) 0xc0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x07, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xac, (byte) 0x00, (byte) 0x00, 
			(byte) 0xd5, (byte) 0xf9, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x09, 
			(byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x34, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x58, (byte) 0x0c, (byte) 0x09, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x54, (byte) 0x0b, 
			(byte) 0xe4, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x1b, (byte) 0xa7, (byte) 0xc5, (byte) 0xc9, (byte) 0x36, 
			(byte) 0x89, (byte) 0x3d, (byte) 0x6b, (byte) 0x83, (byte) 0x33, (byte) 0xc4, (byte) 0x01, (byte) 0x37, 
			(byte) 0xd9, (byte) 0xb3, (byte) 0x00, (byte) 0x30, (byte) 0x64, (byte) 0x03, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x13, (byte) 0xe9, (byte) 0xe3, (byte) 0x22, (byte) 0xf0, 
			(byte) 0x16, (byte) 0x4c, (byte) 0x76, (byte) 0x93, (byte) 0xc5, (byte) 0xae, (byte) 0x3e, (byte) 0x39, 
			(byte) 0x56, (byte) 0x9d, (byte) 0x4c, (byte) 0xb8, (byte) 0xd3, (byte) 0xbf, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x94, (byte) 0x12, (byte) 0x14, (byte) 0xf8, (byte) 0x46, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x11, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, 
			(byte) 0x40, (byte) 0x00, (byte) 0x7c, (byte) 0x58, (byte) 0xca, (byte) 0x72, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1a, (byte) 0x05, 
			(byte) 0x00, (byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x11, 
			(byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x60, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x52, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x4e, (byte) 0x00, (byte) 0x22, 
			(byte) 0x0d, (byte) 0x0c, (byte) 0x2c, (byte) 0x00, (byte) 0x00, (byte) 0x0d, (byte) 0x66, (byte) 0xfc, 
			(byte) 0x04, (byte) 0x00, (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, 
			(byte) 0x45, (byte) 0x00, (byte) 0x00, (byte) 0x3c, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, 
			(byte) 0x3d, (byte) 0x11, (byte) 0xba, (byte) 0x18, (byte) 0xc1, (byte) 0x2f, (byte) 0x54, (byte) 0x04, 
			(byte) 0x5e, (byte) 0x17, (byte) 0x10, (byte) 0x4e, (byte) 0x90, (byte) 0x32, (byte) 0x2c, (byte) 0x3a, 
			(byte) 0x00, (byte) 0x28, (byte) 0xad, (byte) 0xf0, (byte) 0x80, (byte) 0x12, (byte) 0xe4, (byte) 0x26, 
			(byte) 0x00, (byte) 0x01, (byte) 0x0a, (byte) 0xe0, (byte) 0x63, (byte) 0xc3, (byte) 0xcb, (byte) 0xea, 
			(byte) 0x78, (byte) 0x7e, (byte) 0x82, (byte) 0x1b, (byte) 0x00, (byte) 0x6a, (byte) 0xc7, (byte) 0x10, 
			(byte) 0x05, (byte) 0x56, (byte) 0x78, (byte) 0xab, (byte) 0x45, (byte) 0x60, (byte) 0x08, (byte) 0x1a, 
			(byte) 0xde, (byte) 0x78, (byte) 0x06, (byte) 0xd6, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc4, (byte) 0x2a, (byte) 0xbc, 
			(byte) 0xec, (byte) 0x1c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x0d, 
			(byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0xa9, (byte) 0x40, 
			(byte) 0x35, (byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x35, (byte) 0x00, (byte) 0x3f, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x0d, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x90, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x05, (byte) 0xde, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x16, (byte) 0x4d, (byte) 0xca, (byte) 0x62, (byte) 0x9e, 
			(byte) 0x00, (byte) 0x22, (byte) 0x0d, (byte) 0x0c, (byte) 0x2c, (byte) 0x00, (byte) 0x81, (byte) 0x00, 
			(byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x05, (byte) 0xc8, 
			(byte) 0x57, (byte) 0xdd, (byte) 0x40, (byte) 0x00, (byte) 0x7e, (byte) 0x06, (byte) 0x70, (byte) 0xac, 
			(byte) 0x5e, (byte) 0x17, (byte) 0x1f, (byte) 0xa8, (byte) 0x5e, (byte) 0x4e, (byte) 0x52, (byte) 0x99, 
			(byte) 0x00, (byte) 0x50, (byte) 0x10, (byte) 0xc9, (byte) 0x10, (byte) 0x3f, (byte) 0xdd, (byte) 0x90, 
			(byte) 0x9e, (byte) 0xc3, (byte) 0xe4, (byte) 0xd8, (byte) 0x50, (byte) 0x10, (byte) 0xfe, (byte) 0xfb, 
			(byte) 0x33, (byte) 0x83, (byte) 0x00, (byte) 0x00, (byte) 0x39, (byte) 0xab, (byte) 0xc6, (byte) 0xf6, 
			(byte) 0x99, (byte) 0x0b, (byte) 0x2f, (byte) 0x28, (byte) 0x4c, (byte) 0x06, (byte) 0xef, (byte) 0x1e, 
			(byte) 0x74, (byte) 0x74, (byte) 0xa2, (byte) 0xb0, (byte) 0x7e, (byte) 0xf8, (byte) 0x65, (byte) 0x59, 
			(byte) 0x33, (byte) 0xbb, (byte) 0x0e, (byte) 0xd3, (byte) 0x1f, (byte) 0x36, (byte) 0x02, (byte) 0xdf, 
			(byte) 0x59, (byte) 0xdf, (byte) 0x6b, (byte) 0x5b, (byte) 0x48, (byte) 0xd7, (byte) 0xff, (byte) 0xbc, 
			(byte) 0xff, (byte) 0xc4, (byte) 0x9b, (byte) 0xff, (byte) 0x79, (byte) 0x41, (byte) 0x5e, (byte) 0x01, 
			(byte) 0xa5, (byte) 0x64, (byte) 0xc9, (byte) 0x74, (byte) 0xc5, (byte) 0xd6, (byte) 0x00, (byte) 0x27, 
			(byte) 0xa5, (byte) 0xb5, (byte) 0x3a, (byte) 0x8f, (byte) 0x40, (byte) 0xff, (byte) 0x37, (byte) 0xe2, 
			(byte) 0xaf, (byte) 0x5b, (byte) 0x5e, (byte) 0xb5, (byte) 0xbf, (byte) 0xcd, (byte) 0x4c, (byte) 0x70, 
			(byte) 0xa3, (byte) 0xde, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x8c, (byte) 0x2a, (byte) 0xfd, (byte) 0x75, (byte) 0xcf, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x05, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, 
			(byte) 0x40, (byte) 0x00, (byte) 0xbf, (byte) 0x7b, (byte) 0x2a, (byte) 0xd0, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1c, (byte) 0x25, 
			(byte) 0x00, (byte) 0x3f, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x05, 
			(byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x58, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x4a, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x46, (byte) 0x00, (byte) 0x1d, 
			(byte) 0xb5, (byte) 0x20, (byte) 0xf7, (byte) 0xc0, (byte) 0x00, (byte) 0x15, (byte) 0xf9, (byte) 0x99, 
			(byte) 0x37, (byte) 0x40, (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, 
			(byte) 0x45, (byte) 0x00, (byte) 0x00, (byte) 0x34, (byte) 0xce, (byte) 0xa0, (byte) 0x40, (byte) 0x00, 
			(byte) 0x3b, (byte) 0x06, (byte) 0x10, (byte) 0x93, (byte) 0x77, (byte) 0x1b, (byte) 0x3e, (byte) 0xc9, 
			(byte) 0x3e, (byte) 0x02, (byte) 0x6c, (byte) 0xaa, (byte) 0x00, (byte) 0x50, (byte) 0x9f, (byte) 0x42, 
			(byte) 0xea, (byte) 0x10, (byte) 0x2f, (byte) 0xfa, (byte) 0x77, (byte) 0x23, (byte) 0x26, (byte) 0xe9, 
			(byte) 0x80, (byte) 0x11, (byte) 0x00, (byte) 0x36, (byte) 0x5b, (byte) 0xa6, (byte) 0x00, (byte) 0x00, 
			(byte) 0x01, (byte) 0x01, (byte) 0x08, (byte) 0x0a, (byte) 0x36, (byte) 0x7b, (byte) 0xe0, (byte) 0x80, 
			(byte) 0x44, (byte) 0xf4, (byte) 0x06, (byte) 0xb5, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc4, (byte) 0x1a, (byte) 0xcc, 
			(byte) 0x75, (byte) 0xce, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x09, 
			(byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x4c, (byte) 0x96, 
			(byte) 0x6b, (byte) 0xaf, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x16, (byte) 0x35, (byte) 0x00, (byte) 0x3f, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x09, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x90, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x05, (byte) 0xde, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x15, (byte) 0xc7, (byte) 0x00, (byte) 0x08, (byte) 0x00, 
			(byte) 0x00, (byte) 0x17, (byte) 0xcb, (byte) 0xd4, (byte) 0xf9, (byte) 0xa4, (byte) 0x81, (byte) 0x00, 
			(byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x05, (byte) 0xc8, 
			(byte) 0x05, (byte) 0xe5, (byte) 0x40, (byte) 0x00, (byte) 0x7c, (byte) 0x06, (byte) 0x85, (byte) 0xc1, 
			(byte) 0x55, (byte) 0x5b, (byte) 0x95, (byte) 0x0f, (byte) 0x56, (byte) 0x7d, (byte) 0x2c, (byte) 0xa2, 
			(byte) 0x09, (byte) 0xf1, (byte) 0x4f, (byte) 0x44, (byte) 0x94, (byte) 0xc1, (byte) 0x5a, (byte) 0x1c, 
			(byte) 0x0d, (byte) 0xed, (byte) 0xdb, (byte) 0xed, (byte) 0x50, (byte) 0x10, (byte) 0xfd, (byte) 0x2c, 
			(byte) 0x08, (byte) 0x7f, (byte) 0x00, (byte) 0x00, (byte) 0xba, (byte) 0x0b, (byte) 0x70, (byte) 0xfc, 
			(byte) 0x04, (byte) 0x92, (byte) 0xa6, (byte) 0x3e, (byte) 0x68, (byte) 0x59, (byte) 0x7f, (byte) 0xeb, 
			(byte) 0x66, (byte) 0xf8, (byte) 0x78, (byte) 0xe1, (byte) 0xeb, (byte) 0xa0, (byte) 0xaa, (byte) 0xbf, 
			(byte) 0x92, (byte) 0xa6, (byte) 0x7e, (byte) 0x09, (byte) 0xe7, (byte) 0x38, (byte) 0xe4, (byte) 0x2b, 
			(byte) 0x2d, (byte) 0x0a, (byte) 0xdd, (byte) 0xfa, (byte) 0x3d, (byte) 0x66, (byte) 0x98, (byte) 0x3d, 
			(byte) 0xad, (byte) 0x01, (byte) 0x40, (byte) 0x45, (byte) 0x63, (byte) 0xac, (byte) 0x4d, (byte) 0x51, 
			(byte) 0x94, (byte) 0x6f, (byte) 0x6a, (byte) 0x5a, (byte) 0xe2, (byte) 0x00, (byte) 0xff, (byte) 0x1c, 
			(byte) 0x20, (byte) 0xd9, (byte) 0xed, (byte) 0xa8, (byte) 0xa2, (byte) 0xb2, (byte) 0xe5, (byte) 0x5b, 
			(byte) 0xb0, (byte) 0xec, (byte) 0xfd, (byte) 0x3a, (byte) 0xbf, (byte) 0xea, (byte) 0x73, (byte) 0x5a, 
			(byte) 0xb1, (byte) 0xa8, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x84, (byte) 0x12, (byte) 0x14, (byte) 0xf8, (byte) 0x47, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x11, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, 
			(byte) 0x40, (byte) 0x00, (byte) 0x7c, (byte) 0x58, (byte) 0xfc, (byte) 0x93, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x09, 
			(byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x11, 
			(byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x44, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x22, 
			(byte) 0x0d, (byte) 0x0c, (byte) 0x2c, (byte) 0x00, (byte) 0x00, (byte) 0x0b, (byte) 0x60, (byte) 0x91, 
			(byte) 0xe4, (byte) 0x00, (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, 
			(byte) 0x45, (byte) 0x00, (byte) 0x00, (byte) 0x28, (byte) 0x76, (byte) 0x30, (byte) 0x40, (byte) 0x00, 
			(byte) 0x7b, (byte) 0x06, (byte) 0x10, (byte) 0xd3, (byte) 0x57, (byte) 0x7b, (byte) 0xa6, (byte) 0x57, 
			(byte) 0x5b, (byte) 0x79, (byte) 0x1f, (byte) 0x81, (byte) 0x0e, (byte) 0x64, (byte) 0xca, (byte) 0x61, 
			(byte) 0xe4, (byte) 0xdb, (byte) 0x31, (byte) 0x3f, (byte) 0x95, (byte) 0x69, (byte) 0xfe, (byte) 0x06, 
			(byte) 0x50, (byte) 0x10, (byte) 0xc1, (byte) 0x9b, (byte) 0xf3, (byte) 0x1a, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x84, (byte) 0x2a, (byte) 0xbc, 
			(byte) 0xec, (byte) 0x1d, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x0d, 
			(byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0xa9, (byte) 0x40, 
			(byte) 0x85, (byte) 0x43, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x1a, (byte) 0x35, (byte) 0x00, (byte) 0x3f, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00, (byte) 0x0c, (byte) 0x0d, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x44, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x22, (byte) 0x55, (byte) 0x17, (byte) 0x0f, (byte) 0x00, 
			(byte) 0x00, (byte) 0x15, (byte) 0xc7, (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x81, (byte) 0x00, 
			(byte) 0x00, (byte) 0x02, (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x00, (byte) 0x28, 
			(byte) 0xe9, (byte) 0x6c, (byte) 0x40, (byte) 0x00, (byte) 0x7b, (byte) 0x06, (byte) 0x68, (byte) 0xbb, 
			(byte) 0x4f, (byte) 0x71, (byte) 0xd6, (byte) 0xd6, (byte) 0xd4, (byte) 0x75, (byte) 0xb2, (byte) 0xea, 
			(byte) 0xc8, (byte) 0x07, (byte) 0x05, (byte) 0x83, (byte) 0xdc, (byte) 0xb5, (byte) 0xb1, (byte) 0xb1, 
			(byte) 0x1e, (byte) 0x0d, (byte) 0xb3, (byte) 0x68, (byte) 0x50, (byte) 0x10, (byte) 0x7d, (byte) 0xb7, 
			(byte) 0x57, (byte) 0x0d, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			(byte) 0x00, (byte) 0x00 };
	
	private byte data[] = data1;
	
	private SflowHeader rph;
	
	
	public void setUp() throws Exception {
		
	}
	
	public void testParse() throws Exception {
		rph = SflowHeader.parse(data);
	}
	
	public void testGetBytes() throws Exception {
		rph = SflowHeader.parse(data);
		byte[] result = rph.getBytes();
		for (int i = 0; i < Math.min(result.length, data.length); i++) {
			assertEquals(data[i], result[i]);
		}
	}
	
	public void testToString() throws Exception {
		System.out.println(SflowHeader.parse(pkt2));
	}
}