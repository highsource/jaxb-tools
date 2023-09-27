package org.jvnet.jaxb.tests.issues;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb.lang.EnumValue;
import org.jvnet.jaxb.lang.JAXBToStringStrategy;
import org.jvnet.jaxb.lang.ToStringStrategy;
import org.jvnet.jaxb.locator.ObjectLocator;

public class GH31Test {

	@Test
	public void considersDefaultValuesInMerge() {
		final IssueGH31ComplexType t = new IssueGH31ComplexType();

		final ToStringStrategy s = new JAXBToStringStrategy() {
			public boolean isUseIdentityHashCode() {
				return false;
			}

			public StringBuilder append(ObjectLocator locator,
					StringBuilder buffer, Object value) {

				if (value instanceof EnumValue<?>) {
					return super.append(locator, buffer,
							((EnumValue<?>) value).enumValue());
				} else {
					return super.append(locator, buffer, value);
				}
			};
		};
		Assert.assertEquals(
				"org.jvnet.jaxb.tests.issues.IssueGH31ComplexType[testEnum=Male(default)]",
				t.append(null, new StringBuilder(), s).toString());
	}
}
