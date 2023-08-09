package fr.cnrs.iees.omhtk.codeGeneration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ClassGeneratorTest {
	
//	private class Bidon {
//		int n;
//		String s;
//		@Override
//		public String toString() {
//			return n+s;
//		}
//		public Bidon(int n, String s) {
//			super();
//			this.n = n;
//			this.s = s;
//		}
//		public int getN() {
//			return n;
//		}
//		public void setN(int n) {
//			this.n = n;
//		}
//		public String getS() {
//			return s;
//		}
//		public void setS(String s) {
//			this.s = s;
//		}		
//	}

	@Test
	void testClassGeneratorStringStringStringStringSetOfStringStringStringArray() {		
		fail("Not yet implemented");
	}

	@Test
	void testClassGeneratorStringStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test generating a class with just a superClass
	 */
	@Test
	void testClassGeneratorWithSuperClass() {
		// constructor from superclass
		Set<String> meths = new HashSet<>();
		meths.add("setN");
		meths.add("setS");
		ClassGenerator cg = new ClassGenerator(Bidon.class.getPackageName(),
			"// this is a supid comment",
			"Amphore",
			meths,
			Bidon.class.getName(),null);
		assertNotNull(cg);
		// set field
		cg.setField("p", "int", "2");
		assertEquals(cg.nfields(),1);
		// override methods
		MethodGenerator mg = cg.getMethod("setN");
		assertNotNull(mg);
		mg.setStatement("this.n = n+p");
		mg = cg.getMethod("setS");
		assertNotNull(mg);
		mg.setStatement("this.s = s+\"_\"+p");	
		// generate code
		assertEquals(cg.asText("\t"),"package fr.cnrs.iees.omhtk.codeGeneration;\n"
				+ "\n"
				+ "import fr.cnrs.iees.omhtk.codeGeneration.Bidon;\n"
				+ "\n"
				+ "// this is a supid comment\n"
				+ "public class Amphore extends Bidon {\n"
				+ "\n"
				+ "	private int p = 2;\n"
				+ "\n"
				+ "	@Override\n"
				+ "	public void setS(String v0) {\n"
				+ "		this.s = s+\"_\"+p;\n"
				+ "	}\n"
				+ "\n"
				+ "	@Override\n"
				+ "	public void setN(int v0) {\n"
				+ "		this.n = n+p;\n"
				+ "	}\n"
				+ "\n"
				+ "}\n"
				+ "");
//		System.out.println(cg.asText("\t"));
	}

	/**
	 * Test generating code for a class with a generic-type superclass (ie LinkedList\<Bidon\> in this case)
	 */
	@Test
	void testClassGeneratorWithGenericSuperClass() {
		// constructor from superclass
		Set<String> meths = new HashSet<>();
		meths.add("add");
		meths.add("get");
		List<String> pars = new ArrayList<>();
		ClassGenerator cg;
		try {
			cg = new ClassGenerator(Bidon.class.getPackageName(),
				"// this is a supid comment",
				"BidonList",
				meths,
				LinkedList.class.getName(),
				pars);
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		// throws IllegalArgumentException
		pars.add("Bidon");
		cg = new ClassGenerator(Bidon.class.getPackageName(),
			"// this is a supid comment",
			"BidonList",
			meths,
			LinkedList.class.getName(),
			pars);
		assertNotNull(cg);
		// set field
		cg.setField("list", "List<Bidon>", "new LinkedList<>()");
		assertEquals(cg.nfields(),1);
		// override methods
		MethodGenerator mg = cg.getMethod("add");
		mg.setArgumentName(0, "bid");
		mg.setArgumentType(0, "Bidon");
		mg.setReturnStatement("return this");
		assertNotNull(mg);
		mg.setStatement("list.add(bid)");
		mg = cg.getMethod("get");
		assertNotNull(mg);
		mg.setArgumentName(0, "i");
		mg.setReturnType("Bidon");
		mg.setReturnStatement("return list.get(i)");	
		// generate code
		assertEquals(cg.asText("\t"),"package fr.cnrs.iees.omhtk.codeGeneration;\n"
				+ "\n"
				+ "import java.util.LinkedList;\n"
				+ "\n"
				+ "// this is a supid comment\n"
				+ "public class BidonList extends LinkedList<Bidon> {\n"
				+ "\n"
				+ "	private List<Bidon> list = new LinkedList<>();\n"
				+ "\n"
				+ "	@Override\n"
				+ "	public boolean add(Bidon bid) {\n"
				+ "		list.add(bid);\n"
				+ "		return this;\n"
				+ "	}\n"
				+ "\n"
				+ "	@Override\n"
				+ "	public Bidon get(int i) {\n"
				+ "		return list.get(i);\n"
				+ "	}\n"
				+ "\n"
				+ "}\n"
				+ "");
//		System.out.println(cg.asText("\t"));
	}

	
	@Test
	void testSetConstructor() {
		fail("Not yet implemented");
	}

	@Test
	void testGetConstructor() {
		fail("Not yet implemented");
	}

	@Test
	void testSetRawMethodCode() {
		fail("Not yet implemented");
	}

	@Test
	void testGetField() {
		fail("Not yet implemented");
	}

	@Test
	void testAddInterfaceGenericParameter() {
		fail("Not yet implemented");
	}

	@Test
	void testRecordAncestorInterfaceMethods() {
		fail("Not yet implemented");
	}

	@Test
	void testRecordSuperClassMethods() {
		fail("Not yet implemented");
	}

	@Test
	void testName() {
		fail("Not yet implemented");
	}

	@Test
	void testPackageName() {
		fail("Not yet implemented");
	}

	@Test
	void testNInterfaces() {
		fail("Not yet implemented");
	}

	@Test
	void testSetImport() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMethods() {
		fail("Not yet implemented");
	}

	@Test
	void testGetInterfaceParameters() {
		fail("Not yet implemented");
	}


}
