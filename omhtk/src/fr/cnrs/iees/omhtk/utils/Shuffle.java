package fr.cnrs.iees.omhtk.utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import au.edu.anu.omhtk.rng.Pcg32;

/**
 * Shuffle methods.
 * 
 * @author Ian Davies - 4 July 2022
 * 
 */
public class Shuffle {
	/**
	 * Random ordering of n indices. Substitutes {@link Pcg32} for java native
	 * random generator
	 * 
	 * @param n number of items.
	 * @return a random ordering of sequential numbers from 0 to n-1 inclusive.
	 */
	public static Integer[] shuffleIndices(int n) {
		Random rng = new Pcg32();
		return shuffleIndices(n, rng);
	}

	/**
	 * Random ordering of n indices with the given random number generator.
	 * 
	 * @param n   number of items.
	 * @param rng Random number generator.
	 * @return a random ordering of sequential numbers from 0 to n-1 inclusive.
	 */
	public static Integer[] shuffleIndices(int n, Random rng) {
		List<Integer> bag = IntStream.rangeClosed(0, n - 1).boxed().collect(Collectors.toList());
		Collections.shuffle(bag, rng);
		return bag.toArray(new Integer[0]);

	}

}
