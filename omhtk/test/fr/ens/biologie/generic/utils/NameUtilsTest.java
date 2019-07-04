package fr.ens.biologie.generic.utils;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Jacques Gignoux - 4 juil. 2019
 *
 */
class NameUtilsTest {

	private String s = "1	bla bla bla °à\t+^ZZ 22";
	private String s2 = "this is a stupid String.";
	
	private void show(String method,String text) {
		System.out.println(method+": "+text);
	}
	
	@Test
	final void testWordUpperCaseName() {
		show("testWordUpperCaseName",NameUtils.wordUpperCaseName(s));
		show("testWordUpperCaseName",NameUtils.wordUpperCaseName(s2));
	}

	@Test
	final void testInitialUpperCase() {
		show("testInitialUpperCase",NameUtils.initialUpperCase(s));
		show("testInitialUpperCase",NameUtils.initialUpperCase(s2));
	}

	@Test
	final void testWordUnderscoreName() {
		show("testWordUnderscoreName",NameUtils.wordUnderscoreName(s));
		show("testWordUnderscoreName",NameUtils.wordUnderscoreName(s2));
	}

	@Test
	final void testValidJavaName() {
		show("testValidJavaName",NameUtils.validJavaName(s));
		show("testValidJavaName",NameUtils.validJavaName(s2));
	}

}
