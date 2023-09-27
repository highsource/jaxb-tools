package org.jvnet.jaxb.tests.issues;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb.lang.JAXBToStringStrategy;
import org.jvnet.jaxb.lang.ToStringStrategy;

public class GH37Test {

	@Test
	public void considersDefaultValuesInMerge() {
		final IssueGH37Type left = new IssueGH37Type();
		final IssueGH37Type right = new IssueGH37Type();
		final IssueGH37Type result = new IssueGH37Type();
		result.mergeFrom(left, right);
		Assert.assertTrue(result.isTestBoolean());
	}

	@Test
	public void considersDefaultValuesInCopyTo() {
		final IssueGH37Type original = new IssueGH37Type();
		final IssueGH37Type copy = (IssueGH37Type) original.clone();
		Assert.assertTrue(copy.isTestBoolean());
		Assert.assertFalse(copy.isSetTestBoolean());
		Assert.assertEquals(original, copy);
	}

	@Test
	public void considersDefaultValuesInToString() {
		final ToStringStrategy strategy = new JAXBToStringStrategy() {
			@Override
			public boolean isUseIdentityHashCode() {
				return false;
			}
		};
		IssueGH37Type a = new IssueGH37Type();
		Assert.assertEquals(
				"org.jvnet.jaxb.tests.issues.IssueGH37Type[testBoolean=true(default)]",
				a.append(null, new StringBuilder(), strategy).toString());
		IssueGH37Type b = new IssueGH37Type();
		b.setTestBoolean(true);
		Assert.assertEquals(
				"org.jvnet.jaxb.tests.issues.IssueGH37Type[testBoolean=true]",
				b.append(null, new StringBuilder(), strategy).toString());
	}

}
