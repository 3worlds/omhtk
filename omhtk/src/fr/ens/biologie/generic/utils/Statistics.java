package fr.ens.biologie.generic.utils;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Functions to compute statistics iteratively. They all return stat(n+1) as a
 * function of x, n, and stats(n). Optimised for speed.
 * <p>
 * This class performs statistics using values that regularly come in. It assumes the values
 * come from a single variable which values evolve over time. It would be badly wrong to
 * use only one instance of this class to track both an integer and a double variable, since
 * all values would be mixed in a single distribution. The use
 * pattern is as follows:
 * 
 * <pre>
 * stats = new Statistics();
 * stats.add(x);
 * stats.add(y);
 * stats.add(z)
 * stats.average(); // returns the mean of x, y and z
 * stats.n();       // returns 3 (number of observations)
 * stats.reset();   // prepares for a new series of numbers
 * </pre>
 * </p>
 * 
 * @author Jacques Gignoux - 21 juin 2017
 *
 */
// tested OK with version 0.2.2 on 22/10/2020
public class Statistics {

	private double mu = 0;
	private double var = 0;
	private double ss = 0;
	private double sum = 0;
	private int n = 0;
	private double min;
	private double max;
	private SortedMap<String,Integer> stringDistribution;
	private SortedMap<Long,Integer> intDistribution;
	private SortedMap<Double,Integer> doubleDistribution;

	public Statistics() {
		super();
		stringDistribution = new TreeMap<>();
		intDistribution = new TreeMap<>();
		doubleDistribution = new TreeMap<>();
		reset();
	}

	public Statistics(String separator) {
		super();
	}
	public Statistics reset() {
		mu = Double.NaN;
		var = Double.NaN;
		ss = Double.NaN;
		n = 0;
		sum = Double.NaN;
		min = Double.NaN;
		max = Double.NaN;
		stringDistribution.clear();
		intDistribution.clear();
		doubleDistribution.clear();
		return this;
	}

	/**
	 * Statistics for booleans, coded as 1 for true and 0 for false
	 * 
	 * @param x
	 * @return
	 */
	public Statistics add(boolean x) {
		long i = x?1:0;
		if (intDistribution.get(i)==null)
			intDistribution.put(i,1);
		else
			intDistribution.put(i,intDistribution.get(i)+1);
		return update(i);
	}

	/**
	 * Statistics for floating point numbers
	 * 
	 * @param x
	 * @return
	 */
	public Statistics add(double x) {
		if (doubleDistribution.get(x)==null)
			doubleDistribution.put(x,1);
		else
			doubleDistribution.put(x,doubleDistribution.get(x)+1);
		return update(x);
	}
	
	// compute the stats for everything except strings.
	private Statistics update(double x) {
		if (n == 0) {
			mu = x;
			sum = 0;
			var = 0;
			ss = 0;
			min = Double.POSITIVE_INFINITY; 
			max = Double.NEGATIVE_INFINITY;
		}
		else if (n == 1) {
			mu = (mu + x) / 2;
			var = x - mu;
			var *= var;
			ss = 2 * var;
		} else {
			double mu_n1 = (mu * n + x) / (n + 1);
			ss = ss + (x - mu) * (x - mu_n1);
			var = ss / n;
			mu = mu_n1;
		}
		min = Math.min(min, x);
		max = Math.max(max, x);
		sum += x;
		n++;
		return this;
	}
	
	public Statistics add(long x) {
		if (intDistribution.get(x)==null)
			intDistribution.put(x,1);
		else
			intDistribution.put(x,intDistribution.get(x)+1);
		return update(x);
	}

	public Statistics add(String x) {
		if ((x==null)||(x.isEmpty()))
			;
		else {
			if (stringDistribution.get(x)==null)
				stringDistribution.put(x, 1);
			else
				stringDistribution.put(x,stringDistribution.get(x)+1);
			n++;
		}
		return this;
	}
	
	public int n() {
		return n;
	}

	public double average() {
		return mu;
	}

	public double variance() {
		return var;
	}

	public double sum() {
		return sum;
	}

	public double min() {
		return min;
	}
	
	public double max() {
		return max;
	}

	public Map<String,Integer> stringFrequencies() {
		return Collections.unmodifiableMap(stringDistribution);
	}
	
	public SortedMap<Long,Integer> intFrequencies() {
		return Collections.unmodifiableSortedMap(intDistribution);
	}

	public SortedMap<Double,Integer> doubleFrequencies() {
		return Collections.unmodifiableSortedMap(doubleDistribution);
	}

}
