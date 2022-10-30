package fr.cnrs.iees.omhtk.codeGeneration;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>A simple Java enum generator.</p>
 * Very simple at the moment, no fields after constants and no constructor, just inherited interface
 * methods.
 * 
 * @author Jacques Gignoux - 7 oct. 2022
 *
 */
public class EnumGenerator extends AbstractClassGenerator {

	private List<String> constants = new LinkedList<String>();
	
	/**
	 * @param packageName The package of the enum to create
	 * @param classComment The top-level enum file comment
	 * @param name The class name of the enum to be generated.
	 * @param interfaces Array of interfaces to be used by the generated enum.
	 */
	public EnumGenerator (String packageName,
		String classComment,
		String name,
		String... interfaces) {
		super(packageName, name, classComment, interfaces);
		recordAncestorInterfaceMethods();
	}
	
	/**
	 * @param constant A new enum value to list with this enum.
	 * @return fluid interface.
	 */
	public EnumGenerator setConstant(String constant) {
		constants.add(constant);
		return this;
	}

	@Override
	protected String headerText() {
		StringBuilder result = new StringBuilder();
		result.append("public enum ").append(name());
		if (nInterfaces()>0)
			result.append(" implements");
		return result.toString();
	}

	@Override
	protected String fieldText(String indent) {
		StringBuilder result = new StringBuilder();
		for (String constant:constants)
			result.append(indent).append(constant).append(",\n");
		result.append(indent).append(";\n\n");
		return result.toString();
	}

	@Override
	protected String methodText(String indent) {
		StringBuilder result = new StringBuilder();
		for (MethodGenerator m:getMethods())
			result.append(m.asText(indent));
		return result.toString();
	}

}
