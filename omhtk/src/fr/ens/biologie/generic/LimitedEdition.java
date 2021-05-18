package fr.ens.biologie.generic;

/**
 * <p>An interface that returns a limited set of instances, associated to unique ids. It can be seen
 * as a 'id-relative' {@link Singleton}, which returns a unique instance of a class for 
 * a given id (i.e. for a single id it is guaranteed that the returned instance is always the same).</p>
 * 
 * 
 * 
 * @author Jacques Gignoux - 7 oct. 2019
 *
 * @param <T> the type of the returned instances
 * 
 * @see {@link Singleton}
 * @see {@link Factory} 
 */
public interface LimitedEdition<T> {
	
	/**
	 * <p>Returns the unique instance of {@code T} matching the identifier {@code id}.</p>
	 * <p>Implementations should work as follows:</p> 
	 * <ol>
	 * <li>if the unique id is known, return the matching instance;</li>
	 * <li>else if unknown, make a new instance, associate it to this id, and return it.</li>
	 * </ol>
	 * 
	 * @param id the unique identifier
	 * @return the matching {@code T} instance
	 */
	public T getInstance(int id);

}
