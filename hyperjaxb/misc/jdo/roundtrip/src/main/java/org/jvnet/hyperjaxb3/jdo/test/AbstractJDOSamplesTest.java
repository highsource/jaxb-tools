package org.jvnet.hyperjaxb3.jdo.test;

import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import org.jvnet.jaxb2_commons.test.AbstractSamplesTest;
import org.jvnet.jaxb2_commons.xml.bind.ContextPathAware;

public abstract class AbstractJDOSamplesTest extends
		AbstractJDOTest implements ContextPathAware {

	private AbstractSamplesTest samplesTest;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		samplesTest = createSamplesTest();
	}

	protected AbstractSamplesTest createSamplesTest() {
		return new AbstractSamplesTest() {
			@Override
			protected void checkSample(File sample) throws Exception {
				AbstractJDOSamplesTest.this.checkSample(sample);
			}

			@Override
			protected String getContextPath() {
				return AbstractJDOSamplesTest.this.getContextPath();
			}

			@Override
			protected Class<? extends Object> getTestClass() {
				return AbstractJDOSamplesTest.this.getClass();
			}
		};
	}

	public void testSamples() throws Exception {
		samplesTest.testSamples();
	}

	protected JAXBContext createContext() throws JAXBException {
		return samplesTest.createContext();
	}

	protected abstract void checkSample(File sample) throws Exception;

	public String getContextPath() {
		return getClass().getPackage().getName();
	}

}