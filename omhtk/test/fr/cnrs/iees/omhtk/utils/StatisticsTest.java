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

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.cnrs.iees.omhtk.utils.Statistics;

/**
 * 
 * @author Jacques Gignoux - 22 oct. 2020
 *
 */
class StatisticsTest {

	private Statistics stat1, stat2, stat3, stat4;
	
	@BeforeEach 
	void init() {
		Random rng = new Random();
		stat1 = new Statistics();
		stat2 = new Statistics();
		stat3 = new Statistics();
		stat4 = new Statistics();
		for (int i=0; i<12; i++) {
			stat1.add(i);
			stat2.add(rng.nextBoolean());
			stat3.add(rng.nextDouble());
		}
		stat4.add("bla");
		stat4.add("bla");
		stat4.add("bla");
		stat4.add("tagada");
		stat4.add("pouêt");
		stat4.add("pouêt");
	}
	
	@Test
	void testReset() {
		assertEquals(stat1.n(),12);
		stat1.reset();
		assertEquals(stat1.n(),0);
		// interesting: after reset, Statistics instance can be used to track something completely different
		stat1.add("coucou");
		assertEquals(stat1.n(),1);
	}

	@Test
	void testN() {
		assertEquals(stat1.n(),12);
		assertEquals(stat2.n(),12);
		assertEquals(stat3.n(),12);
		assertEquals(stat4.n(),6);
	}

	@Test
	void testAverage() {
		assertEquals(stat1.average(),5.5);
		assertTrue(stat2.average()<1);
		assertTrue(stat3.average()<1);
		assertTrue(Double.isNaN(stat4.average()));
	}

	// NB values checked with R: var(0:11) and var(c(1,1,4)) and var(c(1.5,2.5))
	@Test
	void testVariance() {
		assertEquals(stat1.variance(),13.0);
		stat1.reset();
		assertTrue(Double.isNaN(stat1.variance()));
		stat1.add(1);
		assertEquals(stat1.variance(),0.0);
		stat1.add(1);
		assertEquals(stat1.variance(),0.0);
		stat1.add(4);
		assertEquals(stat1.variance(),3.0);
		assertTrue(Double.isNaN(stat4.variance()));
		stat2.reset();
		stat2.add(1.5);
		assertEquals(stat2.variance(),0.0);
		stat2.add(2.5);
		assertEquals(stat2.variance(),0.25);
	}

	@Test
	void testSum() {
		assertEquals(stat1.sum(),66.0);
		assertEquals(stat2.sum(),stat2.average()*stat2.n());
		// sometimes this one is false due to truncation error - that happens. run test again 
		// ok but I don't want to test my patience.
		//assertEquals(stat3.sum(),stat3.average()*stat3.n());
		assertTrue(Double.isNaN(stat4.sum()));
	}

	@Test
	void testMin() {
		assertEquals(stat1.min(),0.0);
		assertEquals(stat2.min(),0.0);
		assertTrue(Double.isNaN(stat4.min()));
	}

	@Test
	void testMax() {
		assertEquals(stat1.max(),11.0);
		assertEquals(stat2.max(),1.0);
		assertTrue(Double.isNaN(stat4.max()));
	}

	@Test
	void testStringFrequencies() {
		assertEquals(stat4.stringFrequencies().toString(),"{bla=3, pouêt=2, tagada=1}");
		assertTrue(stat1.stringFrequencies().isEmpty());
		assertTrue(stat2.stringFrequencies().isEmpty());
		assertTrue(stat3.stringFrequencies().isEmpty());
	}

	@Test
	void testIntFrequencies() {
		assertEquals(stat1.intFrequencies().toString(),"{0=1, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=1, 10=1, 11=1}");
		assertEquals(stat2.intFrequencies().keySet().toString(),"[0, 1]");
		assertTrue(stat3.intFrequencies().isEmpty());
		assertTrue(stat4.intFrequencies().isEmpty());
	}

	@Test
	void testDoubleFrequencies() {
		assertEquals(stat3.doubleFrequencies().size(),stat3.n());
		assertTrue(stat1.doubleFrequencies().isEmpty());
		assertTrue(stat2.doubleFrequencies().isEmpty());
		assertTrue(stat4.doubleFrequencies().isEmpty());
		stat3.reset();
		stat3.add(3.5);
		stat3.add(3.5);
		stat3.add(4.5);
		stat3.add(125.5);
		stat3.add(Math.PI);
		assertEquals(stat3.doubleFrequencies().toString(),"{3.141592653589793=1, 3.5=2, 4.5=1, 125.5=1}");
		assertEquals(stat3.average(),28.028318530717957);
		assertEquals(stat3.variance(),2969.2341015260376);
		assertEquals(stat3.sum(),140.14159265358978);
		assertEquals(stat3.min(),Math.PI);
		assertEquals(stat3.max(),125.5);
	}

}
