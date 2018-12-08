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

package au.edu.anu.fses.rng;

import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.Random;

import org.junit.jupiter.api.Test;

import au.edu.anu.fses.rng.RngFactory.ResetType;

class RngFactoryTest {

	private long timing(Random rng) {
		long s = System.nanoTime();
		double sum = 0;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < 100_000_000; i++)
			sum += rng.nextDouble();
		long e = System.nanoTime();
		System.out.println(e - s + " nano seconds. Sum: " + sum);
		return e-s;
	}
	private void checkRange(Random rng) {
		double min = Double.MAX_VALUE;
		double max = -min;
		for (int i = 0; i < 100_000_000; i++) {
			double v = rng.nextDouble();
			min=Math.min(min, v);
			max=Math.max(max, v);
		}
		assertTrue(min>=0.0);
		assertTrue(max<1.0);
		
//		System.out.println("min: "+min+" max: "+max);
				
	}

	@Test
	void test() {
		String s = "\t";

		RngFactory.makeRandom("Random", 0, ResetType.ONRUNSTART, new Random());
//		RngFactory.makeRandom("SecureRandom", 0, ResetType.ONRUNSTART, new SecureRandom());
		RngFactory.makeRandom("XSRandom", 0, ResetType.ONRUNSTART, new XSRandom());
		RngFactory.makeRandom("PCGRandom", 0, ResetType.ONRUNSTART, new Pcg32());

		Random random = RngFactory.getRandom("Random");
//		Random secureRandom = RngFactory.getRandom("SecureRandom");
		Random xsRandom = RngFactory.getRandom("XSRandom");
		Random pcgRandom = RngFactory.getRandom("PCGRandom");
		
		
//		System.out.println("random" + s + "xsRandom" + s + "pcgRandom");

		// SecureRandom cannot be reset like this
		RngFactory.resetRun();
		double v1 = random.nextDouble();
		double v2 = xsRandom.nextDouble();
		double v3 = pcgRandom.nextDouble();
		RngFactory.resetRun();
		assertEquals(v1, random.nextDouble());
		assertEquals(v2, xsRandom.nextDouble());
		assertEquals(v3, pcgRandom.nextDouble());
		
		checkRange(random);
		checkRange(xsRandom);
		checkRange(pcgRandom);

		RngFactory.resetRun();
		double t1 = timing(random);
		double t2 = timing(xsRandom);
		double t3 = timing(pcgRandom);
		//timing(new SecureRandom()); too slow to be useful for simulation but maybe something else
		System.out.println((1-t2/t1)*100);
		System.out.println((1-t3/t1)*100);
		
		

	}

}
