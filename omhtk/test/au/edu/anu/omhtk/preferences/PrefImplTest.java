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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class PrefImplTest {

	@Test
	void test() {
		PrefImpl prefs = new PrefImpl(this,"_");
		String key1 = "Test1";
		String key2 = "Test2";
		String key3 = "Test3";
		String key4 = "Test4";
		String key5 = "Test5";
		String key6 = "Test6";
		String key7 = "Test7";
		String key8 = "Test8";
		String key9 = "Test9";
		String key10 = "Test10";
		int[] defInts = { 0, 0, 0, 0, 0 };
		long[] defLongs = { 0, 0, 0, 0, 0 };
		boolean[] defBooleans = { false, false, false, false };
		float[] defFloats = { 0, 0, 0, 0, 0 };
		String[] defStrings = { "s", "s", "s", "s", "s" };

		prefs.putInt(key1, 0);
		prefs.putInts(key2, defInts);
		prefs.putLong(key3, 0);
		prefs.putLongs(key4, defLongs);
		prefs.putBoolean(key5, false);
		prefs.putBooleans(key6, defBooleans);
		prefs.putFloat(key7, 0);
		prefs.putFloats(key8, defFloats);
		prefs.putString(key9, "s");
		prefs.putStrings(key10, defStrings);

		assertTrue(prefs.getInt(key1, 0) == 0);
		assertTrue(prefs.getInts(key2, defInts)[0] == 0);
		assertTrue(prefs.getLong(key3, 0) == 0L);
		assertTrue(prefs.getLongs(key4, defLongs)[0] == 0L);
		assertTrue(prefs.getBoolean(key5, false) == false);
		assertTrue(prefs.getBooleans(key6, defBooleans)[0] == false);
		assertTrue(prefs.getFloat(key7, 0) == 0.0f);
		assertTrue(prefs.getFloats(key8, defFloats)[0] == 0.0f);
		assertTrue(prefs.getString(key9, "s") == "s");
		assertTrue(prefs.getStrings(key10, defStrings)[0] == "s");

		// make changes
		prefs.putInt(key1, 1);
		prefs.putInts(key2, 1, 2, 3, 4, 5);
		prefs.putLong(key3, 1);
		prefs.putLongs(key4, 1, 2, 3, 4, 5);
		prefs.putBoolean(key5, true);
		prefs.putBooleans(key6, true, false, true, false);
		prefs.putFloat(key7, 10.5f);
		prefs.putFloats(key8, 1.1f, 2.2f, 3.3f, 4.4f, 5.5f);
		prefs.putString(key9, "ss");
		prefs.putStrings(key10, "s1", "s2", "s3", "s4", "s5");

		assertTrue(prefs.getInt(key1, 0) == 1);
		assertTrue(prefs.getInts(key2, defInts)[0] == 1);
		assertTrue(prefs.getInts(key2, defInts)[4] == 5);
		assertTrue(prefs.getLong(key3, 0) == 1L);
		assertTrue(prefs.getLongs(key4, defLongs)[0] == 1L);
		assertTrue(prefs.getLongs(key4, defLongs)[4] == 5L);
		assertTrue(prefs.getBoolean(key5, false) == true);
		assertTrue(prefs.getBooleans(key6, defBooleans)[0] == true);
		assertTrue(prefs.getBooleans(key6, defBooleans)[3] == false);
		assertTrue(prefs.getFloat(key7, 0) == 10.5f);
		assertTrue(prefs.getFloats(key8, defFloats)[0] == 1.1f);
		assertTrue(prefs.getFloats(key8, defFloats)[4] == 5.5f);
		assertTrue(prefs.getString(key9, "s") == "ss");
		assertTrue(prefs.getStrings(key10, defStrings)[0] == "s1");
		assertTrue(prefs.getStrings(key10, defStrings)[4] == "s5");

		prefs.remove(key1);
		prefs.remove(key2);
		prefs.remove(key3);
		prefs.remove(key4);
		prefs.remove(key5);
		prefs.remove(key6);
		prefs.remove(key7);
		prefs.remove(key8);
		prefs.remove(key9);
		prefs.remove(key10);

		assertTrue(prefs.isEmpty());

	}

}
