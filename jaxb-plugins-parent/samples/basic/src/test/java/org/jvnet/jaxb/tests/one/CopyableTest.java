package org.jvnet.jaxb.tests.one;

import java.io.File;

import org.junit.Assert;

import org.jvnet.jaxb.lang.JAXBCopyStrategy;
import org.jvnet.jaxb.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb.test.AbstractSamplesTest;

public class CopyableTest extends AbstractSamplesTest {

	@Override
	protected void checkSample(File sample) throws Exception {

		final Object object = createContext().createUnmarshaller().unmarshal(
				sample);
		final Object copy = JAXBCopyStrategy.INSTANCE.copy(null, object);
		Assert.assertTrue("Source and copy must be equal.", JAXBEqualsStrategy.INSTANCE.equals(null, null, object, copy));
	}

}
