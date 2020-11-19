package au.edu.anu.rscs.aot.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import au.edu.anu.rscs.aot.OmhtkException;

class StringUtilsTest {

	@Test
	void test() {
		String s ="hello";
		assertEquals(StringUtils.abbreviate(s,2),"he"+StringUtils.ELLIPSIS);
		assertEquals(StringUtils.abbreviate(s,3),"hel"+StringUtils.ELLIPSIS);
		assertEquals(StringUtils.abbreviate(s,4),"hello");
		assertEquals(StringUtils.abbreviate(s,5),"hello");
		assertThrows(OmhtkException.class,()->StringUtils.abbreviate(s,-1));
		assertThrows(OmhtkException.class,()->StringUtils.abbreviate(null,2));

	}

}
