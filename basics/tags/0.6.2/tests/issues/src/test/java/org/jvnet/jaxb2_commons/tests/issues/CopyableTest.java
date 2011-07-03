package org.jvnet.jaxb2_commons.tests.issues;

import java.io.File;

import junit.framework.Assert;

import org.jvnet.jaxb2_commons.lang.CopyStrategy;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.ExtendedJAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBCopyStrategy;
import org.jvnet.jaxb2_commons.test.AbstractSamplesTest;

public class CopyableTest extends AbstractSamplesTest {

	@Override
	protected void checkSample(File sample) throws Exception {

		final Object original = createContext().createUnmarshaller().unmarshal(
				sample);
		final CopyStrategy copyStrategy = new JAXBCopyStrategy();
		final Object copy = copyStrategy.copy(null, original);
		final EqualsStrategy equalsStrategy = new ExtendedJAXBEqualsStrategy();
		Assert.assertTrue("Source and copy must be equal.", equalsStrategy
				.equals(null, null, original, copy));
	}

}
