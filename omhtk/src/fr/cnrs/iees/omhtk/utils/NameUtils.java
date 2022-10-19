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
package fr.cnrs.iees.omhtk.utils;

/**
 * Utility methods to make java-compatible names from Strings.
 * 
 * @author Jacques Gignoux - 15/2/2012
 *
 */
// tested OK with version 0.1.5
// TODO Check all this - may already be done by apache commons.lang3.wordUtils
public class NameUtils {

	/**
	 * @return a Java compatible String with uppercase words from a String with
	 *         separators.
	 * @param s the input string.
	 * 
	 */
	static public String wordUpperCaseName(String s) {
		String[] tokens = s.split("\\W");
		String result = "";
		if (tokens.length > 0) {
			result = tokens[0];
			for (int i = 1; i < tokens.length; i++) {
				result += initialUpperCase(tokens[i]);
			}
		} else
			result = s;
		return result;
	}

	/**
	 * @return a String with an initial uppercase.
	 * 
	 * @param s the input string.
	 */
	static public String initialUpperCase(String s) {
		if (s.length() > 1)
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		else if (s.length() == 1)
			return s.toUpperCase();
		else
			return s;
	}

	/**
	 * @return a String where separators are replaced by underscores
	 * 
	 * @param s the input string.
	 * 
	 */
	static public String wordUnderscoreName(String s) {
		String[] tokens = s.split("\\W");
		String result = "";
		if (tokens.length > 0) {
			result = tokens[0];
			for (int i = 1; i < tokens.length; i++) {
				if (tokens[i].length() > 0)
					result += "_" + tokens[i];
			}
		} else
			result = s;
		return result;
	}

	/**
	 * Makes a valid java variable name from a string by replacing all non valid
	 * characters with "X".
	 * 
	 * @param s The input string.
	 * @return a valid java variable name.
	 */
	static public String validJavaName(String s) {
		String result = s;
		if (s.substring(0, 1).matches("\\d"))
			result = "_" + s;
		result = result.replaceAll("\\W", "X");
		return result;
	}

}
