package fr.ens.biologie.generic.utils;

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
	 * Random ordering of n indices.
	 * 
	 * @param n number of items.
	 * @return a random ordering.
	 */
	public static Integer[] shuffleIndices(int n) {
		Random rnd = new Pcg32();
		List<Integer> bag = IntStream.rangeClosed(0, n-1).boxed().collect(Collectors.toList());
		Collections.shuffle(bag);
		return bag.toArray(new Integer[0]);
	}

}
