package org.jvnet.jaxb2_commons.tests.one;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.jvnet.jaxb.lang.JAXBCopyStrategy;
import org.jvnet.jaxb.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb.test.AbstractSamplesTest;

public class HashCodeTest extends AbstractSamplesTest {

	@Override
	protected void checkSample(File sample) throws Exception {

		final Object lhs = createContext().createUnmarshaller().unmarshal(
				sample);
		final Object rhs = createContext().createUnmarshaller().unmarshal(
				sample);
		final Object chs = JAXBCopyStrategy.getInstance().copy(null, rhs);
		final int leftHashCode = JAXBHashCodeStrategy.getInstance().hashCode(null,
				0, lhs);
		final int rightHashCode = JAXBHashCodeStrategy.getInstance().hashCode(null,
				0, rhs);
		final int copyHashCode = JAXBHashCodeStrategy.getInstance().hashCode(null,
				0, chs);
		Assertions.assertEquals(leftHashCode, rightHashCode, "Values must be equal.");
        Assertions.assertEquals(leftHashCode, copyHashCode, "Values must be equal.");
	}
}
