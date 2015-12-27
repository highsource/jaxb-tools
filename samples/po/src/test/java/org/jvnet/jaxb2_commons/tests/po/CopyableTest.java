package org.jvnet.jaxb2_commons.tests.po;

import java.io.File;

import org.junit.Assert;

import org.jvnet.jaxb2_commons.lang.JAXBCopyStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.test.AbstractSamplesTest;

public class CopyableTest extends AbstractSamplesTest {

	@Override
	protected void checkSample(File sample) throws Exception {

		final Object object = createContext().createUnmarshaller().unmarshal(
				sample);
		final Object copy = JAXBCopyStrategy.INSTANCE.copy(null, object);
		Assert.assertTrue("Source and copy must be equal.", JAXBEqualsStrategy.INSTANCE.equals(null, null, object, copy));
	}

}
