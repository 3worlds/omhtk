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

package fr.ens.biologie.generic.utils;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Author Ian Davies
 *
 * Date Jan 29, 2019
 */
class UniqueStringTest {

	@Test
	void test() {
		Set<String> set = new HashSet<>();
		set.add(UniqueString.makeString("", set));
		for (int i = 0;i<10;i++) {
			if (i % 2 == 0)
				set.add(UniqueString.makeString(Integer.toString(i), set));
		}
		for (int i = 0;i<10;i++) {
			set.add(UniqueString.makeString(Integer.toString(i), set));
	}
		for (int i = 0;i<10;i++) {
			set.add(UniqueString.makeString(Integer.toString(i), set));
	}
		
		for (String s:set) {
			System.out.println(s);
		}
	}

}
