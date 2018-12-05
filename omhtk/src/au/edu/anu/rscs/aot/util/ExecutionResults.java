/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2018: Shayne FLint, Jacques Gignoux & Ian D. Davies         *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent, plus some other utilities. The kind of things you need    *
 *  in all software projects and keep rebuilding all the time.            *
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
 *  along with UIT.  If not, see <https://www.gnu.org/licenses/gpl.html>. *
 *                                                                        *
 **************************************************************************/
package au.edu.anu.rscs.aot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.AbstractCollection;

import au.edu.anu.rscs.aot.OmhtkException;

/**
 * 
 * @author Shayne Flint - before 27/2/2012
 *
 */
public class ExecutionResults {

	public static final int NORMAL_EXIT = 0;
	public static final int ABNORMAL_EXIT = -1; // something went wrong but the error code is unknown


	private String   command;
	private int      exitCode;
	private String[] results;

	public ExecutionResults(String command, int exitCode, String... results) {
		this.command = command;
		this.exitCode = exitCode;
		this.results = results;
	}

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
			for (int i=0; i< len; i++)
				this.results[i] = br.readLine();
		} catch (Exception e) {
			throw new OmhtkException(e);
		}
	}

	public String command() {
		return command;
	}

	public int exitCode() {
		return exitCode;
	}

	public String[] results() {
		return results;
	}

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


	public String toString() {
		return "[ExecutionResults command=" + command + ", exitCode=" + exitCode + "\n" + resultsString() + "]";
	}
	
	public void check() {
		if (exitCode != NORMAL_EXIT)
			throw new OmhtkException(toString());
	}

}
