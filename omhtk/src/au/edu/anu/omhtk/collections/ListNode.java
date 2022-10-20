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
package au.edu.anu.omhtk.collections;

/**
 * A container for items in a List (formerly AotList). This class is not meant
 * to be used directly, it's hidden in {@link DynamicList}.
 * 
 * @param <T> The list element type
 * @author Shayne Flint - long ago
 */
public class ListNode<T> {

	protected T item;
	protected ListNode<T> next;
	protected ListNode<T> prev;

	/**
	 * ListNode constructor.
	 * 
	 * @param item The item to wrap.
	 * @param next Its next ListNode.
	 * @param prev Its previous ListNode.
	 */
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

	/**
	 * @return If removed, false otherwise.
	 */
	public boolean isRemoved() {
		return (prev == this);
	}

}
