package org.jvnet.jaxb2_commons.tests.posimple;

import java.io.File;

import jakarta.xml.bind.JAXBElement;

import org.jvnet.jaxb2_commons.test.AbstractSamplesTest;

public class EqualsTest extends AbstractSamplesTest {

	@Override
	protected void checkSample(File sample) throws Exception {

		final JAXBElement<?> lhs = (JAXBElement<?>) createContext()
				.createUnmarshaller().unmarshal(sample);
		final JAXBElement<?> rhs = (JAXBElement<?>) createContext()
				.createUnmarshaller().unmarshal(sample);
		assertTrue("Values must be equal.",
				lhs.getValue().equals(rhs.getValue()));
	}
}
