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
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/
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
 * <p>CAUTION: successive additions of observations (calls to an {@code add(...)} method)
 * must keep the data type consistent, i.e. it would be nonsense to add observations that 
 * represent different kinds of variables, objects, etc. All observations are supposed to
 * come from a single source. The <em>meaning</em> and <em>consistency</em> of the computations cannot be 
 * checked in this class and is left to the user.</p>
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

	/** Useless as just calls the default constructor*/
	@Deprecated
	public Statistics(String separator) {
		super();
	}
	
	/**
	 * Clear this instance from all former data.
	 * @return this instance for agile programming
	 */
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
	 * Addition of an observation (booleans, coded as 1 for {@code true} and 0 for {@code false})
	 * 
	 * @param x the new observation
	 * @return this instance for agile programming
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
	 * Addition of an observation (floating point numbers)
	 * 
	 * @param x the new observation
	 * @return this instance for agile programming
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
	
	/**
	 * Addition of an observation (integer numbers)
	 * 
	 * @param x the new observation
	 * @return this instance for agile programming
	 */
	public Statistics add(long x) {
		if (intDistribution.get(x)==null)
			intDistribution.put(x,1);
		else
			intDistribution.put(x,intDistribution.get(x)+1);
		return update(x);
	}

	/**
	 * Addition of an observation (String). NB: only very limited statisics exist for Strings: 
	 * number of observations and frequency distribution.
	 * 
	 * @param x the new observation
	 * @return this instance for agile programming
	 */
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
	
	/**
	 * The number of observations since last call to {@code reset()}.
	 * @return
	 */
	public int n() {
		return n;
	}

	/**
	 * The average of all numeric values received since last call to {@code reset()}.
	 * @return
	 */
	public double average() {
		return mu;
	}

	/**
	 * The variance of all numeric values received since last call to {@code reset()}.
	 * @return
	 */
	public double variance() {
		return var;
	}

	/**
	 * The sum of all numeric values received since last call to {@code reset()}.
	 * @return
	 */
	public double sum() {
		return sum;
	}

	/**
	 * The minimum of all numeric values received since last call to {@code reset()}.
	 * @return
	 */
	public double min() {
		return min;
	}
	
	/**
	 * The maximum of all numeric values received since last call to {@code reset()}.
	 * @return
	 */
	public double max() {
		return max;
	}

	/**
	 * The frequency distribution of all String values received since last call to {@code reset()}.
	 * @return a map of counts per String value
	 */
	public Map<String,Integer> stringFrequencies() {
		return Collections.unmodifiableMap(stringDistribution);
	}
	
	/**
	 * The frequency distribution of all integer values received since last call to {@code reset()}.
	 * @return a map of counts per integer value
	 */
	public SortedMap<Long,Integer> intFrequencies() {
		return Collections.unmodifiableSortedMap(intDistribution);
	}

	/**
	 * The frequency distribution of all floating point values received since last call to {@code reset()}.
	 * CAUTION: to make sure integer constant values representing a floating point number 
	 * are treated as floating points, do not
	 * forget to multiply them by 1.0, otherwise they will be counted as integers.
	 * @return a map of counts per floating point value
	 */
	public SortedMap<Double,Integer> doubleFrequencies() {
		return Collections.unmodifiableSortedMap(doubleDistribution);
	}

}
