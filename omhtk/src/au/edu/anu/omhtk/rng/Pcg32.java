/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2021: Shayne R. Flint, Jacques Gignoux & Ian D. Davies      *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent class hierarchies, plus some other utilities. The kind of  *
 *  things you need in all software projects and keep rebuilding all the  * 
 *  time.                                                                 *
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
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/
package au.edu.anu.omhtk.rng;

import java.util.Random;

/*
 * PCG Random Number Generation for Java
 *
 * Copyright 2014 Melissa O'Neill <oneill@pcg-random.org>, 2015 Alexey Romanov <alexey.v.romanov@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For additional information about the PCG random number generation scheme,
 * including its license and other licensing options, visit
 *
 * http://www.com.github.alexeyr.pcg.pcg-random.org
 */

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>Implementation of a <em>permuted congruential generator</em> (PCG). This family of pseudo-random
 * number generators includes some that are "simultaneously extremely fast, extremely statistically 
 * good, and extremely space efficient" (<a href="https://www.pcg-random.org/pdf/hmc-cs-2014-0905.pdf">O'Neill, 2014</a>).
 * Here, we use the PCG32 algorithm.</p>
 * <p>This class is a descendant of the {@link java.util.Random Random} class.
 * An instance of this class is used to generate a stream of pseudo-random
 * numbers.
 * The main differences are:</p>
 * <ul>
 * <li><a href="http://www.pcg-random.org/">PCG algorithm</a> is used; in
 * particular, this is a port of the
 * <a href="https://github.com/imneme/pcg-c-basic/">minimal C
 * implementation</a>.</li>
 * <li>Instances of Pcg32 are not thread-safe and so it doesn't obey the
 * {@link java.util.Random} contract.</li>
 * </ul>
 * 
 * @author Ian Davies - Dec 6, 2018
 */
public class Pcg32 extends Random {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long state;
	private long inc;

	private final static long MULTIPLIER = 6364136223846793005L;

	/**
	 * Creates a new random number generator using a {@code long} seed and a
	 * {@code long} stream number.
	 * <p>
	 * The invocation {@code new Pcg32(initState,initSeq)} is equivalent to:
	 * 
	 * <pre>
  Pcg32 rnd = new Pcg32();
  rnd.seed(initState, initSeq);
	 * </pre>
	 *
	 * @see #seed(long, long)
	 */
	public Pcg32(long initState, long initSeq) {
		seed(initState, initSeq);
	}

	/**
	 * Creates a new random number generator using current time (returned by
	 * {@link System#nanoTime()}) as the seed and a unique stream number.
	 * 
	 * @see #seed()
	 */
	public Pcg32() {
		seed();
	}

	/**
	 * Set the seed of this pseudo-random number generator.
	 */
	@Override
	 public synchronized void setSeed(long seed) {
		this.state = seed;
		this.inc = 1;
	}

	/**
	 * Returns the next pseudo-random, approximately uniformly distributed
	 * {@code int} value from this random number generator's sequence. The general
	 * contract of {@code nextInt} is that one {@code int} value is pseudo-randomly
	 * generated and returned. All 2<font size="-1"><sup>32 </sup></font> possible
	 * {@code int} values are produced with (approximately) equal probability.
	 *
	 * @see java.util.Random#nextInt()
	 */
	@Override
	public synchronized int nextInt() {
		long oldState = state;

		state = oldState * MULTIPLIER + inc;
		int xorShifted = (int) (((oldState >>> 18) ^ oldState) >>> 27);
		int rot = (int) (oldState >>> 59);
		return Integer.rotateRight(xorShifted, rot);
	}

	/**
	 * Returns the next pseudo-random, approximately uniformly distributed
	 * {@code int} value between 0 (inclusive) and {@code n} (exclusive).
	 *
	 */
	@Override
	public int nextInt(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be positive");
		}
		// the special treatment of powers of 2 as in Random.nextInt(int) shouldn't be
		// necessary
		int bits, val;
		do {
			bits = nextInt() >>> 1;
			val = bits % n;
		} while (bits - val + (n - 1) < 0);
		return val;
	}

	/**
	 * Returns the next pseudo-random, approximately uniformly distributed
	 * {@code long} value from this random number generator's sequence. The general
	 * contract of {@code nextLong} is that one {@code long} value is pseudo-randomly
	 * generated and returned. All 2<font size="-1"><sup>64 </sup></font> possible
	 * {@code long} values are produced with (approximately) equal probability.
	 *
	 */
	@Override
	public long nextLong() {
		return (((long) nextInt()) << 32) + ((long) nextInt());
	}

	/**
	 * Returns the next pseudo-random, approximately uniformly distributed
	 * {@code long} value between 0 (inclusive) and {@code n} (exclusive).
	 */
	
	public synchronized long nextLong(long n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be positive");
		}

		long bits, val;
		do {
			bits = nextLong() >>> 1;
			val = bits % n;
		} while (bits - val + (n - 1) < 0);
		return val;
	}

	/**
	 * Returns the next pseudo-random, approximately uniformly distributed
	 * {@code boolean} value.
	 *
	 */
	@Override
	public boolean nextBoolean() {
		return (nextInt() & 1) != 0;
	}

	/**
	 * Returns the next pseudo-random, approximately uniformly distributed
	 * {@code float} value between 0.0 (inclusive) and 1.0 (exclusive).
	 *
	 */
	@Override
	public float nextFloat() {
		// TODO see http://mumble.net/~campbell/2014/04/28/uniform-random-float
		return nextBits(24) / ((float) (1 << 24));
	}

	/**
	 * Returns the next pseudo-random, approximately uniformly distributed
	 * {@code float} value between 0.0 (inclusive) and bound (exclusive).
	 */
	public float nextFloat(float bound) {
		return bound * nextFloat();
	}

	/**
	 * Returns the next pseudo-random, approximately uniformly distributed
	 * {@code double} value between 0.0 (inclusive) and 1.0 (exclusive).
	 *
	 */
	@Override
	public double nextDouble() {
		// TODO see http://mumble.net/~campbell/2014/04/28/uniform-random-float
		return (((long) (nextBits(26)) << 27) + nextBits(27)) / (double) (1L << 53);
	}

	/**
	 * Returns the next pseudo-random, approximately uniformly distributed
	 * {@code double} value between 0.0 (inclusive) and bound (exclusive).
	 */
	public double nextDouble(double bound) {
		return bound * nextDouble();
	}

	/**
	 * Returns ... [TODO: missing description]
	 * @param bits
	 * @return
	 */
	// TODO: Ian, please document that one
	public int nextBits(int bits) {
		return nextInt() >>> (32 - bits);
	}

	private double nextNextGaussian;
	private boolean haveNextNextGaussian = false;

	/**
	 * Returns the next pseudo-random, Gaussian ("normally") distributed
	 * {@code double} value with mean {@code 0.0} and standard deviation {@code 1.0}
	 * from this random number generator's sequence.
	 *
	 */
	@Override
	public double nextGaussian() {
		// See Knuth, ACP, Section 3.4.1 Algorithm C.
		if (haveNextNextGaussian) {
			haveNextNextGaussian = false;
			return nextNextGaussian;
		} else {
			double v1, v2, s;
			do {
				v1 = 2 * nextDouble() - 1; // between -1 and 1
				v2 = 2 * nextDouble() - 1; // between -1 and 1
				s = v1 * v1 + v2 * v2;
			} while (s >= 1 || s == 0);
			double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s) / s);
			nextNextGaussian = v2 * multiplier;
			haveNextNextGaussian = true;
			return v1 * multiplier;
		}
	}

	/**
	 * Returns the next pseudo-random, Gaussian ("normally") distributed
	 * {@code double} value with given mean and standard deviation from this random
	 * number generator's sequence.
	 *
	 * @param mean the mean of the Gaussian distribution
	 * @param standardDeviation the standard deviation of the Gaussian distribution
	 * @return the next pseudorandom, Gaussian ("normally") distributed double value 
	 *  from this random number generator's sequence
	 */
	public double nextGaussian(double mean, double standardDeviation) {
		return nextGaussian() * standardDeviation + mean;
	}

	/**
	 * Initializes the generator with a {@code long} as the state and a 
	 * {@code long} stream number. The latter must be unique to ensure that multiple generators
	 * with the same seed will have different sequences.
	 * [TODO: check and fix description and parameters]
	 * 
	 * @param initState an initial state
	 * @param initSeq an initial stream
	 */
	// TODO: Ian please fix javadoc
	public void seed(long initState, long initSeq) {
		state = 0;
		inc = 2 * initSeq + 1;
		nextInt();
		state += initState;
		nextInt();
	}

	/**
	 * Initializes the generator with current time as the state and a (very likely
	 * to be) unique stream number. This ensures that even if multiple generators
	 * are initialized using this method while {@link System#nanoTime()} returns the
	 * same time, they will have different sequences.
	 */
	public void seed() {
		seed(System.nanoTime(), streamUniquifier());
	}

	private static long streamUniquifier() {
		for (;;) {
			long current = streamUniquifier.get();
			long next = current * 181783497276652981L;
			if (streamUniquifier.compareAndSet(current, next))
				return next;
		}
	}

	private static final AtomicLong streamUniquifier = new AtomicLong(System.identityHashCode(Pcg32.class));
}
