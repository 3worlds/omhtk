package au.edu.anu.rscs.aot.util;

/**
 * 
 * @author Shayne Flint - 4/4/2012
 *
 */
public class NumberRange {

	public static String range(int min, int max) {
		String result = "";
		if (min == Integer.MIN_VALUE)
			result = "MinInteger";
		else 
			result = result + min;
		result = result + "..";
		if (max == Integer.MAX_VALUE)
			result = result + "MaxInteger";
		else 
			result = result + max;	
		return result;
	}


	public static String range(long min, long max) {
		String result = "";
		if (min == Long.MIN_VALUE)
			result = "MinLong";
		else 
			result = result + min;
		result = result + "..";
		if (max == Long.MAX_VALUE)
			result = result + "MaxLong";
		else 
			result = result + max;	
		return result;
	}

	public static String range(float min, float max) {
		String result = "";
		if (min == Float.MIN_VALUE)
			result = "MinFloat";
		else 
			result = result + min;
		result = result + "..";
		if (max == Float.MAX_VALUE)
			result = result + "MaxFloat";
		else 
			result = result + max;	
		return result;
	}

	public static String range(double min, double max) {
		String result = "";
		if (min == Double.MIN_VALUE)
			result = "MinDouble";
		else 
			result = result + min;
		result = result + "..";
		if (max == Double.MAX_VALUE)
			result = result + "MaxDouble";
		else 
			result = result + max;	
		return result;
	}


}
