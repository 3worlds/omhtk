package fr.ens.biologie.generic.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PairTest {

	@Test
	void testHashCode() {
		Pair<Integer,Double> p =new Pair<>(1,2.0);
		Pair<Integer,Double> p2 =new Pair<>(1,2.0);
		assertTrue(p.hashCode()==p2.hashCode());
	}

	@Test
	void testGetKey() {
		Pair<Integer,Double> p =new Pair<>(1,2.1);
		assertEquals((int)p.getKey(),1);
	}

	@Test
	void testGetValue() {
		Pair<Integer,Double> p =new Pair<>(1,2.1);
		assertEquals((double)p.getValue(),2.1);
	}

	@Test
	void testToString() {
		Pair<Integer,Double> p =new Pair<>(1,2.0);
		assertEquals(p.toString(),"1=2.0");
	}

	@Test
	void testEqualsObject() {
		Pair<Integer,Double> p =new Pair<>(1,2.0);
		assertFalse(p.equals(null));
		Pair<Integer,Double> p2 =new Pair<>(1,2.0);
		assertTrue(p.equals(p2));
		Pair<String,Double> p3 =new Pair<>("1",2.0);
		assertFalse(p.equals(p3));
		Pair<Integer,Double> p4 =new Pair<>(null,2.0);
		assertFalse(p.equals(p4));
		Pair<Integer,Double> p5 =new Pair<>(null,3.0);
		assertFalse(p4.equals(p5));
		Pair<Integer,Double> p6 =new Pair<>(null,2.0);
		assertTrue(p4.equals(p6));
	}

}
