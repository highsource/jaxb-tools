package org.jvnet.jaxb.xml.bind.model.util.tests;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.jvnet.jaxb.xml.bind.model.util.XmlTypeUtils;
import org.jvnet.jaxb.xml.namespace.util.QNameUtils;

public class XmlTypeUtilsTest {

	public static Stream<Arguments> data() {
		return Stream.of(
						//
						Arguments.of(
								"a1",
								org.jvnet.jaxb.xml.bind.model.util.tests.alpha.A1.class ),
						Arguments.of("a2",
								org.jvnet.jaxb.xml.bind.model.util.tests.alpha.A2.class ),
						Arguments.of("AThree",
								org.jvnet.jaxb.xml.bind.model.util.tests.alpha.A3.class ),
						Arguments.of("AFour",
								org.jvnet.jaxb.xml.bind.model.util.tests.alpha.A4.class ),
						Arguments.of("{urn:five}AFive",
								org.jvnet.jaxb.xml.bind.model.util.tests.alpha.A5.class ),
						Arguments.of("a6",
								org.jvnet.jaxb.xml.bind.model.util.tests.alpha.A6.class ),
						Arguments.of("{urn:seven}a7",
								org.jvnet.jaxb.xml.bind.model.util.tests.alpha.A7.class ),
						Arguments.of(null,
								org.jvnet.jaxb.xml.bind.model.util.tests.alpha.A8.class ),
						Arguments.of("{urn:nine}ANine",
								org.jvnet.jaxb.xml.bind.model.util.tests.alpha.A9.class ),
						Arguments.of("a1",
								org.jvnet.jaxb.xml.bind.model.util.tests.beta.A1.class ),
						Arguments.of("a2",
								org.jvnet.jaxb.xml.bind.model.util.tests.beta.A2.class ),
						Arguments.of("AThree",
								org.jvnet.jaxb.xml.bind.model.util.tests.beta.A3.class ),
						Arguments.of("AFour",
								org.jvnet.jaxb.xml.bind.model.util.tests.beta.A4.class ),
						Arguments.of("{urn:five}AFive",
								org.jvnet.jaxb.xml.bind.model.util.tests.beta.A5.class ),
						Arguments.of("a6",
								org.jvnet.jaxb.xml.bind.model.util.tests.beta.A6.class ),
						Arguments.of("{urn:seven}a7",
								org.jvnet.jaxb.xml.bind.model.util.tests.beta.A7.class ),
						Arguments.of(null,
								org.jvnet.jaxb.xml.bind.model.util.tests.beta.A8.class ),
						Arguments.of("{urn:nine}ANine",
								org.jvnet.jaxb.xml.bind.model.util.tests.beta.A9.class ),
						Arguments.of("{urn:gamma}a1",
								org.jvnet.jaxb.xml.bind.model.util.tests.gamma.A1.class ),
						Arguments.of("{urn:gamma}a2",
								org.jvnet.jaxb.xml.bind.model.util.tests.gamma.A2.class ),
						Arguments.of("{urn:gamma}AThree",
								org.jvnet.jaxb.xml.bind.model.util.tests.gamma.A3.class ),
						Arguments.of("AFour",
								org.jvnet.jaxb.xml.bind.model.util.tests.gamma.A4.class ),
						Arguments.of("{urn:five}AFive",
								org.jvnet.jaxb.xml.bind.model.util.tests.gamma.A5.class ),
						Arguments.of("a6",
								org.jvnet.jaxb.xml.bind.model.util.tests.gamma.A6.class ),
						Arguments.of("{urn:seven}a7",
								org.jvnet.jaxb.xml.bind.model.util.tests.gamma.A7.class ),
						Arguments.of(null,
								org.jvnet.jaxb.xml.bind.model.util.tests.gamma.A8.class ),
						Arguments.of("{urn:nine}ANine",
								org.jvnet.jaxb.xml.bind.model.util.tests.gamma.A9.class ),
						Arguments.of("{urn:delta}a1",
								org.jvnet.jaxb.xml.bind.model.util.tests.delta.A1.class ),
						Arguments.of("{urn:delta}a2",
								org.jvnet.jaxb.xml.bind.model.util.tests.delta.A2.class ),
						Arguments.of("{urn:delta}AThree",
								org.jvnet.jaxb.xml.bind.model.util.tests.delta.A3.class ),
						Arguments.of("AFour",
								org.jvnet.jaxb.xml.bind.model.util.tests.delta.A4.class ),
						Arguments.of("{urn:five}five:AFive",
								org.jvnet.jaxb.xml.bind.model.util.tests.delta.A5.class ),
						Arguments.of("a6",
								org.jvnet.jaxb.xml.bind.model.util.tests.delta.A6.class ),
						Arguments.of("{urn:seven}a7",
								org.jvnet.jaxb.xml.bind.model.util.tests.delta.A7.class ),
						Arguments.of(null,
								org.jvnet.jaxb.xml.bind.model.util.tests.delta.A8.class ),
						Arguments.of("{urn:nine}ANine",
								org.jvnet.jaxb.xml.bind.model.util.tests.delta.A9.class )

				);
	}

    @ParameterizedTest
    @MethodSource("data")
	public void producesCorrectTypeName(String key, Class<?> _class) {
		Assertions.assertEquals(key,
				QNameUtils.getKey(XmlTypeUtils.getTypeName(_class)));

	}

}
