package fr.ens.biologie.generic.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author Jacques Gignoux - 12 juin 2019
 *
 */
class IntervalTest {

	private Interval i, j, k, l;
	
	@BeforeEach
	private void init() {
		i = Interval.interval(2.0,5.5);
		j = Interval.openInterval(Double.NEGATIVE_INFINITY,3.0);
		k = Interval.halfOpenInfInterval(2.0,5.5);
		l = Interval.halfOpenSupInterval(2.0,5.5);
	}

	@Test
	final void testClosedInterval() {
		i = Interval.closedInterval(2.0,3.0);
		assertEquals(i.toString(),"[2.0,3.0]");
	}

	@Test
	final void testOpenInterval() {
		i = Interval.openInterval(2.0,3.0);
		assertEquals(i.toString(),"]2.0,3.0[");
	}

	@Test
	final void testHalfOpenInfInterval() {
		i = Interval.halfOpenInfInterval(2.0,3.0);
		assertEquals(i.toString(),"]2.0,3.0]");
	}

	@Test
	final void testHalfOpenSupInterval() {
		i = Interval.halfOpenSupInterval(2.0,3.0);
		assertEquals(i.toString(),"[2.0,3.0[");
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
	final void testContainsInt() {
		fail("Not yet implemented");
	}

	@Test
	final void testContainsLong() {
		fail("Not yet implemented");
	}

	@Test
	final void testContainsShort() {
		fail("Not yet implemented");
	}

	@Test
	final void testContainsByte() {
		fail("Not yet implemented");
	}

	@Test
	final void testContainsDouble() {
		fail("Not yet implemented");
	}

	@Test
	final void testContainsFloat() {
		fail("Not yet implemented");
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
	}

}
