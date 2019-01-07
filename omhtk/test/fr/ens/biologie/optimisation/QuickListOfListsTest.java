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

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import au.edu.anu.rscs.aot.collections.QuickListOfLists;

class QuickListOfListsTest {
	
	private QuickListOfLists<Integer> makeList() {
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new LinkedList<>();
		Set<Integer> list3 = new HashSet<>();
		list1.add(1); list1.add(2); list1.add(3);
		list2.add(4); list2.add(5);
		list3.add(6); list3.add(7); list3.add(8);
		QuickListOfLists<Integer> list = new QuickListOfLists<>(list1,list2,list3);
		return list;
	}
	
	private String listToString(Iterable<Integer> l) {
		StringBuilder sb = new StringBuilder();
		for (Integer i:l)
			sb.append(i).append(',');
		return sb.toString();
	}

	@Test
	void testQuickListOfLists() {
		assertNotNull(makeList());
	}

	@Test
	void testIterator() {
		assertEquals(listToString(makeList()),"1,2,3,4,5,6,7,8,");
	}

	@Test
	void testAddList() {
		List<Integer> list = new LinkedList<Integer>();
		list.add(9);
		QuickListOfLists<Integer> l = makeList();
		assertEquals(listToString(l),"1,2,3,4,5,6,7,8,");
		l.addList(list);
		assertEquals(listToString(l),"1,2,3,4,5,6,7,8,9,");
	}

	@Test
	void testClear() {
		QuickListOfLists<Integer> l = makeList();
		l.clear();
		assertEquals(listToString(l),"");
	}

}
