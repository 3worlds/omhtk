package fr.ens.biologie.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An interface for factories for objects of class {@code T}. 
 * A factory is an object able to return instances of a given class.
 * Here, every call to newInstance() returns
 * a new object of class {@code T}. cf. {@link Singleton} for unique instances. 
 * 
 * @author Jacques Gignoux - 31 mai 2019
 *
 * @param <T> the type of the returned instance

 * @see {@link Singleton}
 * @see {@link LimitedEdition} 
 */
public interface Factory<T> {
	
	/**
	 * Creates and returns a new instance of class {@code T}.
	 * @return the new {@code T} instance
	 */
	public T newInstance();
	
	/**
	 * Creates and returns a collection of new instances of class {@code T}. The default behaviour
	 * is to return a single instance in a collection.
	 * @return the collection of new {@code T} instances
	 */
	public default Collection<T> newInstances() {
		T instance = newInstance();
		List<T> list = new ArrayList<>();
		list.add(instance);
		return list;
	}

}
