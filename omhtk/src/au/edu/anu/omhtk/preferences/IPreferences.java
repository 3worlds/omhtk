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
 *  This system is part of OMHTK (One More Handy Tool Kit).                 *
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
 * Interface for the preferences system. It is intended that implementations of
 * this interface provide a means of saving arrays in text form.
 * 
 * @author Ian Davies - Dec 11, 2018
 *
 */
public interface IPreferences {
	/**
	 * Add a key:int pair to the preferences.
	 * 
	 * @param key   key - unique within the scope of the associated preferences
	 *              node.
	 * @param value integer value
	 */
	public void putInt(String key, int value);

	/**
	 * Add a key:int[] pair to the preferences system
	 * 
	 * @param key    key - unique within the scope of the associated preferences
	 *               node.
	 * @param values array of int
	 */
	public void putInts(String key, int... values);

	/**
	 * Add a key:long pair to the preferences system
	 * 
	 * @param key   key - unique within the scope of the associated preferences
	 *              node.
	 * @param value long value
	 */
	public void putLong(String key, long value);

	/**
	 * Add a key:long[] pair to the preferences system
	 * 
	 * @param key    key - unique within the scope of the associated preferences
	 *               node.
	 * @param values array of long
	 */
	public void putLongs(String key, long... values);

	/**
	 * Add a key:boolean pair to the preferences system
	 * 
	 * @param key   key - unique within the scope of the associated preferences
	 *              node.
	 * @param value boolean value
	 */
	public void putBoolean(String key, boolean value);

	/**
	 * Add a key:boolean[] pair to the preferences system
	 * 
	 * @param key    key - unique within the scope of the associated preferences
	 *               node.
	 * @param values array of boolean
	 */
	public void putBooleans(String key, boolean... values);

	/**
	 * Add a key:float pair to the preferences system
	 * 
	 * @param key   key - unique within the scope of the associated preferences
	 *              node.
	 * @param value float value
	 */
	public void putFloat(String key, float value);

	/**
	 * Add a key:float[] pair to the preferences system
	 * 
	 * @param key    key - unique within the scope of the associated preferences
	 *               node.
	 * @param values array of float
	 */
	public void putFloats(String key, float... values);

	/**
	 * Add a key:double pair to the preferences system
	 * 
	 * @param key   key - unique within the scope of the associated preferences
	 *              node.
	 * @param value double value
	 */
	public void putDouble(String key, double value);

	/**
	 * Add a key:double[] pair to the preferences system
	 * 
	 * @param key    key - unique within the scope of the associated preferences
	 *               node.
	 * @param values array of double
	 */
	public void putDoubles(String key, double... values);

	/**
	 * Add a key:String pair to the preferences system
	 * 
	 * @param key   key - unique within the scope of the associated preferences
	 *              node.
	 * @param value String value
	 */
	public void putString(String key, String value);

	/**
	 * Add a key:String[] pair to the preferences system
	 * 
	 * @param key    key - unique within the scope of the associated preferences
	 *               node.
	 * @param values array of String
	 */
	public void putStrings(String key, String... values);

	/**
	 * Add a key:Enum{@literal <?>} pair to the preferences system.
	 * 
	 * @param key key - unique within the scope of the associated preferences node.
	 * @param e   Any enum.
	 */
	public void putEnum(String key, Enum<?> e);

	/**
	 * Get an int from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param def A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The int associated with this key.
	 */
	public int getInt(String key, int def);

	/**
	 * Get an int array from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param defs A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The int array associated with this key.
	 */
	public int[] getInts(String key, int... defs);

	/**
	 * Get a long from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param def A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The long associated with this key.
	 */
	public long getLong(String key, long def);

	/**
	 * Get a long array from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param defs A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The long array associated with this key.
	 */
	public long[] getLongs(String key, long... defs);

	/**
	 * Get a boolean from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param def A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The boolean associated with this key.
	 */
	public boolean getBoolean(String key, boolean def);

	/**
	 * Get a boolean array from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param defs A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The boolean array associated with this key.
	 */
	public boolean[] getBooleans(String key, boolean... defs);

	/**
	 * Get a float from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param def A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The float associated with this key.
	 */
	public float getFloat(String key, float def);

	/**
	 * Get a float array from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param defs A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The float array associated with this key.
	 */
	public float[] getFloats(String key, float... defs);

	/**
	 * Get a double from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param def A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The double associated with this key.
	 */
	public double getDouble(String key, double def);

	/**
	 * Get a double array from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param defs A default value. If the key is not present, the entry is created
	 *            using the default value and this value returned.
	 * @return The double array associated with this key.
	 */
	public double[] getDoubles(String key, double... defs);

	/**
	 * Get a String from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param def A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The String associated with this key.
	 */
	public String getString(String key, String def);

	/**
	 * Get a String array from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param defs A default value. If the key is not present, the entry is created
	 *            using the default value and this value returned.
	 * @return The String array associated with this key.
	 */
	public String[] getStrings(String key, String... defs);

	/**
	 * Get an Enum{@literal <?>} from the preferences system.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 * @param def A default value. If the key is not present, the entry is created
	 *            and the default value returned.
	 * @return The String associated with this key.
	 */
	public Enum<?> getEnum(String key, Enum<?> def);

	/**
	 * Remove an entry from the preferences.
	 * 
	 * @param key A key that is unique within the scope of the associated
	 *            preferences node.
	 */
	public void remove(String key);

	/**
	 * Forces an update to the backing store, if one exists, or to file.
	 */
	public void flush();

	/**
	 * @return true if no keys are found, false otherwise.
	 */
	public boolean isEmpty();

}
