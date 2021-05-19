package fr.ens.biologie.generic.utils;

/**
 * Utility methods to make java-compatible names from Strings.
 * 
 * @author Jacques Gignoux - 15/2/2012
 *
 */
// tested OK with version 0.1.5
// TODO Check all this - may already be done by apache commons.lang3.wordUtils
public class NameUtils {
    
    /**
     * Return a String with uppercase words from a String with separators.
     * @param s
     * @return
     */
    static public String wordUpperCaseName(String s) {
		String[] tokens = s.split("\\W");
		String result = "";
		if (tokens.length>0) { 
		    result = tokens[0];
		    for (int i=1; i<tokens.length; i++) {
			result += initialUpperCase(tokens[i]);
		    }
		}
		else result = s; 
		return result;
    }

    /**
     * Return a String with an initial uppercase.
     * @param s
     * @return
     */
    static public String initialUpperCase(String s) {
		if (s.length()>1)
		    return s.substring(0,1).toUpperCase()+s.substring(1);
		else if (s.length()==1)
		    return s.toUpperCase();
		else 
		    return s;
    }

    /**
     * Return a String where separators are replaced by underscores
     * @param s
     * @return
     */
    static public String wordUnderscoreName(String s) {
		String[] tokens = s.split("\\W");
		String result = "";
		if (tokens.length>0) { 
		    result = tokens[0];
		    for (int i=1; i<tokens.length; i++) {
			if (tokens[i].length()>0)
			    result += "_"+tokens[i];
		    }
		}
		else result = s; 
		return result;
    }
    
    /**
     * Makes a valid java variable name from a string by replacing all non
     * valid characters with "X".
     * @param s
     * @return
     */
    static public String validJavaName(String s) {
		String result = s;
		if (s.substring(0,1).matches("\\d")) result = "_"+s;
		result = result.replaceAll("\\W", "X");
		return result;
    }
    
}
