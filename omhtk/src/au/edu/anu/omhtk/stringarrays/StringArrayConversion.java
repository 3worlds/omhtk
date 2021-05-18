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
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/

package au.edu.anu.omhtk.stringarrays;

/**
 * {@code String[]} ↔ {@code Number[]} converters. Helper methods, originally for the {@link java.util.prefs.Preferences}
 * system. May be of some general use.
 * Each method converts a list of numbers to a list of Strings, or reciprocally, as per method name.
 *
 * @author Ian Davies - Dec 11, 2018
 */
public class StringArrayConversion {

	public static String[] IntsAsStrings(int... values) {
		String[] result = new String[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Integer.toString(values[i]);
		return result;
	}

	public static String[] LongsAsStrings(long... values) {
		String[] result = new String[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Long.toString(values[i]);
		return result;
	}

	public static String[] BooleansAsStrings(boolean... values) {
		String[] result = new String[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Boolean.toString(values[i]);
		return result;
	}

	public static String[] FloatsAsStrings(float... values) {
		String[] result = new String[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Float.toString(values[i]);
		return result;
	}

	public static String[] DoublesAsStrings(double... values) {
		String[] result = new String[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Double.toString(values[i]);
		return result;
	}

	public static int[] stringsAsInts(String... values) {
		int[] result = new int[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Integer.parseInt(values[i]);
		return result;
	}

	public static long[] stringsAsLongs(String... values) {
		long[] result = new long[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Long.parseLong(values[i]);
		return result;
	}

	public static boolean[] stringsAsBooleans(String... values) {
		boolean[] result = new boolean[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Boolean.parseBoolean(values[i]);
		return result;
	}

	public static float[] stringsAsFloats(String... values) {
		float[] result = new float[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Float.parseFloat(values[i]);
		return result;
	}

	public static double[] stringsAsDoubles(String... values) {
		double[] result = new double[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Double.parseDouble(values[i]);
		return result;
	}

}
