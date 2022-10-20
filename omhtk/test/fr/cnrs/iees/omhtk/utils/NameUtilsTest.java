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
package fr.cnrs.iees.omhtk.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.cnrs.iees.omhtk.utils.NameUtils;

/**
 * 
 * @author Jacques Gignoux - 4 juil. 2019
 *
 */
class NameUtilsTest {

	private String s = "1	bla bla bla °à\t+^ZZ 22";
	private String s2 = "this is a stupid String.";
	
//	private void show(String method,String text) {
//		System.out.println(method+": "+text);
//	}
	
	@Test
	final void testWordUpperCaseName() {
		assertEquals("1BlaBlaBlaZZ22",NameUtils.wordUpperCaseName(s));
		assertEquals("thisIsAStupidString",NameUtils.wordUpperCaseName(s2));
	}

	@Test
	final void testInitialUpperCase() {
		assertEquals("1	bla bla bla °à	+^ZZ 22",NameUtils.initialUpperCase(s));
		assertEquals("This is a stupid String.",NameUtils.initialUpperCase(s2));
	}

	@Test
	final void testWordUnderscoreName() {
		assertEquals("1_bla_bla_bla_ZZ_22",NameUtils.wordUnderscoreName(s));
		assertEquals("this_is_a_stupid_String",NameUtils.wordUnderscoreName(s2));
	}

	@Test
	final void testValidJavaName() {
		assertEquals("_1XblaXblaXblaXXXXXXZZX22",NameUtils.validJavaName(s));
		assertEquals("thisXisXaXstupidXStringX",NameUtils.validJavaName(s2));
	}

}
