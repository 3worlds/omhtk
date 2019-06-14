package fr.ens.biologie.generic.utils;

import au.edu.anu.rscs.aot.OmhtkException;

/**
 * A (simple) class for mathematical intervals.
 * 
 * @author Jacques Gignoux - 12 juin 2019
 *
 */
// Tested OK with version 0.1.4 on 14/6/2019
public class Interval {
	
	private double sup = Double.POSITIVE_INFINITY;
	private double inf = Double.NEGATIVE_INFINITY;
	private boolean openSup = true;
	private boolean openInf = true;

	private Interval(double inf, double sup, boolean openInf, boolean openSup) {
		super();
		if (inf>sup)
			throw new OmhtkException("sup (here "+sup+") must be greater or equal to inf (here "+inf+")");
		this.sup = sup;
		this.inf = inf;
		this.openInf = openInf;
		this.openSup = openSup;
	}
	
	/** by default, intervals are closed. Returns the closed interval [inf,sup]  */
	public static Interval newInstance(double inf, double sup) {
		return new Interval(inf,sup,false,false);
	}
	
	/** returns the closed interval [inf,sup] */
	public static Interval closed(double inf, double sup) {
		return new Interval(inf,sup,false,false);
	}

	/** returns the open interval ]inf,sup[ */
	public static Interval open(double inf, double sup) {
		return new Interval(inf,sup,true,true);
	}

	/** returns the half open interval ]inf,sup] */
	public static Interval halfOpenInf(double inf, double sup) {
		return new Interval(inf,sup,true,false);
	}

	/** returns the half open interval [inf,sup[ */
	public static Interval halfOpenSup(double inf, double sup) {
		return new Interval(inf,sup,false,true);
	}
	
	/** returns the half closed interval ]-∞,sup] */
	public static Interval toNegInf(double sup) {
		return new Interval(Double.NEGATIVE_INFINITY,sup,true,false);
	}

	/** returns the half closed interval [inf,+∞[ */
	public static Interval toPosInf(double inf) {
		return new Interval(inf,Double.POSITIVE_INFINITY,false,true);
	}

	/** returns the open interval ]-∞,sup[ */
	public static Interval openToNegInf(double sup) {
		return new Interval(Double.NEGATIVE_INFINITY,sup,true,true);
	}

	/** returns the open interval ]inf,+∞[ */
	public static Interval openToPosInf(double inf) {
		return new Interval(inf,Double.POSITIVE_INFINITY,true,true);
	}

	public double sup() {
		return sup;
	}

	public double inf() {
		return inf;
	}

	public boolean halfOpenInf() {
		return openInf;
	}
	
	public boolean halfOpenSup() {
		return openSup;
	}
	
	public boolean contains(double x) {
		boolean cdsup = x<sup;
		boolean cdinf = x>inf;
		if ((!openSup) && (!cdsup))
			cdsup = x<=sup;
		if ((!openInf) && (!cdinf))
			cdinf = x>=inf;
		return cdsup && cdinf;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (openInf) 
			sb.append(']');
		else 
			sb.append('[');
		if (inf == Double.NEGATIVE_INFINITY)
			sb.append("-∞");
		else 
			sb.append(inf);
		sb.append(',');
		if (sup == Double.POSITIVE_INFINITY)
			sb.append("+∞");
		else 
			sb.append(sup);
		if (openSup) 
			sb.append('[');
		else 
			sb.append(']');
		return sb.toString();
	}
	
	public static Interval valueOf(String interval) {
		boolean openInf = false;
		boolean openSup = false;
		double inf = 0;
		double sup = 0;
		String s = interval.trim();
		if (s.startsWith("["))
			openInf = false;
		else if (s.startsWith("]"))
			openInf = true;
		else
			throw new OmhtkException("Interval must start with ']' or '[' - '"+s.charAt(0)+"' found instead");
		if (s.endsWith("]"))
			openSup = false;
		else if (s.endsWith("["))
			openSup = true;
		else
			throw new OmhtkException("Interval must end with ']' or '[' - '"+s.charAt(s.length()-1)+"' found instead");
		s = s.substring(1,s.length()-1);
		String[] ss = s.split(",");
		if (ss.length>2)
			throw new OmhtkException("Too many limits in interval string \""+s+"\"");
		if (ss.length<2)
			throw new OmhtkException("Not enough limits in interval string \""+s+"\"");
		if ((ss[0].contains("-∞"))||(ss[0].contains("-Inf")))
			inf = Double.NEGATIVE_INFINITY;
		else
			inf = Double.parseDouble(ss[0].trim());
		if ((ss[1].contains("+∞"))||(ss[1].contains("+Inf")))
			sup = Double.POSITIVE_INFINITY;
		else
			sup = Double.parseDouble(ss[1].trim());
		return new Interval(inf,sup, openInf,openSup);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Interval) {
			Interval i = (Interval) o;
			return (sup==i.sup)&&(inf==i.inf)&&(openSup==i.openSup)&&(openInf==i.openInf);
		}
		return false;
	}
	
	/** returns true if two intervals overlap */
	public boolean overlaps(Interval i) {
		if (inf==i.sup) {
			if ((!openInf)&&(!i.openSup))
				return true;
			else
				return false;
		}
		else if (sup==i.inf) {
			if ((!i.openInf)&&(!openSup))
				return true;
			else
				return false;
		}
		else
			return contains(i.inf) || contains(i.sup);
	}
	
	/** returns true if two intervals are exactly contiguous to each other, eg [0,2[ and [2,3],
	 *  [0,2] and ]2,3] will return true, but [0,2[ and ]2,3], [0,2] and [2,3], will return false */
	public boolean contiguousTo(Interval i) {
		if (inf==i.sup) 
			return openInf ^ i.openSup;
		if (sup==i.inf)
			return openSup ^ i.openInf;
		return false;
	}
	
	/** returns the union of two overlapping intervals (null if non overlapping) */
	public Interval union(Interval i) {
		if (overlaps(i)||contiguousTo(i)) {
			double min = Math.min(inf,i.inf);
			boolean minOpen = false;
			if (inf==min)
				minOpen = openInf;
			else 
				minOpen = i.openInf;
			if (inf==i.inf)
				minOpen = openInf && i.openInf;
			double max = Math.max(sup,i.sup);
			boolean maxOpen = false;
			if (sup==max)
				maxOpen = openSup;
			else 
				maxOpen = i.openSup;
			if (sup==i.sup)
				maxOpen = openSup && i.openSup;
			return new Interval(min,max,minOpen,maxOpen);
		}
		return null;
	}
	
	public Interval intersection(Interval i) {
		if (overlaps(i)) {
			double min = Math.max(inf,i.inf);
			boolean minOpen = false;
			if (inf==min)
				minOpen = openInf;
			else 
				minOpen = i.openInf;
			if (inf==i.inf)
				minOpen = !((!openInf) && (!i.openInf));
			double max = Math.min(sup,i.sup);
			boolean maxOpen = false;
			if (sup==max)
				maxOpen = openSup;
			else 
				maxOpen = i.openSup;
			if (sup==i.sup)
				maxOpen = !((!openSup) && (!i.openSup));
			return new Interval(min,max,minOpen,maxOpen);
		}
		return null;
	}
	
}
