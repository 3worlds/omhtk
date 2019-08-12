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
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/
package fr.ens.biologie.generic.utils;

/**
 * A kind of clone of javafx.util.Pair which is not available in OpenJDK but in Oracle JDK only.
 * cf bug #11 for details on why I implemented this class
 * 
 * @author Jacques Gignoux - 18 avr. 2019
 *
 * @param <F>
 * @param <S>
 */
public class Duple<F,S> {
	
	private F first;
	private S second;
	
	public Duple(F f, S s) {
		super();
		first = f;
		second = s;
	}
	
	public F getFirst() {
		return first;
	}
	
	public S getSecond() {
		return second;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(first).append("|").append(second);
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(java.lang.Object o) {
		if (o==null)
			return false;
		try {
			Duple<F,S> p = (Duple<F,S>) o;
			boolean equalKeys = false;
			if (first==null)
				if (p.first==null)
					equalKeys = true;
				else
					return false;
			else 
				if (p.first==null)
					return false;
				else
					equalKeys = first.equals(p.first);
			boolean equalValues = false;
			if (second==null)
				if (p.second==null)
					equalValues = true;
				else 
					return false;
			else
				if (p.second==null)
					return false;
				else
					equalValues = second.equals(p.second);
			return equalKeys && equalValues;
		} catch (Exception e) {
			return false;
		}
	}

	
}