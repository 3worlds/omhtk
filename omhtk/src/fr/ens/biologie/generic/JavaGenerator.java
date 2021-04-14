package fr.ens.biologie.generic;

/**
 * Interface for classes generating java code
 * 
 * @author Shayne Flint
 * 
 * modified by JG 
 * modified again by JG 23/11/2016
 * modified again by JG 23/5/2017
 *
 */
public interface JavaGenerator {

	/** the procedure that generates the code 
	 *  returns true if the code compiles, false otherwise */	
	public 	boolean generateCode(boolean reportErrors);
	
}
