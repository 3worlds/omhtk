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
package au.edu.anu.rscs.aot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractCollection;

/**
 * <p>
 * A class to store the result of an external command execution, for use in
 * applications that launch processes on a local or remote host. These results
 * are of two kinds:
 * </p>
 * <ul>
 * <li>Text output into the console, or a text file;</li>
 * <li>An error code, with value 0 indicating a normal exit.</li>
 * </ul>
 * <p>
 * This class provides various utilities to process these information in an
 * operating system-independent way. It was designed to facilitate the handling
 * of external processes in an application able to launch such processes, either
 * remotely or locally.
 * </p>
 * <p>
 * Usage: typically, when an external process is run, this class should be
 * instantiated with its output, as for example in the
 * {@link au.edu.anu.rscs.aot.environment.Environment Environment} class methods
 * {@code execute(command)} and {@code run(command)}
 * </p>
 * 
 * @author Shayne Flint - before 27/2/2012
 *
 * @see au.edu.anu.rscs.aot.environment.Environment Environment
 * @see au.edu.anu.rscs.aot.environment.LocalEnvironment LocalEnvironment
 */
public class ExecutionResults {

	/** Indicator of a normal execution (no error) */
	public static final int NORMAL_EXIT = 0;
	/** Indicator of an abnormal execution where the error code is unknown */
	public static final int ABNORMAL_EXIT = -1;

	private String command;
	private int exitCode;
	private String[] results;

	/**
	 * Constructor to use when command output is a single String.
	 * 
	 * @param command  the command that produced the result
	 * @param exitCode the command exit code (0 if no error)
	 * @param results  the text output of the command
	 */
	public ExecutionResults(String command, int exitCode, String... results) {
		this.command = command;
		this.exitCode = exitCode;
		this.results = results;
	}

	/**
	 * Constructor to use when the command output is a list of Strings.
	 * 
	 * @param command  the command that produced the result
	 * @param exitCode the command exit code (0 if no error)
	 * @param results  the text output of the command
	 */
	public ExecutionResults(String command, int exitCode, AbstractCollection<String> results) {
		this.command = command;
		this.exitCode = exitCode;
		this.results = new String[results.size()];
		int i = 0;
		for (String s : results) {
			this.results[i] = s;
			i++;
		}
	}

	/**
	 * Constructor to use when the command output is a file.
	 * 
	 * @param command  the command that produced the result
	 * @param exitCode the command exit code (0 if no error)
	 * @param results  the text output of the command
	 */
	public ExecutionResults(String command, int exitCode, File results) {
		this.command = command;
		this.exitCode = exitCode;
		try {
			BufferedReader br = new BufferedReader(new FileReader(results));
			int len = 0;
			while (br.readLine() != null)
				len++;
			br.close();
			br = new BufferedReader(new FileReader(results));
			this.results = new String[len];
			for (int i = 0; i < len; i++)
				this.results[i] = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Get the command that was submitted.
	 * 
	 */
	public String command() {
		return command;
	}

	/**
	 * @return Get the exit code (0 = no error).
	 */
	public int exitCode() {
		return exitCode;
	}

	/**
	 * @return Get the results as an array of Strings.
	 */
	public String[] results() {
		return results;
	}

	/**
	 * @return Get the results as a single String with line breaks.
	 */
	public String resultsString() {
		String result = "";
		for (String s : results) {
			if (result.length() == 0)
				result = s;
			else
				result = result + "\n" + s;
		}
		return result;
	}

	@Override
	public String toString() {
		return "[ExecutionResults command=" + command + ", exitCode=" + exitCode + "\n" + resultsString() + "]";
	}

	/**
	 * Throw an Exception if the exit code was different from 0.
	 */
	public void check() {
		if (exitCode != NORMAL_EXIT)
			throw new IllegalStateException(toString());
	}

}
