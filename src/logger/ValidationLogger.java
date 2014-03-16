package logger;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class ValidationLogger {
	
	static Logger logger = Logger.getLogger(ValidationLogger.class);

	public static void init() {
		// setting up a FileAppender dynamically...
		SimpleLayout layout = new SimpleLayout();
		FileAppender appender;
		try {
			appender = new FileAppender(layout, "validation.log", false);
			logger.addAppender(appender);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.setLevel((Level) Level.ALL);
	}
	
	public static void info(String message){
		logger.info(message);
	}
	
	public static void error(String message){
		logger.error(message);
	}
	
	public static void fatal(String message){
		logger.fatal(message);
	}
}