package fr.ens.biologie.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An interface for factories for objects of class T. Every call to newInstance() returns
 * a new object of class T. cf. {@link Singleton} for unique instances. 
 * 
 * @author Jacques Gignoux - 31 mai 2019
 *
 * @param <T> the type of the returned instance
 */
public interface Factory<T> {
	
	public T newInstance();
	
	/** this one may be useful sometimes when instances must be created in a block */
	public default Collection<T> newInstances() {
		T instance = newInstance();
		List<T> list = new ArrayList<>();
		list.add(instance);
		return list;
	}

}
