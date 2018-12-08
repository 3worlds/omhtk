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

import java.util.Random;

/**
 * A subclass of java.util.random that implements the 
 * PCG32 random number generator
 * Based on the minimal code example by M.E. O'Neill / pcg-random.org
 * Licensed under Apache License 2.0
 */

/**
 * Author Ian Davies
 *
 * Date 8 Dec. 2018
 * 
 * cf:
 * http://demesos.blogspot.com/2015/11/random-numbers-in-java-comparison-of.html
 * 
 * TODO: It seems this does not work. It is not sufficient to override just next()
 * with this alg. We will have to wait for a better version. Perhaps we could
 * use a RandomInterface, copy java random and implement others as extentions of
 * Random but PCG as a new implementation of the interface.
 */
public class PCGRandom extends Random {
	private long inc;
	private long state;

	public PCGRandom(long seed) {
		this.state = seed;
		inc = 1;
	}

	public PCGRandom(long seed, long initseq) {
		// initseq selects the output sequence for the RNG
		this.state = seed;
		this.inc = initseq;
	}

	protected int next(int nbits) {
		long oldstate = state;
		// Advance internal state
		state = oldstate * 6364136223846793005L + (inc | 1);
		// Calculate output function (XSH RR), uses old state for max ILP
		long xorshifted = ((oldstate >> 18) ^ oldstate) >> 27;
		long rot = oldstate >> 59;
		return (int) ((xorshifted >> rot) | (xorshifted << ((-rot) & 31)));
	}
}
