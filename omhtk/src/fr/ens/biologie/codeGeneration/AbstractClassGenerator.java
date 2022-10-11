package fr.ens.biologie.codeGeneration;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.ens.biologie.generic.JavaCode;

/**
 * NO factory (subclasses are different)
 * TODO: handle typed classes & interfaces in header...
 * 
 * @author Jacques Gignoux - 11 oct. 2022
 *
 */
public abstract class AbstractClassGenerator implements JavaCode {
	
	private String packageName;
	private String name;
	private Set<String> interfaces = new HashSet<String>();
	private Map<String,MethodGenerator> methods = new HashMap<String,MethodGenerator>();
	List<String> methodsToOverride = new ArrayList<>();
	
	private Set<String> imports = new HashSet<String>();
	private String classComment = null;

	// constructor
	AbstractClassGenerator(String packageName,
			String name,
			String classComment,
			String... interfaces) {
		this.packageName = packageName;
		this.classComment = classComment;
		this.name = name;	
		for (String s:interfaces)
			this.interfaces.add(s);
	}
	
	// helper method for constructor
	// MUST be called at the end of the constructor
	void recordAncestorInterfaceMethods() {
		for (String s:interfaces) {
			try { recordAncestorMethods(Class.forName(s)); }
			catch (ClassNotFoundException e) {}
			// an interface name with no package is assumed to be in the same package, hence no import
			if (s.contains(".")) 
				imports.add(stripTemplate(s));
			this.interfaces.remove(s);
			this.interfaces.add(stripPackageFromClassName(s));
		}
	}
	
	String recordSuperClassMethods(String superclass) {
		if (superclass!=null) {
			try { recordAncestorMethods(Class.forName(superclass));	}
			catch (ClassNotFoundException e) {}
			// a class name with no package is assumed to be in the same package, hence no import
			if (superclass.contains(".")) 
				setImport(stripTemplate(superclass));
			return stripPackageFromClassName(superclass);
		}
		return null;
	}

	// helper method for constructor
	private void recordAncestorMethods(Class<?> c) {
		Method[] lm = c.getDeclaredMethods();
		for (Method m:lm) {
			if (!methods.containsKey(m.getName()))
			if (Modifier.isAbstract(m.getModifiers()) ||
				(methodsToOverride.contains(m.getName()))) {
				methods.put(m.getName(), new MethodGenerator(m));
			}
		}
	}

	// helper method for constructor
	private String stripTemplate(String className) {
		String result = className;
		if (className.indexOf('<')>-1)
			result = className.substring(0, className.indexOf('<'));
		return result;
	}
	
	// helper method for constructor
	/**
	 * Remove the package from a class name.
	 * @param fullClassName the fully qualified class name
	 * @return just the class name (last word of fullClassName)
	 */
	private String stripPackageFromClassName(String fullClassName) {
		String[] sc = fullClassName.split("\\.");
		return sc[sc.length-1];
	}
	
	// getters and setters
	
	public final String name() {
		return name;
	}
	
	/**
	 * The package of the class represented by this generator.
	 * @return the generated class package name
	 */
	public final String packageName() {
		return packageName;
	}
	
	public final int nInterfaces() {
		return interfaces.size();
	}
	
	/**
	 * Declares a new import.
	 * @param imp a fully qualified class name to import or more generally any valid java text 
	 * that can fit between "import " and ";" in a java import statement.
	 * @return this class for agile programming
	 */
	public final AbstractClassGenerator setImport(String imp) {
		imports.add(imp);
		return this;
	}
	
	/**
	 * Declare a new method by adding a new {@code MethodGenerator} instance to this class.
	 * @param mname the method key (usually, its name)
	 * @param method the {@code MethodGenerator} instance
	 * @return this class for agile programming
	 */
	public final AbstractClassGenerator setMethod(String mname, MethodGenerator method) {
		methods.put(mname,method);
		return this;
	}

	/**
	 * Get a declared method. This enables one to add statements into automatically generated 
	 * methods (e.g. when building the class from a superclass).
	 * @param mname the method key
	 * @return the method generator
	 */
	public final MethodGenerator getMethod(String mname) {
		return methods.get(mname);
	}

	/**
	 * Get all the methods declared in this class.
	 * @return a collection of method generators
	 */
	public final Collection<MethodGenerator> getMethods() {
		return methods.values();
	}

	
	// code text generation
	
	/**
	 * header text for class/interface/enum declaration. Stops just before the list of interfaces,
	 *  eg:<br/>
	 * 
	 * <em>{@code public class MyClass extends YourClass [implements] }</em> <br/>
	 * or <em>{@code public interface MyInterface [extends] }</em> <br/>
	 * or <em>{@code public enum MyEnum [implements] } </em><br/>
	 * 
	 * 
	 * @return the generated code line
	 */
	protected abstract String headerText();
	
	/**
	 * field text for class, enum and possibly interface declaration. For enum it also comprises
	 * enum constants.
	 * 
	 * @return
	 */
	protected abstract String fieldText(String indent);
	
	/**
	 * method text - different for class, enum and interface.
	 * 
	 * @param indent
	 * @return
	 */
	protected abstract String methodText(String indent);
	
	@Override
	public final String asText(String indent) {
		StringBuilder result = new StringBuilder();
		// package declaration
		result.append("package ").append(packageName).append(";\n\n");
		// imports
		for (String imp:imports)
			result.append("import ").append(imp).append(";\n");
		if (imports.size()>0)
			result.append("\n");
		// class javadoc comment
		if (classComment!=null)
			result.append(classComment).append('\n');
		// header (different for class, interface and enum)
		result.append(headerText());
		String[] ifs = interfaces.toArray(new String[interfaces.size()]);
		for (int i=0; i<ifs.length; i++) {
			if (i<ifs.length-1) result.append(" ").append(ifs[i]).append(",");
			else result.append(" ").append(ifs[i]);
		}
		// opening class body
		result.append(" {\n\n");
		// declaring fields
		result.append(fieldText(indent));
		// declaring methods
		result.append(methodText(indent));		
		// closing class body
		result.append("}\n");
		return result.toString();
	}

	

}
