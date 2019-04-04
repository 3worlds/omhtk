package au.edu.anu.rscs.aot.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import au.edu.anu.rscs.aot.OmhtkException;

/**
 * 
 * @author Jacques Gignoux - 4 avr. 2019
 *
 */
class IntegerRangeTest {

	@Test
	void testIntegerRangeIntInt() {
		IntegerRange r = new IntegerRange(0,3);
		assertEquals(r.toString(),"0..3");
		assertThrows(OmhtkException.class,()->new IntegerRange(3,0));
	}

	@Test
	void testIntegerRangeString() {
		IntegerRange r = new IntegerRange("0..3");
		assertEquals(r.toString(),"0..3");
		r = new IntegerRange("1..*");
		assertEquals(r.toString(),"1..*");
		assertThrows(OmhtkException.class,()->new IntegerRange("blah"));
	}

	@Test
	void testEqualsObject() {
		IntegerRange r = new IntegerRange("0..3");
		IntegerRange r2 = new IntegerRange("0..3");
		IntegerRange r3 = new IntegerRange("0..4");
		assertTrue(r.equals(r2));
		assertFalse(r.equals(r3));
	}

	@Test
	void testInRange() {
		IntegerRange r = new IntegerRange(-12,-2);
		assertTrue(r.inRange(-3));
		assertFalse(r.inRange(0));
		assertTrue(r.inRange(-2));
		assertTrue(r.inRange(-12));
	}

	@Test
	void testCheck() {
		IntegerRange r = new IntegerRange("25..*");
		assertThrows(OmhtkException.class,()->r.check(0));
		r.check(26);
	}

	@Test
	void testValueOf() {
		IntegerRange r = IntegerRange.valueOf("0..*");
		assertEquals(r.toString(),"0..*");
	}

	@Test
	void testToString() {
		IntegerRange r = new IntegerRange(-3,3);
		assertEquals(r.toString(),"-3..3");
	}

	@Test
	void testSetFirst() {
		IntegerRange r = new IntegerRange(-3,3);
		assertEquals(r.toString(),"-3..3");
		r.setFirst(0);
		assertEquals(r.toString(),"0..3");
	}

	@Test
	void testGetFirst() {
		IntegerRange r = new IntegerRange(-3,3);
		assertEquals(r.getFirst(),-3);
	}

	@Test
	void testSetLast() {
		IntegerRange r = new IntegerRange(-3,3);
		assertEquals(r.toString(),"-3..3");
		r.setLast(0);
		assertEquals(r.toString(),"-3..0");
	}

	@Test
	void testGetLast() {
		IntegerRange r = new IntegerRange(-3,3);
		assertEquals(r.getLast(),3);
	}

}
