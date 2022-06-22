/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2021: Shayne R. Flint, Jacques Gignoux & Ian D. Davies      *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent class hierarchies, plus some other utilities. The kind of  *
 *  things you need in all software projects and keep rebuilding all the  * 
 *  time.                                                                 *
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
package fr.ens.biologie.codeGeneration;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import fr.ens.biologie.generic.JavaCode;

/**
 * Utility methods for code generation.
 *
 * @author Jacques Gignoux - 4 juil. 2019
 *
 */
public class CodeGenerationUtils {

	/**
	 * Write a java code file from a {@link JavaCode} instance. 
	 * @param jc the {@code JavaCode} instance to use
	 * @param file the file to save to
	 */
	public static void writeFile(JavaCode jc, File file) {
		file.getParentFile().mkdirs();
		PrintStream classFile;
		try {
			classFile = new PrintStream(file,StandardCharsets.UTF_8);
			classFile.println(jc.asText("\t"));
			classFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks a class name. For any class name except primitive wrappers,
	 * it does nothing. For primitive wrapper classes (e.g. Double, Integer, etc.) it returns 
	 * the matching primitive type.
	 * @param type any class name
	 * @return the class name or the matching primitive type in case of a primitive wrapper
	 */
	public static String checkType(String type) {
		if (type.equals("Double")) return "double";
		else if (type.equals("Integer")) return "int";
		else if (type.equals("Float")) return "float";
		else if (type.equals("Boolean")) return "boolean";
		else if (type.equals("Long")) return "long";
		else if (type.equals("Char")) return "char";
		else if (type.equals("Short")) return "short";
		else if (type.equals("Byte")) return "byte";
		return type;
	}

	/**
	 * Get the "zero" value compatible with a class. For primitive types, it will return the
	 * appropriate zero value. For Strings, it returns an empty String. For all other classes,
	 * it returns {@code null}.
	 * @param type the class simple name (e.g. "String")
	 * @return the proper zero value
	 */
	public static String zero(String type) {
		if (type.equals("int")) return "0";
		else if (type.equals("long")) return "0L";
		else if (type.equals("float")) return "0.0f";
		else if (type.equals("double")) return "0.0d";
		else if (type.equals("boolean")) return "false";
		else if (type.equals("char")) return "\'\\0\'";
		else if (type.equals("short")) return "0";
		else if (type.equals("byte")) return "0";
		else if (type.equals("String")) return "\"\"";
		else return "null";
	}


}
