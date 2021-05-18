package fr.ens.biologie.generic;

/** 
 * An interface for classes which return a java source code as text, typically code generators. 
 * 
 * @author Jacques Gignoux - 19 déc. 2014
 * */
public interface JavaCode {
	
	/**
	 * Constructs and returns valid java code as a plain text {@code String}. The returned String
	 * is assumed to contain lines separated by end-of-line characters so that it can be directly
	 * saved to a {@code .java} code file.
	 * 
	 * @param indent the String to use for indentation of code lines
	 * @return the code as a single String
	 */
	public abstract String asText(String indent);

}
