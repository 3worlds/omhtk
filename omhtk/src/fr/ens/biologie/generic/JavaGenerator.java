package fr.ens.biologie.generic;

/**
 * An interface for classes generating java code. 
 * 
 * @author Shayne Flint<br/>
 * 
 * modified by JG <br/>
 * modified again by JG 23/11/2016<br/>
 * modified again by JG 23/5/2017<br/>
 *
 */
public interface JavaGenerator {

	/**
	 * Generates java code. It assumes code specification has been passed to implementing instances
	 * in some way, for example at construction. Typically, this method should check the code by 
	 * compiling it.
	 *  
	 * @param reportErrors whether errors should be reported in some way
	 * @return {@code true} if the code compiles, {@code false} otherwise.
	 */
	public 	boolean generateCode(boolean reportErrors);
	
}
