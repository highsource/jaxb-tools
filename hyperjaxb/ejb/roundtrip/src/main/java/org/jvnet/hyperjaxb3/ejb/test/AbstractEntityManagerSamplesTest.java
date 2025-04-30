package org.jvnet.hyperjaxb3.ejb.test;

import java.io.File;
import java.io.FilenameFilter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.SystemUtils;
import org.jvnet.jaxb2_commons.test.AbstractSamplesTest;
import org.jvnet.jaxb2_commons.xml.bind.ContextPathAware;

public abstract class AbstractEntityManagerSamplesTest extends
		AbstractEntityManagerTest implements ContextPathAware {

	private AbstractSamplesTest samplesTest;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		samplesTest = createSamplesTest();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	protected AbstractSamplesTest createSamplesTest() {
		return new AbstractSamplesTest() {

			@Override
			protected void checkSample(File sample) throws Exception {
				AbstractEntityManagerSamplesTest.this.checkSample(sample);
			}

			@Override
			protected String getContextPath() {
				return AbstractEntityManagerSamplesTest.this.getContextPath();
			}

			@Override
			protected Class<? extends Object> getTestClass() {
				return AbstractEntityManagerSamplesTest.this.getClass();
			}

			@Override
			@SuppressWarnings("unchecked")
			protected File[] getSampleFiles() {
				return AbstractEntityManagerSamplesTest.this.getSampleFiles();
			}

			@Override
			protected File getSamplesDirectory() {
				return AbstractEntityManagerSamplesTest.this
						.getSamplesDirectory();
			}

			@Override
			protected ClassLoader getContextClassLoader() {
				return AbstractEntityManagerSamplesTest.this
						.getContextClassLoader();
			}

		};
	}

	public void testSamples() throws Exception {
		samplesTest.testSamples();
	}

	protected JAXBContext createContext() throws JAXBException {
		return samplesTest.createContext();
	}

	@Override
	public String getPersistenceUnitName() {
		return getContextPath();
	}

	protected abstract void checkSample(File sample) throws Exception;

	public String getContextPath() {
		return getClass().getPackage().getName();
	}

	protected File getBaseDir() {
		try {
			return (new File(getClass().getProtectionDomain().getCodeSource()
					.getLocation().getFile())).getParentFile().getParentFile()
					.getAbsoluteFile();
		} catch (Exception ex) {
			throw new AssertionError(ex);
		}
	}

	protected File getSamplesDirectory() {
		return new File(getBaseDir(), getSamplesDirectoryName());
	}

	protected File[] getSampleFiles() {
		File samplesDirectory = getSamplesDirectory();
        if (samplesDirectory == null) {
            return new File[] {};
        }
        logger.debug("Sample directory [" + samplesDirectory.getAbsolutePath() + "].");
		if (!samplesDirectory.isDirectory()) {
			return new File[] {};
		} else {
			return samplesDirectory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name != null && name.endsWith(".xml") && !name.endsWith("1.5.xml");
                }
            });
		}
	}

	public static final String DEFAULT_SAMPLES_DIRECTORY_NAME = "src/test/samples";

	protected String getSamplesDirectoryName() {
		return DEFAULT_SAMPLES_DIRECTORY_NAME;
	}

	protected ClassLoader getContextClassLoader() {
		return getClass().getClassLoader();
	}
}
