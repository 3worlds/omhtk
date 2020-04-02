package fr.ens.biologie.generic.utils;

/**
 * Functions to compute statistics iteratively. They all return stat(n+1) as a
 * function of xn, n, and stats(n). Optimised for speed. TODO: contingency
 * tables for Strings
 * <p>
 * This class performs statistics using numbers that regularly come in. The use
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
public class Statistics {

	private String sep = ":";
	private double mu = 0;
	private double var = 0;
	private double ss = 0;
	private double sum = 0;
	private int n = 0;
	private String s = "";
	private double min;
	private double max;

	public Statistics() {
		super();
		reset();
	}

	public Statistics(String separator) {
		super();
		sep = separator;
	}
	public Statistics reset() {
		mu = 0;
		var = 0;
		ss = 0;
		n = 0;
		sum = 0;
		s = "";
		min = Double.POSITIVE_INFINITY; 
		max = Double.NEGATIVE_INFINITY;
		return this;
	}

	/**
	 * Statistics for booleans, coded as 1 for true and 0 for false
	 * 
	 * @param x
	 * @return
	 */
	public Statistics add(boolean x) {
		if (x)
			return add(1.0);
		else
			return add(0.0);
	}

	/**
	 * Statistics for any number
	 * 
	 * @param x
	 * @return
	 */
	public Statistics add(double x) {
		if (n == 0)
			mu = x;
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

	public Statistics add(String x) {
		if (s.equals(""))
			s = x;
		else
			s += sep + x;
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

	public String concatenation() {
		return s;
	}

}
