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
public class ClassGenerator implements JavaCode {

	private class field {
		public String scope = null;
		public String type = null;
		public String name = null;
		public String defaultValue = null;
	}

	private String packageName;
	private Set<String> imports = new HashSet<String>();
	private String classComment = null;
	private boolean isInterface = false;
	private String scope = "public";
	private String name;
	private String superclass = null;
	private Set<String> interfaces = new HashSet<String>();
	private Map<String,field> fields = new HashMap<String,field>();
	private Map<String,MethodGenerator> methods = new HashMap<String,MethodGenerator>();
	private Map<String,MethodGenerator> constructors = new HashMap<String,MethodGenerator>();
	private List<String> rawMethods = null;
	private List<String> methodsToOverride = new ArrayList<>();

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

	private String stripTemplate(String className) {
		String result = className;
		if (className.indexOf('<')>-1)
			result = className.substring(0, className.indexOf('<'));
		return result;
	}

	/**
	 *Constructor. The default behaviour is to generate method bodies only for methods declared
	 * {@code abstract} in the superclass or interfaces. To generate a body for a concrete or default method,
	 * pass its name in arg methodsToOverride (otherwise methodsToOverride can safely be set to null).
	 *
	 * @param packageName the package of the class to create
	 * @param classComment the class file comment
	 * @param name the class name
	 * @param isInterface {@code true} if the class to generate is an interface
	 * @param methodsToOverride the list of methods to override if different from abstract methods
	 * @param superclass the superclass
	 * @param interfaces the interfaces
	 */
	public ClassGenerator(String packageName,
		String classComment,
		String name,
		boolean isInterface, 
		Set<String> methodsToOverride,
		String superclass,
		String... interfaces) {
		super();
		scope = "public";
		this.packageName = packageName;
		this.classComment = classComment;
		this.name = name;
		this.isInterface = isInterface;
		if (methodsToOverride!=null)
			this.methodsToOverride.addAll(methodsToOverride);
		this.superclass = superclass;
		for (String s:interfaces)
			this.interfaces.add(s);
		if (superclass!=null) {
			try { recordAncestorMethods(Class.forName(superclass));	}
			catch (ClassNotFoundException e) {}
			// a class name with no package is assumed to be in the same package, hence no import
			if (superclass.contains(".")) 
				imports.add(stripTemplate(superclass));
			this.superclass = stripPackageFromClassName(superclass);
		}
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

	/**
	 * Declares a new constructor with its argument types.
	 * @param argTypes argument types. Must be valid type names (i.e. java types or your own classes)
	 * @return this class for agile programming
	 */
	public ClassGenerator setConstructor(String... argTypes) {
		MethodGenerator c = new MethodGenerator("public",false,null,name,argTypes);
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
	 * Remove the package from a class name.
	 * @param fullClassName the fully qualified class name
	 * @return just the class name (last word of fullClassName)
	 */
	private String stripPackageFromClassName(String fullClassName) {
		String[] sc = fullClassName.split("\\.");
		return sc[sc.length-1];
	}

	/**
	 * Declares a new import.
	 * @param imp a fully qualified class name to import or more generally any valid java text 
	 * that can fit between "import " and ";" in a java import statement.
	 * @return this class for agile programming
	 */
	public ClassGenerator setImport(String imp) {
		imports.add(imp);
		return this;
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

	/**
	 * Declare a new method by adding a new {@code MethodGenerator} instance to this class.
	 * @param mname the method key (usually, its name)
	 * @param method the {@code MethodGenerator} instance
	 * @return this class for agile programming
	 */
	public ClassGenerator setMethod(String mname, MethodGenerator method) {
		methods.put(mname,method);
		return this;
	}

	/**
	 * Get a declared method. This enables one to add statements into automatically generated 
	 * methods (e.g. when building the class from a superclass).
	 * @param mname the method key
	 * @return the method generator
	 */
	public MethodGenerator getMethod(String mname) {
		return methods.get(mname);
	}

	/**
	 * Get all the methods declared in this class.
	 * @return a collection of method generators
	 */
	public Collection<MethodGenerator> getMethods() {
		return methods.values();
	}

	@Override
	public String asText(String indent) {
		String result = "";
		result += "package "+packageName+";\n\n";
		for (String imp:imports)
			result += "import "+imp+";\n";
		if (imports.size()>0)
			result += "\n";
		if (classComment!=null)
			result += classComment+"\n";
		if (isInterface) {
			result += "interface "+name;
			if (interfaces.size()>0)
				result += " extends";
		}
		else {
			result += scope+" class "+name;
			if (superclass!=null) result += " extends "+superclass;
			if (interfaces.size()>0)
				result += " implements";
		}
		String[] ifs = interfaces.toArray(new String[interfaces.size()]);
		for (int i=0; i<ifs.length; i++) {
			if (i<ifs.length-1) result += " "+ifs[i]+", ";
			else result += " "+ifs[i];
		}
		result += " {\n\n"; // 1
		for (field f:fields.values()) {
			result += indent+f.scope+" "+f.type+" "+f.name;
			if (f.defaultValue!=null)
				result += " = "+f.defaultValue;
			result += ";\n";
		}
		if (!fields.isEmpty())
			result += "\n";
		for (MethodGenerator c:constructors.values())
			result += c.asText(indent);
		for (MethodGenerator m:methods.values())
			result += m.asText(indent);
		if (rawMethods!=null)
			for (String s:rawMethods)
				result += s+"\n";
		result += "}\n"; // 1
		return result;
	}

	/**
	 * The name of the class represented by this generator.
	 * @return the generated class simple name
	 */
	public String getClassName() {
		return name;
	}

	/**
	 * The package of the class represented by this generator.
	 * @return the generated class package name
	 */
	public String packageName() {
		return packageName;
	}

}
