package au.edu.anu.rscs.aot.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Jacques Gignoux - 13 août 2019
 *
 */
class JavaUtilsTest {

	private void show(String method,String text) {
		System.out.println(method+": "+text);
	}

	@Test
	final void testGetClassString() {
		assertThrows(Exception.class,()->JavaUtils.getClass("JavaUtils"));
		try {
			Class<?> c = JavaUtils.getClass("au.edu.anu.rscs.aot.util.JavaUtils");
			show("testGetClassString",c.getName());
			assertNotNull(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	final void testClassExists() {
		assertTrue(JavaUtils.classExists("au.edu.anu.rscs.aot.util.JavaUtils"));
		assertFalse(JavaUtils.classExists("JavaUtils"));
	}

	@Test
	final void testGetClassesInPackage() {
		List<Class<?>> list = JavaUtils.getClassesInPackage("au.edu.anu.rscs.aot.collections");
		assertNotNull(list);
		for (Class<?> c:list)
			show("testGetClassesInPackage",c.getSimpleName());
		assertEquals(list.size(),8);
	}

	@Test
	final void testGetClassesInPackageTree() {
		List<Class<?>> list = JavaUtils.getClassesInPackageTree("au.edu.anu.rscs.aot");
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
	final void testGetClassListListOfString() {
		List<String> list = new ArrayList<>();
		list.add("au.edu.anu.rscs.aot.util.JavaUtils");
		list.add("java.lang.Object");
		List<Class<?>> result = JavaUtils.getClassList(list);
		assertNotNull(result);
		for (Class<?> c:result)
			show("testGetClassListListOfString",c.getName());
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
		File f = JavaUtils.fileForClass("au.edu.anu.rscs.aot.collections.DynamicList");
		show("testFileForClassClassOfQ",f.toString());
		assertNotNull(f);
	}

}
