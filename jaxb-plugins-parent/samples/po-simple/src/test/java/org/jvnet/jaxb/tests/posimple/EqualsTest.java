package org.jvnet.jaxb.tests.posimple;

import java.io.File;

import jakarta.xml.bind.JAXBElement;

import org.junit.jupiter.api.Assertions;

import org.jvnet.jaxb.test.AbstractSamplesTest;

public class EqualsTest extends AbstractSamplesTest {

	@Override
	protected void checkSample(File sample) throws Exception {

		final JAXBElement<?> lhs = (JAXBElement<?>) createContext()
				.createUnmarshaller().unmarshal(sample);
		final JAXBElement<?> rhs = (JAXBElement<?>) createContext()
				.createUnmarshaller().unmarshal(sample);
		Assertions.assertTrue(lhs.getValue().equals(rhs.getValue()), "Values must be equal.");
	}
}
