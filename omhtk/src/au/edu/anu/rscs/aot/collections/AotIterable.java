package au.edu.anu.rscs.aot.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

import au.edu.anu.rscs.aot.queries.Query;

public class AotIterable<T> implements Iterable<T> {

	private Iterable<T> parentIterable;
	private Query       query;

	public AotIterable(Iterable<T> iterable, Query query) {
		this.parentIterable = iterable;
		this.query = query;
	}

	public AotIterable(Iterable<T> iterable) {
		this(iterable, null);
	}
	
	public AotIterable() {		
	}

	@Override
	public Iterator<T> iterator() {
		return new AotIterator<T>();
	}

	private class AotIterator<T> implements Iterator<T> {
		Iterator<T> iter = (Iterator<T>) parentIterable.iterator();
		T next;

		public AotIterator() {
			if (iter.hasNext())
				next = iter.next();
			else
				next = null;
		}

		@Override
		public boolean hasNext() {
			skipToNextMatch();
			return next != null;
		}

		@Override
		public T next() {
			skipToNextMatch();
			if (next == null)
				throw new NoSuchElementException();
			T result = next;
			if (iter.hasNext())
				next = iter.next();
			else
				next = null;
			return result;
		}

		@Override
		public void remove() {
			skipToNextMatch();
			iter.remove();
			if (iter.hasNext())
				next = iter.next();
			else
				next = null;
		}

		private void skipToNextMatch() {
			if (query != null)
				while (next != null && !query.satisfied(next))
					if (iter.hasNext())
						next = iter.next();
					else
						next = null;
		}
	}
}
