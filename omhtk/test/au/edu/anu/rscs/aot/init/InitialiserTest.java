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

package au.edu.anu.rscs.aot.init;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import fr.cnrs.iees.omhtk.Initialisable;

/**
 * @author Ian Davies - 27 Aug 2019
 */
class InitialiserTest {

	@Test
	void test() {
		Random rnd = new Random();
		List<Initialisable> list = new ArrayList<>();
		for (int i=0;i<20;i++) 
			list.add(new DummyNode(rnd.nextInt(10)));
		Initialiser initer = new Initialiser(list);
		initer.initialise();
		int lastRank = 0;
		for (InitialiseMessage msg:initer.errorList()) {
			Initialisable i = (Initialisable) msg.getTarget();
			assertTrue(i.initRank()>=lastRank);
			lastRank = i.initRank();
			//System.out.println(i.initRank());
		}
		
	}

}
