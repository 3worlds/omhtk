package au.edu.anu.rscs.aot.util;

import java.io.File;
import java.io.Serializable;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import au.edu.anu.rscs.aot.OmhtkException;


/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
// Test history:
// Fully tested by JG 23/10/2018 on version 0.0.1
public class Uid implements Serializable, Comparable<Uid> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static byte[]  macAddress; // this is causing trouble because the bloody java bytes are signed!
	static short   count;
	static long    lastUidTime;
	public static int UID_LENGTH = 6+2+8; // was 16

	private byte[] mac = new byte[6];
	private long   time;
	private short  cnt;


	static {
		try {
			lastUidTime = new DateTime().getTime();
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while(networkInterfaces.hasMoreElements())
			{
				NetworkInterface network = networkInterfaces.nextElement();
				//System.out.println("network : " + network);
				macAddress = network.getHardwareAddress();
				if(macAddress != null)
					break;
			}
			if (macAddress == null)
				//				throw new AotException("Unable to get a MAC address for UIDs.");
				macAddress = new byte[6];
			count = 0;
		} catch (SocketException e){
			throw new OmhtkException("Unable to get a MAC address for UIDs.", e);
		}
	}

	public Uid() {
		newUid();
	}
	
	// CAUTION: only use this constructor to rebuild uids from messages.
	public Uid(byte[] macAddr, long timeStamp, short count) {
		this.mac = macAddr;
		this.time = timeStamp;
		this.cnt = count;		
	}

	public Uid(String uidStr) {
		if (uidStr==null) {
			setMacAddress(0,0,0,0,0,0);
			time = 0;
			cnt  = 0;
		}
		else if (uidStr.equals("randomUid")) {
			newUid();
		} else if (uidStr.equals("nullUid")||uidStr.equals("null")) {
			setMacAddress(0,0,0,0,0,0);
			time = 0;
			cnt  = 0;
		} else if (uidStr.contains("-") && uidStr.length() == 34) { // as in bundle filenames
			mac  = extractMac(uidStr, 0, 12);
			check(uidStr, 12, '-');
			time = extractLong(uidStr, 13, 29);
			check(uidStr, 29, '-');
			cnt = extractShort(uidStr, 30, 34);
		} else
			throw new OmhtkException("Uid: Cannot create Uid from string '" + uidStr + "'");
	}


	public void setMacAddress(int a, int b, int c, int d, int e, int f) {
		mac[0] = (byte)a;
		mac[1] = (byte)b;
		mac[2] = (byte)c;
		mac[3] = (byte)d;
		mac[4] = (byte)e;
		mac[5] = (byte)f;
	}

	public byte[] getMacAddress() {
		return mac;
	}

	// This was very buggy (1) error in long size (2) problem with java not handling unsigned bytes
	public long getMacAddressAsLong() {
		long n = 0L;
		int pos = 0;
		n += Byte.toUnsignedLong(mac[pos++]) << 40;
		n += Byte.toUnsignedLong(mac[pos++]) << 32;
		n += Byte.toUnsignedLong(mac[pos++]) << 24;
		n += Byte.toUnsignedLong(mac[pos++]) << 16;
		n += Byte.toUnsignedLong(mac[pos++]) << 8;
		n += Byte.toUnsignedLong(mac[pos++]); // << 0;
		return n;
//		return mac[0] << 80 + mac[1] << 64 + mac[2] << 48 + mac[3] << 32 + mac[4] << 16 + mac[5]; 
	}

	public long getTimeStamp() {
		return time;
	}

	public short getCount() {
		return cnt;
	}

	private void newUid(long time, short cnt) {
		this.time = time;
		mac = macAddress;
		this.cnt = cnt;
	}

	private void newUid() {
		mac = macAddress;
		long time = new DateTime().getTime();
		if (time > lastUidTime) {
			lastUidTime = time;
			count = 0;
		} else
			count++;
		newUid(time, count);
	}


	private byte[] extractMac(String str, int start, int end) {
		byte[] result = new byte[6];
		for (int i=0; i<6; i++)
			result[i] = (byte)Integer.parseInt(str.substring(i*2, i*2+2), 16);
		return result;
	}

	private long extractLong(String str, int start, int end) {
		return Long.parseLong(str.substring(start, end), 16);
	}

	private short extractShort(String str, int start, int end) {
		return Short.parseShort(str.substring(start, end), 16);
	}

	private void check(String str, int index, char ch) {
		if (str.charAt(index) != ch)
			throw new OmhtkException("Uid: expected '" + ch + "' at position " + index + " in '" + str + "'");
	}

	static private String toString(byte[] macAddress) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < macAddress.length; i++) {
			sb.append(String.format("%02x%s", macAddress[i], (i < macAddress.length - 1) ? ":" : ""));		
		}
		return sb.toString();
	}

	public String toString() {
		return this.fileName("");
		//		String result = "";
		//
		//		// mac address
		//
		//		for (int i=0; i<5; i++)
		//			result = result + String.format("%02x:", uid.get(i));
		//		result = result + String.format("%02x-", uid.get(5));
		//
		//		// time
		//		for (int i=6; i<14; i++)
		//			result = result + String.format("%02x", uid.get(i));
		//		result = result + "-";
		//
		//		// count
		//		for (int i=14; i<16; i++)
		//			result = result + String.format("%02x", uid.get(i));		
		//
		//		return result;
	}

	/**
	 * Creates a file name suffixed by this Uid. Useful to create unique
	 * file or directory names.
	 * 
	 * @param prefix the beginning of the file name
	 * @return the file name (e.g. myPrefix0687F6B2D88A-0000014DBC0F8B3A-0000)
	 */
	public String fileName(String prefix) {
		return prefix + toHexString();
	}

	public File file(String prefix) {
		return new File(fileName(prefix));
	}

	public String toShortString() {
		return toString();
	}

	public String toLongString() {
		Date date = new Date(getTimeStamp());
		return "MAC Address: " + toString(getMacAddress()) + ", Time: " + new DateTime(date).dateTimeString() + ", Count: " + getCount();
	}

	public String toHexString() {
		String result = "";
		for (int i=0; i<6; i++)
			result = result + String.format("%02X", mac[i]);
		result = result + "-" + String.format("%016X-%04X", time, cnt);
		return result;
	}

	@Override
	public boolean equals(Object otherId) {
		Uid otherUid = null;
		if (otherId == null)
			return false;
		if (otherId instanceof Uid) {
			otherUid = (Uid)otherId;
		} else if (otherId instanceof String)
			otherUid = new Uid((String)otherId);
		// fixed by JG 6/10/2015 - for some reason the equals method below didnt work properly on different instances of MacAddress
//		return (otherUid.getMacAddress().equals(getMacAddress())) 
		return (otherUid.getMacAddressAsLong() == getMacAddressAsLong()) 
			&& (otherUid.getTimeStamp() == getTimeStamp())
			&& (otherUid.getCount() == getCount());
	}

	public static Uid nullUid() {
		return new Uid("nullUid");
	}

	public boolean isNull() {
		for (int i=0; i<6; i++)
			if (mac[i] != 0)
				return false;
		return time==0 && cnt==0;
	}

	@Override
	public int compareTo(Uid o) {
		Uid uid1 = this;
		Uid uid2 = o;

		long  mac1   = uid1.getMacAddressAsLong();
		long  mac2   = uid2.getMacAddressAsLong();
		long  ts1    = uid1.getTimeStamp();
		long  ts2    = uid2.getTimeStamp();
		short count1 = uid1.getCount();
		short count2 = uid2.getCount();

		if (mac1==mac2 && ts1==ts2 && count1==count2)
			return 0;

		if (mac1 < mac2)
			return -1;

		if (ts1 < ts2)
			return -1;

		if (count1 < count2)
			return -1;

		return 1;
	}

}
