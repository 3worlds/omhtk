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
 * <p>Simplest possible random number stream factory for simulators.</p>
 * 
 * <p>Model developers create a stream as:</p>
 * 
 * {@code RandomFactory.makeRandom("test1", 0, ResetType.ONRUNSTART);}<br/>
 * <p>Then use it as:</p>
 * 
 * {@code Random rns = RandomFactory.getRandom("test1");}<br/>
 * 
 * {@code rns.nextDouble();} etc</p>
 * 
 * <p>This system contains a table of 1000 random numbers that have been generated
 * from atmospheric noise. This are used as seeds by the given index. If the
 * supplied seedIndex is outside the table range, then a seed will be generated
 * by SecureRandom algorithm.</p>
 * 
 * 
 */
public class RngFactory {
	
	private static Random seedGenerator = new SecureRandom();
	private static Logger log = Logging.getLogger(RngFactory.class);

	public enum ResetType {
		/* Do not use a seed. Always created with a new "unique" seed */
		NEVER,
		/* Set the seed only at model creation time - for debugging */
		ONCREATIONSTART,
		/* Reset seed at start of every run */
		ONRUNSTART,
		/* Reset seed at start of every experiment */
		ONEXPERIMENTSTART; //
	}

	private final static class Generator {
		ResetType rt;
		long seed;
		Random rns;

		private Generator(int seedIndex, ResetType rt, Random rnd) {
			this.rns = rnd;
			if (seedIndex >= 0 && seedIndex < RandomSeeds.nSeeds()) {
				log.info("Get seed from table [" + seedIndex + "]");
				seed = RandomSeeds.getSeed(seedIndex);
			} else {
				log.info("Get seed from SecureRandom [index: " + seedIndex + "]");
				seed = seedGenerator.nextLong();
			}
			this.rt = rt;

			if (!rt.equals(ResetType.NEVER))
				reset();
			log.info("Created random number stream" + toString());
		}

		private void reset() {
			rns.setSeed(seed);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("[class: ");
			sb.append(rns.getClass().getSimpleName());
			sb.append("; Seed: ");
			sb.append(seed);
			sb.append("; Reset: ");
			sb.append(rt.name());
			sb.append("]");
			return sb.toString();

		}
	}

	private static Map<String, Generator> rngs = new HashMap<>();

	/**
	 * 
	 * There are 4 random number generators available. However, SecureRandom cannot be
	 * reset to a seed. As this factory relies on the ability to reset the seed and
	 * get a deterministic outcome it should not be included but could be used for
	 * some other purpose e.g.creating seed sets for other rngs. The currently
	 * available prng are:
	 * <ol>
	 * <li> Java.util.Random - medium speed, poor quality;</li>
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
	 * @param st        type of reset method
	 * @param rnd       random number generator
	 */
	public static void makeRandom(String name, int seedIndex, ResetType st, Random rnd) {
		if (rnd instanceof SecureRandom) {
			log.warning("Creating random number stream with VERY slow algorithm.[" + name + "]");
		}
		if (!rngs.containsKey(name)) {
			if ((rnd instanceof SecureRandom) && (!st.equals(ResetType.NEVER)))
				throw new OmhtkException("Can only use SecureRandom with ResetType.NEVER.[" + name + "]");

			log.info("Creating random stream [" + name + "]");
			Generator rng = new Generator(seedIndex, st, rnd);
			rngs.put(name, rng);
		} else
			throw new OmhtkException("Attempt to create duplicate random number generetor [" + name + "]");
	}

	public static Random getRandom(String name) {
		try {
			return rngs.get(name).rns;
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

	private static void reset(ResetType type) {
		rngs.entrySet().forEach(entry -> {
			Generator rng = entry.getValue();
			if (rng.rt.equals(type))
				rng.reset();
		});
	}

}
