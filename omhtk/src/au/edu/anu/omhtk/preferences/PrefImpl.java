/**************************************************************************
 *  TW-CORE - 3Worlds Core classes and methods                            *
 *                                                                        *
 *  Copyright 2018: Shayne Flint, Jacques Gignoux & Ian D. Davies         *
 *       shayne.flint@anu.edu.au                                          * 
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  TW-CORE is a library of the principle components required by 3W       *
 *                                                                        *
 **************************************************************************                                       
 *  This file is part of TW-CORE (3Worlds Core).                          *
 *                                                                        *
 *  TW-CORE is free software: you can redistribute it and/or modify       *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  TW-CORE is distributed in the hope that it will be useful,            *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *                         
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with TW-CORE.                                                   *
 *  If not, see <https://www.gnu.org/licenses/gpl.html>                   *
 *                                                                        *
 **************************************************************************/

package au.edu.anu.omhtk.preferences;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
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
	private String sep = "_";

	public PrefImpl(Object item, String sep) {
		this.prefs = Preferences.userRoot().node(item.getClass().getName());
		this.sep = sep;
		// TODO: Need logging system here
		System.out.println(prefs.absolutePath().toString());
	}

	private String keyAppend(String key, int i) {
		return key + sep + i;
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
		for (int i = 0; i < values.length; i++)
			prefs.putLong(keyAppend(key, i), values[i]);
	}

	@Override
	public void putBoolean(String key, boolean value) {
		prefs.putBoolean(key, value);
	}

	@Override
	public void putBooleans(String key, boolean... values) {
		for (int i = 0; i < values.length; i++)
			prefs.putBoolean(keyAppend(key, i), values[i]);
	}

	@Override
	public void putFloat(String key, float value) {
		prefs.putFloat(key, value);
	}

	@Override
	public void putFloats(String key, float... values) {

		for (int i = 0; i < values.length; i++)
			prefs.putFloat(keyAppend(key, i), values[i]);
	}

	@Override
	public void putDouble(String key, double value) {
		prefs.putDouble(key, value);

	}

	@Override
	public void putDoubles(String key, double... values) {
		for (int i = 0; i < values.length; i++)
			prefs.putDouble(keyAppend(key, i), values[i]);
	}

	@Override
	public void putString(String key, String value) {
		prefs.put(key, value);
	}

	@Override
	public void putStrings(String key, String... values) {
		for (int i = 0; i < values.length; i++)
			prefs.put(keyAppend(key, i), values[i]);
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
		long[] res = new long[defs.length];
		for (int i = 0; i < defs.length; i++)
			res[i] = prefs.getLong(keyAppend(key, i), defs[i]);
		return res;
	}

	@Override
	public boolean getBoolean(String key, boolean def) {
		return prefs.getBoolean(key, def);
	}

	@Override
	public boolean[] getBooleans(String key, boolean... defs) {
		boolean[] res = new boolean[defs.length];
		for (int i = 0; i < defs.length; i++)
			res[i] = prefs.getBoolean(keyAppend(key, i), defs[i]);
		return res;
	}

	@Override
	public float getFloat(String key, float def) {
		return prefs.getFloat(key, def);
	}

	@Override
	public float[] getFloats(String key, float... defs) {
		float[] res = new float[defs.length];
		for (int i = 0; i < defs.length; i++)
			res[i] = prefs.getFloat(keyAppend(key, i), defs[i]);
		return res;
	}

	@Override
	public double getDouble(String key, double def) {
		return prefs.getDouble(key, def);
	}

	@Override
	public double[] getDoubles(String key, double... defs) {
		double[] res = new double[defs.length];
		for (int i = 0; i < defs.length; i++)
			res[i] = prefs.getDouble(keyAppend(key, i), defs[i]);
		return res;
	}

	@Override
	public String getString(String key, String def) {
		return prefs.get(key, def);
	}

	@Override
	public String[] getStrings(String key, String... defs) {
		String[] res = new String[defs.length];
		for (int i = 0; i < defs.length; i++)
			res[i] = prefs.get(keyAppend(key, i), defs[i]);
		return res;
	}

	@Override
	public void remove(String key) {
		try {
			String[] keys = prefs.keys();
			for (String akey : prefs.keys()) {
				if (arrayKey(key, akey)) {
					prefs.remove(akey);
				}
			}
			prefs.remove(key);

		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean arrayKey(String key, String akey) {
		if (!akey.contains(key + sep))
			return false;
		String[] items = akey.split(sep);
		String last = items[items.length - 1];
		try {
			int i = Integer.parseInt(last);
		} catch (Exception e) {
			return false;
		}
		return true;
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
