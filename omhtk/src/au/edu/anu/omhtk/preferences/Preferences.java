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

package au.edu.anu.omhtk.preferences;

/**
 * Author Ian Davies
 *
 * Date Dec 11, 2018
 */

/**
 * Java has a preferences API {@link java.util.prefs.Preferences} . Therefore, I
 * hope we no longer need the graph-dependent implementation for preference
 * saving. However, the Java system only deals with primitives and so this code
 * is static wrapper to an interface to a preference helper class for 3Worlds to implement array
 * handling.
 */

public class Preferences {
	private static Preferenceable impl;

	// prevent instantiation
	private Preferences() {
	};

	public static void initialise(Preferenceable impl) {
		if (Preferences.impl != null)
			Preferences.impl.flush();
		Preferences.impl = impl;
	}

	public static void putInt(String key, int value) {
		impl.putInt(key, value);
	};

	public static void putInts(String key, int... values) {
		impl.putInts(key, values);
	};

	public static void putLong(String key, long value) {
		impl.putLong(key, value);
	};

	public static void putLongs(String key, long... values) {
		impl.putLongs(key, values);
	};

	public static void putBoolean(String key, boolean value) {
		impl.putBoolean(key, value);
	};

	public static void putBooleans(String key, boolean... values) {
		impl.putBooleans(key, values);
	};

	public static void putFloat(String key, float value) {
		impl.putFloat(key, value);
	};

	public static void putFloats(String key, float... values) {
		impl.putFloats(key, values);
	};

	public static void putDouble(String key, double value) {
		impl.putDouble(key, value);
	};

	public static void putDoubles(String key, double... values) {
		impl.putDoubles(key, values);
	};

	public static void putString(String key, String value) {
		impl.putString(key, value);
	};

	public void putStrings(String key, String... values) {
		impl.putStrings(key, values);
	};

	public static int getInt(String key, int def) {
		return impl.getInt(key, def);
	};

	public static int[] getInts(String key, int... defs) {
		return impl.getInts(key, defs);
	};

	public static long getLong(String key, long def) {
		return impl.getLong(key, def);
	};

	public static long[] getLongs(String key, long... defs) {
		return impl.getLongs(key, defs);
	};

	public static boolean getBoolean(String key, boolean def) {
		return impl.getBoolean(key, def);
	};

	public static boolean[] getBooleans(String key, boolean... defs) {
		return impl.getBooleans(key, defs);
	};

	public static float getFloat(String key, float def) {
		return impl.getFloat(key, def);
	};

	public static float[] getFloats(String key, float... defs) {
		return impl.getFloats(key, defs);
	};

	public static double getDouble(String key, double def) {
		return impl.getDouble(key, def);
	};

	public static double[] getDoubles(String key, double... defs) {
		return impl.getDoubles(key, defs);
	};

	public static String getString(String key, String def) {
		return impl.getString(key, def);
	};

	public static String[] getStrings(String key, String... defs) {
		return impl.getStrings(key, defs);
	};

	public static void remove(String key) {
		impl.remove(key);
	};

	public static void flush() {
		impl.flush();
	};

	public static boolean isEmpty() {
		return impl.isEmpty();
	};

}
