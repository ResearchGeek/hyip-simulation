package logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PjiitOutputter {

	/**
	 * Lets avoid in the normal situations outputting all the debug messages
	 * to a log file, it can grow enormous big, so we want to disable it
	 * for every day use..
	 * 
	 * __DONT__FORCE__SKIP__ = true; will enable diagnostic messages
	 */
	private static boolean __DONT__FORCE__SKIP__ = true;

	public static void say(String s) {
		if (__DONT__FORCE__SKIP__) {
			String dateStringRepresentation = getDateLogs();
			System.out.println(dateStringRepresentation + ": " + s);
			PjiitLogger.info(dateStringRepresentation + ": " + s);
		}
	}
	
	public static void log(String s) {
		if (__DONT__FORCE__SKIP__) {
			String dateStringRepresentation = getDateLogs();
			PjiitLogger.info(dateStringRepresentation + ": " + s);
		}
	}

	public static void sanity(String s) {
		if (__DONT__FORCE__SKIP__) {
			String dateStringRepresentation = getDateLogs();
			System.out.println(dateStringRepresentation + ": " + s);
			SanityLogger.sanity(dateStringRepresentation + ": " + s);
		}
	}

	private static String getDateLogs() {
		return new SimpleDateFormat("DD/MM/yyyy HH:mm").format(new Date());
	}
}
