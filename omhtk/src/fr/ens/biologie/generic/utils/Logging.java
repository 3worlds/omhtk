package fr.ens.biologie.generic.utils;

import java.util.HashMap;
import java.util.Map;
import static java.util.logging.Level.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A centralized logging system for big applications.
 * Only static methods. Based on 
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.logging/java/util/logging/package-summary.html">java.util.logging</a>.
 * 
 * @author Jacques Gignoux - 6 sept. 2019
 *
 */
public class Logging {

	private static Level defaultLevel = INFO;
	private static Map<Class<?>,Logger> loggers = new HashMap<>();
	
	private Logging() {	}
	
	/**
	 * Get a Logger for a given class. Separate loggers are used for different classes so
	 * that the log level can be finely tuned
	 *  
	 * @param forclass
	 * @return
	 */
	public static Logger getLogger(Class<?> forclass) {
		if (!loggers.containsKey(forclass)) {
			Logger log = Logger.getLogger(forclass.getName());
			log.setLevel(defaultLevel);
			loggers.put(forclass,log);
		}
		return loggers.get(forclass);
	}
	
	/** 
	 * Set the logging level of all loggers
	 * @param level the logging level. Possible values are: 
	 * <ul>
	 * <li>OFF: no logging at all</li>
	 * <li>SEVERE: error messages only</li>
	 * <li>WARNING: error+warning messages only</li>
	 * <li>INFO: error+warning+debug messages only</li>
	 * </ul>
	 */
	public static void setLogLevel(Level level) {
		for (Logger log:loggers.values())
			log.setLevel(level);
	}
	
	/**
	 * Set the logging level for a given Logger identified by its class.
	 * @param level level the logging level.
	 * @param forclass the class identifying the Logger
	 * @see Logging#setLogLevel(Level)
	 */
	public static void setLogLevel(Level level, Class<?> forclass) {
		loggers.get(forclass).setLevel(level);
	}
	
	/**
	 * Set the default logging level. 
	 * @param level level the logging level.
	 * @see Logging#setLogLevel(Level)
	 */
	public static void setDefaultLogLevel(Level level) {
		defaultLevel = level;
		setLogLevel(level);
	}

}
