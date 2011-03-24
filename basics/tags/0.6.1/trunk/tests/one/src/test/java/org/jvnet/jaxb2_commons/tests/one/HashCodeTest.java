package org.jvnet.jaxb2_commons.tests.one;

import java.io.File;

import org.jvnet.jaxb2_commons.lang.JAXBCopyStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.test.AbstractSamplesTest;

public class HashCodeTest extends AbstractSamplesTest {

	@Override
	protected void checkSample(File sample) throws Exception {

		final Object lhs = createContext().createUnmarshaller().unmarshal(
				sample);
		final Object rhs = createContext().createUnmarshaller().unmarshal(
				sample);
		final Object chs = JAXBCopyStrategy.INSTANCE.copy(null, rhs);
		final int leftHashCode = JAXBHashCodeStrategy.INSTANCE.hashCode(null,
				0, lhs);
		final int rightHashCode = JAXBHashCodeStrategy.INSTANCE.hashCode(null,
				0, rhs);
		final int copyHashCode = JAXBHashCodeStrategy.INSTANCE.hashCode(null,
				0, chs);
		assertEquals("Values must be equal.", leftHashCode, rightHashCode);
		assertEquals("Values must be equal.", leftHashCode, copyHashCode);
	}
}
