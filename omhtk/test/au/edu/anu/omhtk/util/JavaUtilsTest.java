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
package au.edu.anu.omhtk.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Jacques Gignoux - 13 août 2019
 *
 */
class JavaUtilsTest {

	private void show(String method,String text) {
//		System.out.println(method+": "+text);
	}


	@Test
	final void testGetClassesInPackage() {
		List<Class<?>> list = JavaUtils.getClassesInPackage("fr.cnrs.iees.omugi.collections");
		assertNotNull(list);
		for (Class<?> c:list)
			show("testGetClassesInPackage",c.getSimpleName());
		assertEquals(list.size(),9);
	}

	@Test
	final void testGetClassesInPackageTree() {
		List<Class<?>> list = JavaUtils.getClassesInPackageTree("au.edu.anu.omhtk");
		assertNotNull(list);
		for (Class<?> c:list)
			show("testGetClassesInPackageTree",c.getName());
	}

	@Test
	final void testGetClassList() {
//		List<Class<?>> l = JavaUtils.getClassList();
//		show("testGetClassList",String.valueOf(l.size()));
//		assertTrue(l.size()>0);
	}


	@Test
	final void testGetClassNameList() {
		List<String> l = JavaUtils.getClassNameList();
		show("testGetClassNameList",String.valueOf(l.size()));
		assertTrue(l.size()>0);
	}

	@Test
	final void testFileForClassClassOfQ() {
		File f = JavaUtils.fileForClass(this.getClass());
		show("testFileForClassClassOfQ",f.toString());
		assertNotNull(f);
	}

	@Test
	final void testFileForClassString() {
		File f = JavaUtils.fileForClass("fr.cnrs.iees.omugi.collections.DynamicList");
		show("testFileForClassClassOfQ",f.toString());
		assertNotNull(f);
	}

}
