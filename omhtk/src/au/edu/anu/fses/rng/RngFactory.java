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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import au.edu.anu.rscs.aot.OmhtkException;

/**
 * Author Ian Davies
 *
 * Date Dec 5, 2018
 */

/**
 * Simplest possible random number stream factory for simulators. I have not
 * allowed for different types of PRNG. The java PRNG will reportedly not
 * change, is fast and well reviewed. I presume it suffers from the "planes"
 * problem but not sure.
 * 
 * Model developers create a stream as:
 * 
 * RandomFactory.makeRandom("test1", 0, ResetType.ONRUNSTART);
 * 
 * Then use it as:
 * 
 * Random rns = RandomFactory.getRandom("test1");
 * 
 * rns.nextDouble(); etc
 * 
 */
public class RngFactory {
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
		int seedIndex;
		Random rns;

		private Generator(int seedIndex, ResetType rt) {
			this.rns = new Random();
			this.seedIndex = seedIndex;
			this.rt = rt;
			// DO NOT use a fixed seed for "NEVER" - use that supplied by the system.
			if (!rt.equals(ResetType.NEVER))
				reset();
			else
				this.seedIndex = -1;// should cause a crash if mistakenly used.
		}

		private void reset() {
			rns.setSeed(RandomSeeds.getSeed(seedIndex));
		}
	}

	private static Map<String, Generator> rngs = new HashMap<>();

	public static void makeRandom(String name, int seedIndex, ResetType st) {
		if (!rngs.containsKey(name)) {
			Generator rng = new Generator(seedIndex, st);
			rngs.put(name, rng);
		} else
			throw new OmhtkException("Attempt to create duplicate random number generetor (" + name + ")");
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
