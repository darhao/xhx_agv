package com.jimi.xhx_agv.util;

import org.apache.logging.log4j.Logger;

public class ExceptionLogger {
	
	private final static String PACKAGE_NAME_PREFIX = "com.jimi";
	
	
	public static void logError(Logger logger, Throwable e) {
		for (StackTraceElement element : e.getStackTrace()) {
			if(element.toString().startsWith(PACKAGE_NAME_PREFIX)) {
				logger.error(e.getClass().getSimpleName() + " : " + e.getMessage() + getErrorStackString(e));
				return;
			}
		}
		logWarn(logger, e);
	}
	
	
	public static void logWarn(Logger logger, Throwable e) {
		logger.warn(e.getClass().getSimpleName() + " : " + e.getMessage() + getErrorStackString(e));
	}
	

	private static String getErrorStackString(Throwable e) {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			sb.append("\n");
			sb.append(stackTraceElement.toString());
		}
		return sb.toString();
	}

}
