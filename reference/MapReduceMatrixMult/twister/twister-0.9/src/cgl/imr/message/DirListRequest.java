/*
 * Software License, Version 1.0
 *
 *  Copyright 2003 The Trustees of Indiana University.  All rights reserved.
 *
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1) All redistributions of source code must retain the above copyright notice,
 *  the list of authors in the original source code, this list of conditions and
 *  the disclaimer listed in this license;
 * 2) All redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the disclaimer listed in this license in
 *  the documentation and/or other materials provided with the distribution;
 * 3) Any documentation included with all redistributions must include the
 *  following acknowledgement:
 *
 * "This product includes software developed by the Community Grids Lab. For
 *  further information contact the Community Grids Lab at
 *  http://communitygrids.iu.edu/."
 *
 *  Alternatively, this acknowledgement may appear in the software itself, and
 *  wherever such third-party acknowledgments normally appear.
 *
 * 4) The name Indiana University or Community Grids Lab or Twister,
 *  shall not be used to endorse or promote products derived from this software
 *  without prior written permission from Indiana University.  For written
 *  permission, please contact the Advanced Research and Technology Institute
 *  ("ARTI") at 351 West 10th Street, Indianapolis, Indiana 46202.
 * 5) Products derived from this software may not be called Twister,
 *  nor may Indiana University or Community Grids Lab or Twister appear
 *  in their name, without prior written permission of ARTI.
 *
 *
 *  Indiana University provides no reassurances that the source code provided
 *  does not infringe the patent or any other intellectual property rights of
 *  any other entity.  Indiana University disclaims any liability to any
 *  recipient for claims brought by any other entity based on infringement of
 *  intellectual property rights or otherwise.
 *
 * LICENSEE UNDERSTANDS THAT SOFTWARE IS PROVIDED "AS IS" FOR WHICH NO
 * WARRANTIES AS TO CAPABILITIES OR ACCURACY ARE MADE. INDIANA UNIVERSITY GIVES
 * NO WARRANTIES AND MAKES NO REPRESENTATION THAT SOFTWARE IS FREE OF
 * INFRINGEMENT OF THIRD PARTY PATENT, COPYRIGHT, OR OTHER PROPRIETARY RIGHTS.
 * INDIANA UNIVERSITY MAKES NO WARRANTIES THAT SOFTWARE IS FREE FROM "BUGS",
 * "VIRUSES", "TROJAN HORSES", "TRAP DOORS", "WORMS", OR OTHER HARMFUL CODE.
 * LICENSEE ASSUMES THE ENTIRE RISK AS TO THE PERFORMANCE OF SOFTWARE AND/OR
 * ASSOCIATED MATERIALS, AND TO THE PERFORMANCE AND VALIDITY OF INFORMATION
 * GENERATED USING SOFTWARE.
 */

package cgl.imr.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cgl.imr.base.SerializationException;

/**
 * Message sent requesting a directory listing.
 * 
 * @author Jaliya Ekanayake (jaliyae@gmail.com, jekanaya@cs.indiana.edu)
 * 
 */
public class DirListRequest extends PubSubMessage {

	private String directry;
	private String fileFilter;
	private String responseTopic;

	public DirListRequest() {
	}

	public DirListRequest(String directry, String fileFilter,
			String responseTopic) {
		super();
		this.directry = directry;
		this.fileFilter = fileFilter;
		this.responseTopic = responseTopic;
	}

	@Override
	public void fromBytes(byte[] bytes) throws SerializationException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(baInputStream);

		try {
			// First byte is the message type
			byte msgType = din.readByte();
			if (msgType != DIR_LIST_REQ) {
				throw new SerializationException(
						"Invalid set of bytes to deserialize "
								+ this.getClass().getName() + ".");
			}

			byte[] data = null;
			int len = 0;

			// Read the refId if any and set the boolean flag.
			readRefIdIfAny(din);

			len = din.readInt();
			data = new byte[len];
			din.readFully(data);
			this.directry = new String(data);

			// read the sink bytes
			len = din.readInt();
			data = new byte[len];
			din.readFully(data);
			this.fileFilter = new String(data);

			// read the response topic bytes
			len = din.readInt();
			data = new byte[len];
			din.readFully(data);
			this.responseTopic = new String(data);

			din.close();
			baInputStream.close();

		} catch (IOException ioe) {
			throw new SerializationException(ioe);
		}
	}

	@Override
	public byte[] getBytes() throws SerializationException {
		byte[] serializedBytes = null;

		try {

			ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(baOutputStream);

			// First byte is the message type.
			dout.writeByte(DIR_LIST_REQ);

			byte[] data = null;

			// Write the refID if any with the boolean flag.
			serializeRefId(dout);

			// Now write the directory to list
			data = directry.getBytes();
			dout.writeInt(data.length);
			dout.write(data);

			// Now write the filter pattern
			data = fileFilter.getBytes();
			dout.writeInt(data.length);
			dout.write(data);

			// Now write the filter pattern
			data = responseTopic.getBytes();
			dout.writeInt(data.length);
			dout.write(data);

			dout.flush();
			serializedBytes = baOutputStream.toByteArray();
			baOutputStream = null;
			dout = null;

		} catch (IOException ioe) {
			throw new SerializationException(ioe);
		}
		return serializedBytes;
	}

	public String getDirectry() {
		return directry;
	}

	public String getFileFilter() {
		return fileFilter;
	}

	public String getResponseTopic() {
		return responseTopic;
	}
}
