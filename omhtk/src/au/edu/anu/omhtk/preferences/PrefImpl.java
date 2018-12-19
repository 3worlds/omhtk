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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import au.edu.anu.omhtk.stringarrays.StringArrayConversion;

/**
 * Author Ian Davies
 *
 * Date Dec 11, 2018
 */
public class PrefImpl implements Preferenceable {
	private Preferences prefs;
	private final String sep = ",";
	private File file;

	/**
	 * A wrapper class for {@link java.util.prefs.Preferences}. This impl loads the
	 * node from file, if present, and writes to file on flush()
	 * 
	 * It also contains methods to get/put primitive arrays by conversion to String.
	 * 
	 * Keys and values have a maximum size imposed by
	 * {@link java.util.prefs.Preferences}. If this is exceeded there will be
	 * problems.
	 * 
	 * Keys: 80
	 * 
	 * Values: 8192
	 * 
	 * @param file file name for local storage of the preferences (and for node
	 *             hierarchy in backingstore)
	 */
	public PrefImpl(File file) {
		// file /a/b/c creates node children a -> b -> c
		this.prefs = Preferences.userRoot().node(file.getAbsolutePath());
		try {
			// Ignore the backingstore: we want to see the file for checking and possible
			// editing
			prefs.clear();
		} catch (BackingStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.file = file;
		if (file.exists())
			try {
				Preferences.importPreferences(new FileInputStream(file));
			} catch (IOException | InvalidPreferencesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	@Override
	public void putInt(String key, int value) {
		prefs.putInt(key, value);
	}

	@Override
	public void putInts(String key, int... values) {
		prefs.put(key, String.join(sep, StringArrayConversion.IntsAsStrings(values)));
	}

	@Override
	public void putLong(String key, long value) {
		prefs.putLong(key, value);
	}

	@Override
	public void putLongs(String key, long... values) {
		prefs.put(key, String.join(sep, StringArrayConversion.LongsAsStrings(values)));
	}

	@Override
	public void putBoolean(String key, boolean value) {
		prefs.putBoolean(key, value);
	}

	@Override
	public void putBooleans(String key, boolean... values) {
		prefs.put(key, String.join(sep, StringArrayConversion.BooleansAsStrings(values)));
	}

	@Override
	public void putFloat(String key, float value) {
		prefs.putFloat(key, value);
	}

	@Override
	public void putFloats(String key, float... values) {
		prefs.put(key, String.join(sep, StringArrayConversion.FloatsAsStrings(values)));
	}

	@Override
	public void putDouble(String key, double value) {
		prefs.putDouble(key, value);

	}

	@Override
	public void putDoubles(String key, double... values) {
		prefs.put(key, String.join(sep, StringArrayConversion.DoublesAsStrings(values)));
	}

	@Override
	public void putString(String key, String value) {
		prefs.put(key, value);
	}

	@Override
	public void putStrings(String key, String... values) {
		prefs.put(key, String.join(sep, values));
	}

	@Override
	public int getInt(String key, int def) {
		return prefs.getInt(key, def);
	}

	@Override
	public int[] getInts(String key, int... defs) {
		String value = prefs.get(key, String.join(sep, StringArrayConversion.IntsAsStrings(defs)));
		return StringArrayConversion.stringsAsInts(value.split(sep));
	}

	@Override
	public long getLong(String key, long def) {
		return prefs.getLong(key, def);
	}

	@Override
	public long[] getLongs(String key, long... defs) {
		String value = prefs.get(key, String.join(sep, StringArrayConversion.LongsAsStrings(defs)));
		return StringArrayConversion.stringsAsLongs(value.split(sep));
	}

	@Override
	public boolean getBoolean(String key, boolean def) {
		return prefs.getBoolean(key, def);
	}

	@Override
	public boolean[] getBooleans(String key, boolean... defs) {
		String value = prefs.get(key, String.join(sep, StringArrayConversion.BooleansAsStrings(defs)));
		return StringArrayConversion.stringsAsBooleans(value.split(sep));
	}

	@Override
	public float getFloat(String key, float def) {
		return prefs.getFloat(key, def);
	}

	@Override
	public float[] getFloats(String key, float... defs) {
		String value = prefs.get(key, String.join(sep, StringArrayConversion.FloatsAsStrings(defs)));
		return StringArrayConversion.stringsAsFloats(value.split(sep));
	}

	@Override
	public double getDouble(String key, double def) {
		return prefs.getDouble(key, def);
	}

	@Override
	public double[] getDoubles(String key, double... defs) {
		String value = prefs.get(key, String.join(sep, StringArrayConversion.DoublesAsStrings(defs)));
		return StringArrayConversion.stringsAsDoubles(value.split(sep));
	}

	@Override
	public String getString(String key, String def) {
		return prefs.get(key, def);
	}

	@Override
	public String[] getStrings(String key, String... defs) {
		String s = prefs.get(key, String.join(sep, defs));
		return (s.split(sep));
	}

	@Override
	public void remove(String key) {
		prefs.remove(key);
	}

	@Override
	public void flush() {
		try {
			prefs.flush();
			try {
				prefs.exportNode(new FileOutputStream(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isEmpty() {
		try {
			return prefs.keys().length == 0;
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return true;
	}

}
