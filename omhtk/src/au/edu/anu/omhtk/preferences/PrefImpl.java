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

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import au.edu.anu.omhtk.bytearrays.ByteArrayConversion;

/**
 * Author Ian Davies
 *
 * Date Dec 11, 2018
 */
public class PrefImpl implements Preferable {
	private Preferences prefs;
	private final String sep = "\t";

	public PrefImpl(Object item) {
		this.prefs = Preferences.userRoot().node(item.getClass().getName());
		// TODO: Need logging system here
		System.out.println(prefs.absolutePath().toString());
	}

	@Override
	public void putInt(String key, int value) {
		prefs.putInt(key, value);
	}

	@Override
	public void putInts(String key, int... values) {
		prefs.putByteArray(key, ByteArrayConversion.IntsAsBytes(values));
	}

	@Override
	public void putLong(String key, long value) {
		prefs.putLong(key, value);
	}

	@Override
	public void putLongs(String key, long... values) {
		prefs.putByteArray(key, ByteArrayConversion.LongsAsBytes(values));
	}

	@Override
	public void putBoolean(String key, boolean value) {
		prefs.putBoolean(key, value);
	}

	@Override
	public void putBooleans(String key, boolean... values) {
		prefs.putByteArray(key, ByteArrayConversion.BooleansAsBytes(values));
	}

	@Override
	public void putFloat(String key, float value) {
		prefs.putFloat(key, value);
	}

	@Override
	public void putFloats(String key, float... values) {
		prefs.putByteArray(key, ByteArrayConversion.FloatsAsBytes(values));
	}

	@Override
	public void putDouble(String key, double value) {
		prefs.putDouble(key, value);

	}

	@Override
	public void putDoubles(String key, double... values) {
		prefs.putByteArray(key, ByteArrayConversion.DoublesAsBytes(values));
	}

	@Override
	public void putString(String key, String value) {
		prefs.put(key, value);
	}

	@Override
	public void putStrings(String key, String... values) {
		String s = String.join(sep, values);
		prefs.put(key, s);
	}

	@Override
	public int getInt(String key, int def) {
		return prefs.getInt(key, def);
	}

	@Override
	public int[] getInts(String key, int... defs) {
		byte[] bytes = prefs.getByteArray(key, ByteArrayConversion.IntsAsBytes(defs));
		return ByteArrayConversion.bytesAsInts(bytes);
	}

	@Override
	public long getLong(String key, long def) {
		return prefs.getLong(key, def);
	}

	@Override
	public long[] getLongs(String key, long... defs) {
		byte[] bytes = prefs.getByteArray(key, ByteArrayConversion.LongsAsBytes(defs));
		return ByteArrayConversion.bytesAsLongs(bytes);
	}

	@Override
	public boolean getBoolean(String key, boolean def) {
		return prefs.getBoolean(key, def);
	}

	@Override
	public boolean[] getBooleans(String key, boolean... defs) {
		byte[] bytes = prefs.getByteArray(key, ByteArrayConversion.BooleansAsBytes(defs));
		return ByteArrayConversion.bytesAsBooleans(bytes);
	}

	@Override
	public float getFloat(String key, float def) {
		return prefs.getFloat(key, def);
	}

	@Override
	public float[] getFloats(String key, float... defs) {
		byte[] bytes = prefs.getByteArray(key, ByteArrayConversion.FloatsAsBytes(defs));
		return ByteArrayConversion.bytesAsFloats(bytes);
	}

	@Override
	public double getDouble(String key, double def) {
		return prefs.getDouble(key, def);
	}

	@Override
	public double[] getDoubles(String key, double... defs) {
		byte[] bytes = prefs.getByteArray(key, ByteArrayConversion.DoublesAsBytes(defs));
		return ByteArrayConversion.bytesAsDoubles(bytes);
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
