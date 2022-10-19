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
package fr.ens.biologie.generic.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.cnrs.iees.omhtk.utils.Interval;

/**
 * 
 * @author Jacques Gignoux - 12 juin 2019
 *
 */
class IntervalTest {

	private Interval i, j, k, l;
	
	@BeforeEach
	private void init() {
		i = Interval.newInstance(2.0,5.5);
		j = Interval.open(Double.NEGATIVE_INFINITY,3.0);
		k = Interval.halfOpenInf(2.0,5.5);
		l = Interval.halfOpenSup(2.0,5.5);
	}

	@Test
	final void testClosedInterval() {
		i = Interval.closed(2.0,3.0);
		assertEquals(i.toString(),"[2.0,3.0]");
	}

	@Test
	final void testOpenInterval() {
		i = Interval.open(2.0,3.0);
		assertEquals(i.toString(),"]2.0,3.0[");
	}

	@Test
	final void testHalfOpenInfInterval() {
		i = Interval.halfOpenInf(2.0,3.0);
		assertEquals(i.toString(),"]2.0,3.0]");
	}

	@Test
	final void testHalfOpenSupInterval() {
		i = Interval.halfOpenSup(2.0,3.0);
		assertEquals(i.toString(),"[2.0,3.0[");
	}

	@Test
	final void testToNegInf() {
		i = Interval.toNegInf(12.0);
		assertEquals(i.toString(),"]-∞,12.0]");
	}

	@Test
	final void testToPosInf() {
		i = Interval.toPosInf(12.0);
		assertEquals(i.toString(),"[12.0,+∞[");
	}
	@Test
	final void testOpenToNegInf() {
		i = Interval.openToNegInf(12.0);
		assertEquals(i.toString(),"]-∞,12.0[");
	}
	@Test
	final void testOpenToPosInf() {
		i = Interval.openToPosInf(12.0);
		assertEquals(i.toString(),"]12.0,+∞[");
	}
	
	
	@Test
	final void testSup() {
		assertEquals(i.sup(),5.5);
	}

	@Test
	final void testInf() {
		assertEquals(i.inf(),2.0);
	}

	@Test
	final void testHalfOpenInf() {
		assertFalse(i.halfOpenInf());
		assertTrue(j.halfOpenInf());
		assertTrue(k.halfOpenInf());
		assertFalse(l.halfOpenInf());
	}

	@Test
	final void testHalfOpenSup() {
		assertFalse(i.halfOpenSup());
		assertTrue(j.halfOpenSup());
		assertFalse(k.halfOpenSup());
		assertTrue(l.halfOpenSup());
	}

	@Test
	final void testContains() {
		assertTrue(i.contains(2));
		assertTrue(j.contains(2));
		assertFalse(k.contains(2));
		assertTrue(l.contains(2));
		assertTrue(i.contains(5.5d));
		assertTrue(j.contains(-5.5d));
		assertTrue(k.contains(5.5d));
		assertFalse(l.contains(5.5d));
	}

	@Test
	final void testToString() {
		assertEquals(i.toString(),"[2.0,5.5]");
		assertEquals(j.toString(),"]-∞,3.0[");
		assertEquals(k.toString(),"]2.0,5.5]");
		assertEquals(l.toString(),"[2.0,5.5[");
	}

	@Test
	final void testValueOf() {
		Interval z = Interval.valueOf("[2.0,5.5[");
		assertEquals(z,l);
		z = Interval.valueOf("[2.0,+Inf[");
		assertEquals(z.toString(),"[2.0,+∞[");
	}

	@Test
	final void testOverlaps() {
		assertTrue(i.overlaps(j));
		Interval z = Interval.open(0,2); 
		assertFalse(i.overlaps(z));
	}

	@Test
	final void testUnion() {
		assertEquals(k.union(l),i);
		assertEquals(i.union(j).toString(),"]-∞,5.5]");
	}
	
	@Test
	final void testIntersection() {
		assertEquals(i.intersection(k),k);
		Interval z = Interval.closed(0,2);
		assertEquals(i.intersection(z).toString(),"[2.0,2.0]");
		assertEquals(k.intersection(j).toString(),"]2.0,3.0[");
	}
	
	@Test
	final void testContiguousTo() {
		Interval z = Interval.closed(0,2);
		assertTrue(z.contiguousTo(k));
		assertFalse(z.contiguousTo(l));
	}

}
