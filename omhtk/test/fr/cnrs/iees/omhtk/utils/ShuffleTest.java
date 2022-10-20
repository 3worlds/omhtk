package fr.cnrs.iees.omhtk.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.cnrs.iees.omhtk.utils.Shuffle;

class ShuffleTest {

	@Test
	void test() {
		int n = 10;
		int trgSum = 0;
		for (int i = 0;i<n;i++)
			trgSum+=i;
		Random r = new Random();
		Integer[] result = Shuffle.shuffleIndices(n, r);
		int sum=0;
		for (int i : result) {
			sum+=i;
		}
		// duplicates?
		for (int i = 0; i<result.length;i++) {
			for (int j = i+1;j<result.length;j++)
				Assertions.assertNotEquals(result[i], result[j]);
		}
		// something missing?
		assertEquals(sum,trgSum);
	}

}
