package logger;

import java.io.IOException;
import java.util.Enumeration;

import org.apache.log4j.Category;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.SimpleLayout;

/**
 * PjiitLogger implements standard wrap-around for Java Logger
 * adds methods for initiation, logging with different
 * importance, also provide special method *sanity_check*
 * to log only important messages in compliance with simulation
 * model description.
 * 
 * @version 1.1
 * @since model_taskset_description.docx
 * 
 * @author Oskar Jarczyk
 * @since 1.0
 */
public class PjiitLogger {
	
	/**
	 * TODO: rewrite to use log4j configuration file instead
	 * of manually coding appenders !
	 */
	public static Logger logger = null;
	
	public static void init() throws IOException{
		init(Level.ALL);
	}
	
	public static void init(Level level) throws IOException{
		logger = getRootLogger();
		SimpleLayout layout = new SimpleLayout();   
		FileAppender appender = new FileAppender(layout, "simulation_logs.txt",false);    
	      logger.addAppender(appender);
	    setLevel(level);
	    
	    dumpAll();
	}
	
	private static void dumpAll(){
		Enumeration<Category> loggers = LogManager.getCurrentLoggers();
		while (loggers.hasMoreElements()){
			Category e = loggers.nextElement();
			info(e.getName());
		}
	}
	
	// Creation & retrieval methods:
	public static Logger getRootLogger() {
		return Logger.getRootLogger();
	}
	
	public static void setLevel(Level level){
		logger.setLevel(level);
	}

	public static Logger getLogger(String name) {
		return Logger.getLogger(name);
	}

	// printing methods:
	public static void trace(String message) {
		logger.log(Priority.INFO, message);
	}

	public static void debug(Object message) {
		logger.log(Priority.DEBUG, message);
	}

	public static void info(String message) {
		logger.log(Priority.INFO, message);
	}

	public static void warn(Object message) {
		logger.log(Priority.WARN, message);
	}

	public static void error(Object message) {
		logger.log(Priority.ERROR, message);
	}

	public static void fatal(Object message) {
		logger.log(Priority.FATAL, message);
	}

	// generic printing method:
	public static void log(Level l, Object message) {
		logger.log(Priority.INFO, message);
	}
}
