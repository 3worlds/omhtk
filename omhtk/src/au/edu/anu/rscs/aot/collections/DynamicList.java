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
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import fr.ens.biologie.generic.Sizeable;
import fr.ens.biologie.generic.Textable;

/**
 * Implementation of {@code List<T>} with a <em>self-correcting</em> iterator
 * which allows removing of items while in a for loop on the list. It is
 * implemented as a linked list with a clever iterator that skips removed items
 * while looping. Use this class when dealing with highly dynamic lists (where
 * there are many insertions and deletions in the list).
 * <p>
 * [NB:This was formerly known as AotList. Code modified from
 * au.edu.anu.rscs.aot.collections. {@code AotList<T>} by Shayne Flint. Removed
 * Query, and dependency from AotCollection and AotIterable.]
 * </p>
 * <p>
 * Given the new facilities provided in java 8 lists, this class may be
 * obsolete. For example, to remove items from a list can be done with
 * instructions such as {@code list.sublist(from,to).clear()}.
 * </p>
 *
 * @author Shayne Flint - loooong ago. <br/>
 *         refactored by Jacques Gignoux - 30 Nov. 2018
 *
 * @param <T> the list item type.
 */
// TODO: finish testing - only partly done.
public class DynamicList<T> implements List<T>, Deque<T>, Queue<T>, Sizeable, Textable {

	protected ListNode<T> head = null;
	protected ListNode<T> tail = null;
	protected int size = 0;

	// Constructors

	/**
	 * Default constructor.
	 */
	public DynamicList() {
	}

	/**
	 * Construct a DynamicList with a simple array of items.
	 * 
	 * @param items Items to add to the list.
	 */
	@SafeVarargs
	public DynamicList(T... items) {
		super();
		for (T item : items)
			add(item);
	}

	/**
	 * Construct a DynamicList from an iterable.
	 * 
	 * @param iterable The interable over the items of type {@code T}.
	 */
	public DynamicList(Iterable<T> iterable) {
		super();
		for (T item : iterable)
			add(item);
	}

	// Sizeable

	@Override
	public int size() {
		return size;
	}

	// List

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(Object o) {
		for (T item : this)
			if (item.equals(o))
				return true;
		return false;
	}

	private boolean isRemovedItem(ListNode<T> node) {
		if (node == null)
			return false;
		else
			return node.isRemoved();
	}

	/**
	 * An iterator which skips removed items.
	 *
	 * @author Shayne Flint - long ago
	 *
	 */
	private class CorrectingIterator implements Iterator<T> {

		protected ListNode<T> current = head;
		protected boolean correcting;
		protected ListNode<T> lastReturned = null;

		public CorrectingIterator(boolean correcting) {
			this.correcting = correcting;
		}

		protected void correctIterator() {
			if (correcting) {
				if (isRemovedItem(current))
					current = current.next;
			} else {
				if (isRemovedItem(current))
					throw new IllegalStateException("Non-correcting iterator is pointing to a removed item");
			}
		}

		@Override
		public boolean hasNext() {
			correctIterator();
			return current != null;
		}

		@Override
		public T next() {
			correctIterator();
			if (current == null)
				throw new NoSuchElementException();
			lastReturned = current;
			current = current.next;
			return lastReturned.item;
		}

		@Override
		public void remove() {
			if (lastReturned == null)
				throw new IllegalStateException();
			removeNode(lastReturned);
			lastReturned = null;
		}

		public String toString() {
			return "[CorrectingIterator correcting=" + correcting + ", current=" + current + "]";
		}

	} // private class CorrectingIterator

	@Override
	public Iterator<T> iterator() {
		return new CorrectingIterator(true);
	}

	/**
	 * Returns an iterator on this list
	 *
	 * @param correcting if true, the iterator is correcting
	 * @return an iterator, optionally correcting
	 */
	public Iterator<T> correctingIterator(boolean correcting) {
		return new CorrectingIterator(correcting);
	}

	/**
	 * Returns a correcting iterator on this list.
	 * <p>
	 * Note: the default iterator() method returns a correcting iterator.
	 * </p>
	 *
	 * @return a correcting iterator
	 */
	public Iterator<T> correctingIterator() {
		return new CorrectingIterator(true);
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		int i = 0;
		for (ListNode<T> n = head; n != null; n = n.next)
			result[i++] = n.item;
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E[] toArray(E[] a) {
		if (a.length < size)
			a = (E[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		int i = 0;
		Object[] result = a;
		for (ListNode<T> n = head; n != null; n = n.next)
			result[i++] = n.item;
		if (a.length > size)
			a[size] = null;
		return a;
	}

	private void addToEnd(T item) {
		ListNode<T> node = new ListNode<T>(item, null, tail);
		if (size == 0) {
			head = node;
		} else {
			tail.next = node;
		}
		tail = node;
		size++;
	}

	@Override
	public boolean add(T e) {
		addToEnd(e);
		return true;
	}

	private ListNode<T> find(Object o) {
		ListNode<T> node = head;
		while (node != null)
			if (node.item != null && node.item.equals(o))
				return node;
			else
				node = node.next;
		return null;
	}

	private void removeNode(ListNode<T> n) {
		if (n == null)
			throw new NoSuchElementException();
		if (head == n && tail == n) {
			n.next = null;
			head = null;
			tail = null;
		} else if (head == n) {
			n.next.prev = null;
			head = n.next;
		} else if (tail == n) {
			n.next = null;
			n.prev.next = null;
			tail = n.prev;
		} else {
			n.prev.next = n.next;
			n.next.prev = n.prev;
		}
		n.prev = n;
		size--;
	}

	@Override
	public boolean remove(Object o) {
		ListNode<T> n = find(o);
		if (n != null) {
			removeNode(n);
			return true;
		} else
			return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c)
			if (!contains(o))
				return false;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T item : c) {
			add(item);
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		ListIterator<T> iter = this.listIterator(index);
		for (T item : c) {
			iter.add(item);
			iter.next();
		}
		return true;
	}

	/**
	 * Add items from a simple array.
	 * 
	 * @param items Array of items to add.
	 * @return True if all items successfully added, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean addAll(T... items) {
		boolean result = true;
		for (T item : items) {
			if (!add(item)) {
				result = false;
			}
			;
		}
		return result;
	}

	private void removeAll(Object o) {
		ListNode<T> node = find(o);
		while (node != null) {
			removeNode(node);
			node = find(o);
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object o : c) {
			removeAll(o);
			modified = true;
		}
		return modified;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			if (!c.contains(it.next())) {
				it.remove();
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public void clear() {
		// invalidate all nodes so that checked iterators know that the nodes have been
		// deleted
		//
		ListNode<T> node = head;
		while (node != null) {
			ListNode<T> next = node.next;
			node.prev = node;
			node.next = node;
			node = next;
		}
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public T get(int index) {
		return nodeAt(index).item;
	}

	@Override
	public T set(int index, T element) {
		nodeAt(index).item = element;
		return element;
	}

	@Override
	public void add(int index, T element) {
		ListNode<T> node = nodeAt(index);
		ListNode<T> newNode = new ListNode<T>(element, node.next, node);
		if (index < size - 1)
			node.next.prev = newNode;
		else
			tail = newNode;
		node.next = newNode;
	}

	@Override
	public T remove(int index) {
		ListNode<T> node = nodeAt(index);
		if (size == 1) {
			node.next = node;
			head = null;
			tail = null;
		} else if (index == 0) {
			head = node.next;
			node.next.prev = null;
		} else if (index == size - 1) {
			tail = node.prev;
			node.prev.next = null;
			node.next = node;
		} else {
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}
		node.prev = node;
		size--;
		return node.item;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		ListNode<T> n = head;
		while (n != null) {
			if (n.item.equals(o))
				return index;
			index++;
			n = n.next;
		}
		throw new NoSuchElementException();
	}

	@Override
	public int lastIndexOf(Object o) {
		int index = size - 1;
		ListNode<T> n = tail;
		while (n != null) {
			if (n.item.equals(o))
				return index;
			index--;
			n = n.prev;
		}
		throw new NoSuchElementException();
	}

	private int indexOf(ListNode<T> node) {
		int index = 0;
		ListNode<T> n = head;
		while (n != null) {
			if (n == node)
				return index;
			index++;
			n = n.next;
		}
		throw new NoSuchElementException();
	}

	private ListNode<T> nodeAt(int index) {
		if (index < 0 || index >= size)
			throw new NoSuchElementException();
		else if (index == 0)
			return head;
		else if (index == size - 1)
			return tail;
		else {
			ListNode<T> result = head;
			for (int i = 0; i < index; i++)
				result = result.next;
			return result;
		}

	}

	/**
	 * A correcting list iterator, ie an iterator that can traverse the list up or
	 * down
	 *
	 * @author Shayne Flint - long ago
	 *
	 */
	private class CorrectingListIterator extends CorrectingIterator implements ListIterator<T> {

		public CorrectingListIterator(boolean correcting, int index) {
			super(correcting);
			if (size() == 0)
				current = null;
			else
				current = nodeAt(index);
		}

		@Override
		public boolean hasPrevious() {
			correctIterator();
			return current.prev != null;
		}

		@Override
		public T previous() {
			correctIterator();
			if (current == null)
				throw new NoSuchElementException();
			lastReturned = current;
			current = current.prev;
			return lastReturned.item;
		}

		@Override
		public int nextIndex() {
			correctIterator();
			if (current == null)
				throw new NoSuchElementException();

			if (current.next == null)
				throw new NoSuchElementException();
			else if (current.prev == null)
				return 1;
			else
				return indexOf(current) + 1;
		}

		@Override
		public int previousIndex() {
			correctIterator();
			if (current == null)
				throw new NoSuchElementException();

			if (current.prev == null)
				throw new NoSuchElementException();
			if (current.next == null)
				return size - 2;
			return indexOf(current) - 1;
		}

		@Override
		public void set(T e) {
			if (lastReturned == null)
				throw new IllegalStateException();
			else
				lastReturned.item = e;
		}

		@Override
		public void add(T e) {
			correctIterator();
			if (current == null)
				throw new NoSuchElementException();

			ListNode<T> newNode = new ListNode<T>(e, current, current.prev);
			if (current.prev == null)
				head = newNode;
			else
				current.prev.next = newNode;
			current.prev = newNode;
			size++;
		}

		public String toString() {
			return "[CorrectingListIterator correcting=" + correcting + ", current=" + current + "]";
		}

	} // private class CorrectingListIterator

	@Override
	public ListIterator<T> listIterator() {
		return new CorrectingListIterator(true, 0);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new CorrectingListIterator(true, index);
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper sequence),
	 * starting at the list top.
	 * 
	 * @param correcting {@code true} for a correctingIterator
	 * @return an iterator over the list
	 */
	public ListIterator<T> checkedListIterator(boolean correcting) {
		return new CorrectingListIterator(correcting, 0);
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper sequence),
	 * starting at the specified position in the list. The specified index indicates
	 * the first element that would be returned by an initial call to
	 * {@link java.util.Iterator#next next}.
	 * 
	 * @param index      Starting position in the list.
	 * @param correcting true for a correctingIterator
	 * @return an iterator over the list
	 */
	public ListIterator<T> correctingListIterator(int index, boolean correcting) {
		return new CorrectingListIterator(correcting, index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		DynamicList<T> result = new DynamicList<T>();
		ListNode<T> node = nodeAt(fromIndex);
		for (int i = fromIndex; i <= toIndex; i++) {
			result.add(node.item);
			node = node.next;
		}
		return result;
	}

	/**
	 * Debugging method to display contents along with a message.
	 * 
	 * @param message The message.
	 */
	public void show(String message) {
		System.out.println(message);
		System.out.println("  Head: " + head);
		System.out.println("  Tail: " + tail);
		System.out.println("  Size: " + size);

		ListNode<T> node = head;
		int index = 0;
		while (node != null) {
			System.out.println("    " + index + ": " + node);
			index++;
			node = node.next;
		}
	}

	/**
	 * Debugging method to display contents.
	 */
	public void show() {
		show("AotList");
	}

	// Deque interface
	//

	@Override
	public void addFirst(T e) {
		ListNode<T> node = new ListNode<T>(e, head, null);
		if (size == 0) {
			tail = node;
		} else {
			head.prev = node;
		}
		head = node;
		size++;
	}

	@Override
	public void addLast(T e) {
		addToEnd(e);
	}

	@Override
	public boolean offerFirst(T e) {
		addFirst(e);
		return true;
	}

	@Override
	public boolean offerLast(T e) {
		addLast(e);
		return true;
	}

	@Override
	public T removeFirst() {
		T result = head.item;
		remove(0);
		return result;
	}

	@Override
	public T removeLast() {
		T result = tail.item;
		remove(size - 1);
		return result;
	}

	@Override
	public T pollFirst() {
		if (size == 0)
			return null;
		else
			return removeFirst();
	}

	@Override
	public T pollLast() {
		if (size == 0)
			return null;
		else
			return removeLast();
	}

	@Override
	public T getFirst() {
		return head.item;
	}

	@Override
	public T getLast() {
		return tail.item;
	}

	@Override
	public T peekFirst() {
		if (size == 0)
			return null;
		else
			return getFirst();
	}

	@Override
	public T peekLast() {
		if (size == 0)
			return null;
		else
			return getLast();
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		try {
			remove(indexOf(o));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		try {
			remove(lastIndexOf(o));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean offer(T e) {
		addFirst(e);
		return true;
	}

	@Override
	public T remove() {
		T result = head.item;
		remove(0);
		return result;
	}

	@Override
	public T poll() {
		if (size == 0)
			return null;
		else
			return remove();
	}

	@Override
	public T element() {
		if (size == 0)
			throw new NoSuchElementException();
		else
			return head.item;
	}

	@Override
	public T peek() {
		if (size == 0)
			return null;
		else
			return head.item;
	}

	@Override
	public void push(T e) {
		addFirst(e);
	}

	@Override
	public T pop() {
		return removeFirst();
	}

	@Override
	public Iterator<T> descendingIterator() {
		return new CorrectingDescendingIterator(true);
	}

//	private Iterator<T> descendingCorrectingIterator(boolean correcting) {
//		return new CorrectingDescendingIterator(correcting);
//	}

	private class CorrectingDescendingIterator implements Iterator<T> {

		protected ListNode<T> current = tail;
		protected boolean correcting;
		protected ListNode<T> lastReturned = null;

		public CorrectingDescendingIterator(boolean correcting) {
			this.correcting = correcting;
		}

		protected void correctIterator() {
			if (isRemovedItem(current)) {
				if (correcting) {
					if (current.next == null) {
						current = tail;
					} else {
						current = current.next.prev;
					}
				} else {
					throw new IllegalStateException("Non-correcting iterator is pointing to a removed item");
				}
			}
		}

		@Override
		public boolean hasNext() {
			correctIterator();
			return current != null;
		}

		@Override
		public T next() {
			correctIterator();
			if (current == null)
				throw new NoSuchElementException();
			lastReturned = current;
			current = current.prev;
			return lastReturned.item;
		}

		@Override
		public void remove() {
			if (lastReturned == null)
				throw new IllegalStateException();
			removeNode(lastReturned);
			lastReturned = null;
		}

		public String toString() {
			return "[CorrectingDescendingIterator correcting=" + correcting + ", current=" + current + "]";
		}

	} // private class CorrectingDescendingIterator

	// Additional helper methods
	//

	/**
	 * Helper method to return items in one list that are not in the other.
	 * 
	 * @param list Comparision list.
	 * @return List of differences.
	 */
	public DynamicList<T> difference(DynamicList<T> list) {
		DynamicList<T> result = new DynamicList<T>();
		for (T item : list)
			if (!this.contains(item))
				result.add(item);
		for (T item : this)
			if (!list.contains(item))
				result.add(item);
		return result;
	}

	/**
	 * Helper method to return items that appear in both lists.
	 * 
	 * @param list Comparison list.
	 * @return Intersection of the two lists.
	 */
	public DynamicList<T> intersection(DynamicList<T> list) {
		DynamicList<T> result = new DynamicList<T>();
		for (T item : list)
			if (this.contains(item))
				result.add(item);
		return result;
	}

	/**
	 * Sorts this list using a {@link java.util.Comparator Comparator}. Renamed from
	 * sort to sortList to avoid conflict with JDK 1.8 List.sort().
	 *
	 * @param comparator The comparator to use for sorting.
	 */
	public void sortList(Comparator<? super T> comparator) {
		if (this.size() > 0)
			Collections.sort(this, comparator);
	}

	/**
	 * Checks if the list is sorted.
	 * 
	 * @param comparator the comparator to use for sorting
	 * @return {@code true} if the list is in the comparator order, {@code false}
	 *         otherwise
	 */
	public boolean isSorted(Comparator<T> comparator) {
		Iterator<T> iter = iterator();
		if (!iter.hasNext()) {
			return true;
		}
		T previous = iter.next();
		while (iter.hasNext()) {
			T next = iter.next();
			if (comparator.compare(previous, next) > 0) {
				return false;
			}
			previous = next;
		}
		return true;
	}

	/**
	 * Adds an item to the list only if it is not already present.
	 * 
	 * @param item the item to add
	 * @return {@code true} if the item was added, {@code false} otherwise
	 */
	public boolean addUnique(T item) {
		if (this.contains(item))
			return false;
		else
			return add(item);
	}

	/**
	 * Adds all elements of its argument into the list, only if not already present.
	 * 
	 * @param list the list of items to add
	 * @return {@code true} if all items were added, {@code false} otherwise
	 */
	public boolean addAllUnique(Iterable<T> list) {
		boolean result = true;
		for (T item : list) {
			if (!addUnique(item))
				result = false;
		}
		return result;
	}

	// Copying and Cloning AotLists
	//

	/**
	 * Creates a normal array comprising elements which refer to the data items in
	 * this DynamicList
	 *
	 * @return The simple array of type {@code T}.
	 */
	@SuppressWarnings("unchecked")
	public T[] makeArray() {
		Object[] result;
		result = new Object[size()];
		int i = 0;
		for (T t : this) {
			result[i] = t;
			i++;
		}
		return (T[]) result;
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
	public String toShortString() {
		return "[DynamicList of " + size() + " element(s)]";
	}

}
