package logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationOutputter {

	/**
	 * Lets avoid in the normal situations outputting all the debug messages to
	 * a log file, it can grow enormous big, so we want to disable it for every
	 * day use..
	 * 
	 * __DONT__FORCE__SKIP__ = true; will enable diagnostic messages
	 */
	private static boolean __DONT__FORCE__SKIP__;
	
	public static void init(boolean skip){
		__DONT__FORCE__SKIP__ = skip;
	}

	public static void say(String s) {
		if (__DONT__FORCE__SKIP__) {
			String dateStringRepresentation = getDateLogs();
			System.out.println(dateStringRepresentation + ": " + s);
			ValidationLogger.info(dateStringRepresentation + ": " + s);
		}
	}

	public static void fatal(String s) {
		String dateStringRepresentation = getDateLogs();
		System.out.println(dateStringRepresentation + ": " + s);
		ValidationLogger.fatal(dateStringRepresentation + ": " + s);
	}

	public static void error(String s) {
		String dateStringRepresentation = getDateLogs();
		System.out.println(dateStringRepresentation + ": " + s);
		ValidationLogger.error(dateStringRepresentation + ": " + s);
	}

	private static String getDateLogs() {
		return new SimpleDateFormat("DD/MM/yyyy HH:mm").format(new Date());
	}
}
