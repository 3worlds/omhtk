/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2021: Shayne R. Flint, Jacques Gignoux & Ian D. Davies      *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent class hierarchies, plus some other utilities. The kind of  *
 *  things you need in all software projects and keep rebuilding all the  * 
 *  time.                                                                 *
 *                                                                        *
 **************************************************************************                                       
 *  This file is part of OMHTK (One More Handy Tool Kit).                 *
 *                                                                        *
 *  OMHTK is free software: you can redistribute it and/or modify         *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  OMHTK is distributed in the hope that it will be useful,              *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *                         
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/
package au.edu.anu.rscs.aot.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import au.edu.anu.rscs.aot.OmhtkException;
import fr.ens.biologie.generic.Sizeable;
import fr.ens.biologie.generic.Textable;

/**
 * <p>
 * An immutable list of lists enabling iteration over the whole set as if it was
 * a single list, with a minimal cost at creation time (hence the 'Quick').
 * Immutable, but the elementary lists added to this one may not be immutable.
 * </p>
 * <p>
 * CAUTION: the contained lists may have changed between two accesses to this
 * one - so use with care!
 * </p>
 * <p>
 * Inherited methods {@link java.util.Collection#toArray() toArray()} and
 * {@link java.util.Collection#containsAll containsAll(...)} are not supported
 * and will throw an {@link UnsupportedOperationException}.
 * </p>
 *
 * @param <T> the list content type
 * 
 * @author Jacques Gignoux - 4/6/2012
 */
public class QuickListOfLists<T> implements Collection<T>, Textable, Sizeable {

	private LinkedList<Collection<T>> lists = new LinkedList<Collection<T>>();
	private int size = 0;

	/**
	 * Construct a QuickListOfLists from a collection.
	 * 
	 * @param list The list from which to build this list.
	 */
	@SafeVarargs
	public QuickListOfLists(Collection<T>... list) {
		super();
		for (int i = 0; i < list.length; i++) {
			lists.add(list[i]);
			size += list[i].size();
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new AggregatedIterator<T>(lists);
	}

	/**
	 * Add a collection to this list.
	 * 
	 * @param list the collection to add
	 */
	public void addList(Collection<T> list) {
		lists.add(list);
		size += list.size();
	}

	@Override
	public void clear() {
		lists.clear();
	}

	// All this copied from Shayne's AggregatedIterator
	private class AggregatedIterator<U> implements Iterator<U> {

		private Iterator<Collection<U>> iterablesIterator;

		private Iterator<U> iterator;

		public AggregatedIterator(Collection<Collection<U>> list) {
			super();
			iterablesIterator = list.iterator();
			if (iterablesIterator.hasNext()) {
				Iterable<U> i = iterablesIterator.next();
				iterator = i.iterator();
			} else
				iterator = null;
		}

		private Iterator<U> nextIterator() {
			Iterator<U> result;
			if (iterablesIterator.hasNext()) {
				result = iterablesIterator.next().iterator();
				if (!result.hasNext())
					result = nextIterator();
				return result;
			} else
				return null;
		}

		@Override
		public boolean hasNext() {
			if (iterator == null)
				return false;

			if (iterator.hasNext())
				return true;
			else {
				iterator = nextIterator();
				if (iterator == null)
					return false;
				else
					return iterator.hasNext();
			}
		}

		@Override
		public U next() {
			return (U) iterator.next();
		}

		@Override
		public void remove() {
			iterator.remove();
		}

	}

	// Object
	//
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("{");
		if (!isEmpty()) {
			for (T item : this)
				if (item instanceof Textable)
					result.append(((Textable) item).toShortString()).append(", ");
				else
					result.append(item.toString()).append(", ");
			result.delete(result.length() - 2, result.length());
		}
		result.append('}');
		return result.toString();
	}

	// Textable
	//
	@Override
	public String toUniqueString() {
		return super.toString();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(Object o) {
		for (Collection<T> coll : lists)
			if (coll.contains(o))
				return true;
		return false;
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(T e) {
		throw new OmhtkException(getClass().getSimpleName() + " is immutable.");
	}

	@Override
	public boolean remove(Object o) {
		throw new OmhtkException(getClass().getSimpleName() + " is immutable.");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new OmhtkException(getClass().getSimpleName() + " is immutable.");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new OmhtkException(getClass().getSimpleName() + " is immutable.");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new OmhtkException(getClass().getSimpleName() + " is immutable.");
	}

}
