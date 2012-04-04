/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: MPLogger.java
 * Author: Jin Hyung Park (jp2105)
 * Reviewer: YoungHoon Jung (yj2244)
 * Description: MPLogger
 */
package edu.columbia.mipl.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MPLogger {
	private static MPLogger instance = null;

	MPLogger() {
	}

	public static synchronized MPLogger getInstance() {
		if (instance == null) {
			instance = new MPLogger();
		}

		return instance;
	}

	public static void info(String log) {
		Throwable t = new Throwable();
		StackTraceElement methodCaller = t.getStackTrace()[1];
		Logger logger = LogManager.getLogger(methodCaller.getClassName());
		logger.info(log);
	}

	public static void warn(String log) {
		Throwable t = new Throwable();
		StackTraceElement methodCaller = t.getStackTrace()[1];
		Logger logger = LogManager.getLogger(methodCaller.getClassName());
		logger.warn(log);
	}

	public static void error(String log) {
		Throwable t = new Throwable();
		StackTraceElement methodCaller = t.getStackTrace()[1];
		Logger logger = LogManager.getLogger(methodCaller.getClassName());
		logger.error(log);
	}

	public static void err(String log) {
		instance.error(log);
	}
}
