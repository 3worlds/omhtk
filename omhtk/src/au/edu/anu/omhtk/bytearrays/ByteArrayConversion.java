/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2018: Shayne FLint, Jacques Gignoux & Ian D. Davies         *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent, plus some other utilities. The kind of things you need    *
 *  in all software projects and keep rebuilding all the time.            *
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

package au.edu.anu.omhtk.bytearrays;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Author Ian Davies
 *
 * Date Dec 11, 2018
 */
public class ByteArrayConversion {
	
	public static int[] bytesAsInts(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		IntBuffer ib = bb.asIntBuffer();
		int n = bytes.length / Integer.BYTES;
		int[] res = new int[n];
		for (int i = 0; i < res.length; i++)
			res[i] = ib.get(i);
		return res;
	}

	public static byte[] IntsAsBytes(int... values) {
		ByteBuffer buffer = ByteBuffer.allocate(values.length * Integer.BYTES);
		IntBuffer ib = buffer.asIntBuffer();
		for (int i = 0; i < values.length; i++)
			ib.put(values[i]);
		return buffer.array();
	}

}
