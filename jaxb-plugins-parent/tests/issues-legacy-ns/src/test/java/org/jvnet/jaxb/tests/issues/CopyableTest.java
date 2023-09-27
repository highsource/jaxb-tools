package org.jvnet.jaxb.tests.issues;

import java.io.File;

import org.junit.Assert;
import org.jvnet.jaxb.lang.CopyStrategy;
import org.jvnet.jaxb.lang.EqualsStrategy;
import org.jvnet.jaxb.lang.ExtendedJAXBEqualsStrategy;
import org.jvnet.jaxb.lang.JAXBCopyStrategy;
import org.jvnet.jaxb.test.AbstractSamplesTest;

@SuppressWarnings("deprecation")
public class CopyableTest extends AbstractSamplesTest {

	@Override
	protected void checkSample(File sample) throws Exception {

		final Object original = createContext().createUnmarshaller().unmarshal(
				sample);
		final CopyStrategy copyStrategy = new JAXBCopyStrategy();
		final Object copy = copyStrategy.copy(null, original, true);
		final EqualsStrategy equalsStrategy = new ExtendedJAXBEqualsStrategy();
		Assert.assertTrue("Source and copy must be equal.",
				equalsStrategy.equals(null, null, original, copy, true, true));
	}

}
