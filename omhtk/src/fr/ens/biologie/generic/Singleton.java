package fr.ens.biologie.generic;

/**
 * An interface for a singleton instance of class {@code T}. Like a {@link Factory}, but the returned
 * object is always the same instance.
 * <p>To make sure the instance returned by this class is unique, {@code T} should have a protected
 * constructor only visible to this class.</p>
 * 
 * @author Jacques Gignoux - 31 mai 2019
 *
 * @param <T> the type of the returned instance
 * 
 * @see {@link LimitedEdition}
 * @see {@link Factory} 
 */
public interface Singleton<T> {
	
	/**
	 * Gets the singleton (i.e., unique) instance of class {@code T}.
	 * 
	 * @return the unique instance of {@code T}
	 */
	public T getInstance();

}
