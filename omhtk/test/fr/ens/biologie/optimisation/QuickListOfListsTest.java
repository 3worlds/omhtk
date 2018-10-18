package fr.ens.biologie.optimisation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class QuickListOfListsTest {
	
	private QuickListOfLists<Integer> makeList() {
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new LinkedList<Integer>();
		Set<Integer> list3 = new HashSet<Integer>();
		list1.add(1); list1.add(2); list1.add(3);
		list2.add(4); list2.add(5);
		list3.add(6); list3.add(7); list3.add(8);
		QuickListOfLists<Integer> list = new QuickListOfLists<Integer>(list1,list2,list3);
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
