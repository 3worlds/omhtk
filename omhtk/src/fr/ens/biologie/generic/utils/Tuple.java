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
package fr.ens.biologie.generic.utils;

/**
 * A class holding a triplet (in the mathematical sense, ie ordered) of objects of possibly different classes. 
 * A three element Tuple based on javafx.util.Pair. Immutable.
 * 
 * @author Ian Davies - 14 Oct 2019
 * 
 * @param <F> The class of the first triplet member
 * @param <S> The class of the second triplet member
 * @param <T> The class of the triplet member
 *
 *	@see Duple
 */
public class Tuple<F, S, T> {

	private F first;
	private S second;
	private T third;

	/**
	 * 
	 * @param f the first member 
	 * @param s the second member
	 * @param t the third member
	 */
	public Tuple(F f, S s, T t) {
		super();
		first = f;
		second = s;
		third = t;
	}

	/**
	 * The first member.
	 * @return
	 */
	public F getFirst() {
		return first;
	}

	/**
	 * The second member.
	 * @return
	 */
	public S getSecond() {
		return second;
	}

	/**
	 * The third member.
	 * @return
	 */
	public T getThird() {
		return third;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(first).append("|").append(second).append("|").append(third);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(java.lang.Object o) {
		if (o == null)
			return false;
		try {
			Tuple<F, S, T> other = (Tuple<F, S, T>) o;
			boolean equalFirst = false;
			if (first == null)
				if (other.first == null)
					equalFirst = true;
				else
					return false;
			else if (other.first == null)
				return false;
			else
				equalFirst = first.equals(other.first);

			boolean equalSecond = false;
			if (second == null)
				if (other.second == null)
					equalSecond = true;
				else
					return false;
			else if (other.second == null)
				return false;
			else
				equalSecond = second.equals(other.second);

			boolean equalThird = false;
			if (third == null)
				if (other.third == null)
					equalThird = true;
				else
					return false;
			else if (other.third == null)
				return false;
			else
				equalThird = third.equals(other.third);

			return equalFirst && equalSecond && equalThird;

		} catch (Exception e) {
			return false;
		}
	}

}
