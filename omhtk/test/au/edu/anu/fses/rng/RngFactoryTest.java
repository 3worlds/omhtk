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

	@Test
	void test() {
		RngFactory.makeRandom("Random", 0, ResetType.ONCREATIONSTART, new Random());
		Random random = RngFactory.getRandom("Random");
		assertNotNull(random);
		System.out.println("Random: "+ random.nextLong());
		assertNotEquals(random.nextLong(),random.nextLong());
		
		RngFactory.makeRandom("SecureRandom", 0, ResetType.ONCREATIONSTART, new SecureRandom());
		Random secureRandom = RngFactory.getRandom("SecureRandom");
		assertNotNull(secureRandom);
		System.out.println("SercureRandom: "+ secureRandom.nextLong());	
		assertNotEquals(secureRandom.nextLong(),secureRandom.nextLong());

		RngFactory.makeRandom("XSRandom", 0, ResetType.ONCREATIONSTART, new XSRandom());
		Random xsRandom = RngFactory.getRandom("XSRandom");
		assertNotNull(xsRandom);
		System.out.println("XSRandom: "+ xsRandom.nextLong());	
		assertNotEquals(xsRandom.nextLong(),xsRandom.nextLong());

		RngFactory.makeRandom("PCGRandom", 0, ResetType.ONCREATIONSTART, new PCGRandom(0));
		Random pcgRandom = RngFactory.getRandom("PCGRandom");
		assertNotNull(pcgRandom);
		System.out.println("PCGRandom: "+ pcgRandom.nextLong());	
		assertNotEquals(pcgRandom.nextLong(),pcgRandom.nextLong());
		String s = "\t";
		for (int i = 0;i<100;i++) {
			System.out.println(random.nextLong()+s+secureRandom.nextLong()+s+xsRandom.nextLong()+s+pcgRandom.nextLong());
		}
	// Only nextLong seems valid for pcgRandom. Other methods will need to be overwritten!
		
//		for (int i = 0;i<1000;i++) {
//			double v = pcgRandom.nextDouble();
//			assertTrue(v<=1.0);
//			assertTrue(v>=0.0);
//		}
	}

}
