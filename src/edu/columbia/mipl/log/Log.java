/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: Log.java
 * Author: Jin Hyung Park (jp2105)
 * Reviewer: YoungHoon Jung (yj2244)
 * Description: Log
 */
package edu.columbia.mipl.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log {
	private static Log instance = null;

	Log() {
	}

	public static synchronized Log getInstance() {
		if (instance == null) {
			instance = new Log();
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
}
