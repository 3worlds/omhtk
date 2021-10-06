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

import au.edu.anu.rscs.aot.OmhtkException;

/**
 * Static methods to manipulate strings
 * 
 * @author Shayne Flint - 2007
 * @author Luke Worth - 2007
 *
 */
public class StringUtils {

	/** The '...' character */
	public static final String ELLIPSIS = "\u2026";

	/**
	 * Abbreviate a string to a given length.
	 * @param s the String to abbreviate
	 * @param l the number of characters to keep
	 * @return the abbreviated String
	 */
	public static String abbreviate(String s, int l) {
		if (l < 0)
			throw new OmhtkException("Attempt to abbreviate a string to less than 0");
		if (s == null)
			throw new OmhtkException("Attempt to abbreviate a null string");
		
		if (s.length() <= (l + 1))
			return s;
		return s.substring(0, l) + ELLIPSIS;
	}

	/**
	 * Capitalise the first character of a String.
	 * @param s the String
	 * @return the String with uppercase initial
	 */
	public static String cap(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	/**
	 * Uncapitalise the first character of a String.
	 * @param s the String
	 * @returnthe String with lowercase initial
	 */
	public static String uncap(String s) {
		return Character.toLowerCase(s.charAt(0)) + s.substring(1);
	}

	/**
	 * Concatenate two strings with a separator. If one String is empty, returns the other.
	 * 
	 * @param name1 first String to concatenate
	 * @param separator the separator String to use
	 * @param name2 second String to concatenate
	 * @return the concatenated String
	 */
	public static String concat(String name1, String separator, String name2) {
		if (name1.length() == 0 || name2.length() == 0)
			return name1 + name2;
		else if (name1.length() == 0)
			return name2;
		else if (name2.length() == 0)
			return name1;
		else
			return name1 + separator + name2;
	}

	/**
	 * Pluralise a String.
	 * 
	 * @param s
	 * @return
	 */
	@Deprecated
	public static String plural(String s) {
		//
		// Not perfect, but probably good enough. We can extend this function as we go.
		//

		if (s.endsWith("s"))
			return s + "es";
		if (s.endsWith("sh"))
			return s + "es";
		if (s.endsWith("ch"))
			return s + "es";
		if (s.endsWith("y"))
			return s.substring(0, s.length() - 1) + "ies";
		if (s.endsWith("o"))
			return s + "es";
		else
			return s + "s";
	}

	/**
	 * Replace ",", "[" and "]" delimiters by plain characters.
	 * 
	 * @param s
	 * @return
	 */
	public static String delimited(String s) {
		String result;
		result = s.replaceAll(",", "\\,");
		result = s.replaceAll("[", "\\[");
		result = s.replaceAll("]", "\\]");
		return result;
	}

	/**
	 * Replace ",", "[" and "]" plain characters by delimiters.
	 * 
	 * @param s
	 * @return
	 */
	public static String plain(String s) {
		String result;
		result = s.replaceAll("\\,", ",");
		result = s.replaceAll("\\[", "[");
		result = s.replaceAll("\\]", "]");
		return result;
	}

	/**
	 * Constructs a range String from two integers. 
	 * 
	 * @see NumberRange
	 * @param min
	 * @param max
	 * @return
	 */
	public static String rangeString(int min, int max) {
		String result = "";
		if (min == Integer.MIN_VALUE)
			result = "MIN_INTEGER";
		else
			result = String.valueOf(min);
		result = result + "..";
		if (max == Integer.MAX_VALUE)
			result = result + "MAX_INTEGER";
		else
			result = result + String.valueOf(max);
		return result;
	}

	/**
	 * Checks if a String only contains white space.
	 * 
	 * @param str
	 * @return {@code true} if the argument only contains white space
	 */
	public static boolean isWhite(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isWhitespace(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * Compare two Strings.
	 * 
	 * @param str1
	 * @param str2
	 * @return {@code true} if both arguments are equal, including if they are both {@code null}.
	 */
	public static boolean isEqual(String str1, String str2) {
		if (str1 == null && str2 == null)
			return true;
		if (str1 == null)
			return false;
		if (str2 == null)
			return false;
		return (str1.equals(str2));
	}

	/**
	 * Replaces all occurrences of a String pattern in a String.
	 * 
	 * @param str the String to search in
	 * @param from the String to search for
	 * @param to the replacement for 'from'
	 * @return
	 */
	public static String replaceAll(String str, String from, String to) {
		String result = str;
		while (result.contains(from)) {
			result = result.replace(from, to);
		}
		return result;
	}

	// TESTING
	//

//	public static void main(String[] args) {
//		assertEquals(StringUtils.cap("hello"), "Hello");
//		assertEquals(StringUtils.cap("Hello"), "Hello");
//		assertEquals(StringUtils.cap("_hello"), "_hello");
//
//		assertEquals(StringUtils.uncap("hello"), "hello");
//		assertEquals(StringUtils.uncap("Hello"), "hello");
//		assertEquals(StringUtils.uncap("_hello"), "_hello");
//
//		assertEquals(StringUtils.plural("test"), "tests");
//		assertEquals(StringUtils.plural("hero"), "heroes");
//		assertEquals(StringUtils.plural("quality"), "qualities");
//		assertEquals(StringUtils.plural("bush"), "bushes");
//		assertEquals(StringUtils.plural("church"), "churches");
//		assertEquals(StringUtils.plural("glass"), "glasses");
//		
//		System.out.println("All OK");
//	}

}
