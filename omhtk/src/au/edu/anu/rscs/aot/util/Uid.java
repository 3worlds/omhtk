/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2021: Shayne R. Flint, Jacques Gignoux & Ian D. Davies      *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent class hierarchies, plus some other utilities. The kind of  *
 *  things you need in all software projects and keep rebuilding all the  * 
 *  time.                                                                 *
 *                                                                        *
 **************************************************************************                                       
 *  This file is part of OMHTK (One More Handy Tool Kit).                 *
 *                                                                        *
 *  OMHTK is free software: you can redistribute it and/or modify         *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  OMHTK is distributed in the hope that it will be useful,              *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *                         
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/
package au.edu.anu.rscs.aot.util;

import java.io.File;
import java.io.Serializable;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import au.edu.anu.rscs.aot.OmhtkException;
import fr.ens.biologie.generic.Textable;

/**
 * Guaranteed universally unique identifers. The identifier is constructed from:
 * <ol>
 * <li>the host computer mac address - these are worldwide unique;</li>
 * <li>the current time in milliseconds (i.e. at the time of constructor call) - very likely
 * to be different unless successive calls to the constructor are done during the same millisecond
 * on the same computer;</li>
 * <li>a count (as a {@code short} integer) in case constructor calls are done during the same millisecond.</li>
 * </ol>
 * <p>When exported as a String, a Uid instance looks like:<br/>
 * D89EF3043496-00000167D04FC89F-0002<br/>
 * where the first part represents the mac address, the second the time stamp, and the third the count.
 * </p>
 * <p>Not very user friendly, but guaranteed unique.</p>
 * 
 * @author Shayne Flint - long before 2012
 */
// Test history:
// Fully tested by JG 23/10/2018 on version 0.0.1
public class Uid implements Serializable, Comparable<Uid>, Textable {

	private static final long serialVersionUID = 1L;
	static byte[] macAddress; // this is causing trouble because the bloody java bytes are signed!
	static short count;
	static long lastUidTime;
	public static int UID_LENGTH = 6 + 2 + 8; // was 16

	private byte[] mac = new byte[6];
	private long time;
	private short cnt;

	static {
		try {
//			lastUidTime = new DateTime().getTime();
			lastUidTime = System.currentTimeMillis();
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface network = networkInterfaces.nextElement();
				// Take the first non-null address because the last may be null
				byte[] tmp = network.getHardwareAddress();
				if (tmp != null)
					macAddress = tmp;
				if (macAddress != null)
					break;
			}
			if (macAddress == null)
				// throw new AotException("Unable to get a MAC address for UIDs.");
				macAddress = new byte[6];
			count = 0;
		} catch (SocketException e) {
			throw new OmhtkException("Unable to get a MAC address for UIDs.", e);
		}
	}

	/**
	 * Default constructor to use in the general case
	 */
	public Uid() {
		newUid();
	}

	/**
	 * Constructor to use when rebuilding a UID instance from a message. Do NOT
	 * use in any other circumstance as you will lose the guarantee of uniqueness.
	 *  
	 * @param macAddr the mac address read from the message
	 * @param timeStamp the time stamp read from the message
	 * @param count the count read from the message
	 */
	public Uid(byte[] macAddr, long timeStamp, short count) {
		this.mac = macAddr;
		this.time = timeStamp;
		this.cnt = count;
	}

	/**
	 * Constructor to use when rebuilding a UID instance from a text file. Do NOT
	 * use in any other circumstance as you will lose the guarantee of uniqueness.
	 * <p>Some Strings have a special meaning:</p>
	 * <ul>
	 * <li>"randomUid" will generate a new UID through the default constructor</li>
	 * <li>"nullUid" or "null" or a {@code null} String will generate a null UID 
	 * (000000000000-0000000000000000-0000)</li>
	 * </ul>
	 * <p>An invalid UID String will raise an Exception. Valid UID Strings must contain
	 * 12 hexadecimal digits (the mac address), a minus sign "-", 16 hexadecimal digits
	 * (the time stamp), a minus sign "-", and 4 hexadecimal digits (the count). </p>
	 * 
	 * @param uidStr the Uid as saved with {@link Uid#toString() toString()}.
	 */
	public Uid(String uidStr) {
		if (uidStr == null) {
			setMacAddress(0, 0, 0, 0, 0, 0);
			time = 0;
			cnt = 0;
		} else if (uidStr.equals("randomUid")) {
			newUid();
		} else if (uidStr.equals("nullUid") || uidStr.equals("null")) {
			setMacAddress(0, 0, 0, 0, 0, 0);
			time = 0;
			cnt = 0;
		} else if (uidStr.contains("-") && uidStr.length() == 34) { // as in bundle filenames
			mac = extractMac(uidStr, 0, 12);
			check(uidStr, 12, '-');
			time = extractLong(uidStr, 13, 29);
			check(uidStr, 29, '-');
			cnt = extractShort(uidStr, 30, 34);
		} else
			throw new OmhtkException("Uid: Cannot create Uid from string '" + uidStr + "'");
	}

	/**
	 * Set the mac address from its components.
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param f
	 */
	public void setMacAddress(int a, int b, int c, int d, int e, int f) {
		mac[0] = (byte) a;
		mac[1] = (byte) b;
		mac[2] = (byte) c;
		mac[3] = (byte) d;
		mac[4] = (byte) e;
		mac[5] = (byte) f;
	}

	/**
	 * Get the mac address of this UID.
	 * @return the mac address
	 */
	public byte[] getMacAddress() {
		return mac;
	}

	// This was very buggy (1) error in long size (2) problem with java not handling
	// unsigned bytes
	/**
	 * Get the mac address of this UID.
	 * @return the mac address as a {@code long}
	 */
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
		// return mac[0] << 80 + mac[1] << 64 + mac[2] << 48 + mac[3] << 32 + mac[4] <<
		// 16 + mac[5];
	}

	/**
	 * Get the time stamp of this UID.
	 * 
	 * @return the time stamp
	 */
	public long getTimeStamp() {
		return time;
	}

	/**
	 * Get the count of this UID.
	 * 
	 * @return the count
	 */
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
		//long time = new DateTime().getTime();
		long time = System.currentTimeMillis();
		if (time > lastUidTime) {
			lastUidTime = time;
			count = 0;
		} else
			count++;
		newUid(time, count);
	}

	private byte[] extractMac(String str, int start, int end) {
		byte[] result = new byte[6];
		for (int i = 0; i < 6; i++)
			result[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
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

	@Override
	public String toString() {
		return toHexString();
	}

	/**
	 * Creates a file name suffixed by this Uid. Useful to create unique file or
	 * directory names.
	 * 
	 * @param prefix
	 *            the beginning of the file name
	 * @return the file name (e.g. myPrefix0687F6B2D88A-0000014DBC0F8B3A-0000)
	 */
	public String fileName(String prefix) {
		return prefix + toHexString();
	}

	/**
	 * Creates a file with a name suffixed by this Uid. Useful to create unique file or
	 * directory names.
	 * 
	 * @param prefix
	 *            the beginning of the file name
	 * @return the file (e.g. myPrefix0687F6B2D88A-0000014DBC0F8B3A-0000)
	 */
	public File file(String prefix) {
		return new File(fileName(prefix));
	}

	@Override
	public String toShortString() {
		return toHexString();
	}

	@Override
	public String toDetailedString() {
		Date date = new Date(getTimeStamp());
		return "MAC Address: " + toString(getMacAddress()) + ", Time: " + new DateTime(date).dateTimeString()
				+ ", Count: " + getCount();
	}
	
	@Override
	public String toUniqueString() {
		return toHexString();
	}

	/**
	 * Construct the String representation of this UID. NB: used by {@code toString()},
	 * {@code toShortString()} and
	 * {@code toUniqueString()}, which all return the same result.
	 * @return
	 */
	public String toHexString() {
		String result = "";
		for (int i = 0; i < mac.length; i++)
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
			otherUid = (Uid) otherId;
		} else if (otherId instanceof String)
			otherUid = new Uid((String) otherId);
		// fixed by JG 6/10/2015 - for some reason the equals method below didnt work
		// properly on different instances of MacAddress
		// return (otherUid.getMacAddress().equals(getMacAddress()))
		return (otherUid.getMacAddressAsLong() == getMacAddressAsLong()) && (otherUid.getTimeStamp() == getTimeStamp())
				&& (otherUid.getCount() == getCount());
	}

	/**
	 * Creates a null UID (000000000000-0000000000000000-0000).
	 * 
	 * @return
	 */
	public static Uid nullUid() {
		return new Uid("nullUid");
	}

	/**
	 * Checks if a UID is null (= 000000000000-0000000000000000-0000).
	 * 
	 * @return {@code true} if UID is null.
	 */
	public boolean isNull() {
		for (int i = 0; i < 6; i++)
			if (mac[i] != 0)
				return false;
		return time == 0 && cnt == 0;
	}

	@Override
	public int compareTo(Uid o) {
		Uid uid1 = this;
		Uid uid2 = o;

		long mac1 = uid1.getMacAddressAsLong();
		long mac2 = uid2.getMacAddressAsLong();
		long ts1 = uid1.getTimeStamp();
		long ts2 = uid2.getTimeStamp();
		short count1 = uid1.getCount();
		short count2 = uid2.getCount();

		if (mac1 == mac2 && ts1 == ts2 && count1 == count2)
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
