package fr.cnrs.iees.omhtk.codeGeneration;

import java.util.Set;

/**
 * <p>A simple Java interface generator.</p>
 * It can generate almost any standard interface code, as long as there are no inner classes.</p>
 *  <p>Usage:</p>
 *  <ol>
 *  <li>Get an instance of this class. This will require some basic information about the interface
 *  to generate (e.g. superinterfaces, package, methods to override...).</li>
 *  <li>Adapt the generator to your needs by adding methods, using
 *  methods defined here.</li>
 *  <li>When all this is finished, call {@link InterfaceGenerator#asText(String) asText()} to generate a String that can be 
 *  saved as a java source file.</li>
 *  </ol>
 *  <p>You should then check that the generated code, saved as a source file, properly compiles
 *  using a {@link JavaCompiler}</p>
 *  
 * @author Jacques Gignoux - 11 oct. 2022
 *
 */
public class InterfaceGenerator extends AbstractClassGenerator {

	public InterfaceGenerator(String packageName, 
			String classComment, 
			String name, 
			Set<String> methodsToOverride, 
			String... interfaces) {
		super(packageName, name, classComment, interfaces);
		if (methodsToOverride!=null)
			this.methodsToOverride.addAll(methodsToOverride);
		recordAncestorInterfaceMethods();
	}
	
	public InterfaceGenerator(String packageName, 
			String classComment, 
			String name) {
		super(packageName, name, classComment);
	}
	
	@Override
	protected String headerText() {
		StringBuilder result = new StringBuilder();
		result.append("public interface ").append(name());
		if (nInterfaces()>0)
			result.append(" extends");
		return result.toString();
	}

	@Override
	protected String fieldText(String indent) {
		// Default: no fields 
		// maybe implement static fields later...
		return "";
	}

	// no check that methods are abstract or default or public.... later maybe
	@Override
	protected String methodText(String indent) {
		StringBuilder result = new StringBuilder();
		for (MethodGenerator m:getMethods())
			result.append(m.asText(indent));
		return result.toString();
	}

	

}
