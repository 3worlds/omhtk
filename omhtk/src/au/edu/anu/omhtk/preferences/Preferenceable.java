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
package au.edu.anu.omhtk.preferences;

/**
 * Java has a preferences API {@link java.util.prefs.Preferences} . Therefore, I
 * hope we no longer need the graph-dependent implementation for preference saving.
 * However, the Java system only deals with primitives and so this code is an
 * interface to a preference helper class for 3Worlds to implement array handling.
 * 
 * @author Ian Davies - Dec 11, 2018
 */
// TODO: Ian, please refactor javadoc and comment every entry below.
public interface Preferenceable {
	public void putInt(String key, int value);
	public void putInts(String key, int... values);
	public void putLong(String key, long value);
	public void putLongs(String key, long... values);
	public void putBoolean(String key, boolean value);
	public void putBooleans(String key, boolean... values);
	public void putFloat(String key, float value);
	public void putFloats(String key, float... values);
	public void putDouble(String key, double value);
	public void putDoubles(String key, double... values);
	public void putString(String key, String value);
	public void putStrings(String key, String... values);
	
	public void putEnum(String key,Enum<?> e);

	public int getInt(String key,int def);
	public int[] getInts(String key, int... defs);
	public long getLong(String key,long def);
	public long[] getLongs(String key, long... defs);
	public boolean getBoolean(String key,boolean def);
	public boolean[] getBooleans(String key, boolean... defs);
	public float getFloat(String key,float def);
	public float[] getFloats(String key, float... defs);
	public double getDouble(String key, double def);
	public double[] getDoubles(String key, double...defs);
	public String getString(String key,String def);
	public String[] getStrings(String key, String... defs);
	
	public Enum<?> getEnum(String key,Enum<?> def);
	
	public void remove(String key);
	public void flush();
	public boolean isEmpty();

}
