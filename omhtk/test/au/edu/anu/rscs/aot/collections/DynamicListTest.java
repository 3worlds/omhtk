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
package au.edu.anu.rscs.aot.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DynamicListTest {
	
	private DynamicList<Integer> l1, l2, l3;
	
	@BeforeEach
	private void init() {
		l1 = new DynamicList<Integer>();
		l2 = new DynamicList<Integer>(1,2,3,4,5);
//		ArrayList<Integer> t = new ArrayList<>();
//		t.add(1); t.add(2); t.add(3);t.add(4);t.add(5);
		l3 = new DynamicList<>(l2);
	}
	
	private void show(String method,String text) {
//		System.out.println(method+": "+text);
	}

	@Test
	void testDynamicList() {
		assertNotNull(l1);
	}

	@Test
	void testDynamicListTArray() {
		assertNotNull(l2);
	}

	@Test
	void testDynamicListIterableOfT() {
		assertNotNull(l3);
	}

	@Test
	void testSize() {
		show("testSize",String.valueOf(l1.size()));
		assertEquals(l1.size(),0);
		show("testSize",String.valueOf(l2.size()));
		assertEquals(l2.size(),5);
		show("testSize",String.valueOf(l3.size()));
		assertEquals(l3.size(),5);
	}

	@Test
	void testIsEmpty() {
		assertTrue(l1.isEmpty());
		assertFalse(l2.isEmpty());
	}

	@Test
	void testContains() {
		assertTrue(l2.contains(3));
		assertFalse(l3.contains(8));
	}

	@Test
	void testIterator() {
		Iterator<Integer> it = l3.iterator();
		int i=0;
		while (it.hasNext())
			i += it.next();
		assertEquals(i,15);
		i=0;
		// this implicitly calls iterator()
		for (int ii:l2)
			i += ii;
		assertEquals(i,15);
		i=0;
		// example with removal while looping
		for (int ii:l2) {
			if (ii==3)
				l2.remove((Integer)ii);
			else
				i += ii;
		}
		assertEquals(i,12);
		assertEquals(l2.size(),4);
	}

	@Test
	void testCorrectingIteratorBoolean() {
		Iterator<Integer> it = l2.correctingIterator(false);
		int i=0;
		while (it.hasNext()) {
			i += it.next();
		}
		assertEquals(i,15);
	}

	@Test
	void testCorrectingIterator() {
		fail("Not yet implemented");
	}

	@Test
	void testToArray() {
		fail("Not yet implemented");
	}

	@Test
	void testToArrayEArray() {
		fail("Not yet implemented");
	}

	@Test
	void testAddT() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveObject() {
		fail("Not yet implemented");
	}

	@Test
	void testContainsAll() {
		fail("Not yet implemented");
	}

	@Test
	void testAddAllCollectionOfQextendsT() {
		fail("Not yet implemented");
	}

	@Test
	void testAddAllIntCollectionOfQextendsT() {
		fail("Not yet implemented");
	}

	@Test
	void testAddAllTArray() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveAll() {
		fail("Not yet implemented");
	}

	@Test
	void testRetainAll() {
		fail("Not yet implemented");
	}

	@Test
	void testClear() {
		fail("Not yet implemented");
	}

	@Test
	void testGet() {
		fail("Not yet implemented");
	}

	@Test
	void testSet() {
		fail("Not yet implemented");
	}

	@Test
	void testAddIntT() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveInt() {
		fail("Not yet implemented");
	}

	@Test
	void testIndexOf() {
		fail("Not yet implemented");
	}

	@Test
	void testLastIndexOf() {
		fail("Not yet implemented");
	}

	@Test
	void testListIterator() {
		fail("Not yet implemented");
	}

	@Test
	void testListIteratorInt() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckedListIterator() {
		fail("Not yet implemented");
	}

	@Test
	void testCorrectingListIterator() {
		fail("Not yet implemented");
	}

	@Test
	void testSubList() {
		fail("Not yet implemented");
	}

	@Test
	void testShowString() {
		fail("Not yet implemented");
	}

	@Test
	void testShow() {
		fail("Not yet implemented");
	}

	@Test
	void testAddFirst() {
		fail("Not yet implemented");
	}

	@Test
	void testAddLast() {
		fail("Not yet implemented");
	}

	@Test
	void testOfferFirst() {
		fail("Not yet implemented");
	}

	@Test
	void testOfferLast() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveFirst() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveLast() {
		fail("Not yet implemented");
	}

	@Test
	void testPollFirst() {
		fail("Not yet implemented");
	}

	@Test
	void testPollLast() {
		fail("Not yet implemented");
	}

	@Test
	void testGetFirst() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLast() {
		fail("Not yet implemented");
	}

	@Test
	void testPeekFirst() {
		fail("Not yet implemented");
	}

	@Test
	void testPeekLast() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveFirstOccurrence() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveLastOccurrence() {
		fail("Not yet implemented");
	}

	@Test
	void testOffer() {
		fail("Not yet implemented");
	}

	@Test
	void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	void testPoll() {
		fail("Not yet implemented");
	}

	@Test
	void testElement() {
		fail("Not yet implemented");
	}

	@Test
	void testPeek() {
		fail("Not yet implemented");
	}

	@Test
	void testPush() {
		fail("Not yet implemented");
	}

	@Test
	void testPop() {
		fail("Not yet implemented");
	}

	@Test
	void testDescendingIterator() {
		fail("Not yet implemented");
	}

	@Test
	void testDescendingCorrectingIterator() {
		fail("Not yet implemented");
	}

	@Test
	void testDifference() {
		fail("Not yet implemented");
	}

	@Test
	void testIntersection() {
		fail("Not yet implemented");
	}

	@Test
	void testSortList() {
		fail("Not yet implemented");
	}

	@Test
	void testIsSorted() {
		fail("Not yet implemented");
	}

	@Test
	void testAddUnique() {
		fail("Not yet implemented");
	}

	@Test
	void testAddAllUnique() {
		fail("Not yet implemented");
	}

	@Test
	void testMakeArray() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

	@Test
	void testToLongString() {
		fail("Not yet implemented");
	}

}
