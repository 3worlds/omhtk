package fr.ens.biologie.generic;

/** 
 * an interface for classes which return a java source code as text 
 * @author Jacques Gignoux - 19 déc. 2014
 * */
public interface JavaCode {
	
	public abstract String asText(String indent);

}
