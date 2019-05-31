package fr.ens.biologie.generic;

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

}
