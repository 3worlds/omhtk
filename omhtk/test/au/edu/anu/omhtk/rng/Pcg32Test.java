package au.edu.anu.omhtk.rng;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class Pcg32Test {

	@Test
	void test() {
		Random r = new Pcg32();
		Long seed = 1234L;
		r.setSeed(seed);
		double d1 = r.nextDouble();
		double d2 = r.nextDouble();
		double d3 = r.nextDouble();
		r.setSeed(seed);
		double d4 = r.nextDouble();
		double d5 = r.nextDouble();
		double d6 = r.nextDouble();
		assertTrue(d1==d4);
		assertTrue(d2==d5);
		assertTrue(d3==d6);
		
	}

}
