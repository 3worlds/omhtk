
//Copyright (C) 2007, Shayne Flint and Luke Worth

//Any user wishing to make a use of this software must
//contact Shayne Flint at shayne.flint@anu.edu.au to 
//arrange an appropriate license.

package au.edu.anu.rscs.aot.util;

import com.google.common.base.Objects;

import au.edu.anu.rscs.aot.OmhtkException;

/**
 * 
 * @author Shayne Flint - 2007
 * @author Luke Worth - 2007
 *
 */
public class StringUtils {

	public static final String ELLIPSIS = "\u2026";

	public static String abbreviate(String s, int l) {
		if (l < 0)
			throw new OmhtkException("Attempt to abbreviate a string to less than 0");
		if (s == null)
			throw new OmhtkException("Attempt to abbreviate a null string");
		
		if (s.length() <= (l + 1))
			return s;
		return s.substring(0, l) + ELLIPSIS;
	}

	public static String cap(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	public static String uncap(String s) {
		return Character.toLowerCase(s.charAt(0)) + s.substring(1);
	}

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

	public static String delimited(String s) {
		String result;
		result = s.replaceAll(",", "\\,");
		result = s.replaceAll("[", "\\[");
		result = s.replaceAll("]", "\\]");
		return result;
	}

	public static String plain(String s) {
		String result;
		result = s.replaceAll("\\,", ",");
		result = s.replaceAll("\\[", "[");
		result = s.replaceAll("\\]", "]");
		return result;
	}

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

	public static boolean isWhite(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isWhitespace(str.charAt(i)))
				return false;
		}
		return true;
	}

	public static boolean isEqual(String str1, String str2) {
		if (str1 == null && str2 == null)
			return true;
		if (str1 == null)
			return false;
		if (str2 == null)
			return false;
		return (str1.equals(str2));
	}

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
