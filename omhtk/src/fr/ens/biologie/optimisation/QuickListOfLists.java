/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2018: Shayne FLint, Jacques Gignoux & Ian D. Davies         *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent, plus some other utilities. The kind of things you need    *
 *  in all software projects and keep rebuilding all the time.            *
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
 *  along with UIT.  If not, see <https://www.gnu.org/licenses/gpl.html>. *
 *                                                                        *
 **************************************************************************/
package fr.ens.biologie.optimisation;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * <p>An immutable list of list enabling to iterate over the whole set as if it was a single 
 * list, with a minimal cost at creation time (hence the 'Quick').
 * Immutable.</p>
 * <p>CAUTION: the contained lists may have changed between two accesses to this one - so
 * use with care !</p>
 * @author gignoux
 *
 * @param <T> the list content type
 */
public class QuickListOfLists<T> implements Iterable<T> {

	private LinkedList<Iterable<T>> lists = new LinkedList<Iterable<T>>();

	@SafeVarargs
	public QuickListOfLists(Iterable<T>...list) {
		super();
		for (int i=0;i<list.length;i++)
			lists.add(list[i]);
	}
	
	@Override
	public Iterator<T> iterator() {
		return new AggregatedIterator<T>(lists);
	}

	public void addList(Iterable<T> list) {
		lists.add(list);
	}
		
	public void clear() {
		lists.clear();
	}
	
	// All this copied from Shayne's AggregatedIterator
    private class AggregatedIterator<U> implements Iterator<U> {

		private Iterator<Iterable<U>> iterablesIterator;
	
		private Iterator<U> iterator;
	
		public AggregatedIterator(Iterable<Iterable<U>> list) {
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
    

}
