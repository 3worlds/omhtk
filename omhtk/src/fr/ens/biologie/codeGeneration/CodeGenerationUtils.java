package fr.ens.biologie.codeGeneration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import fr.ens.biologie.generic.JavaCode;

/**
 *
 * @author Jacques Gignoux - 4 juil. 2019
 *
 */
public class CodeGenerationUtils {

	public static void writeFile(JavaCode jc, File file, String className) {
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

	public static String checkType(String type) {
		if (type.equals("Double")) return "double";
		else if (type.equals("Integer")) return "int";
		else if (type.equals("Float")) return "float";
		else if (type.equals("Boolean")) return "boolean";
		else if (type.equals("Long")) return "long";
		else if (type.equals("Char")) return "char";
		return type;
	}

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
