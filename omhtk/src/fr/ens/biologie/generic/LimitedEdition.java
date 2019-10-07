package fr.ens.biologie.generic;

/**
 * A class that returns a limited set of instances, associated to unique ids. It can be seen
 * as a 'id-relative' {@linkplain Singleton}, which returns a unique instance of a class for 
 * a given id (i.e. for a single id it is guaranteed that the returned instance is always the same)
 * 
 * @author Jacques Gignoux - 7 oct. 2019
 *
 * @param <T>
 */
public interface LimitedEdition<T> {
	
	/**
	 * Returns the unique instance of {@code T} matching the identifier {@code id}.
	 * @param id the unique identifier
	 * @return the mathcing {@code T} instance
	 */
	public T getInstance(Object id);

}
