package fr.ens.biologie.generic;

/**
 * An interface for singleton instances of class T. Like a {@link Factory}, but the returned
 * object is always the same instance.
 * 
 * @author Jacques Gignoux - 31 mai 2019
 *
 * @param <T> the type of the returned instance
 */
public interface Singleton<T> {
	
	public T getInstance();

}
