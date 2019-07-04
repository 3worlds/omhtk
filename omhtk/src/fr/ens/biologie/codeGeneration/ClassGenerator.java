package fr.ens.biologie.codeGeneration;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.ens.biologie.generic.JavaCode;

/**
 *  a simple class generator, i.e. with no inner classes. 
 *  This is generic, can generate (almost) any code.
 *  
 * @author Jacques Gignoux - 19 déc. 2014
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
	private String scope = "public";
	private String name;
	private String superclass = null;
	private Set<String> interfaces = new HashSet<String>();
	private Map<String,field> fields = new HashMap<String,field>();
	private Map<String,MethodGenerator> methods = new HashMap<String,MethodGenerator>();
	private Map<String,MethodGenerator> constructors = new HashMap<String,MethodGenerator>();
	private List<String> rawMethods = null;
	
	private void recordAncestorMethods(Class<?> c) {
		Method[] lm = c.getDeclaredMethods();
		for (Method m:lm) {
			if (!methods.containsKey(m.getName()))
			if (Modifier.isAbstract(m.getModifiers())) {
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
	
	public ClassGenerator(String packageName, 
		String classComment,
		String name, 
		String superclass, 
		String... interfaces) {
		super();
		scope = "public";
		this.packageName = packageName;
		this.classComment = classComment;
		this.name = name;
		this.superclass = superclass;
		for (String s:interfaces)
			this.interfaces.add(s);
		if (superclass!=null) {
			try { recordAncestorMethods(Class.forName(superclass));	} 
			catch (ClassNotFoundException e) {}
			imports.add(stripTemplate(superclass));
			this.superclass = stripPackageFromClassName(superclass);
		}
		for (String s:interfaces) {
			try { recordAncestorMethods(Class.forName(s)); }
			catch (ClassNotFoundException e) {}
			imports.add(stripTemplate(s));
			this.interfaces.remove(s);
			this.interfaces.add(stripPackageFromClassName(s));
		}
	}
	
	public ClassGenerator setConstructor(String... argTypes) {
		MethodGenerator c = new MethodGenerator("public",null,name,argTypes);
		constructors.put("constructor"+String.valueOf(constructors.size()+1),c);
		return this;
	}
	
	public MethodGenerator getConstructor(String key) {
		return constructors.get(key);
	}
	
	private String stripPackageFromClassName(String fullClassName) {
		String[] sc = fullClassName.split("\\.");
		return sc[sc.length-1];		
	}
	
	public ClassGenerator setImport(String imp) {
		imports.add(imp);
		return this;
	}
	
	/** this method to add 'flat' method code, ie code snippets for private methods, typically */
	public ClassGenerator setRawMethodCode(List<String> snippet) {
		rawMethods = snippet;
		return this;
	}
	
	public int nfields() {
		return fields.size();
	}
	
	public Set<String> fields() {		
		return fields.keySet();
	}
	
	public ClassGenerator setField(String fname, String ftype, String fvalue) {
		field f = new field();
		f.scope = "private";
		f.name = fname;
		f.type = ftype;
		f.defaultValue = fvalue;
		fields.put(fname, f);
		return (this);
	}	
	
	// is that one really needed ?
	public field getField(String fname) {
		return fields.get(fname);
	}
	
	public ClassGenerator setMethod(String mname, MethodGenerator method) {
		methods.put(mname,method);
		return this;
	}
	
	/**
	 * This method to enable one to add statements into automatically generated MethodGenerators
	 * (eg when building the class from a superclass).
	 * @param mname
	 * @return
	 */
	public MethodGenerator getMethod(String mname) {
		return methods.get(mname);
	}

	public Collection<MethodGenerator> getMethods() {
		return methods.values();
	}
	
	public String asText(String indent) {
		String result = "";
		result += "package "+packageName+";\n\n";
		for (String imp:imports) 
			result += "import "+imp+";\n";
		if (imports.size()>0)
			result += "\n";
		if (classComment!=null)
			result += classComment+"\n";
		result += scope+" class "+name;
		if (superclass!=null) result += " extends "+superclass;
		if (interfaces.size()>0) {
			result += " implements";
			String[] ifs = interfaces.toArray(new String[interfaces.size()]);
			for (int i=0; i<ifs.length; i++) {
				if (i<ifs.length-1) result += " "+ifs[i]+", ";
				else result += " "+ifs[i];
			}
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
	
	public String getClassName() {
		return name;		
	}
	
	public String packageName() {
		return packageName;
	}
	
}
