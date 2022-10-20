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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DupleTest {
	
	private Duple<String,Integer> dup1 = new Duple<>("blah",2);
	private Duple<String,Integer> dup2 = new Duple<>("blah",2);
	private Duple<String,Integer> dup3 = new Duple<>("lah",2);
	private Duple<String,Integer> dup4 = new Duple<>("blah",3);
	private Duple<String,String> dup5 = new Duple<>("blah","2");
	private Duple<String,String> dup6 = new Duple<>(null,"2");

	@Test
	final void testGetFirst() {
		assertEquals(dup1.getFirst(),"blah");
	}

	@Test
	final void testGetSecond() {
		assertEquals(dup5.getSecond(),"2");
	}

	@Test
	final void testToString() {
		assertEquals(dup1.toString(),"blah|2");
	}

	@Test
	final void testEqualsObject() {
		assertTrue(dup1.equals(dup2));
		assertTrue(dup2.equals(dup1));
		assertFalse(dup1.equals(dup3));
		assertFalse(dup1.equals(dup4));
		assertFalse(dup1.equals(dup5));
		assertFalse(dup3.equals(dup6));
	}

}
