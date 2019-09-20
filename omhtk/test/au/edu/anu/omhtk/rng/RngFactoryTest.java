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
 *  along with UIT.  If not, see <https://www.gnu.org/licenses/gpl.html>. *
 *                                                                        *
 **************************************************************************/

package au.edu.anu.omhtk.rng;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import au.edu.anu.omhtk.rng.Pcg32;
import au.edu.anu.omhtk.rng.RngFactory;
import au.edu.anu.omhtk.rng.XSRandom;
import au.edu.anu.omhtk.rng.RngFactory.ResetType;
import au.edu.anu.omhtk.rng.RngFactory.SeedSource;

class RngFactoryTest {

	private final static int trials = 100_000_000;

//	private Integer[] intTest(Random rng) {
//		Integer[] res = new Integer[10];
//		for (int i = 0; i < res.length; i++)
//			res[i] = new Integer(0);
//		return res;
//	}

	private long timing(Random rng) {
		long s = System.nanoTime();
		double sum = 0;
		for (int i = 0; i < trials; i++)
			sum += rng.nextDouble();
		long e = System.nanoTime();
		double mean = sum / (double) trials;
		assertTrue(mean > 0.4999);
		assertTrue(mean < 0.5001);
		return e - s;
	}

	private void checkRange(Random rng) {
		double min = Double.MAX_VALUE;
		double max = -min;
		for (int i = 0; i < trials; i++) {
			double v = rng.nextDouble();
			min = Math.min(min, v);
			max = Math.max(max, v);
		}
		assertTrue(min >= 0.0);
		assertTrue(max < 1.0);
	}

	@Test
	void test() {

		RngFactory.makeRandom("Random", 0, ResetType.ONRUNSTART, SeedSource.SECURE, new Random());
		RngFactory.makeRandom("XSRandom", 0, ResetType.ONRUNSTART, SeedSource.ZERO, new XSRandom());
		RngFactory.makeRandom("PCGRandom", 0, ResetType.ONRUNSTART, SeedSource.TABLE, new Pcg32());
		//RngFactory.makeRandom("SecureRandom", 0, ResetType.NEVER, SeedSource.TABLE, new SecureRandom());

		Random random = RngFactory.getRandom("Random");
		Random xsRandom = RngFactory.getRandom("XSRandom");
		Random pcgRandom = RngFactory.getRandom("PCGRandom");
//		Random secureRandom = RngFactory.getRandom("SecureRandom");

		RngFactory.resetRun();
		double v1 = random.nextDouble();
		double v2 = xsRandom.nextDouble();
		double v3 = pcgRandom.nextDouble();
		RngFactory.resetRun();
		assertEquals(v1, random.nextDouble());
		assertEquals(v2, xsRandom.nextDouble());
		assertEquals(v3, pcgRandom.nextDouble());

		// Integer[] r1 = intTest(random);
		// System.out.println("Random: "+Arrays.deepToString(r1));
		// Integer[] r2 = intTest(xsRandom);
		// System.out.println("XSRandom: "+Arrays.deepToString(r2));
		// Integer[] r3 = intTest(pcgRandom);
		// System.out.println("PCGRandom: "+Arrays.deepToString(r3));
		// Integer[] r4 = intTest(secureRandom);
		// System.out.println("SecureRandom: "+Arrays.deepToString(r4));
		// String s = "\t";
		// System.out.println("Random"+s+"XSRandom"+s+"PCGRandom"+s+"SecureRandom");
		// for (int i= 0;i<r1.length;i++) {
		// System.out.println(r1[i]+s+r2[i]+s+r3[i]+s+r4[i]);
		// }

		System.out.println("Range check Random");
		checkRange(random);
		System.out.println("Range check XSRandom");
		checkRange(xsRandom);
		System.out.println("Range check PCGRandom");
		checkRange(pcgRandom);
		// System.out.println("Range check Random");
		// checkRange(secureRandom);

		RngFactory.resetRun();

		System.out.println("Time trial Random");
		double t1 = timing(random);
		System.out.println("Time trial XSRandom");
		double t2 = timing(xsRandom);
		System.out.println("Time trial PCGRandom");
		double t3 = timing(pcgRandom);
		// > 2000% slower
		// double t4 = timing(secureRandom);

		System.out.println(("xsRandom is: " + (1 - t2 / t1) * 100) + " % faster than Java.util.Random");
		System.out.println(("pcgRandom is: " + (1 - t3 / t1) * 100) + " % faster than Java.util.Random");
		// System.out.println(("SecureRandom is: "+(1-t4/t1)*100)+" % faster than
		// Java.util.Random");
	}

}
