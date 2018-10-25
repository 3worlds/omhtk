package au.edu.anu.rscs.aot.util;

import au.edu.anu.rscs.aot.OmhtkException;

public class IntegerRange {

	private int first = 0;
	private int last  = Integer.MAX_VALUE;

	public IntegerRange(int first, int last) {
		this.first = first;
		this.last   = last;
		check();
	}

	public IntegerRange(String str) {
		int idx = str.indexOf("..");
		if (idx > 0 && (str.length() - idx) > 2) {
			String startStr = str.substring(0, idx);
			String endStr   = str.substring(idx+2);
			first = Integer.valueOf(startStr);
			if (endStr.equals("*"))
				last = Integer.MAX_VALUE;
			else
				last = Integer.valueOf(endStr);
		} else
			throw new OmhtkException("'" + str + "' is not a valid integer range");
		check();
	}

	@Override
	public boolean equals(Object value) {
		IntegerRange test = (IntegerRange)value;
		return first==test.first && last==test.last;
	}
	
	private void check() {
		if (first > last)
			throw new OmhtkException("Invalid integer range: " + first + " must <= " + last);
	}
	
	public boolean inRange(int value) {
		return (value >= first) && (value <= last);
	}
	
	public void check(int value) {
		if (!inRange(value))
			throw new OmhtkException(value + " is not in integer range " + first + ".." + last);
	}
	
	
	public static IntegerRange valueOf(String str) {
		return new IntegerRange(str);
	}

	private String integerString(int i) {
		if (i==Integer.MIN_VALUE)
			return "MIN_INTEGER";
		if (i==Integer.MAX_VALUE)
			return "*";
		return String.valueOf(i);
	}
	
	public String toString() {
		return integerString(first) + ".." + integerString(last);
	}

	public IntegerRange setFirst(int first) {
		this.first = first;
		return this;
	}
	
	public int getFirst() {
		return first;
	}
	
	public IntegerRange setLast(int last) {
		this.last = last;
		return this;
	}
	
	public int getLast() {
		return last;
	}
	
	// TESTING
	//
	public static void main(String[] args) {
		System.out.println(IntegerRange.valueOf("0..23"));
		System.out.println(IntegerRange.valueOf("0..*"));
		System.out.println(IntegerRange.valueOf("-12..-2"));
		System.out.println(IntegerRange.valueOf(".."));
	}
}
