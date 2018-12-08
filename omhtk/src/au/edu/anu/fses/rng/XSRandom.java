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
 * Author Ian Davies
 *
 * Date 8 Dec. 2018
 * 
 * imported from:
 * 
 * http://demesos.blogspot.com/2011/09/replacing-java-random-generator.html
 */

public class XSRandom extends Random{
	private static final long serialVersionUID = 6208727693524452904L;
	private long seed;

	/**
	 * Creates a new pseudo random number generator. The seed is initialized to
	 * the current time, as if by
	 * <code>setSeed(System.currentTimeMillis());</code>.
	 */
	public XSRandom() {
		this(System.nanoTime());
	}

	/**
	 * Creates a new pseudo random number generator, starting with the specified
	 * seed, using <code>setSeed(seed);</code>.
	 * 
	 * @param seed
	 *            the initial seed
	 */
	public XSRandom(long seed) {
		this.seed = seed;
	}

	/**
	 * Returns the current state of the seed, can be used to clone the object
	 * 
	 * @returns the current seed
	 */
	public synchronized long getSeed() {
		return seed;
	}

	/**
	 * Sets the seed for this pseudo random number generator. As described
	 * above, two instances of the same random class, starting with the same
	 * seed, produce the same results, if the same methods are called.
	 * 
	 * @param s
	 *            the new seed
	 */
	public synchronized void setSeed(long seed) {
		this.seed = seed;
		super.setSeed(seed);
	}

	/**
	 * Returns an XSRandom object with the same state as the original
	 */
	public XSRandom clone() {
		return new XSRandom(getSeed());
	}

	/**
	 * Implementation of George Marsaglia's elegant Xorshift random generator
	 * 30% faster and better quality than the built-in java.util.random see also
	 * see http://www.javamex.com/tutorials/random_numbers/xorshift.shtml
	 */
	protected int next(int nbits) {
		long x = seed;
		x ^= (x << 21);
		x ^= (x >>> 35);
		x ^= (x << 4);
		seed = x;
		x &= ((1L << nbits) - 1);
		return (int) x;
	}
}
