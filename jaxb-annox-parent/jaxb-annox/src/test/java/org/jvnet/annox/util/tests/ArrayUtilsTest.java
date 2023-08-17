package org.jvnet.annox.util.tests;

import java.util.Arrays;

import org.junit.Assert;
import junit.framework.TestCase;

import org.jvnet.annox.util.ArrayUtils;

public class ArrayUtilsTest extends TestCase {

	public void testToObjectArray() throws Exception {

		final int[] ein = null;
		final Integer[] one = ArrayUtils.asObjectArray(ein);
		final int[] zwei = new int[] {};
		final Integer[] two = ArrayUtils.asObjectArray(zwei);
		final int[] drei = new int[] { 1, 2 };
		final Integer[] three = ArrayUtils.asObjectArray(drei);

		final int[] un = ArrayUtils.asPrimitiveArray(one);
		final int[] deux = ArrayUtils.asPrimitiveArray(two);
		final int[] troi = ArrayUtils.asPrimitiveArray(three);

		Assert.assertTrue("Wrong value.", Arrays.equals(ein, un));
		Assert.assertTrue("Wrong value.", Arrays.equals(zwei, deux));
		Assert.assertTrue("Wrong value.", Arrays.equals(drei, troi));
	}
}
