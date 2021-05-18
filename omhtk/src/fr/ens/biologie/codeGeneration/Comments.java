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
 * A class for defining generic comments in generated code files.
 *
 * @author Jacques Gignoux - 19 déc. 2014
 *
 */
public class Comments {

	/** A warning comment - generated code should not be edited */
	public static String[] generated = {"CAUTION: generated code - do not modify"};

	/** A warning comment - editing code is permitted */
	public static String[] editableCode = 
		{"CAUTION: Edit this template but do not change class declaration."};

	/** A code insertion comment - sart of insertion zone */
	public static final String beginCodeInsert = "Code insert Begin";
	/** A code insertion comment - sart of insertion zone */
	public static String[] startCodeInsertion = {"---- "+beginCodeInsert+ "-->"};

	/** A code insertion comment - end of insertion zone */
	public static final String endCodeInsert = "Code insert End";
	/** A code insertion comment - end of insertion zone */
	public static String[] endCodeInsertion = {"---- "+endCodeInsert+ "----<"};

	/** A comment separating line */
	public static String[] separatingLine = {// 80 chars
		    "********************************************************************************"
	};

	/**
	 * Generate a copyright comment
	 * @param year year of authorship
	 * @param authors list of author names
	 * @return the generated comment
	 */
	public static String[] copyright(int year, String... authors) {
		String[] s = new String[1];
		s[0] = "Copyright (C) "+year+" - ";
		for (String a:authors) {
			s[0]+=a+" - ";
		}
		return s;
	}

	/**
	 * Generate a 'generated code' notice at the beginning of a class file.
	 * @param editable {@code true} if this code can be edited, {@code false} if it must stay as is
	 * @param model name of the generated piece of code (called a 'Model')
	 * @param version version number
	 * @return a comment including an edition warning and a time stamp indicating when 
	 * this comment was generated
	 */
	public static String[] generatedCode(boolean editable, String model, String version) {
		String[] s = new String[2];
//		s[0] = "Model "+model+" - "+version+" - "+new GregorianCalendar().toString();
		s[0] = "Model \""+model+"\" - "+version+" - "+ new Date().toString();
		if (editable) s[1] = editableCode[0];
		else s[1] = generated[0];
		return s;

	}

	/**
	 * Generate a class comment, e.g. "Class myClass"
	 * @param className the class name
	 * @return the cgenerated omment
	 */
	public static String[] classComment(String className) {
		String[] s = new String[1];
		s[0] = "Class "+className;
		return s;
	}

	/**
	 * Generate a header comment, ie enclosed by /* ... &#042;&#047;
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
	 * Generate a header comment, ie enclosed by /* ... &#042;&#047;
	 * @param indent the String used for indentation at the start of comment lines
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
	 * Generate a single line comment, ie starting with //
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
