package logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class EndRunLogger {
	
	private static Logger logger = Logger.getLogger(EndRunLogger.class);
	private static boolean headersBuilt = false;

	public static void init() {
		// setting up a FileAppender dynamically...
		SimpleLayout layout = new SimpleLayout();
		FileAppender appender;
		try {
			appender = new FileAppender(
					layout, "batch.log", true);
			logger.addAppender(appender);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.setLevel((Level) Level.ALL);
	}
	
	public static void init_singleton() {
		// setting up a FileAppender dynamically...
		SimpleLayout layout = new SimpleLayout();
		FileAppender appender;
		try {
			appender = new FileAppender(
					layout, "batch_" + getCurrentTimeStamp() + ".log", false);
			logger.addAppender(appender);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.setLevel((Level) Level.ALL);
	}
	
	private static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
	public static void finalMessage(String message){
		logger.info(message);
	}
	
	public static void buildHeaders(String s){
		if (!headersBuilt){
			finalMessage(s);
			headersBuilt = true;
		}
	}
}