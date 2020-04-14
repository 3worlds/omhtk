/**************************************************************************
 *  TW-CORE - 3Worlds Core classes and methods                            *
 *                                                                        *
 *  Copyright 2018: Shayne Flint, Jacques Gignoux & Ian D. Davies         *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            *
 *                                                                        *
 *  TW-CORE is a library of the principle components required by 3W       *
 *                                                                        *
 **************************************************************************
 *  This file is part of TW-CORE (3Worlds Core).                          *
 *                                                                        *
 *  TW-CORE is free software: you can redistribute it and/or modify       *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  TW-CORE is distributed in the hope that it will be useful,            *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with TW-CORE.                                                   *
 *  If not, see <https://www.gnu.org/licenses/gpl.html>                   *
 *                                                                        *
 **************************************************************************/

package fr.ens.biologie.codeGeneration;

import java.util.Date;


/**
 * A class for defining generic comments in generated ode files.
 *
 * @author Jacques Gignoux - 19 déc. 2014
 *
 */
public class Comments {

	public static String[] generated = {
		"CAUTION: generated code - do not modify"
	};

	public static String[] editableCode = {
		"CAUTION: Edit this template but do not change class declaration."
	};

	public static String[] startCodeInsertion = {
		"*************************** INSERT YOUR CODE BELOW THIS LINE ***************************"
	};

	public static String[] endCodeInsertion = {
		"******************************** END CODE INSERTION ZONE *******************************"
	};

	public static String[] separatingLine = {
		"****************************************************************************************"
	};

	public static String[] copyright(int year, String... authors) {
		String[] s = new String[1];
		s[0] = "Copyright (C) "+year+" - ";
		for (String a:authors) {
			s[0]+=a+" - ";
		}
		return s;
	}

	public static String[] generatedCode(boolean editable, String model, String version) {
		String[] s = new String[2];
//		s[0] = "Model "+model+" - "+version+" - "+new GregorianCalendar().toString();
		s[0] = "Model \""+model+"\" - "+version+" - "+ new Date().toString();
		if (editable) s[1] = editableCode[0];
		else s[1] = generated[0];
		return s;

	}

	public static String[] classComment(String className) {
		String[] s = new String[1];
		s[0] = "Class "+className;
		return s;
	}

	/**
	 * generates a header comment, ie enclosed by /* ... &#042;&#047;
	 * @param strings the lines inside the comment block
	 * @return the full comment as a String
	 */
	public static String comment(String[]...strings) {
		String cm = "/*\n";
		for (String[] ss:strings) {
			for (String s:ss) {
				cm += " * "+s+"\n";
			}
		}
		cm += "*/\n";
		return cm;
	}

	/**
	 * generates a header comment, ie enclosed by /* ... &#042;&#047;
	 * @param strings the lines inside the comment block
	 * @return the full comment as a String
	 */
	public static String javaDocComment(String indent,String[]...strings) {
		if (indent==null)
			indent = "";
		String cm = indent+"/**\n";
		for (String[] ss:strings) {
			for (String s:ss) {
				cm += indent+" * "+s+"\n";
			}
		}
		cm += indent+"*/\n";
		return cm;
	}

	/**
	 * generates a single line comment, ie starting with //
	 * @param strings the lines to comment
	 * @return the full comment as a String
	 */
	public static String singleLineComment(String[]...strings) {
		String cm = "";
		for (String[] ss:strings) {
			for (String s:ss) {
				cm += "// "+s+"\n";
			}
		}
		return cm;
	}



}
