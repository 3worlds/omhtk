package fr.ens.biologie.codeGeneration;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import fr.ens.biologie.generic.JavaCode;

import static fr.ens.biologie.codeGeneration.Comments.*;

/**
 * A simple method code generator - does not handle exceptions, generic types nor annotations.
 * <p>3Worlds: component threeWorlds</p>
 * @author Jacques Gignoux - 19 déc. 2014
 *
 * TODO: allow to rename the arguments to more handy user friendly ones (for methods
 * generated from ancestor classes)
 *
 */
public class MethodGenerator implements JavaCode {

	protected String override;
	protected String scope;
	protected boolean isAbstract = false;
	protected String returnType;
	protected String name;
	protected String[] argTypes;
	protected String[] argNames;
	protected List<String> statements = new LinkedList<String>();
	protected String returnStatement = "return null";
	protected Set<String> dependencies = new HashSet<String>();
	protected boolean insertCodeInsertionComment = false;

	private String getLastBit(String s, String regexp) {
		String[] ss = s.split(regexp);
		return ss[ss.length-1];
	}

	public String[] dependencies() {
		String[] a = new String[dependencies.size()];
		return dependencies.toArray(a);
	}

	public void insertCodeInsertionComment() {
		insertCodeInsertionComment = true;
	}

	/**
	 * Constructor from a superclass method - copies all method information from ancestor
	 * @param method - the ancestor method to generate this one from
	 */
	public MethodGenerator(Method method) {
		super();
		name = method.getName();
		returnType = method.getReturnType().getSimpleName();
		if (returnType.equals("Class")) returnType+="<?>";
		override = "@Override";
		int m = method.getModifiers();
		if (Modifier.isPublic(m)) scope="public";
		else if (Modifier.isProtected(m)) scope="protected";
		else scope="private";
		Class<?>[] ct = method.getParameterTypes();

		Type[] pt = method.getGenericParameterTypes();
		argTypes = new String[pt.length];
		argNames = new String[pt.length];

		for (int i=0; i<pt.length; i++) {
			String s = pt[i].toString();
			if (s.contains("class "))
				s = getLastBit(s,"class ");
			if (s.contains("interface "))
				s = getLastBit(s,"interface ");
			dependencies.add(s);
			argTypes[i] = ct[i].getSimpleName();
			// TODO: this doesnt work with templates, i.e. Map<ComplexSystem,?>
			if (argTypes[i].equals("Class")) argTypes[i]+="<?>";
			argNames[i] = "v"+i;
//			not needed:
//			if (t instanceof ParameterizedType) {
//				ParameterizedType aType = (ParameterizedType) t;
//				Type[] parameterArgTypes = aType.getActualTypeArguments();
//				for(Type parameterArgType : parameterArgTypes){
//		            System.out.println("parameterArgClass = " + parameterArgType);
//		        }
//			}
		}
//		for (int i=0; i<pt.length; i++) {
//			argTypes[i] = pt[i].getSimpleName();
//			if (argTypes[i].equals("Class")) argTypes[i]+="<?>";
//			argNames[i] = "v"+i;
//		}
	}
	/**
	 * Constructor from scratch - self explained parameters
	 * @param scope
	 * @param returnType
	 * @param name
	 * @param argTypes
	 */
	public MethodGenerator(String scope, boolean isAbstract, String returnType, String name, String... argTypes) {
		super();
		this.scope = scope;
		this.isAbstract = isAbstract;
		this.returnType = returnType;
		this.name = name;
		this.argTypes = argTypes;
		argNames = new String[argTypes.length];
		for (int i=0; i<argNames.length; i++) argNames[i] = "v"+i;
		override = null;
	}

	/**
	 * Constructor from another MethodGenerator
	 * @param mgen
	 */
	public MethodGenerator(MethodGenerator mgen) {
		super();
		scope = mgen.scope;
		returnType = mgen.returnType;
		name = mgen.name;
		argTypes = mgen.argTypes.clone();
		argNames = mgen.argNames.clone();
		override = mgen.override;
		statements.addAll(mgen.statements);
		dependencies.addAll(mgen.dependencies);
		insertCodeInsertionComment = mgen.insertCodeInsertionComment;
	}

	public MethodGenerator setStatement(String statement) {
		statements.add(statement);
		return this;
	}

	public MethodGenerator setReturnStatement(String statement) {
		returnStatement = statement;
		return this;
	}

	public MethodGenerator setArgumentName(int index, String name) {
		argNames[index] = name;
		return this;
	}

	// use with caution - only when inherits template types, eg Map<A,B>
	public MethodGenerator setArgumentType(int index, String type) {
		argTypes[index] = type;
		return this;
	}

	public MethodGenerator setArgumentNames(String... names) {
		for (int i=0; i<names.length; i++)
			argNames[i] = names[i];
		return this;
	}

	public String name() {
		return name;
	}

	// use with caution.
	public MethodGenerator setReturnType(String type) {
		returnType = type;
		return this;
	}

	@Override
	public String asText(String indent) {
		String result = "";
		if (override!=null)	result += indent + override + "\n";
		if (returnType==null) // constructors only (never abstract)
			result += indent + scope + " " + name + "(";
		else { // other methods (may be abstract)
			if (isAbstract)
				result += indent + scope + " abstract " + returnType + " " + name + "(";
			else
				result += indent + scope + " " + returnType + " " + name + "(";
		}
		if (argTypes!=null)
		for (int i=0; i< argTypes.length; i++) {
			result += argTypes[i]+" "+argNames[i];
			if (i==argTypes.length-1);
			else result += ", ";
		}
		if (isAbstract) // no body
			result += ");\n\n";
		else { // print body
			result += ") {\n";
			if (insertCodeInsertionComment)
				result += indent+singleLineComment(startCodeInsertion);
			for (String s:statements) {
				result += indent+indent+s+";\n";
			}
			if (returnType==null) ;
			else if (returnType.equals("void")) ;
			else result += indent+indent+returnStatement+";\n";
			if (insertCodeInsertionComment)
				result += indent+singleLineComment(endCodeInsertion);
			result += indent+"}\n\n";
		}
		return result;
	}

}
