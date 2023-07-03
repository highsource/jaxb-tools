package org.jvnet.jaxb2_commons.xml.bind.model.util.tests;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.jvnet.jaxb2_commons.xml.bind.model.util.XmlTypeUtils;
import org.jvnet.jaxb2_commons.xml.namespace.util.QNameUtils;

@RunWith(Parameterized.class)
public class XmlTypeUtilsTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays
				.asList(new Object[][] {
						//
						{
								"a1",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.alpha.A1.class },
						{
								"a2",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.alpha.A2.class },
						{
								"AThree",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.alpha.A3.class },
						{
								"AFour",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.alpha.A4.class },
						{
								"{urn:five}AFive",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.alpha.A5.class },
						{
								"a6",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.alpha.A6.class },
						{
								"{urn:seven}a7",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.alpha.A7.class },
						{
								null,
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.alpha.A8.class },
						{
								"{urn:nine}ANine",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.alpha.A9.class },
						{
								"a1",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.beta.A1.class },
						{
								"a2",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.beta.A2.class },
						{
								"AThree",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.beta.A3.class },
						{
								"AFour",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.beta.A4.class },
						{
								"{urn:five}AFive",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.beta.A5.class },
						{
								"a6",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.beta.A6.class },
						{
								"{urn:seven}a7",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.beta.A7.class },
						{
								null,
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.beta.A8.class },
						{
								"{urn:nine}ANine",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.beta.A9.class },
						{
								"{urn:gamma}a1",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.gamma.A1.class },
						{
								"{urn:gamma}a2",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.gamma.A2.class },
						{
								"{urn:gamma}AThree",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.gamma.A3.class },
						{
								"AFour",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.gamma.A4.class },
						{
								"{urn:five}AFive",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.gamma.A5.class },
						{
								"a6",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.gamma.A6.class },
						{
								"{urn:seven}a7",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.gamma.A7.class },
						{
								null,
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.gamma.A8.class },
						{
								"{urn:nine}ANine",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.gamma.A9.class },
						{
								"{urn:delta}a1",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.delta.A1.class },
						{
								"{urn:delta}a2",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.delta.A2.class },
						{
								"{urn:delta}AThree",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.delta.A3.class },
						{
								"AFour",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.delta.A4.class },
						{
								"{urn:five}five:AFive",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.delta.A5.class },
						{
								"a6",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.delta.A6.class },
						{
								"{urn:seven}a7",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.delta.A7.class },
						{
								null,
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.delta.A8.class },
						{
								"{urn:nine}ANine",
								org.jvnet.jaxb2_commons.xml.bind.model.util.tests.delta.A9.class },

				});
	}

	private final String key;

	private final Class<?> _class;

	public XmlTypeUtilsTest(String key, Class<?> _class) {
		this.key = key;
		this._class = _class;
	}

	@Test
	public void producesCorrectTypeName() {
		Assert.assertEquals(key,
				QNameUtils.getKey(XmlTypeUtils.getTypeName(_class)));

	}

}
