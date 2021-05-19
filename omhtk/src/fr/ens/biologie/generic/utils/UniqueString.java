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
package fr.ens.biologie.generic.utils;

import java.util.Set;

/**
 * A factory for unique Strings. 
 * 
 * @author Ian Davies - 28 jan. 2019
 *
 */
public class UniqueString {

	/**
	 * Create a String unique within a set of existing Strings (= scope). The first argument
	 * is a String the user wants to be unique within the scope. The second argument is the scope.
	 * If the String is already present in the scope, this method returns the String suffixed
	 * with increasing numbers.
	 * 
	 * @param proposedString the String one wants to be unique
	 * @param set the scope, i.e. the set of already existing Strings
	 * @return either proposedString, or proposedString suffixed by an increasing number
	 */
	public static String makeString(String proposedString, Set<String> set) {
		if (!set.contains(proposedString))
			return proposedString;
		Duple<String, Integer> duple = parseNumberedString(proposedString);
		int count = duple.getSecond() + 1;
		proposedString = duple.getFirst() + count;
		return makeString(proposedString, set);
	}

	private static Duple<String, Integer> parseNumberedString(String numberedString) {
		int idx = getCountStartIndex(numberedString);
		// has no numbers at the end
		if (idx < 0)
			return new Duple<>(numberedString, 0);
		// all numbers
		if (idx == 0)
			return new Duple<String, Integer>(numberedString + "_", 0);
		// ends with some numbers
		String key = numberedString.substring(0, idx);
		String sCount = numberedString.substring(idx, numberedString.length());
		int count = Integer.parseInt(sCount);
		return new Duple<>(key, count);
	}

	private static int getCountStartIndex(String numberedString) {
		int result = -1;
		for (int i = numberedString.length() - 1; i >= 0; i--) {
			String s = numberedString.substring(i, i + 1);
			try {
				Integer.parseInt(s);
				result = i;
			} catch (NumberFormatException e) {
				return result;
			}
		}
		return result;
	}

}
