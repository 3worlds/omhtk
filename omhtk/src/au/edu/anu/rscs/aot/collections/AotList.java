package au.edu.anu.rscs.aot.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.util.Resource;
import au.edu.anu.rscs.aot.util.Uid;

public class AotList<T> extends AotIterable<T> implements AotCollection<T>  {

	protected ListNode<T> head = null;
	protected ListNode<T> tail = null;
	protected int         size = 0;

	public AotList() {
	}

	@SafeVarargs
	public AotList(T... items)  {
		super();
		for (T item : items)
			add(item);
	}

	public AotList(Iterable<T> collection)  {
		super(collection);
		for (T item : collection)
			add(item);
	}
	
	public AotList(Iterable<T> collection, Query query)  {
		super(collection,query);
		for (T item : collection)
			add(item);
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


	private class CorrectingIterator implements Iterator<T> {

		protected ListNode<T> current = head;
		protected boolean  correcting;
		protected ListNode<T> lastReturned = null;
		protected Query    query        = null;

		public CorrectingIterator(boolean correcting, Query query) {
			this.correcting = correcting;
			this.query = query;
		}

		protected void correctIterator() {
			if (correcting) {
				if (isRemovedItem(current))
					current = current.next;
			} else {
				if (isRemovedItem(current))
					throw new IllegalStateException("Non-correcting iterator is pointing to a removed item");
			}	

			// move to next node matching query
			//
			if (query != null) 
				while (current != null && !query.satisfied(current.item))
					current = current.next;
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

	}


	@Override
	public Iterator<T> iterator() {
		return new CorrectingIterator(true, null);
	}


	public Iterator<T> correctingIterator(boolean correcting, Query query) {
		return new CorrectingIterator(correcting, query);
	}

	public Iterator<T> correctingIterator(boolean correcting) {
		return new CorrectingIterator(correcting, null);
	}

	public Iterator<T> correctingIterator() {
		return new CorrectingIterator(true, null);
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
			a = (E[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
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
		if (n==null)
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

	@SuppressWarnings("unchecked")
	public boolean addAll(T... items) {
		for (T item : items) {
			add(item);
		}
		return true;
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
		// invalidate all nodes so that checked iterators know that the nodes have been deleted
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
		if (index < size-1)
			node.next.prev = newNode;
		else
			tail = newNode;
		node.next = newNode;
	}

	@Override
	public T remove(int index) {
		ListNode<T> node = nodeAt(index);
		if (size==1) {
			node.next = node;
			head = null;
			tail = null;
		} else if (index == 0) {
			head = node.next;
			node.next.prev = null;
		} else if (index == size-1) {
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
		int index = size -1;
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
		else if (index == size -1)
			return tail;
		else {
			ListNode<T> result = head;
			for (int i=0; i<index; i++)
				result = result.next;
			return result;
		}

	}

	private class CorrectingListIterator extends CorrectingIterator implements ListIterator<T> {

		public CorrectingListIterator(boolean correcting, int index, Query query) {
			super(correcting, query);
			if (size()==0)
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


	}

	@Override
	public ListIterator<T> listIterator() {
		return new CorrectingListIterator(true, 0, null);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new CorrectingListIterator(true, index, null);
	}

	public ListIterator<T> checkedListIterator(boolean correcting) {
		return new CorrectingListIterator(correcting, 0, null);
	}

	public ListIterator<T> correctingListIterator(int index, boolean correcting, Query query) {
		return new CorrectingListIterator(correcting, index, query);
	}

	public ListIterator<T> correctingListIterator(int index, boolean correcting) {
		return new CorrectingListIterator(correcting, index, null);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		AotList<T> result = new AotList<T>();
		ListNode<T> node = nodeAt(fromIndex);
		for (int i=fromIndex; i<=toIndex; i++) {
			result.add(node.item);
			node = node.next;
		}
		return result;
	}


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
		remove(size-1);
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

	public Iterator<T> descendingCorrectingIterator(boolean correcting) {
		return new CorrectingDescendingIterator(correcting);
	}

	private class CorrectingDescendingIterator implements Iterator<T> {

		protected ListNode<T> current = tail;
		protected boolean  correcting;
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
	}


	// Additional helper methods
	//

	public AotList<T> difference(AotList<T> list) {
		AotList<T> result = new AotList<T>();
		for (T item : list)
			if (!this.contains(item))
				result.add(item);
		for (T item : this)
			if (!list.contains(item))
				result.add(item);			
		return result;
	}

	public AotList<T> intersection(AotList<T> list) {
		AotList<T> result = new AotList<T>();
		for (T item : list)
			if (this.contains(item))
				result.add(item);
		return result;
	}

	/**
	 * Renamed from sort to sortList to avpid conflict with JDK 1.8 List.sort()
	 * 
	 * @param comparator
	 */
	public void sortList(Comparator<? super T> comparator) {
		if (this.size() > 0)
			Collections.sort(this, comparator);
	}

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

	public boolean addUnique(T item) {
		if (this.contains(item))
			return false;
		else
			return add(item);
	}

	public boolean addAllUnique(AotList<T> list) {
		boolean result = true;
		for (T item : list) {
			if (!addUnique(item))
				result = false;
		}
		return result;
	}

	@Override
	public void pack() {	
	}

	// Copying and Cloning AotLists
	//

	/**
	 * Creates a normal array comprising elements which refer to the data items in this AotList.  Includes
	 * items that satisfy the specified query
	 * 
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T[] makeArray(Query query) {
		Object[] result;
		if (query == null) {
			result = new Object[size()];
			int i=0;
			for (T t : this) {
				result[i] = t;
				i++;
			}
		} else {
			Iterator<T> iter = this.correctingIterator(true, query);
			result = new Object[size()];
			// TODO: finish implementing this
		}
		return (T[])result;
	}

	/**
	 * Creates a normal array comprising elements which refer to the data items in this AotList
	 * 
	 * @return
	 */
	public T[] makeArray() {
		return makeArray(null);
	}

	//	/**
	//	 * Creates a new AotList which refers to the data items in this list.  Includes
	//	 * items that satisfy the specified query
	//	 * 
	//	 * @param query
	//	 * @return
	//	 */
	//	public AotList<T> toAotList(Query query) {
	//		AotList<T> result = new AotList<T>();
	//		if (query == null) {
	//			result = new Object[size()];
	//			int i=0;
	//			for (T t : this) {
	//				result[i] = t;
	//				i++;
	//			}
	//		} else {
	//			Iterator<T> iter = this.correctingIterator(true, query);
	//			result = new Object[size()];
	//			
	//		}
	//		return (T[])result;
	//		
	//	}
	//	
	//	/**
	//	 * Creates a new AotList whoch refers to the data items in this list
	//	 * 
	//	 * @return
	//	 */
	//	public AotList<T> toAotList() {
	//		return toAotList(null);
	//	}
	//	
	//	/**
	//	 * Creates a new AotArray in which elements refer to the data items in this list. Includes
	//	 * items that satisfy the specified query
	//	 * 
	//	 * @param query
	//	 * @return
	//	 */
	//	public AotArray<T> toAotArray(Query query) {
	//		
	//	}
	//	
	//	/**
	//	 * Creates a new AotArray in which elements refer to the data items in this list.
	//	 * 
	//	 * @return
	//	 */
	//	public AotArray<T> toAotArray() {
	//		return toAotArray(null);
	//	}
	//	
	//	/**
	//	 * Creates a new AotRefArray in which elements refer to the nodes in this list. Only includes
	//	 * nodes referring to data which satisfied the specified query. See the 
	//	 * AotRefArray class for an explanation of why this is useful 
	//	 * 
	//	 * @param query
	//	 * @return
	//	 */
	//	public AotRefArray<T> toAotRefArray(Query query) {
	//		
	//	}
	//	
	//	/**
	//	 * Creates a new AotRefList in which elements refer to the nodes in this list. See the 
	//	 * AotRefList class for an explanation of why this is useful 
	//	 * 
	//	 * @return
	//	 */
	//	public AotRefArray<T> toAotRefArray() {
	//		return toAotRefArray(null);
	//	}
	//	
	//	/**
	//	 * Creates a new AotRefList in which elements refer to the nodes in this list. Only includes
	//	 * nodes referring to data which satisfied the specified query. See the 
	//	 * AotRefList class for an explanation of why this is useful 
	//	 * 
	//	 * @param query
	//	 * @return
	//	 */
	//	public AotRefList<T> toAotRefList(Query query) {
	//		
	//	}
	//	
	//	/**
	//	 * Creates a new AotRefList in which elements refer to the nodes in this list. See the 
	//	 * AotRefList class for an explanation of why this is useful 
	//	 * 
	//	 * @return
	//	 */
	//	public AotRefList<T> toAotRefArray() {
	//		return toAotRefList(null);
	//	}
	//	
	//	
	//	/**
	//	 * Makes a copy of this AotList. List items are cloned. Includes items that satisfy the specified query.
	//	 * 
	//	 * @param query
	//	 * @return
	//	 */
	//	public AotList<T> cloneAotList(Query query) {
	//		
	//	}
	//
	//	/**
	//	 * Makes a copy of this AotList. List items are cloned.
	//	 * 
	//	 * @return
	//	 */
	//	public AotList<T> cloneAotList() {
	//		cloneAotList(null);
	//	}
	//
	//	/**
	//	 * Makes a new AotArray which contains clones of elements in this AotList. Includes items 
	//	 * that satisfy the specified query
	//	 * 
	//	 * @param query
	//	 * @return
	//	 */
	//	public AotArray<T> cloneAotArray(Query query) {
	//		
	//	}
	//
	//	/**
	//	 * Makes a new AotArray which contains clones of elements in this AotList.
	//	 * 
	//	 * @return
	//	 */
	//	public AotArray<T> cloneAotArray() {
	//		return cloneAotArray(null);
	//	}
	//
	//	/** Makes a standard array containing clones of elements in this AotList. Includes items 
	//	 * that satisfy the specified query
	//	 * 
	//	 * @param query
	//	 * @return
	//	 */
	//	public Object[] cloneArray(Query query) {
	//		
	//	}
	//
	//	/** Makes a standard array containing clones of elements in this AotList.
	//	 * 
	//	 * @return
	//	 */
	//	public Object[] cloneArray() {
	//		return cloneArray(null);
	//	}

	// IO
	//
	@Override
	public String toString() {
		return "[AotList of " + size() + " element(s)]";
	}

	public String toLongString() {
		String result = "[AotList of " + size() + " element(s)\n";
		int idx = 0;
		for (T item : this) {
			result = result + "    [" + idx + ":" + item + "]\n";
			idx++;
		}
		return result + "]";
	}

	// Visualisation
	//

	@SuppressWarnings("unchecked")
	public void visualise(Iterator<T>... iterators) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		MultiGraph graphStream = new MultiGraph(this.toString());

		graphStream.setAttribute("ui.stylesheet", "url('file://" + Resource.getFile("aotList.css", this.getClass()) + "')");
		graphStream.addAttribute("ui.quality");
		graphStream.addAttribute("ui.antialias");

		ListNode<T> listNode = head;
		while (listNode != null) {
			Node node = graphStream.addNode(listNode.toString());
			node.setAttribute("ui.label",  listNode.item.toString());
			node.setAttribute("ui.class",  "list");
			listNode = listNode.next;
		}

		listNode = head;
		while (listNode != null) {
			if (listNode.prev != null) {
				Edge edge = graphStream.addEdge(new Uid().toString(), listNode.toString(), listNode.prev.toString(), true);
				edge.setAttribute("ui.label",  "p");
			}
			if (listNode.next != null) {
				Edge edge = graphStream.addEdge(new Uid().toString(), listNode.toString(), listNode.next.toString(), true);
				edge.setAttribute("ui.label",  "n");
			}

			listNode = listNode.next;
		}

		Node headNode = graphStream.addNode("HEAD");
		headNode.setAttribute("ui.label",  "HEAD");
		headNode.setAttribute("ui.class",  "head");
		Edge headEdge = graphStream.addEdge(new Uid().toString(), "HEAD", head.toString(), true);
		Node tailNode = graphStream.addNode("TAIL");
		tailNode.setAttribute("ui.label",  "TAIL");
		tailNode.setAttribute("ui.class",  "tail");
		Edge tailEdge = graphStream.addEdge(new Uid().toString(), "TAIL", tail.toString(), true);

		for (Iterator<T> iter : iterators) {
			CorrectingIterator ci = (CorrectingIterator)iter;
			Node iterNode = graphStream.addNode(ci.toString());
			iterNode.setAttribute("ui.label",  "ITER");
			ListNode<T> currentListNode = ci.current;
			if (isRemovedItem(currentListNode)) {
				Node removedListNode = graphStream.addNode(currentListNode.toString());
				removedListNode.setAttribute("ui.class",  "removed");
				removedListNode.setAttribute("ui.label",  currentListNode.item.toString());
				if (currentListNode.prev != null) {
					Edge edge = graphStream.addEdge(new Uid().toString(), currentListNode.toString(), currentListNode.prev.toString(), true);
					edge.setAttribute("ui.label",  "p");
				}
				if (currentListNode.next != null) {
					Edge edge = graphStream.addEdge(new Uid().toString(), currentListNode.toString(), currentListNode.next.toString(), true);
					edge.setAttribute("ui.label",  "n");
				}
				Edge iterEdge = graphStream.addEdge(new Uid().toString(), iterNode.toString(), removedListNode.toString(), true);								
			} else {
				Edge iterEdge = graphStream.addEdge(new Uid().toString(), iterNode.toString(), currentListNode.toString(), true);				
			}
		}
		graphStream.display();
	}

}
