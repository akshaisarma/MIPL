/**
 * Software License, Version 1.0
 * 
 * Copyright 2003 The Trustees of Indiana University.  All rights reserved.
 * 
 *
 *Redistribution and use in source and binary forms, with or without 
 *modification, are permitted provided that the following conditions are met:
 *
 *1) All redistributions of source code must retain the above copyright notice,
 * the list of authors in the original source code, this list of conditions and
 * the disclaimer listed in this license;
 *2) All redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the disclaimer listed in this license in
 * the documentation and/or other materials provided with the distribution;
 *3) Any documentation included with all redistributions must include the 
 * following acknowledgement:
 *
 *"This product includes software developed by the Community Grids Lab. For 
 * further information contact the Community Grids Lab at 
 * http://communitygrids.iu.edu/."
 *
 * Alternatively, this acknowledgement may appear in the software itself, and 
 * wherever such third-party acknowledgments normally appear.
 * 
 *4) The name Indiana University or Community Grids Lab or Twister, 
 * shall not be used to endorse or promote products derived from this software 
 * without prior written permission from Indiana University.  For written 
 * permission, please contact the Advanced Research and Technology Institute 
 * ("ARTI") at 351 West 10th Street, Indianapolis, Indiana 46202.
 *5) Products derived from this software may not be called Twister, 
 * nor may Indiana University or Community Grids Lab or Twister appear
 * in their name, without prior written permission of ARTI.
 * 
 *
 * Indiana University provides no reassurances that the source code provided 
 * does not infringe the patent or any other intellectual property rights of 
 * any other entity.  Indiana University disclaims any liability to any 
 * recipient for claims brought by any other entity based on infringement of 
 * intellectual property rights or otherwise.  
 *
 *LICENSEE UNDERSTANDS THAT SOFTWARE IS PROVIDED "AS IS" FOR WHICH NO 
 *WARRANTIES AS TO CAPABILITIES OR ACCURACY ARE MADE. INDIANA UNIVERSITY GIVES
 *NO WARRANTIES AND MAKES NO REPRESENTATION THAT SOFTWARE IS FREE OF 
 *INFRINGEMENT OF THIRD PARTY PATENT, COPYRIGHT, OR OTHER PROPRIETARY RIGHTS. 
 *INDIANA UNIVERSITY MAKES NO WARRANTIES THAT SOFTWARE IS FREE FROM "BUGS", 
 *"VIRUSES", "TROJAN HORSES", "TRAP DOORS", "WORMS", OR OTHER HARMFUL CODE.  
 *LICENSEE ASSUMES THE ENTIRE RISK AS TO THE PERFORMANCE OF SOFTWARE AND/OR 
 *ASSOCIATED MATERIALS, AND TO THE PERFORMANCE AND VALIDITY OF INFORMATION 
 *GENERATED USING SOFTWARE.
 */

package cgl.imr.samples.blastalter;

import cgl.imr.base.Key;
import cgl.imr.base.MapOutputCollector;
import cgl.imr.base.MapTask;
import cgl.imr.base.TwisterException;
import cgl.imr.base.Value;
import cgl.imr.base.impl.JobConf;
import cgl.imr.base.impl.MapperConf;
import cgl.imr.data.file.FileData;
import cgl.imr.samples.blastutil.OutputHandler;

/**
 * MapTask, get the query file from mapper conf Replace the inputFilePrefix with
 * outputFilePrefix for output
 * 
 * @author Bingjing Zhang (zhangbj@cs.indiana.edu) 5/25/2010
 * 
 */
public class BLASTAlterMapTask implements MapTask {

	private String dataDir;
	private String inputPrefix;
	private String outputPrefix;
	private String exeCmd;
	private String inOp;
	private String outOp;
	private FileData fileData;

	@Override
	public void close() throws TwisterException {

	}

	@Override
	public void configure(JobConf jobConf, MapperConf mapConf)
			throws TwisterException {

		dataDir = jobConf.getProperty("dataDir");
		inputPrefix = jobConf.getProperty("inputPrefix");
		outputPrefix = jobConf.getProperty("outputPrefix");
		exeCmd = jobConf.getProperty("exeCmd");
		inOp = jobConf.getProperty("inOp");
		outOp = jobConf.getProperty("outOp");

		fileData = (FileData) mapConf.getDataPartition();
	}

	@Override
	public void map(MapOutputCollector arg0, Key arg1, Value arg2)
			throws TwisterException {

		String inputFile = fileData.getFileName();
		// System.out.println("inputFile: " + inputFile);

		//get input file name, replace the prefix assigned in the command line and replace it with assigned output prefix
		String inputFileName = inputFile.substring(
				inputFile.lastIndexOf('/') + 1, inputFile.lastIndexOf("."));
		// System.out.println("inputFileName: " + inputFileName);

		String outputFileName = inputFileName.replaceAll(inputPrefix,
				outputPrefix);
		// System.out.println("outputFileName: " + outputFileName);

		String outputFile = dataDir + outputFileName + ".out";
		String stdErrFile = dataDir + outputFileName + ".err";

		String in = " " + inOp + " " + inputFile;
		String out = " " + outOp + " " + outputFile;

		try {
			// System.out.println(exeCmd + in + out);
			Process p = Runtime.getRuntime().exec(exeCmd + in + out);

			// error stream handler, output into files by using OutputHandler

			OutputHandler errorStream = new OutputHandler(p.getErrorStream(),
					"ERROR", stdErrFile);

			// start the stream threads.
			// inputStream.start();
			errorStream.start();

			p.waitFor();

		} catch (Exception e) {
			throw new TwisterException(e);
		}
	}
}
