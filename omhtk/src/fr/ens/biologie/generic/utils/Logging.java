/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2021: Shayne R. Flint, Jacques Gignoux & Ian D. Davies      *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent class hierarchies, plus some other utilities. The kind of  *
 *  things you need in all software projects and keep rebuilding all the  * 
 *  time.                                                                 *
 *                                                                        *
 **************************************************************************                                       
 *  This file is part of OMHTK (One More Handy Tool Kit).                 *
 *                                                                        *
 *  OMHTK is free software: you can redistribute it and/or modify         *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  OMHTK is distributed in the hope that it will be useful,              *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *                         
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/
package fr.ens.biologie.generic.utils;

import java.util.HashMap;
import java.util.Map;
import static java.util.logging.Level.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A centralized logging system for big applications. Only static methods. Based
 * on <a href=
 * "https://docs.oracle.com/en/java/javase/11/docs/api/java.logging/java/util/logging/package-summary.html">java.util.logging</a>.
 * 
 * @author Jacques Gignoux - 6 sept. 2019
 *
 */
public class Logging {

	private static Level defaultLevel = INFO;
	private static Map<Class<?>, Logger> loggers = new HashMap<>();

	private Logging() {
	}

	/**
	 * Get a Logger for a given class. Separate loggers are used for different
	 * classes so that the log level can be finely tuned
	 * 
	 * @param forclass Class of logger to search for.
	 * @return The Logger for the given class
	 */
	public static Logger getLogger(Class<?> forclass) {
		if (!loggers.containsKey(forclass)) {
			Logger log = Logger.getLogger(forclass.getName());
			log.setLevel(defaultLevel);
			loggers.put(forclass, log);
		}
		return loggers.get(forclass);
	}

	/**
	 * Set the logging level of all loggers
	 * 
	 * @param level the logging level. Possible values are:
	 *              <ul>
	 *              <li>OFF: no logging at all</li>
	 *              <li>SEVERE: error messages only</li>
	 *              <li>WARNING: error+warning messages only</li>
	 *              <li>INFO: error+warning+debug messages only</li>
	 *              </ul>
	 */
	public static void setLogLevel(Level level) {
		for (Logger log : loggers.values())
			log.setLevel(level);
	}

	/**
	 * Set the logging level for a given Logger identified by its class.
	 * 
	 * @param level    level the logging level.
	 * @param forclass the class identifying the Logger
	 * @see Logging#setLogLevel(Level)
	 */
	public static void setLogLevel(Level level, Class<?> forclass) {
		loggers.get(forclass).setLevel(level);
	}

	/**
	 * Set the default logging level.
	 * 
	 * @param level level the logging level.
	 * @see Logging#setLogLevel(Level)
	 */
	public static void setDefaultLogLevel(Level level) {
		defaultLevel = level;
		setLogLevel(level);
	}

}
