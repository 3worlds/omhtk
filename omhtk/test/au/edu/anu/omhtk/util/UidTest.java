/**
 * 
 */
package au.edu.anu.omhtk.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author gignoux
 *
 */
class UidTest {
	
	private Uid uid, uid1, uid2;
	
	@BeforeEach
	void init() {
		uid = new Uid();
		uid1 = new Uid("0687F6B2D88A-0000014DBC0F8B3A-0000");
		uid2 = new Uid(null);
	}
	
	private void show(String method,String text) {
//		System.out.println(method+": "+text);
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#Uid()}.
	 */
	@Test
	@DisplayName("Uid instantiation from scratch")
	void testUid() {
		show("testUid",uid.toString());
		assertNotNull(uid);
		assertFalse(uid.toShortString().equals("000000000000-0000000000000000-0000"));
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#Uid(byte[], long, short)}.
	 */
	@Test
	@DisplayName("Uid instantiation from mac address, time and count")
	void testUidByteArrayLongShort() {
		show("testUidByteArrayLongShort",uid.toString());
		uid2 = new Uid(uid.getMacAddress(),uid.getTimeStamp(),uid.getCount());
		show("testUidByteArrayLongShort",uid.toString());
		assertNotNull(uid2);
		assertEquals(uid,uid2);
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#Uid(java.lang.String)}.
	 */
	@Test
	@DisplayName("Uid instantiation from String")
	void testUidString() {
		uid = new Uid("randomUid");
		show("testUidString",uid.toString());
		assertNotNull(uid);
		
		uid = new Uid("nullUid");
		show("testUidString",uid.toString());
		assertNotNull(uid);
		
		show("testUidString",uid.toString());
		assertNotNull(uid1);

		try {
			uid = new Uid("blablabla");
			fail("Wrong String exception not raised");
		}
		catch (Exception e) {
			// test OK
		}
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#setMacAddress(int, int, int, int, int, int)}.
	 */
	@Test
	void testSetMacAddress() {
		uid1.setMacAddress(1, 2, 3, 4, 5, 6);
		show("testSetMacAddress",uid1.toString());
		assertEquals(uid1.toString(),"010203040506-0000014DBC0F8B3A-0000");
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#getMacAddress()}.
	 */
	@Test
	void testGetMacAddress() {
		uid.setMacAddress(1, 2, 3, 4, 5, 6);
		byte[] bid = uid.getMacAddress();
		byte[] bid2 = {1, 2, 3, 4, 5, 6};
		for (int i=0; i<6; i++)
			assertEquals(bid[i],bid2[i]);
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#getMacAddressAsLong()}.
	 */
	@Test
	void testGetMacAddressAsLong() {
		assertTrue(uid.getMacAddressAsLong()>0);
		assertTrue(uid2.getMacAddressAsLong()==0);
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#getTimeStamp()}.
	 */
	@Test
	void testGetTimeStamp() {
		long time = new DateTime().getTime(); 
		short c=0;
		uid = new Uid(null,time,c);
		assertEquals(uid.getTimeStamp(),time);
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#getCount()}.
	 */
	@Test
	void testGetCount() {
		long time = new DateTime().getTime(); 
		short c=765;
		uid = new Uid(null,time,c);
		assertEquals(uid.getCount(),c);
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#toString()}.
	 */
	@Test
	void testToString() {
		assertEquals(uid1.toString(),"0687F6B2D88A-0000014DBC0F8B3A-0000");
		assertEquals(uid2.toString(),"000000000000-0000000000000000-0000");
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#fileName(java.lang.String)}.
	 */
	@Test
	void testFileName() {
		String s = uid2.fileName("bidon-");
		assertEquals(s,"bidon-000000000000-0000000000000000-0000");
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#file(java.lang.String)}.
	 */
	@Test
	void testFile() {
		File f = uid.file("bidon");
		assertNotNull(f);
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#toShortString()}.
	 */
	@Test
	void testToShortString() {
//		assertEquals(uid2.toShortString(),"0");
		show("testToShortString",uid2.toShortString());
		assertEquals(uid2.toShortString(),"000000000000-0000000000000000-0000");
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#toDetailedString()}.
	 */
	@Test
	void testToLongString() {
		show("testToLongString",uid1.toDetailedString());
		show("testToLongString",uid2.toDetailedString());
		assertEquals(uid1.toDetailedString(),"MAC Address: 06:87:f6:b2:d8:8a, Time: 2015/06/04 00:54:04.858, Count: 0");
		assertEquals(uid2.toDetailedString(),"MAC Address: 00:00:00:00:00:00, Time: 1970/01/01 00:00:00.000, Count: 0");
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#toHexString()}.
	 */
	@Test
	void testToHexString() {
		assertEquals(uid1.toHexString(),"0687F6B2D88A-0000014DBC0F8B3A-0000");
		assertEquals(uid2.toHexString(),"000000000000-0000000000000000-0000");
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#equals(java.lang.Object)}.
	 */
	@Test
	void testEqualsObject() {
		// these objects are different
		assertFalse(uid.equals(uid1));
		assertFalse(uid.equals(uid2));
		assertFalse(uid2.equals(uid1));
		// this is the same object
		assertTrue(uid.equals(uid));
		// these objects are different instances but with the same content, so must
		// be considered equal
		uid2 = new Uid(uid.getMacAddress(),uid.getTimeStamp(),uid.getCount());
		assertTrue(uid.equals(uid2));
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#nullUid()}.
	 */
	@Test
	void testNullUid() {
		uid1 = Uid.nullUid();
		assertEquals(uid1,uid2);
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#isNull()}.
	 */
	@Test
	void testIsNull() {
		assertTrue(uid2.isNull());
		assertFalse(uid.isNull());
	}

	/**
	 * Test method for {@link au.edu.anu.omhtk.util.Uid#compareTo(au.edu.anu.omhtk.util.Uid)}.
	 */
	@Test
	void testCompareTo() {
		assertTrue(uid.compareTo(uid)==0);
		assertTrue(uid.compareTo(uid2)>0);
		// uid1 should be greater than uid1 because its time stamp or its count increased
		uid1 = new Uid();
		assertTrue(uid.compareTo(uid1)<0);
		assertTrue(uid1.compareTo(uid)>0);
	}

}
