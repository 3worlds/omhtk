package fr.ens.biologie.codeGeneration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

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
			classFile = new PrintStream(file);
			classFile.println(jc.asText("\t"));
			classFile.close();
		} catch (FileNotFoundException e) {
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
		else if (type.equals("String")) return "\"\"";
		else return "null";
	}


}
