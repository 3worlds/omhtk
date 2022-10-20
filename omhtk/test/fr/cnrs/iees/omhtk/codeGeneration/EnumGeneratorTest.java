package fr.cnrs.iees.omhtk.codeGeneration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.cnrs.iees.omhtk.Factory;
import fr.cnrs.iees.omhtk.Sizeable;

class EnumGeneratorTest {

	@Test // plain enum
	void testAsText1() {
		EnumGenerator eg = new EnumGenerator(this.getClass().getPackageName(),
			"/** some javadoc comment */\n",
			"EnumTest");
		eg.setConstant("Un");
		eg.setConstant("Deux");
		eg.setConstant("Trois");
		assertEquals(eg.asText("\t"),"package fr.cnrs.iees.omhtk.codeGeneration;\n"
			+ "\n"
			+ "/** some javadoc comment */\n"
			+ "\n"
			+ "public enum EnumTest {\n"
			+ "\n"
			+ "	Un,\n"
			+ "	Deux,\n"
			+ "	Trois,\n"
			+ "	;\n"
			+ "\n"
			+ "}\n"
			+ "");
	}

	@Test // enum implementing interface
	void testAsText2() {
		EnumGenerator eg = new EnumGenerator(this.getClass().getPackageName(),
			"/** some javadoc comment */\n",
			"EnumTest",
			Sizeable.class.getName());
		eg.setConstant("Un");
		eg.setConstant("Deux");
		eg.setConstant("Trois");
		eg.getMethod("size").setReturnStatement("return 3");
//		System.out.print(eg.asText("\t"));
		assertEquals(eg.asText("\t"),"package fr.cnrs.iees.omhtk.codeGeneration;\n"
			+ "\n"
			+ "import fr.cnrs.iees.omhtk.Sizeable;\n"
			+ "\n"
			+ "/** some javadoc comment */\n"
			+ "\n"
			+ "public enum EnumTest implements Sizeable {\n"
			+ "\n"
			+ "	Un,\n"
			+ "	Deux,\n"
			+ "	Trois,\n"
			+ "	;\n"
			+ "\n"
			+ "	@Override\n"
			+ "	public int size() {\n"
			+ "		return 3;\n"
			+ "	}\n"
			+ "\n"
			+ "}\n"
			+ "");
	}

	@Test // interface with generic type
	void testAsText3() {
		EnumGenerator eg = new EnumGenerator(this.getClass().getPackageName(),
			"/** some javadoc comment */",
			"EnumTest",
			Factory.class.getCanonicalName());
		eg.addInterfaceGenericParameter("Factory", ClassGenerator.class.getName());
		eg.setConstant("Un");
		eg.setConstant("Deux");
		eg.setConstant("Trois");
		eg.getMethod("newInstance").setReturnType(ClassGenerator.class.getSimpleName());
		System.out.print(eg.asText("\t"));
	}

	@Test // multiple interfaces
	void testAsText4() {
		EnumGenerator eg = new EnumGenerator(this.getClass().getPackageName(),
			"/** some javadoc comment */",
			"EnumTest",
			Factory.class.getName(),Sizeable.class.getName());
		eg.addInterfaceGenericParameter("Factory", ClassGenerator.class.getName());
		eg.setConstant("Un");
		eg.setConstant("Deux");
		eg.setConstant("Trois");
		eg.getMethod("newInstance").setReturnType(ClassGenerator.class.getSimpleName());
		eg.getMethod("size").setReturnStatement("return 3;");
		System.out.print(eg.asText("\t"));
	}
	
}
