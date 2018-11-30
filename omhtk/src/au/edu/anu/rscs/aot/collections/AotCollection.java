package au.edu.anu.rscs.aot.collections;

import java.util.Deque;
import java.util.List;
import java.util.Queue;

public interface AotCollection<T> extends List<T>, Deque<T>, Queue<T> {

	/**
	 * Minimises the amount of space used to stored the collection. Usually does nothing, but
	 * for AotArrays, which expand and contract dynamically, this method will release memory which 
	 * is no longer required to hold the current array.
	 * 
	 */
	public abstract void pack();
	
}
