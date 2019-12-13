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

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import au.edu.anu.rscs.aot.OmhtkException;
import fr.ens.biologie.generic.utils.Logging;

/**
 * Author Ian Davies
 *
 * Date Dec 5, 2018
 */
/**
 * <p>
 * Simple random number stream factory that manages resetting seeds in various
 * ways.
 * </p>
 * 
 * <p>
 * Usage: Model developers create a stream as:
 * </p>
 * 
 * {@code RandomFactory.makeRandom("test1", 0, ResetType.ONRUNSTART);}<br/>
 * <p>
 * Then use it as:
 * </p>
 * 
 * {@code Random rns = RandomFactory.getRandom("test1");}<br/>
 * 
 * {@code rns.nextDouble();} etc
 * </p>
 * 
 * <p>
 * This system contains a table of 1000 random numbers that have been generated
 * from atmospheric noise. This are used as seeds by the given index.
 * Alternatively, seeds can also be generated by SecureRandom algorithm.
 * </p>
 * 
 * 
 */
public class RngFactory {

	private static Random seedGenerator = new SecureRandom();
	private static Logger log = Logging.getLogger(RngFactory.class);

	public enum ResetType {
		/* Never reset */
		NEVER,
		/* Reset seed at start of every run. */
		ONRUNSTART,
		/* Reset seed at start of every experiment. */
		ONEXPERIMENTSTART,; //
	}

	public enum SeedSource {
		/* seed source from table by give index [0..999] */
		TABLE,
		/* seed produced by a call to SecureRandom - i.e 'never' replicated. */
		SECURE,
		/* seed set to zero (for debugging?) */
		ZERO,
		/*
		 * we could also have an option to create seed by system time but it serves no
		 * purpose
		 */
	}

	private final static class Generator {
		ResetType resetType;
		long seed;
		Random rng;

		private Generator(long seed, ResetType resetType, Random rng) {
			this.rng = rng;
			this.resetType = resetType;
			this.seed = seed;
			reset();
			log.info("Created random number stream" + toString());
		}

		private void reset() {
			rng.setSeed(seed);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("[class: ");
			sb.append(rng.getClass().getSimpleName());
			sb.append("; Seed: ");
			sb.append(seed);
			sb.append("; Reset: ");
			sb.append(resetType.name());
			sb.append("]");
			return sb.toString();

		}
	}

	private static Map<String, Generator> rngs = new HashMap<>();

	/**
	 * 
	 * There are 4 random number generators available. However, SecureRandom cannot
	 * be reset to a seed. As this factory relies on the ability to reset the seed
	 * and get a deterministic outcome it should not be included but could be used
	 * for some other purpose e.g.creating seed sets for other rngs. The currently
	 * available prng are:
	 * <ol>
	 * <li>Java.util.Random - medium speed, poor quality;</li>
	 * 
	 * <li>java.security.SecureRandom.SecureRandom() - slow, good quality, cannot be
	 * reset in the normal way so should only be used with ResetType.NEVER; Maybe
	 * this has changed now - not sure, But its very slow so really useless for us
	 * except for generating seeds for other generators.</li>
	 * 
	 * <li>au.edu.anu.fses.rng.XSRandom - very fast (76% faster than
	 * Java.util.Random), medium quality</li>
	 * 
	 * <li>au.edu.anu.fses.rng.Pcg32 - fast (65% faster than Java.util.Random) and
	 * good quality</li>
	 * </ol>
	 * 
	 * The choice is really between 3 & 4.
	 * 
	 * @param name      unique name
	 * @param seedIndex index into array[0..999] of naturally generated random
	 *                  numbers to act as seeds for resetting.
	 * @param resetType        type of reset method
	 * 
	 * @param source	Method of creating the random number seed
	 * @param rns       random number generator
	 */
	public static void makeRandom(String name, int seedIndex, ResetType resetType, SeedSource source, Random rns) {
		if (rns instanceof SecureRandom)
			throw new OmhtkException("SecureRandom algorithm is not supported.");
		if (source.equals(SeedSource.TABLE))
			if (seedIndex < 0 || seedIndex >= RandomSeeds.nSeeds())
				throw new OmhtkException(
						"SeedIndex is out of range [0.." + (RandomSeeds.nSeeds() - 1) + "] found: " + seedIndex);
		if (rngs.containsKey(name))
			throw new OmhtkException("Attempt to create duplicate random number generetor [" + name + "]");
		long seed;
		if (source.equals(SeedSource.TABLE))
			seed = RandomSeeds.getSeed(seedIndex);
		else if (source.equals(SeedSource.SECURE))
			seed = seedGenerator.nextLong();
		else if (rns instanceof XSRandom)
			seed = 1L; // NB Cannot be set to zero!
		else
			seed = 0L;
		log.info("Creating random stream [" + name + "; Seed: " + seed);
		Generator rng = new Generator(seed, resetType, rns);
		rngs.put(name, rng);
	}

	public static Random getRandom(String name) {
		try {
			return rngs.get(name).rng;
		} catch (NullPointerException e) {
			throw new OmhtkException("Random number stream not found: " + name);
		}
	}

	public static void resetRun() {
		reset(ResetType.ONRUNSTART);
	}

	public static void resetExperiment() {
		reset(ResetType.ONEXPERIMENTSTART);
	}
	
	/**
	 * A little check method to call before calling makeRandom(...) or getRandom(...)
	 * 
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		return rngs.containsKey(key);
	}

	private static void reset(ResetType type) {
		rngs.entrySet().forEach(entry -> {
			Generator rng = entry.getValue();
			if (rng.resetType.equals(type))
				rng.reset();
		});
	}

}
