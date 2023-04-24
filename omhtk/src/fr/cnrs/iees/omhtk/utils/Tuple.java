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
package fr.cnrs.iees.omhtk.utils;

import java.util.Objects;

/**
 * A class holding a triplet (in the mathematical sense, ie ordered) of objects
 * of possibly different classes. A three element Tuple based on
 * javafx.util.Pair. Immutable.
 * 
 * @author Ian Davies - 14 Oct 2019
 * 
 * @param <F> The class of the first triplet member
 * @param <S> The class of the second triplet member
 * @param <T> The class of the triplet member
 *
 * @see Duple
 */
public class Tuple<F, S, T> {

	private F first;
	private S second;
	private T third;
	// hash code for faster comparison in maps
	private int hash = 0;

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

	@Override
	public int hashCode() {
		if (hash==0)
			hash = Objects.hash(first,second,third); 
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Tuple))
			return false;
		Tuple<?,?,?> other = (Tuple<?,?,?>) obj;
		return Objects.equals(first,other.first) && Objects.equals(second,other.second)
				&& Objects.equals(third,other.third);
	}

	/**
	 * @return The first member.
	 */
	public F getFirst() {
		return first;
	}

	/**
	 * @return The second member.
	 */
	public S getSecond() {
		return second;
	}

	/**
	 * @return The third member.
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


}
