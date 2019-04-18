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

// I get a crash here because this class is not found in my system.
// I found this explanation on the internet:
// This is because javafx.util.Pair is not a part of OpenJDK. 
// You need to use Oracle JDK which include JavaFX where it contains javafx.util.Pair
// Do use this class often ? if not, I prefer to redefine it at our level
// since it's a handy utility.
// import javafx.util.Pair;

/**
 * A Scope for unique Ids
 * 
 * @author Ian Davies - 28 jan. 2019
 *
 */
public class UniqueString {

	public static String makeString(String proposedString, Set<String> set) {
		if (!set.contains(proposedString))
			return proposedString;
		Pair<String, Integer> pair = parseNumberedString(proposedString);
		int count = pair.getValue() + 1;
		proposedString = pair.getKey() + count;
		return makeString(proposedString, set);
	}

	private static Pair<String, Integer> parseNumberedString(String numberedString) {
		int idx = getCountStartIndex(numberedString);
		// has no numbers at the end
		if (idx < 0)
			return new Pair<>(numberedString, 0);
		// all numbers
		if (idx == 0)
			return new Pair<String, Integer>(numberedString + "_", 0);
		// ends with some numbers
		String key = numberedString.substring(0, idx);
		String sCount = numberedString.substring(idx, numberedString.length());
		int count = Integer.parseInt(sCount);
		return new Pair<>(key, count);
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
