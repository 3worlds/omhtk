package au.edu.anu.omhtk.rng;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class Pcg32Test {

	@Test
	void test() {
		Random r = new Pcg32();
		Long seed = 1234L;
		int n = 1000;
		double[] d1 = new double[n];
		r.setSeed(seed);
		for (int i = 0;i<n;i++)
			d1[i] = r.nextDouble();
		r.setSeed(seed);
		for (int i = 0;i<n;i++)
			assertTrue(d1[i]== r.nextDouble());
		
		seed = 123456789L;	
		r.setSeed(seed);
		for (int i = 0;i<n;i++)
			d1[i] = r.nextDouble();
		r.setSeed(seed);
		for (int i = 0;i<n;i++)
			assertTrue(d1[i]== r.nextDouble());

	}

}
