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
package au.edu.anu.rscs.aot.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;

import au.edu.anu.rscs.aot.environment.LocalEnvironment;

class ResourcesTest {

	String textFile = "TestFile.txt";
	String javaFile = "DateTime";
	String pkg = "au.edu.anu.rscs.aot.util";
	String location = pkg+"."+javaFile;
	String nonExistantFile = "OtherTestFile.txt";
	
	private void show(String method,String text) {
//		System.out.println(method+": "+text);
	}
	
	@Test
	final void testGetURLString() {
		URL url = Resources.getURL(textFile,pkg);
		assertTrue(new File(url.getFile()).exists());
		show("testGetURLString",url.toString());
	}

	@Test
	final void testGetURLStringString() {
		URL url = Resources.getURL(textFile,pkg);
		show("testGetURLStringString",url.toString());
	}

	@Test
	final void testGetURLStringClassOfQ() {
		URL url = Resources.getURL(textFile,IntegerRange.class);
		show("testGetURLStringClassOfQ",url.toString());
	}

	@Test
	final void testGetFileString() {
		File file = Resources.getFile(textFile);
		assertNull(file);
		file = Resources.getFile(pkg.replace('.',File.separatorChar)+File.separator+textFile);
		assertTrue(file.exists());
	}

	@Test
	final void testGetFileStringClassOfQ() {
		File file = Resources.getFile(textFile, IntegerRange.class);
		assertTrue(file.exists());
		// file does not exist
		file = Resources.getFile(nonExistantFile, IntegerRange.class);
		assertNull(file);
		// wrong package
		file = Resources.getFile(textFile, LocalEnvironment.class);
		assertNull(file);

	}

	@Test
	final void testGetFileStringString() {
		File file = Resources.getFile(textFile, pkg);
		assertTrue(file.exists());
		file = Resources.getFile(nonExistantFile, pkg);
		assertNull(file);
	}

	@Test
	final void testGetTextResourceString() {
		// read a plain file
		List<String> fcontent = Resources.getTextResource(pkg.replace('.',File.separatorChar)+File.separator+textFile);
		show("testGetTextResourceString",fcontent.toString());
		assertEquals(fcontent.toString(),"[Test file , line 1, line 2]");
	}

	@Test
	final void testGetTextResourceStringClassOfQ() {
		// read a plain file
		List<String> fcontent = Resources.getTextResource(textFile,IntegerRange.class);
		show("testGetTextResourceStringClassOfQ",fcontent.toString());
		assertEquals(fcontent.toString(),"[Test file , line 1, line 2]");
	}

	@Test
	final void testGetPackagedFileName() {
		// THis is crazy non-os independent crap!
		String s = Resources.getPackagedFileName("au.edu.anu.twcore.archetype.tw.3wA-Experiment.ugt");
		show("testGetPackagedFileName",s);
		assertEquals(s,"au/edu/anu/twcore/archetype/tw/3wA-Experiment.ugt");
		s = Resources.getPackagedFileName("au.edu.anu.twcore.archetype.tw/3wA-Experiment.ugt");
		show("testGetPackagedFileName",s);
		assertEquals(s,"au/edu/anu/twcore/archetype/tw/3wA-Experiment.ugt");
	}
	
}
