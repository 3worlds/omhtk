package fr.ens.biologie.generic.utils;

import au.edu.anu.rscs.aot.OmhtkException;

/**
 * 
 * @author Jacques Gignoux - 12 juin 2019
 *
 */
public class Interval {
	
	private double sup = Double.POSITIVE_INFINITY;
	private double inf = Double.NEGATIVE_INFINITY;
	private boolean openSup = true;
	private boolean openInf = true;

	private Interval(double inf, double sup, boolean openInf, boolean openSup) {
		super();
		this.sup = sup;
		this.inf = inf;
		if (inf>sup) {
			this.sup = inf;
			this.inf = sup;
			// TODO: issue a warning
		}
		this.openInf = openInf;
		this.openSup = openSup;
	}
	
	/** by default, intervals are closed */
	public static Interval interval(double inf, double sup) {
		return new Interval(inf,sup,false,false);
	}
	
	public static Interval closedInterval(double inf, double sup) {
		return new Interval(inf,sup,false,false);
	}

	public static Interval openInterval(double inf, double sup) {
		return new Interval(inf,sup,true,true);
	}

	public static Interval halfOpenInfInterval(double inf, double sup) {
		return new Interval(inf,sup,true,false);
	}

	public static Interval halfOpenSupInterval(double inf, double sup) {
		return new Interval(inf,sup,false,true);
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
	
	public boolean contains(int x) {
		boolean cdsup = x<sup;
		boolean cdinf = x>inf;
		if ((!openSup) && (!cdsup))
			cdsup = x<=sup;
		if ((!openInf) && (!cdinf))
			cdinf = x>=inf;
		return cdsup && cdinf;
	}
	
	public boolean contains(long x) {
		boolean cdsup = x<sup;
		boolean cdinf = x>inf;
		if ((!openSup) && (!cdsup))
			cdsup = x<=sup;
		if ((!openInf) && (!cdinf))
			cdinf = x>=inf;
		return cdsup && cdinf;
	}
	
	public boolean contains(short x) {
		boolean cdsup = x<sup;
		boolean cdinf = x>inf;
		if ((!openSup) && (!cdsup))
			cdsup = x<=sup;
		if ((!openInf) && (!cdinf))
			cdinf = x>=inf;
		return cdsup && cdinf;
	}
	
	public boolean contains(byte x) {
		boolean cdsup = x<sup;
		boolean cdinf = x>inf;
		if ((!openSup) && (!cdsup))
			cdsup = x<=sup;
		if ((!openInf) && (!cdinf))
			cdinf = x>=inf;
		return cdsup && cdinf;
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
	
	public boolean contains(float x) {
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
	
}
