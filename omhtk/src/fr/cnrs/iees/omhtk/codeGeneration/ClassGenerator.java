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
package fr.cnrs.iees.omhtk.codeGeneration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  <p>A simple java class generator.
 *  It can generate almost any standard class code, as long as there are no inner classes.</p>
 *  <p>Usage:</p>
 *  <ol>
 *  <li>Get an instance of this class. This will require some basic information about the class
 *  to generate (e.g. superclass, package, methods to override...).</li>
 *  <li>Adapt the generator to your needs by adding methods, constructors, fields, etc., using
 *  methods defined here.</li>
 *  <li>When all this is finished, call {@link ClassGenerator#asText(String) asText()} to generate a String that can be 
 *  saved as a java source file.</li>
 *  </ol>
 *  <p>You should then check that the generated code, saved as a source file, properly compiles
 *  using a {@link JavaCompiler}</p>
 *
 * @author Jacques Gignoux - 19 déc. 2014
 * @see MethodGenerator
 *
 */
public class ClassGenerator extends AbstractClassGenerator {

	private class field {
		public String scope = null;
		public String type = null;
		public String name = null;
		public String defaultValue = null;
	}

	private String scope = "public";
	private String superclass = null;
	private Map<String,field> fields = new HashMap<String,field>();
	private Map<String,MethodGenerator> constructors = new HashMap<String,MethodGenerator>();
	private List<String> rawMethods = null;
	private List<String> superClassParameters = new ArrayList<>();

	/**
	 *Constructor. The default behaviour is to generate method bodies only for methods declared
	 * {@code abstract} in the superclass or interfaces. To generate a body for a concrete or default method,
	 * pass its name in arg methodsToOverride (otherwise methodsToOverride can safely be set to null).
	 *
	 * @param packageName the package of the class to create
	 * @param classComment the class file comment
	 * @param name the class name
	 * @param scope The declared scope of the class constructor.
	 * @param methodsToOverride the list of methods to override if different from abstract methods
	 * @param superclass the superclass
	 * @param interfaces the interfaces
	 */
	public ClassGenerator(String packageName,
			String classComment,
			String name,
			String scope, 
			Set<String> methodsToOverride,
			String superclass,
			List<String> genericTypes,
			String... interfaces) {
		super(packageName, name, classComment, interfaces);
		try {
			Class<?> c = Class.forName(superclass);
			// get generic parameters of super class, if any
			Type t = c.getGenericSuperclass();
			if (t instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) t;
				Type[] types = pt.getActualTypeArguments();
				for (Type tt:types)
					superClassParameters.add(tt.getTypeName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// replace generic types (eg 'E','T') with real classes (eg 'String', 'EcosystemComponent')
		if (genericTypes!=null) {
			if (genericTypes.size()!=superClassParameters.size())
				throw new IllegalArgumentException("Expecting "+superClassParameters.size()+
					" generic parameters - "+ genericTypes.size()+ " provided");
			for (int i=0; i<genericTypes.size(); i++) {
				superClassParameters.remove(i);
				superClassParameters.add(i,genericTypes.get(i));
			}
		}
		// class scope
		if (scope.equals("public") || scope.equals("") || scope.equals("protected"))
			this.scope = scope;
		else
			throw new IllegalArgumentException("The scope argument must be equal to \"public\",  \"protected\", or\"\".");
		// methods from ancestors (interfaces and superclasses
		if (methodsToOverride!=null)
			this.methodsToOverride.addAll(methodsToOverride);
		recordAncestorInterfaceMethods();
		this.superclass = recordSuperClassMethods(superclass);
	}

	/**
	 * Constructor for a public class with no ancestry
	 * 
	 * @param packageName The package name for the class.
	 * @param classComment Top-level comments for the generated class file.
	 * @param name The name of the class represented by this generator.
	 */
	public ClassGenerator(String packageName,
			String classComment,
			String name) {
			super(packageName, name, classComment);			
	}

	/**
	 * Constructor for a public class with a superclass but no interfaces, and possibly generic parameters
	 * 
	 * @param packageName The package name for the class.
	 * @param classComment Top-level comments for the generated class file.
	 * @param name The name of the class represented by this generator.
	 * @param methodsToOverride Set of method names that are inherited by this class
	 * @param superclass The name of the super class of this generated class.
	 */
	public ClassGenerator(String packageName,
			String classComment,
			String name,
			Set<String> methodsToOverride,
			String superclass,
			List<String> genericTypes) {
		this(packageName, classComment, name, "public", methodsToOverride, superclass, genericTypes);
	}	
	
//	/**
//	 * replace a generic parameter (eg 'T' or 'E') with a class name (eg 'String')
//	 * 
//	 * @param ancestorPar
//	 * @param par
//	 */
//	public final void addSuperClassGenericParameter(String ancestorPar, String par) {
//		superClassParameters.add(superClassParameters.indexOf(ancestorPar),par);
//	}
//
	/**
	 * Declares a new constructor with its argument types.
	 * @param argTypes argument types. Must be valid type names (i.e. java types or your own classes)
	 * @return this class for agile programming
	 */
	public ClassGenerator setConstructor(String... argTypes) {
		MethodGenerator c = new MethodGenerator("public",false,null,name(),argTypes);
		constructors.put("constructor"+String.valueOf(constructors.size()+1),c);
		return this;
	}

	/**
	 * Retrieves a declared constructor by its key. NB constructor keys are Strings of the form
	 * "constructor1", "constructor2", etc. where the number is the order in which they were declared
	 * (i.e. 1 is the first constructor you added).
	 * @param key the constructor key
	 * @return the constructor generator
	 */
	public MethodGenerator getConstructor(String key) {
		return constructors.get(key);
	}

	/**
	 * Inserts raw code lines. These lines will be inserted verbatim in the class, with no indentation.
	 * You can use this to implement private methods, typically, or code copied from external sources.
	 * 
	 * @param snippet the lines to insert
	 * @return this class for agile programming
	 */
	public ClassGenerator setRawMethodCode(List<String> snippet) {
		rawMethods = snippet;
		return this;
	}

	/**
	 * The number of fields declared in this class
	 * @return the number of fields
	 */
	public int nfields() {
		return fields.size();
	}

	/**
	 * The names of the fields declared in this class.
	 * @return the fields declared in this class
	 */
	public Set<String> fields() {
		return fields.keySet();
	}

	/**
	 * Declare a new field. By default, all fields are private and not static.
	 * @param fname the field name
	 * @param ftype the field type (a valid java class - don't forget the import if needed)
	 * @param fvalue the default value of the field, if any
	 * @return this class for agile programming
	 */
	public ClassGenerator setField(String fname, String ftype, String fvalue) {
		field f = new field();
		f.scope = "private";
		f.name = fname;
		f.type = ftype;
		f.defaultValue = fvalue;
		fields.put(fname, f);
		return (this);
	}

	/**
	 * Get a declared field by its name. Isn't that a stupid method? Well, you can use it to check
	 * the spelling of your field name...
	 * @param fname the name of the field
	 * @return the name of the field
	 */
	// is that one really needed ?
	public field getField(String fname) {
		return fields.get(fname);
	}
	
	@Override
	protected String headerText() {
		StringBuilder result = new StringBuilder();
		result.append(scope).append(" class ").append(name());
		if (superclass!=null) result.append(" extends ").append(superclass);
		if (!superClassParameters.isEmpty()) {
			result.append('<');
			for (String par:superClassParameters)
				result.append(par).append(',');
			result.deleteCharAt(result.length()-1);
			result.append('>');
		}
		if (nInterfaces()>0)
			result.append(" implements");
		return result.toString();
	}

	@Override
	protected String fieldText(String indent) {
		StringBuilder result = new StringBuilder();
		for (field f:fields.values()) {
			result.append(indent)
				.append(f.scope).append(' ')
				.append(f.type).append(' ')
				.append(f.name);
			if (f.defaultValue!=null)
				result.append(" = ").append(f.defaultValue);
			result.append(";\n");
		}
		if (!fields.isEmpty())
			result.append("\n");
		return result.toString();
	}

	@Override
	protected String methodText(String indent) {
		StringBuilder result = new StringBuilder();
		for (MethodGenerator c:constructors.values())
			result.append(c.asText(indent));
		for (MethodGenerator m:getMethods())
			result.append(m.asText(indent));
		if (rawMethods!=null)
			for (String s:rawMethods)
				result.append(s).append("\n");
		return result.toString();
	}

	
//	@Override
//	public String asText(String indent) {
//		String result = "";
//		result += "package "+packageName+";\n\n";
//		for (String imp:imports)
//			result += "import "+imp+";\n";
//		if (imports.size()>0)
//			result += "\n";
//		if (classComment!=null)
//			result += classComment+"\n";
//		// start header
//		result += headerText(name);
////		if (isInterface) {
////			result += "interface "+name;
////			if (interfaces.size()>0)
////				result += " extends";
////		}
////		else {
////			result += scope+" class "+name;
////			if (superclass!=null) result += " extends "+superclass;
////			if (interfaces.size()>0)
////				result += " implements";
////		}
//		// end specific part of header
//		String[] ifs = interfaces.toArray(new String[interfaces.size()]);
//		for (int i=0; i<ifs.length; i++) {
//			if (i<ifs.length-1) result += " "+ifs[i]+", ";
//			else result += " "+ifs[i];
//		}
//		result += " {\n\n"; // 1
//		for (field f:fields.values()) {
//			result += indent+f.scope+" "+f.type+" "+f.name;
//			if (f.defaultValue!=null)
//				result += " = "+f.defaultValue;
//			result += ";\n";
//		}
//		if (!fields.isEmpty())
//			result += "\n";
//		for (MethodGenerator c:constructors.values())
//			result += c.asText(indent);
//		for (MethodGenerator m:methods.values())
//			result += m.asText(indent);
//		if (rawMethods!=null)
//			for (String s:rawMethods)
//				result += s+"\n";
//		result += "}\n"; // 1
//		return result;
//	}


}
