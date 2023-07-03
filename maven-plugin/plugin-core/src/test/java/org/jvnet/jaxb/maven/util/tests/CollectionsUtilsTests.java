package org.jvnet.jaxb.maven.util.tests;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb.maven.util.CollectionUtils;
import org.jvnet.jaxb.maven.util.CollectionUtils.Function;

public class CollectionsUtilsTests {

	@Test
	public void correctlyCompares() {

		final Function<String, String> identity = new Function<String, String>() {
			@Override
			public String eval(String argument) {
				return argument;
			}
		};
		final Comparator<String> gt = CollectionUtils
				.<String> gtWithNullAsGreatest();
		final Comparator<String> lt = CollectionUtils
				.<String> ltWithNullAsSmallest();
		Assert.assertEquals("b", CollectionUtils.bestValue(
				Arrays.<String> asList("a", "b"), identity, gt));
		Assert.assertEquals("a", CollectionUtils.bestValue(
				Arrays.<String> asList("a", "b"), identity, lt));
		Assert.assertEquals(
				null,
				CollectionUtils.bestValue(
						Arrays.<String> asList("a", null, "b"), identity, gt));
		Assert.assertEquals(
				null,
				CollectionUtils.bestValue(
						Arrays.<String> asList("a", null, "b"), identity, lt));
	}
}
