package au.edu.anu.rscs.aot.collections;

/**
 * A container for items in a List (formerly AotList).
 * 
 * @author Shayne Flint - long ago
 *
 * @param <T>
 */
public class ListNode<T> {
	
	protected T item;
	protected ListNode<T> next;
	protected ListNode<T> prev;

	public ListNode(T item, ListNode<T> next, ListNode<T> prev) {
		this.item = item;
		this.next = next;
		this.prev = prev;
	}

	public String toString() {
		String result = "[ListNode "; 
		if (isRemoved())
			result = result + "(removed) ";
		result = result + this.item + " prev=";
		if (prev == null)
			result = result + "null    ";
		else
			result = result + prev.item;
		result = result + " next=";
		if (next == null)
			result = result + "null    ";
		else
			result = result + next.item;
		result = result + " item=" + item + "]";
		return result;
	}
	
	public boolean isRemoved() {
		return (prev == this);
	}

}