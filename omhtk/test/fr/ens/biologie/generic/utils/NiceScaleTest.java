package fr.ens.biologie.generic.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.cnrs.iees.omhtk.utils.NiceScale;

class NiceScaleTest {

	@Test
	void test() {
		NiceScale ns = new NiceScale(0, 1);
		assertEquals(ns.getTickSpacing(), 0.1);
		assertEquals(ns.getNiceMin(), 0.0);
		assertEquals(ns.getNiceMax(), 1.0);

		ns = new NiceScale(0, 0);
		assertEquals(ns.getTickSpacing(), 0.0);
		assertEquals(ns.getNiceMin(), Double.NaN);
		assertEquals(ns.getNiceMax(), Double.NaN);

		ns = new NiceScale(-12345.6789, 12345.6789);
		assertEquals(ns.getTickSpacing(), 5000.0);
		assertEquals(ns.getNiceMin(), -15000.0);
		assertEquals(ns.getNiceMax(), 15000.0);
		
		ns = new NiceScale(0, 12345.6789);
		assertEquals(ns.getTickSpacing(), 2000.0);
		assertEquals(ns.getNiceMin(), 0.0);
		assertEquals(ns.getNiceMax(), 14000.0);
		
		ns = new NiceScale(-12345.6789,0);
		assertEquals(ns.getTickSpacing(), 2000.0);
		assertEquals(ns.getNiceMin(), -14000);
		assertEquals(ns.getNiceMax(), 0.0);

		ns = new NiceScale(0.0, Double.MAX_VALUE);
		assertEquals(ns.getTickSpacing(), Double.POSITIVE_INFINITY);
		assertEquals(ns.getNiceMin(), Double.NaN);
		assertEquals(ns.getNiceMax(), Double.NaN);

		ns = new NiceScale(Double.MIN_VALUE, Double.MAX_VALUE * 0.5);
		assertEquals(ns.getTickSpacing(), 1.0E307);
		assertEquals(ns.getNiceMin(), 0.0);
		assertEquals(ns.getNiceMax(), 9.0E307);

		ns = new NiceScale(Double.MIN_VALUE, Double.MIN_VALUE * 2);
		assertEquals(ns.getTickSpacing(), 0.0);
		assertEquals(ns.getNiceMin(), Double.NaN);
		assertEquals(ns.getNiceMax(), Double.NaN);
		
		ns = new NiceScale(-1+Double.MIN_VALUE*10000, 1+Double.MIN_VALUE*10000);
		assertEquals(ns.getTickSpacing(), 0.2);
		assertEquals(ns.getNiceMin(), -1.0);
		assertEquals(ns.getNiceMax(), 1.0);

		ns = new NiceScale(1, 0);
		assertEquals(ns.getTickSpacing(),  Double.NaN);
		assertEquals(ns.getNiceMin(), Double.NaN);
		assertEquals(ns.getNiceMax(), Double.NaN);
		
		
//		System.out.println("Tick Spacing:\t" + ns.getTickSpacing());
//		System.out.println("Nice Minimum:\t" + ns.getNiceMin());
//		System.out.println("Nice Maximum:\t" + ns.getNiceMax());

	}

}
