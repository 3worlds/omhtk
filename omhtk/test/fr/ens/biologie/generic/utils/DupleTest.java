package fr.ens.biologie.generic.utils;

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
