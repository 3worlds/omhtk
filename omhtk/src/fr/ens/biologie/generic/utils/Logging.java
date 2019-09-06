package fr.ens.biologie.generic.utils;

import java.util.HashMap;
import java.util.Map;
import static java.util.logging.Level.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A centralized logging system for big applications.
 * Only static methods.
 * 
 * @author Jacques Gignoux - 6 sept. 2019
 *
 */
public class Logging {

	private static Level defaultLevel = INFO;
	private static Map<Class<?>,Logger> loggers = new HashMap<>();
	
	private Logging() {	}
	
	/**
	 * returns a Logger for a given class. Separate loggers are used for different classes so
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
	 * sets the log level of all loggers
	 * @param level the logging level. Possible values are: 
	 * <ul>
	 * <li>SEVERE: error messages only</li>
	 * <li>WARNING: error+warning messages only</li>
	 * <li>INFO: error+warning+debug messages only</li>
	 * </ul>
	 */
	public static void setLogLevel(Level level) {
		for (Logger log:loggers.values())
			log.setLevel(level);
	}
	
	public static void setLogLevel(Level level, Class<?> forclass) {
		loggers.get(forclass).setLevel(level);
	}
	
	public static void setDefaultLogLevel(Level level) {
		defaultLevel = level;
		setLogLevel(level);
	}

}
